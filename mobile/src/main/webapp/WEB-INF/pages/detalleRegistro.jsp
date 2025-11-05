<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="logica.data_types.DTRegistro" %>
<%
  String ctx = request.getContextPath();
  DTRegistro reg = (DTRegistro) request.getAttribute("registro");
  if (reg == null) {
%>
  <h2>No hay datos de registro para mostrar.</h2>
<%
    return;
  }

  String imgUsuario  = (String) request.getAttribute("imagenUsuario");
  String imgEdicion  = (String) request.getAttribute("imagenEdicion");
  if (imgUsuario == null || imgUsuario.isBlank()) imgUsuario = "uploads/usuarios/default.jpg";
  if (imgEdicion == null || imgEdicion.isBlank()) imgEdicion = "uploads/ediciones/default.jpg";

  String nickUsuario = (String) request.getAttribute("usuario");
  String nombreEdicion = reg.getNombreEdicion(); 
  String tipoReg = reg.getTipoRegistro().getNombre();
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Detalle de Registro - Eventos.uy</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/detalleregistro.css">
  <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/assets/icons/Logo.png">
</head>
<body>
<jsp:include page="/WEB-INF/templates/header.jsp" />


<div class="container d-flex flex-column align-items-center justify-content-center py-4">
  <div class="card shadow detalle-registro-card">
    <div class="card-body">
      <div class="d-flex flex-row align-items-center justify-content-center gap-5 mb-4">
        <img src="<%= ctx %>/<%= imgUsuario %>"
             alt="Foto Usuario" class="img-fluid rounded-circle detalle-registro-img-usuario">
        <img src="<%= ctx %>/<%= imgEdicion %>"
             alt="Foto Edición" class="img-fluid rounded detalle-registro-img-edicion">
      </div>

      <div class="info-evento text-center">
        <h1 class="mb-3">Detalle de Registro</h1>

        <div class="mb-2">
          <strong>Usuario:</strong> <%= nickUsuario %>
        </div>

        <div class="mb-2">
          <strong>Edición de Evento:</strong> <%= (nombreEdicion == null ? "" : nombreEdicion) %>
        </div>
		
		<div class="mb-2">
          <strong>Tipo de registro:</strong> <%= (nombreEdicion == null ? "" : tipoReg) %>
        </div>
		
        <div class="mb-2">
          <strong>Fecha de Registro:</strong> <%= reg.getFechaRegistro() %>
        </div>

        <div class="mb-2">
          <strong>Costo:</strong> $<%= reg.getCosto() %>
        </div>

      </div>

    </div>
  </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
