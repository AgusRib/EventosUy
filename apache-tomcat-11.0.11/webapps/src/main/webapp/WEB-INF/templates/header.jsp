<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="logica.data_types.*" %>
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
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
	crossorigin="anonymous"></script>
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
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
	crossorigin="anonymous"></script>	
	
<% 
	DataUsuario user = (DataUsuario) session.getAttribute("usuario");
	if (user == null) { %>
	<header>
		<nav class="navbar bg-white shadow-sm" style="height: 86px;">
			<div>
				<a class="fw-bold text-dark fs-2 m-4 text-decoration-none" href="HomeServlet"><b>Eventos.uy</b></a>
			</div>
			<div class="header-auth m-3 d-flex justify-content-end">
				<a href="iniciosesion" class="text-decoration-none">
					<button type="button" class="button1 rounded-3">
						<div class="header-button">Iniciar sesión</div>
					</button>
				</a> <a href="registro" class="text-decoration-none">
					<button type="button" class="button2 rounded-3">
						<div class="header-button">Regístrarse</div>
					</button>
				</a>
			</div>
		</nav>
	</header>
	<% } else { %> 
	
	<header>
		<nav class="navbar bg-white shadow-sm">
					<div>
				<a class="fw-bold text-dark fs-2 m-4 text-decoration-none" href="HomeServlet"><b>Eventos.uy</b></a>
			</div>
			<div class="text-center align-items-center"></div>
			<div class="d-flex justify-content-end align-items-center">
				<div class="dropdown">
					<a class="d-flex align-items-center text-decoration-none gap-2 m-3"
						href="#" id="userMenuDropdown" data-bs-toggle="dropdown"
						aria-expanded="false" aria-haspopup="true"> <img
						src="uploads/usuarios/<%= session.getAttribute("pfp") %>" alt="JA"
						class="rounded-circle"
						style="width: 38px; height: 38px; object-fit: cover; border: 1px solid rgba(0, 0, 0, .06);">
						<span><%= user.getNombre() %></span> <i class="bi bi-chevron-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-menu-end shadow-sm"
						aria-labelledby="userMenuDropdown">
						<li><a class="dropdown-item" href="perfil">Mi
								perfil</a></li>
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
