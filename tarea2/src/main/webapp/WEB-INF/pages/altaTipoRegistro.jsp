<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.*" %>
<%
  String ctx = request.getContextPath();

  String nombre = (String) request.getAttribute("nombre");
  if (nombre == null) nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");

  String descripcion = (String) request.getAttribute("descripcion");
  if (descripcion == null) descripcion = request.getParameter("descripcion") == null ? "" : request.getParameter("descripcion");

  String costo = (String) request.getAttribute("costo");
  if (costo == null) costo = request.getParameter("costo") == null ? "" : request.getParameter("costo");

  String cupo = (String) request.getAttribute("cupo");
  if (cupo == null) cupo = request.getParameter("cupo") == null ? "" : request.getParameter("cupo");

  String error = (String) request.getAttribute("error");
  String mensaje = (String) request.getAttribute("mensaje");

  @SuppressWarnings("unchecked")
  Set<String> eventos = (Set<String>) request.getAttribute("eventos");
  if (eventos == null) eventos = Collections.emptySet();

  String eventoSel = (String) request.getAttribute("eventoSel");
  if (eventoSel == null) eventoSel = request.getParameter("evento");

  @SuppressWarnings("unchecked")
  Set<String> ediciones = (Set<String>) request.getAttribute("ediciones");
  if (ediciones == null) ediciones = Collections.emptySet();

  String edPreParam = request.getParameter("edicion");
  String edAttr      = (String) request.getAttribute("edicion");
  String edSel       = (edPreParam != null && !edPreParam.isBlank()) ? edPreParam : (edAttr == null ? "" : edAttr);

  String volverUrl = (edSel == null || edSel.isBlank())
      ? (ctx + "/")
      : (ctx + "/detalleEdicion?nombre=" + URLEncoder.encode(edSel, "UTF-8"));
%>
<!doctype html>
<html lang="es">
<head>
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Alta Tipo de Registro — Eventos.uy</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%= ctx %>/assets/css/ConsultaEvento.css">
<link rel="stylesheet" href="<%= ctx %>/assets/css/styles.css">
<link rel="icon" type="image/x-icon" href="<%= ctx %>/assets/icons/Logo.png">

<style>
:root { --bg:#f4f6f8; --surface:#fff; --text:#1f2937; --muted:#6b7280; --border:#e6e8eb; --accent:#6d5dfc; --accent-weak:#edeaff; --radius:8px; --shadow:0 6px 18px rgba(15,23,42,.06); }
body { background:var(--bg); color:var(--text); margin:0; }
.container-xl { max-width:980px; }
.card { border:1px solid var(--border); border-radius:var(--radius); box-shadow:var(--shadow); }
.form-label { font-weight:600; }
</style>
</head>

<body>
<jsp:include page="/WEB-INF/templates/header.jsp" />


<main class="py-4">
  <div class="container-xl">

    <!-- Header -->
    <section class="card mb-3">
      <div class="card-body d-flex align-items-center gap-3">
        <a href="<%= volverUrl %>" class="btn btn-light d-flex align-items-center justify-content-center p-2" style="width:40px;height:40px;border-radius:50%;">
          <i class='bx bx-chevron-left fs-4'></i>
        </a>
        <i class="bx bx-list-plus fs-3"></i>
        <h2 class="mb-0 fw-bold">Alta Tipo de Registro</h2>
      </div>
    </section>

    <!-- Mensajes -->
    <% if (error != null) { %><div class="alert alert-danger"><%= error %></div><% } %>
    <% if (mensaje != null) { %><div class="alert alert-success"><%= mensaje %></div><% } %>

    <!-- Selección de EVENTO (GET para encadenar ediciones) -->
    <section class="card mb-3">
      <div class="card-body">
        <form method="get" action="<%= ctx %>/alta-tipo-registro" class="row g-3">
          <div class="col-12 col-md-6">
            <label for="eventoSelect" class="form-label">Evento</label>
            <select id="eventoSelect" name="evento" class="form-select" required onchange="this.form.submit()">
              <option value="" disabled <%= (eventoSel==null || eventoSel.isBlank() ? "selected" : "") %>>Seleccioná un evento</option>
              <% for (String ev : eventos) { 
                   String sel = (ev != null && ev.equals(eventoSel)) ? "selected" : "";
              %>
                <option value="<%= ev %>" <%= sel %>><%= ev %></option>
              <% } %>
            </select>
          </div>
          <%-- Si querés mantener valores ya tipeados al cambiar evento --%>
          <input type="hidden" name="nombre" value="<%= nombre %>">
          <input type="hidden" name="descripcion" value="<%= descripcion %>">
          <input type="hidden" name="costo" value="<%= costo %>">
          <input type="hidden" name="cupo" value="<%= cupo %>">
        </form>
      </div>
    </section>

    <!-- Form final (POST) -->
    <section class="card mb-3">
      <div class="card-body">
        <form id="tipoRegistroForm" method="post" action="<%= ctx %>/alta-tipo-registro" accept-charset="UTF-8">
          
          <!-- Ediciones dependientes del evento -->
          <div class="mb-3">
            <label for="edicionSelect" class="form-label">Edición</label>
            <select id="edicionSelect" name="edicion" class="form-select" <%= (eventoSel==null || eventoSel.isBlank()) ? "disabled" : "required" %>>
              <% if (eventoSel==null || eventoSel.isBlank()) { %>
                <option value="">Elegí primero un evento</option>
              <% } else { %>
                <option value="" disabled <%= (edSel==null || edSel.isBlank() ? "selected" : "") %>>Seleccioná una edición</option>
                <% for (String ed : ediciones) {
                     String sel = (ed != null && ed.equals(edSel)) ? "selected" : "";
                %>
                  <option value="<%= ed %>" <%= sel %>><%= ed %></option>
                <% } %>
              <% } %>
            </select>
          </div>

          <div class="mb-3">
            <label for="nombreTipo" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="nombreTipo" name="nombre" value="<%= nombre %>" placeholder="Ingrese el nombre" required>
          </div>

          <div class="mb-3">
            <label for="descripcionTipo" class="form-label">Descripción</label>
            <textarea class="form-control" id="descripcionTipo" name="descripcion" rows="3" placeholder="Descripción del tipo de registro" required><%= descripcion %></textarea>
          </div>

          <div class="mb-3">
            <label for="costoTipo" class="form-label">Costo</label>
            <input type="text" class="form-control" id="costoTipo" name="costo" value="<%= costo %>" placeholder="0.00" inputmode="decimal" required>
            <div class="form-text">Podés usar coma o punto como separador decimal.</div>
          </div>

          <div class="mb-3">
            <label for="cupoTipo" class="form-label">Cupo</label>
            <input type="number" class="form-control" id="cupoTipo" name="cupo" value="<%= cupo %>" placeholder="Cantidad de registros" min="1" required>
          </div>

          <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary" <%= (eventoSel==null || eventoSel.isBlank()) ? "disabled" : "" %>>Guardar</button>
            <a href="<%= volverUrl %>" class="btn btn-secondary">Cancelar</a>
          </div>
        </form>
      </div>
    </section>

  </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
// Normaliza el costo a punto antes de enviar (el servlet igual tolera coma)
document.getElementById('tipoRegistroForm').addEventListener('submit', function(e){
  var costo = document.getElementById('costoTipo');
  if (costo && costo.value) costo.value = costo.value.replace(',', '.');
});
</script>
</body>
</html>
