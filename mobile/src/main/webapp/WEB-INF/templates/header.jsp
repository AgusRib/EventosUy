<%@page import="webservices.DataUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="webservices.*" %>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="icon" type="image/x-icon" href="../assets/icons/Logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="icon" type="image/x-icon" href="assets/icons/Logo.png">
<!-- Icono de la pestaña -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
	<link rel="stylesheet" href="assets/css/styles.css">
	
	
<% 
	DataUsuario user = (DataUsuario) session.getAttribute("usuario");
	if (user == null) { %>
	<header>
		<nav class="navbar bg-white shadow-sm" style="height: 86px;">
			<div>
				<a class="fw-bold text-dark fs-2 m-4 text-decoration-none d-flex justify-content-center align-items-center" href="HomeServlet" href="HomeServlet"><b>Eventos.uy</b></a>
			</div>
		</nav>
	</header>
	<% } else { %> 
	
	<header>
		<nav class="navbar bg-white shadow-sm">
			<button class="navbar-toggler btn btn-link p-2 border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMenu" aria-controls="navbarMenu" aria-expanded="false" aria-label="Toggle navigation" style="width:48px; height:48px;">
  				<span class="navbar-toggler-icon"></span>
			</button>
			
			<div class="collapse navbar-collapse" id="navbarMenu">
			  <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
			    <li class="nav-item">
			      <a class="nav-link" href="listarEventos">Consulta Edición</a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" href="HomeServlet">Consulta Registro</a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" href="HomeServlet">Asistencia</a>
			    </li>
			  </ul>
			</div>
		
			<div>
        		<a class="fw-bold text-dark fs-2 m-4 text-decoration-none d-flex justify-content-center align-items-center" href="HomeServlet"><b>Eventos.uy</b></a>
    		</div>
			<div class="text-center align-items-center"></div>
			<div class="d-flex justify-content-end align-items-center">
				<div class="dropdown">
					<a class="d-flex align-items-center text-decoration-none gap-2 m-3"
						href="#" id="userMenuDropdown" data-bs-toggle="dropdown"
						aria-expanded="false" aria-haspopup="true"> <img
						src="<%= session.getAttribute("pfp") %>" 
						class="rounded-circle"
						style="width: 38px; height: 38px; object-fit: cover; border: 1px solid rgba(0, 0, 0, .06);">
						<span><%= user.getNombre() %></span> <i class="bi bi-chevron-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-menu-end shadow-sm"
						aria-labelledby="userMenuDropdown">
						<li><a class="dropdown-item" href="perfil">Mi perfil</a></li>
						<li>
							<hr class="dropdown-divider">
						</li>
						<li><a class="dropdown-item text-danger"
							href="cerrarsesion"
							style="color: #dc3545 !important;">Cerrar sesión</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	<script src="assets/js/main.js"></script>
	
	<%}
%>
