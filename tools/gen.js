// Generador: lee bd_plaza_megacentimetros.sql y emite el proyecto Spring Boot.
const fs = require('fs');
const path = require('path');

const SQL = process.argv[2];
const OUT = process.argv[3];
const raw = fs.readFileSync(SQL, 'utf8');
const ddlText = raw.split('-- DATOS DE PRUEBA')[0];

// ---------- utilidades ----------
const camel = (s) => s.replace(/_([a-z0-9])/g, (_, c) => c.toUpperCase());
const pascal = (s) => { const c = camel(s); return c[0].toUpperCase() + c.slice(1); };
const kebab = (s) => s.replace(/_/g, '-');
const write = (rel, content) => {
  const f = path.join(OUT, rel);
  fs.mkdirSync(path.dirname(f), { recursive: true });
  fs.writeFileSync(f, content.replace(/\r?\n/g, '\n'), 'utf8');
};

// ---------- parseo del DDL ----------
function splitTop(body) {
  const parts = []; let depth = 0, cur = '', q = false;
  for (const ch of body) {
    if (ch === "'") q = !q;
    if (!q) { if (ch === '(') depth++; if (ch === ')') depth--; }
    if (ch === ',' && depth === 0 && !q) { parts.push(cur.trim()); cur = ''; } else cur += ch;
  }
  if (cur.trim()) parts.push(cur.trim());
  return parts;
}

const tables = [];
let moduleActual = 'almacenes';
const lines = ddlText.split('\n');
// recorre línea a línea para capturar el módulo vigente de cada CREATE TABLE
const tablePos = [];
{
  let offset = 0;
  for (const ln of lines) {
    const m = ln.match(/^-- MS-([A-Z]+)/);
    if (m) moduleActual = m[1].toLowerCase();
    const t = ln.match(/^CREATE TABLE (\w+)/);
    if (t) tablePos.push({ name: t[1], mod: moduleActual });
    offset += ln.length + 1;
  }
}
const modOverride = { traslado: 'almacenes', detalle_traslado: 'almacenes' };
const re = /CREATE TABLE (\w+) \(([\s\S]*?)\n\) ENGINE=InnoDB;/g;
let mm;
while ((mm = re.exec(ddlText))) {
  const name = mm[1];
  const items = splitTop(mm[2]);
  const t = { name, mod: modOverride[name] || tablePos.find((x) => x.name === name).mod, cols: [], pk: [], fks: {}, uniques: [] };
  for (const it of items) {
    let m;
    if ((m = it.match(/^PRIMARY KEY\s*\(([^)]*)\)/i))) { t.pk = m[1].split(',').map((s) => s.trim()); continue; }
    if ((m = it.match(/^CONSTRAINT\s+\w+\s+FOREIGN KEY\s*\((\w+)\)\s*REFERENCES\s+(\w+)\((\w+)\)/i))) { t.fks[m[1]] = { table: m[2], col: m[3] }; continue; }
    if ((m = it.match(/^CONSTRAINT\s+\w+\s+UNIQUE\s*\(([^)]*)\)/i))) { t.uniques.push(m[1].split(',').map((s) => s.trim())); continue; }
    if (/^CONSTRAINT/i.test(it)) continue; // CHECK
    m = it.match(/^(\w+)\s+(ENUM\s*\([^)]*\)|\w+(?:\([^)]*\))?)([\s\S]*)$/i);
    if (!m) throw new Error('No se pudo parsear: ' + it);
    const [, cname, ctype, rest] = m;
    const col = { name: cname, sql: ctype.toUpperCase(), notNull: /NOT NULL/i.test(rest), unique: /\bUNIQUE\b/i.test(rest), auto: /AUTO_INCREMENT/i.test(rest), def: null, enumVals: null, len: null, prec: null, scale: null };
    if (/PRIMARY KEY/i.test(rest)) t.pk = [cname];
    const dm = rest.match(/DEFAULT\s+('[^']*'|CURRENT_TIMESTAMP|TRUE|FALSE|-?[\d.]+)/i);
    if (dm) col.def = dm[1];
    const em = ctype.match(/^ENUM\s*\((.*)\)$/i);
    if (em) col.enumVals = [...em[1].matchAll(/'([^']*)'/g)].map((x) => x[1]);
    const lm = ctype.match(/^(\w+)\((\d+)(?:,\s*(\d+))?\)/);
    if (lm) { if (/DECIMAL/i.test(lm[1])) { col.prec = +lm[2]; col.scale = +lm[3]; } else col.len = +lm[2]; }
    t.cols.push(col);
  }
  for (const u of t.uniques) if (u.length === 1) t.cols.find((c) => c.name === u[0]).unique = true;
  tables.push(t);
}
const byName = Object.fromEntries(tables.map((t) => [t.name, t]));

