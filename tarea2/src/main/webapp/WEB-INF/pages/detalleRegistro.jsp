<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="logica.dataTypes.DTRegistro" %>
<%
  DTRegistro reg = (DTRegistro) request.getAttribute("registro");
  if (reg == null) {
%>
  <h2>No hay datos de registro para mostrar.</h2>
<%
    return;
  }
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Detalle de Registro - Eventos.uy</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Usá rutas con contextPath para que no fallen según la URL actual -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/DetalleRegistro.css">
  <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/assets/icons/Logo.png">
</head>
<body>
<header>
  <nav class="navbar bg-white shadow-sm">
    <div class="d-flex align-items-center">
      <a class="fw-bold text-dark fs-2 ms-4 text-decoration-none"
         href="<%= request.getContextPath() %>/">Eventos.uy</a>
    </div>
  </nav>
</header>

<div class="container d-flex flex-column align-items-center justify-content-center py-4">
  <div class="card shadow detalle-registro-card">
    <div class="card-body">
      <div class="d-flex flex-row align-items-center justify-content-center gap-5 mb-4">
        <img src="<%= request.getContextPath() %>algo.jpg"
             alt="Foto Usuario" class="img-fluid rounded-circle detalle-registro-img-usuario">
        <img src="<%= request.getContextPath() %>algo.jpg"
             alt="Foto Edición" class="img-fluid rounded detalle-registro-img-edicion">
      </div>

      <div class="info-evento text-center">
        <h1 class="mb-3">Detalle de Registro</h1>

        <div class="mb-2">
          <strong>Usuario:</strong> <%= request.getAttribute("usuario") %>
        </div>

        <div class="mb-2">
          <strong>Edición de Evento:</strong>
          <%= (reg.getClass().getMethod("getNombreEdicion") != null ? reg.getNombreEdicion() : "") %>
        </div>

        <div class="mb-2">
          <strong>Fecha de Registro:</strong> <%= reg.getFechaRegistro() %>
        </div>

        <div class="mb-2">
          <strong>Costo:</strong> $<%= reg.getCosto() %>
        </div>

        <div class="mt-4">
          <a class="btn btn-outline-secondary"
             href="<%= request.getContextPath() %>/listar-registros?edicion=<%= (reg.getClass().getMethod("getNombreEdicion") != null ? reg.getNombreEdicion() : "") %>">
            Volver al listado
          </a>
        </div>
      </div>

    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
