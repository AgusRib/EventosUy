<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="logica.dataTypes.DTRegistro" %>

<%
  String edicion = (String) request.getAttribute("edicion");
  @SuppressWarnings("unchecked")
  List<Map.Entry<String, DTRegistro>> registros =
      (List<Map.Entry<String, DTRegistro>>) request.getAttribute("registros");
  String mensaje = (String) request.getAttribute("mensaje");
  if (registros == null) registros = java.util.Collections.emptyList();
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Listar registros</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/RolVisitante_listarUsuarios.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/assets/icons/Logo.png">
</head>
<body>

<header>
  <nav class="navbar bg-white shadow-sm">
    <div class="text-center align-items-center">
      <a class="fw-bold text-dark fs-2 m-4 text-decoration-none" href="<%= request.getContextPath() %>/">
        <b>Eventos.uy</b>
      </a>
    </div>
    <div class="d-flex justify-content-end align-items-center">
      <div class="dropdown">
        <a class="d-flex align-items-center text-decoration-none gap-2 m-3" href="#" id="userMenuDropdown"
           data-bs-toggle="dropdown" aria-expanded="false" aria-haspopup="true">
          <img src="<%= request.getContextPath() %>/assets/images/IMG-US04.jpeg" alt="user"
               class="rounded-circle"
               style="width: 38px; height: 38px; object-fit: cover; border: 1px solid rgba(0,0,0,.06);">
          <span>MisEventos</span>
          <i class="bi bi-chevron-down"></i>
        </a>
        <ul class="dropdown-menu dropdown-menu-end shadow-sm" aria-labelledby="userMenuDropdown">
          <li><a class="dropdown-item" href="#">Mi perfil</a></li>
          <li><hr class="dropdown-divider"></li>
          <li><a class="dropdown-item text-danger" href="<%= request.getContextPath() %>/" style="color:#dc3545!important;">Cerrar sesión</a></li>
        </ul>
      </div>
    </div>
  </nav>
</header>

<div class="container my-4">
  <h2 class="mb-4">Registros a edición: <%= (edicion != null ? edicion : "") %></h2>

	 <% if (mensaje != null && !mensaje.isBlank()) { %>
	   <div class="alert alert-info"><%= mensaje %></div>
	 <% } %>
	
	 <% if (registros.isEmpty()) { %>
	 <div class="alert alert-secondary">No hay registros para esta edición.</div>
	<% } else { %>
	  <div class="listadoUsarios">
	    <% for (Map.Entry<String, DTRegistro> e : registros) {
         String nick = e.getKey();
         DTRegistro r = e.getValue();
         String verDetalleUrl = request.getContextPath() + "/ver-registro"
           + "?edicion=" + URLEncoder.encode(edicion == null ? "" : edicion, "UTF-8")
           + "&usuario=" + URLEncoder.encode(nick == null ? "" : nick, "UTF-8");
	    %>
	      <div class="listadoUsuarios_itemUsuario" style="border-radius:1rem; padding:1rem; display:flex; align-items:center; gap:1rem; border:1px solid #ddd; margin-bottom:1rem;">
	        <div class="contenedor-foto">
	          <img class="foto-usuario"
	               src="<%= request.getContextPath() %>/assets/images/IMG-US01.jpg"
	               alt="<%= nick %>" style="height:127px; width:127px; object-fit:cover; border-radius:50%; border:1px solid #ddd;">
	        </div>
	        <div class="contenedor-NickRolUser" style="flex:1;">
	          <div class="nickname">
	            <a href="<%= verDetalleUrl %>" style="font-weight:bold;"><%= nick %></a>
	          </div>
	          <div class="rol" style="font-style:italic;">Asistente</div>
	        </div>
	        <a href="<%= verDetalleUrl %>">
	          <button type="button" style="margin-left:1rem; padding:0.5em 1em; border-radius:0.5em; border:1px solid #ccc; background:#fafafa; cursor:pointer;">Ver detalle registro</button>
	        </a>
	      </div>
	    <% } %>
	  </div>
	<% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>