// ---------- estrategia de baja lógica ----------
const INACTIVOS = ['INACTIVO', 'INACTIVA', 'ANULADO', 'ANULADA', 'CANCELADA', 'CERRADO', 'DESCONTINUADO', 'RETIRADO'];
const INMUTABLES = new Set(['auditoria', 'movimiento_inventario']);
for (const t of tables) {
  const est = t.cols.find((c) => c.name === 'estado' && c.enumVals);
  const inact = est && INACTIVOS.find((v) => est.enumVals.includes(v));
  if (INMUTABLES.has(t.name)) t.baja = { tipo: 'ninguna' };
  else if (inact) t.baja = { tipo: 'estado', inactivo: inact, activo: est.def.replace(/'/g, '') };
  else {
    t.baja = { tipo: 'activo' };
    t.cols.push({ name: 'activo', sql: 'TINYINT(1)', notNull: true, unique: false, auto: false, def: 'TRUE', enumVals: null, len: null, prec: null, scale: null, agregada: true });
  }
}

// ---------- tipos Java ----------
function javaType(c) {
  if (c.agregada) return 'Boolean';
  const s = c.sql;
  if (c.enumVals) return 'String';
  if (/^BIGINT/.test(s)) return 'Long';
  if (/^(INT|TINYINT)/.test(s)) return 'Integer';
  if (/^(VARCHAR|CHAR)/.test(s)) return 'String';
  if (/^DECIMAL/.test(s)) return 'BigDecimal';
  if (/^DATETIME/.test(s)) return 'LocalDateTime';
  if (/^DATE/.test(s)) return 'LocalDate';
  if (/^TIME/.test(s)) return 'LocalTime';
  if (/^BOOLEAN/.test(s)) return 'Boolean';
  throw new Error('Tipo no soportado ' + s);
}
function javaDefault(c) {
  if (c.def == null) return null;
  const t = javaType(c);
  if (/CURRENT_TIMESTAMP/i.test(c.def)) return 'LocalDateTime.now()';
  if (/^'/.test(c.def)) return JSON.stringify(c.def.slice(1, -1));
  if (/^(TRUE|FALSE)$/i.test(c.def)) return c.def.toLowerCase();
  if (t === 'BigDecimal') return `new BigDecimal("${c.def}")`;
  if (t === 'Integer') return c.def;
  if (t === 'Long') return c.def + 'L';
  return null;
}

// ---------- entidades ----------
function entityName(t) { return pascal(t.name); }
function fkField(col) { return camel(col.replace(/^id_/, '')); }
const composite = (t) => t.pk.length > 1;

function genEntity(t) {
  const E = entityName(t);
  const out = [];
  const imports = ['jakarta.persistence.*', 'jakarta.validation.constraints.*', 'java.math.BigDecimal', 'java.time.*', 'lombok.*'];
  if (t.name === 'usuario') imports.push('com.fasterxml.jackson.annotation.JsonProperty');
  out.push(`package com.megacentro.entity;\n\n${imports.map((i) => `import ${i};`).join('\n')}\n`);
  out.push(`/** Tabla ${t.name} (ms-${t.mod}). */`);
  out.push(`@Data\n@NoArgsConstructor\n@AllArgsConstructor\n@Entity\n@Table(name = "${t.name}")\npublic class ${E} {\n`);

  if (composite(t)) {
    out.push(`    @EmbeddedId\n    private ${E}Id id;\n`);
  }
  for (const c of t.cols) {
    const fk = t.fks[c.name];
    const isPk = t.pk.includes(c.name);
    if (composite(t) && isPk) continue; // se representa con @MapsId
    if (fk) {
      const target = pascal(fk.table);
      const f = fkField(c.name);
      out.push(`    @ManyToOne(fetch = FetchType.EAGER)\n    @JoinColumn(name = "${c.name}"${c.notNull ? ', nullable = false' : ''})${c.notNull ? '\n    @NotNull' : ''}\n    private ${target} ${f};\n`);
      continue;
    }
    const jt = javaType(c);
    const f = camel(c.name);
    const ann = [];
    if (isPk) {
      ann.push('@Id');
      if (c.auto) ann.push('@GeneratedValue(strategy = GenerationType.IDENTITY)');
    } else {
      if (c.notNull) ann.push(jt === 'String' ? '@NotBlank' : '@NotNull');
      if (jt === 'String' && c.len) ann.push(`@Size(max = ${c.len})`);
      if (c.enumVals) ann.push(`@Pattern(regexp = "${c.enumVals.join('|')}", message = "Valores permitidos: ${c.enumVals.join(', ')}")`);
    }
    let colAnn = `@Column(name = "${c.name}"`;
    if (c.len && jt === 'String') colAnn += `, length = ${c.len}`;
    if (c.prec) colAnn += `, precision = ${c.prec}, scale = ${c.scale}`;
    if (c.notNull && !isPk) colAnn += ', nullable = false';
    if (c.agregada) colAnn += ', columnDefinition = "TINYINT(1)"';
    colAnn += ')';
    ann.push(colAnn);
    if (t.name === 'usuario' && c.name === 'password_hash') { ann.length = 0; ann.push('@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)', '@Size(max = 255)', '@Column(name = "password_hash", nullable = false, length = 255)'); }
    const d = javaDefault(c);
    out.push(`    ${ann.join('\n    ')}\n    private ${jt} ${f}${d ? ' = ' + d : ''};\n`);
  }
  out.push('}\n');
  return out.join('\n');
}

function genEmbeddable(t) {
  const E = entityName(t);
  const fields = t.pk.map((p) => {
    const c = t.cols.find((x) => x.name === p);
    return `    @Column(name = "${p}")\n    private ${javaType(c)} ${camel(p)};\n`;
  }).join('\n');
  return `package com.megacentro.entity;\n\nimport jakarta.persistence.*;\nimport lombok.*;\n\nimport java.io.Serializable;\n\n/** Clave compuesta de ${t.name}. */\n@Data\n@NoArgsConstructor\n@AllArgsConstructor\n@Embeddable\npublic class ${E}Id implements Serializable {\n\n${fields}}\n`;
}
// campos de composición con @MapsId se agregan aquí para las tablas con PK compuesta
function genEntityComposite(t) {
  let src = genEntity(t);
  const maps = t.pk.map((p) => {
    const fk = t.fks[p];
    return `    @ManyToOne(fetch = FetchType.EAGER)\n    @MapsId("${camel(p)}")\n    @JoinColumn(name = "${p}")\n    private ${pascal(fk.table)} ${fkField(p)};\n`;
  }).join('\n');
  return src.replace(/(    @EmbeddedId\n    private \w+ id;\n)/, `$1\n${maps}`);
}

// ---------- repositorio / servicio / controlador ----------
function idType(t) {
  if (composite(t)) return entityName(t) + 'Id';
  return javaType(t.cols.find((c) => c.name === t.pk[0]));
}
function genRepo(t) {
  const E = entityName(t), ID = idType(t);
  let extra = '';
  if (t.baja.tipo === 'estado') extra = `\n    /** Registros cuyo estado no es el de baja lógica. */\n    List<${E}> findByEstadoNot(String estado);\n`;
  if (t.baja.tipo === 'activo') extra = `\n    List<${E}> findByActivoTrue();\n`;
  return `package com.megacentro.repository;\n\nimport com.megacentro.entity.*;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.stereotype.Repository;\n\nimport java.util.List;\n\n@Repository\npublic interface ${E}Repository extends JpaRepository<${E}, ${ID}> {\n${extra}}\n`;
}
function genService(t) {
  const E = entityName(t), ID = idType(t);
  return `package com.megacentro.service;\n\nimport com.megacentro.common.CrudService;\nimport com.megacentro.entity.*;\n\npublic interface ${E}Service extends CrudService<${E}, ${ID}> {\n}\n`;
}
function genServiceImpl(t) {
  const E = entityName(t), ID = idType(t);
  const L = [];
  L.push(`package com.megacentro.service.impl;\n\nimport com.megacentro.common.AbstractCrudService;\nimport com.megacentro.entity.*;\nimport com.megacentro.exception.ConflictoException;\nimport com.megacentro.repository.${E}Repository;\nimport com.megacentro.service.${E}Service;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.stereotype.Service;\n\nimport java.util.List;\n`);
  L.push(`@Service\npublic class ${E}ServiceImpl extends AbstractCrudService<${E}, ${ID}> implements ${E}Service {\n`);
  L.push(`    private final ${E}Repository repository;\n\n    public ${E}ServiceImpl(${E}Repository repository) {\n        this.repository = repository;\n    }\n`);
  L.push(`    @Override\n    protected JpaRepository<${E}, ${ID}> repo() {\n        return repository;\n    }\n`);
  L.push(`    @Override\n    protected String nombre() {\n        return "${E}";\n    }\n`);
  if (composite(t)) {
    const [a, b] = t.pk;
    const fa = fkField(a), fb = fkField(b), Ea = pascal(t.fks[a].table), Eb = pascal(t.fks[b].table);
    const ga = 'get' + pascal(a), gb = 'get' + pascal(b);
    L.push(`    @Override\n    protected void antesDeRegistrar(${E} e) {\n        if (e.get${pascal(fa)}() == null || e.get${pascal(fa)}().${ga}() == null\n                || e.get${pascal(fb)}() == null || e.get${pascal(fb)}().${gb}() == null) {\n            throw new IllegalArgumentException("Debe enviar ${fa}.${camel(a)} y ${fb}.${camel(b)}");\n        }\n        e.setId(new ${E}Id(e.get${pascal(fa)}().${ga}(), e.get${pascal(fb)}().${gb}()));\n        if (repository.existsById(e.getId())) {\n            throw new ConflictoException("Ya existe un registro de ${E} con esa clave compuesta");\n        }\n    }\n`);
    L.push(`    @Override\n    protected void asignarId(${E} e, ${ID} id) {\n        e.setId(id);\n        ${Ea} a = new ${Ea}();\n        a.set${pascal(a)}(id.get${pascal(a)}());\n        e.set${pascal(fa)}(a);\n        ${Eb} b = new ${Eb}();\n        b.set${pascal(b)}(id.get${pascal(b)}());\n        e.set${pascal(fb)}(b);\n    }\n`);
  } else {
    const p = pascal(t.pk[0]);
    if (t.name !== 'usuario') L.push(`    @Override\n    protected void antesDeRegistrar(${E} e) {\n        e.set${p}(null);\n    }\n`);
    L.push(`    @Override\n    protected void asignarId(${E} e, ${ID} id) {\n        e.set${p}(id);\n    }\n`);
  }
  if (t.name === 'usuario') {
    L.push(`    @Override\n    protected void antesDeRegistrar(${E} e) {\n        e.setIdUsuario(null);\n        if (e.getPasswordHash() == null || e.getPasswordHash().isBlank()) {\n            throw new IllegalArgumentException("passwordHash es obligatorio");\n        }\n        e.setPasswordHash(com.megacentro.common.Hashing.sha256(e.getPasswordHash()));\n    }\n`);
    L.push(`    @Override\n    protected void antesDeActualizar(${E} nuevo, ${E} existente) {\n        if (nuevo.getPasswordHash() == null || nuevo.getPasswordHash().isBlank()) {\n            nuevo.setPasswordHash(existente.getPasswordHash());\n        } else {\n            nuevo.setPasswordHash(com.megacentro.common.Hashing.sha256(nuevo.getPasswordHash()));\n        }\n    }\n`);
  }
  if (t.baja.tipo === 'estado') {
    L.push(`    @Override\n    protected boolean tieneBajaLogica() {\n        return true;\n    }\n`);
    L.push(`    @Override\n    protected void marcarInactivo(${E} e) {\n        e.setEstado("${t.baja.inactivo}");\n    }\n`);
    L.push(`    @Override\n    protected void marcarActivo(${E} e) {\n        e.setEstado("${t.baja.activo}");\n    }\n`);
    L.push(`    @Override\n    public List<${E}> listarActivos() {\n        return repository.findByEstadoNot("${t.baja.inactivo}");\n    }\n`);
  } else if (t.baja.tipo === 'activo') {
    L.push(`    @Override\n    protected boolean tieneBajaLogica() {\n        return true;\n    }\n`);
    L.push(`    @Override\n    protected void marcarInactivo(${E} e) {\n        e.setActivo(false);\n    }\n`);
    L.push(`    @Override\n    protected void marcarActivo(${E} e) {\n        e.setActivo(true);\n    }\n`);
    L.push(`    @Override\n    public List<${E}> listarActivos() {\n        return repository.findByActivoTrue();\n    }\n`);
  }
  L.push('}\n');
  return L.join('\n');
}
function genController(t) {
  const E = entityName(t), ID = idType(t), base = '/api/' + kebab(t.name);
  const comp = composite(t);
  const [a, b] = t.pk;
  const pathVars = comp ? `/{${camel(a)}}/{${camel(b)}}` : `/{id}`;
  const params = comp
    ? `@PathVariable ${javaType(t.cols.find((c) => c.name === a))} ${camel(a)},\n            @PathVariable ${javaType(t.cols.find((c) => c.name === b))} ${camel(b)}`
    : `@PathVariable ${ID} id`;
  const idExpr = comp ? `new ${E}Id(${camel(a)}, ${camel(b)})` : 'id';
  const L = [];
  L.push(`package com.megacentro.controller;\n\nimport com.megacentro.entity.*;\nimport com.megacentro.service.${E}Service;\nimport jakarta.validation.Valid;\nimport lombok.RequiredArgsConstructor;\nimport org.springframework.http.HttpStatus;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.*;\n\nimport java.util.List;\nimport java.util.Map;\n`);
  L.push(`/** CRUD de ${t.name} (ms-${t.mod}). */\n@RestController\n@RequestMapping("${base}")\n@RequiredArgsConstructor\npublic class ${E}Controller {\n\n    private final ${E}Service service;\n`);
  L.push(`    @GetMapping\n    public List<${E}> listar() {\n        return service.listar();\n    }\n`);
  if (t.baja.tipo !== 'ninguna') L.push(`    @GetMapping("/activos")\n    public List<${E}> listarActivos() {\n        return service.listarActivos();\n    }\n`);
  L.push(`    @GetMapping("${pathVars}")\n    public ${E} buscar(${params}) {\n        return service.buscar(${idExpr});\n    }\n`);
  L.push(`    @PostMapping\n    public ResponseEntity<${E}> registrar(@Valid @RequestBody ${E} body) {\n        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));\n    }\n`);
  L.push(`    @PutMapping("${pathVars}")\n    public ${E} actualizar(${params},\n            @Valid @RequestBody ${E} body) {\n        return service.actualizar(${idExpr}, body);\n    }\n`);
  if (t.baja.tipo !== 'ninguna') {
    L.push(`    /** Eliminación lógica: no borra el registro, solo cambia su estado. */\n    @DeleteMapping("${pathVars}")\n    public Map<String, String> eliminar(${params}) {\n        service.eliminar(${idExpr});\n        return Map.of("mensaje", "${E} dado de baja (eliminación lógica)");\n    }\n`);
    L.push(`    @PatchMapping("${pathVars}/habilitar")\n    public ${E} habilitar(${params}) {\n        return service.habilitar(${idExpr});\n    }\n`);
  }
  L.push('}\n');
  return L.join('\n');
}

// ---------- Postman ----------
function sampleBody(t) {
  const body = {};
  for (const c of t.cols) {
    if (t.pk.includes(c.name) && !t.fks[c.name]) continue; // PK autogenerada
    if (c.agregada) continue;
    const fk = t.fks[c.name];
    if (fk) {
      if (!c.notNull && !t.pk.includes(c.name)) { body[fkField(c.name)] = null; delete body[fkField(c.name)]; }
      if (c.notNull || t.pk.includes(c.name)) {
        const n = /_destino$/.test(c.name) ? 2 : 1;
        body[fkField(c.name)] = { [camel(fk.col)]: n };
      }
      continue;
    }
    if (!c.notNull || c.def != null) {
      continue;
    }
    const jt = javaType(c);
    let v;
    if (c.enumVals) v = c.enumVals[0];
    else if (jt === 'String') {
      const len = c.len || 50;
      if (c.name === 'dni') v = '{{rnd8}}';
      else if (c.name === 'ruc' || c.name === 'numero_documento') v = '{{rnd11}}';
      else if (c.name === 'serie') v = 'F001';
      else if (c.unique && /correo/.test(c.name)) v = 'u{{rnd}}@correo.pe';
      else if (c.unique) v = ('X-{{rnd}}').slice(0, len);
      else v = 'Texto de prueba'.slice(0, len);
    } else if (jt === 'Integer' || jt === 'Long') v = (t.uniques.some((u) => u.includes(c.name)) ? '{{rnd}}' : (c.name === 'calificacion' ? 5 : 1));
    else if (jt === 'BigDecimal') v = 10.5;
    else if (jt === 'LocalDate') v = c.name === 'fecha_fin' ? '2026-12-31' : '2026-10-13';
    else if (jt === 'LocalDateTime') v = '2026-10-13T10:00:00';
    else if (jt === 'LocalTime') v = '08:00:00';
    else if (jt === 'Boolean') v = false;
    body[camel(c.name)] = v;
  }
  // columnas opcionales que conviene mostrar
  for (const c of t.cols) {
    if (c.notNull || c.agregada || t.fks[c.name] || t.pk.includes(c.name)) continue;
    if (c.name === 'calificacion') body.calificacion = 5;
    if (c.name === 'direccion' || c.name === 'descripcion') body[camel(c.name)] = 'Dato de prueba';
  }
  if (t.name === 'usuario') body.passwordHash = 'Clave2026*';
  if (t.name === 'comprobante_pago') { body.tipoPago = 'CLIENTE'; body.pagoCliente = { idPagoCliente: 1 }; }
  if (t.name === 'puntos_cliente') { /* FK opcionales omitidas */ }
  return body;
}
function varName(t) { return 'id_' + t.name; }

function postmanItems() {
  const mods = {};
  for (const t of tables) (mods[t.mod] = mods[t.mod] || []).push(t);
  const folders = [];
  const baseUrl = '{{baseUrl}}';
  const mk = (name, method, urlPath, body, tests) => {
    const parts = urlPath.split('/').filter(Boolean);
    const item = {
      name,
      request: {
        method,
        header: body ? [{ key: 'Content-Type', value: 'application/json' }] : [],
        url: { raw: baseUrl + urlPath, host: ['{{baseUrl}}'], path: parts },
      },
    };
    if (body) item.request.body = { mode: 'raw', raw: JSON.stringify(body, null, 2), options: { raw: { language: 'json' } } };
    if (tests) item.event = [{ listen: 'test', script: { type: 'text/javascript', exec: tests } }];
    return item;
  };
  for (const [mod, ts] of Object.entries(mods)) {
    const ents = ts.map((t) => {
      const base = '/api/' + kebab(t.name);
      const comp = composite(t);
      const body = sampleBody(t);
      const idPath = comp ? `/{{${camel(t.pk[0])}}}/{{${camel(t.pk[1])}}}` : `/{{${varName(t)}}}`;
      const pkField = camel(t.pk[0]);
      const items = [];
      const regTests = [
        'pm.test("201 Created", () => pm.response.to.have.status(201));',
        ...(comp ? [] : [`pm.collectionVariables.set("${varName(t)}", pm.response.json().${pkField});`]),
      ];
      items.push(mk('Registrar (POST)', 'POST', base, body, regTests));
      items.push(mk('Buscar por id (GET)', 'GET', base + idPath, null, ['pm.test("200 OK", () => pm.response.to.have.status(200));']));
      items.push(mk('Actualizar (PUT)', 'PUT', base + idPath, body, ['pm.test("200 OK", () => pm.response.to.have.status(200));']));
      items.push(mk('Listar (GET)', 'GET', base, null, ['pm.test("200 OK", () => pm.response.to.have.status(200));']));
      if (t.baja.tipo !== 'ninguna') {
        items.push(mk('Listar activos (GET)', 'GET', base + '/activos', null, ['pm.test("200 OK", () => pm.response.to.have.status(200));']));
        items.push(mk('Eliminar lógico (DELETE)', 'DELETE', base + idPath, null, ['pm.test("200 OK", () => pm.response.to.have.status(200));']));
        items.push(mk('Habilitar (PATCH)', 'PATCH', base + idPath + '/habilitar', null, ['pm.test("200 OK", () => pm.response.to.have.status(200));']));
      }
      return { name: t.name, item: items };
    });
    folders.push({ name: 'ms-' + mod, item: ents });
  }
  return folders;
}

// ---------- SQL final (con columna activo) ----------
function genSql() {
  let sql = raw;
  for (const t of tables) {
    if (t.baja.tipo !== 'activo') continue;
    const reT = new RegExp(`(CREATE TABLE ${t.name} \\([\\s\\S]*?)(\\n\\) ENGINE=InnoDB;)`);
    sql = sql.replace(reT, (_, head, tail) => `${head},\n  activo           TINYINT(1) NOT NULL DEFAULT 1${tail}`);
  }
  const nota = `-- NOTA (proyecto Spring Boot): se agregó la columna  activo TINYINT(1) NOT NULL DEFAULT 1\n-- a las tablas sin estado de baja lógica, para poder aplicar eliminación lógica.\n-- Tablas afectadas: ${tables.filter((t) => t.baja.tipo === 'activo').map((t) => t.name).join(', ')}\n\n`;
  return nota + sql;
}

// ---------- emisión ----------
for (const t of tables) {
  write(`src/main/java/com/megacentro/entity/${entityName(t)}.java`, composite(t) ? genEntityComposite(t) : genEntity(t));
  if (composite(t)) write(`src/main/java/com/megacentro/entity/${entityName(t)}Id.java`, genEmbeddable(t));
  write(`src/main/java/com/megacentro/repository/${entityName(t)}Repository.java`, genRepo(t));
  write(`src/main/java/com/megacentro/service/${entityName(t)}Service.java`, genService(t));
  write(`src/main/java/com/megacentro/service/impl/${entityName(t)}ServiceImpl.java`, genServiceImpl(t));
  write(`src/main/java/com/megacentro/controller/${entityName(t)}Controller.java`, genController(t));
}
write('src/main/resources/db/bd_plaza_megacentimetros.sql', genSql());

const collection = {
  info: { name: 'Plaza Megacentimetros API', schema: 'https://schema.getpostman.com/json/collection/v2.1.0/collection.json' },
  variable: [{ key: 'baseUrl', value: 'http://localhost:8080' }, ...tables.filter((t) => !composite(t)).map((t) => ({ key: varName(t), value: '1' })), ...[...new Set(tables.filter(composite).flatMap((t) => t.pk))].map((p) => ({ key: camel(p), value: '1' }))],
  event: [{ listen: 'prerequest', script: { type: 'text/javascript', exec: [
    'pm.collectionVariables.set("rnd", String(Math.floor(100000 + Math.random() * 899999)));',
    'pm.collectionVariables.set("rnd8", String(Math.floor(10000000 + Math.random() * 89999999)));',
    'pm.collectionVariables.set("rnd11", String(Math.floor(10000000000 + Math.random() * 89999999999)));',
  ] } }],
  item: postmanItems(),
};
write('postman/Plaza-Megacentimetros.postman_collection.json', JSON.stringify(collection, null, 2));

console.log('tablas:', tables.length);
console.log('compuestas:', tables.filter(composite).map((t) => t.name).join(', '));
console.log('baja estado:', tables.filter((t) => t.baja.tipo === 'estado').length, '| activo:', tables.filter((t) => t.baja.tipo === 'activo').length, '| ninguna:', tables.filter((t) => t.baja.tipo === 'ninguna').length);
