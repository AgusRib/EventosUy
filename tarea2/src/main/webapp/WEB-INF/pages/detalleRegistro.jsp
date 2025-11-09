<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="webservices.*"%>
<%
  String ctx = request.getContextPath();
  DtRegistro reg = (DtRegistro) request.getAttribute("registro");
  if (reg == null) { %>
  <h2>No hay datos de registro para mostrar.</h2>
<%  return; }

  String imgUsuario  = (String) request.getAttribute("imagenUsuario");
  String imgEdicion  = (String) request.getAttribute("imagenEdicion");
  if (imgUsuario == null || imgUsuario.isBlank()) imgUsuario = "uploads/usuarios/default.jpg";
  if (imgEdicion == null || imgEdicion.isBlank()) imgEdicion = "uploads/ediciones/default.jpg";

  String nickUsuario   = (String) request.getAttribute("usuario");
  String nombreEdicion = reg.getNombreEdicion();
  String tipoReg       = reg.getTipoRegistro();
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
<title>Detalle de Registro - Eventos.uy</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/detalleregistro.css">
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/assets/icons/Logo.png">

<style>
  body { font-size: 1.05rem; } /* sube todo un poco */
  .dr-title { font-size: 2rem; font-weight: 800; letter-spacing: .2px; } /* grande en mobile */
  .dr-muted { color: #6c757d; } /* similar a .text-muted, pero controlable */

  .dr-list .list-group-item {
    padding: 1rem 1.1rem;
    font-size: 1.15rem;   /* texto grande en ítems */
    line-height: 1.25;
  }

  .badge-xl {
    font-size: 1.15rem;
    padding: .65rem 1rem;
    border-radius: .75rem;
  }

  .btn-block { width: 100%; }

  .dr-avatars img {
    width: 150px; height: 150px; object-fit: cover;
  }

  .detalle-registro-card { border-radius: 1rem; }
  .detalle-registro-card .card-body { padding: 1.25rem; }

  @media (min-width: 768px) {
    body { font-size: 1.05rem; }          /* mantenemos tamaño */
    .dr-title { font-size: 2.25rem; }     /* un toque más grande en md+ */
    .dr-avatars img { width: 170px; height: 170px; }
    .detalle-registro-card .card-body { padding: 2rem; }
  }
</style>
</head>
<body>
  <jsp:include page="/WEB-INF/templates/header.jsp" />

  <div class="container d-flex flex-column align-items-stretch justify-content-center py-4">
    <div class="card shadow detalle-registro-card mx-auto" style="max-width: 820px;">
      <div class="card-body">
        <div class="dr-avatars row g-3 align-items-center justify-content-center mb-3 mb-md-4">
          <div class="col-12 col-md-auto d-flex justify-content-center">
            <img src="<%= ctx %>/<%= imgUsuario %>" alt="Foto Usuario"
                 class="img-fluid rounded-circle shadow-sm">
          </div>
          <div class="col-12 col-md-auto d-flex justify-content-center">
            <img src="<%= ctx %>/<%= imgEdicion %>" alt="Foto Edición"
                 class="img-fluid rounded shadow-sm">
          </div>
        </div>

        <div class="info-evento text-center"
             data-usuario="<%= nickUsuario %>"
             data-edicion="<%= (nombreEdicion == null ? "" : nombreEdicion) %>">

          <h1 class="dr-title mb-3">Detalle de Registro</h1>

          <!-- Datos -->
          <ul class="list-group dr-list text-start mx-auto mb-3" style="max-width: 640px;">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="dr-muted">Usuario</span>
              <strong class="ms-3"><%= nickUsuario %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="dr-muted">Edición</span>
              <strong class="ms-3"><%= (nombreEdicion == null ? "" : nombreEdicion) %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="dr-muted">Tipo de registro</span>
              <strong class="ms-3"><%= (nombreEdicion == null ? "" : tipoReg) %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="dr-muted">Fecha de registro</span>
              <strong class="ms-3"><%= reg.getFechaRegistro() %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="dr-muted">Costo</span>
              <strong class="ms-3">$<%= reg.getCosto() %></strong>
            </li>
          </ul>

        </div>

      </div>
    </div>
  </div>


  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
  
</body>
</html>
