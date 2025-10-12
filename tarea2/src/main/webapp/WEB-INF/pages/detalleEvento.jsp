<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="logica.dataTypes.DTDetalleEvento" %>
<%@ page import="logica.enumerators.EstadoEdicion" %>
<%@ page import="logica.dataTypes.DataUsuario.TipoUsuario" %>

<%
    DTDetalleEvento evento = (DTDetalleEvento) request.getAttribute("evento");
    String imagenEvento = (String) request.getAttribute("imagenEvento");
    @SuppressWarnings("unchecked")
    Set<Map<String, Object>> ediciones = (Set<Map<String, Object>>) request.getAttribute("ediciones");
    
    // Obtener información del usuario para determinar si es organizador
    logica.dataTypes.DataUsuario usuario = (logica.dataTypes.DataUsuario) request.getSession().getAttribute("usuario");
    boolean esOrganizador = usuario != null && usuario.getTipo() == TipoUsuario.ORGANIZADOR;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Consulta Evento - Eventos.uy</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/styles.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/ConsultaEvento.css">
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/icons/Logo.png">
</head>

<body>
	<header>
		<nav class="navbar bg-white shadow-sm">
			<div class="d-flex align-items-center">
				<a class="fw-bold text-dark fs-2 ms-4 text-decoration-none"
					href="<%=request.getContextPath()%>/eventos">Eventos.uy</a>
			</div>
			<div class="d-flex justify-content-end align-items-center">
				<% if (usuario != null) { %>
					<div class="dropdown">
						<a class="d-flex align-items-center text-decoration-none gap-2 m-3"
							href="#" id="userMenuDropdown" data-bs-toggle="dropdown"
							aria-expanded="false" aria-haspopup="true"> 
							<img src="<%=request.getContextPath()%>/assets/images/IMG-US01.jpg" alt="<%=usuario.getNickname().substring(0,1).toUpperCase()%>"
								class="rounded-circle"
								style="width: 38px; height: 38px; object-fit: cover; border: 1px solid rgba(0, 0, 0, .06);">
							<span><%=usuario.getNombre()%></span> <i class="bi bi-chevron-down"></i>
						</a>
						<ul class="dropdown-menu dropdown-menu-end shadow-sm"
							aria-labelledby="userMenuDropdown">
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/usuario?accion=perfil">Mi perfil</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item text-danger"
								href="<%=request.getContextPath()%>/cerrarsesion"
								style="color: #dc3545 !important;">Cerrar sesión</a></li>
						</ul>
					</div>
				<% } else { %>
					<div class="header-auth m-3">
						<a href="<%=request.getContextPath()%>/iniciosesion" class="text-decoration-none">
							<button type="button" class="btn btn-outline-primary me-2">
								Iniciar sesión
							</button>
						</a>
						<a href="<%=request.getContextPath()%>/registro" class="text-decoration-none">
							<button type="button" class="btn btn-primary">
								Regístrarse
							</button>
						</a>
					</div>
				<% } %>
			</div>
		</nav>
	</header>

	<div class="container-fluid px-4 mt-4">
		<jsp:include page="../templates/searchbarevento.jsp" />
	</div>

	<div class="container-fluid px-4 mt-3">
		<div class="row">

			<!-- Columna derecha: categorías usando componente separado -->
			<div class="col-md-3 col-lg-2 col-xl-2 mt-3 mt-md-0">
				<jsp:include page="../templates/categorias-sidebar.jsp" />
			</div>

			<!-- Columna izquierda: imagen -->
			<div class="col-md-4 col-lg-3 col-xl-2 px-5">
				<img src="<%=imagenEvento%>" 
				     alt="Imagen del Evento <%=evento != null ? evento.getNombre() : ""%>"
				     class="img-fluid rounded imagen-evento"
				     onerror="this.onerror=null;this.src='<%=request.getContextPath()%>/assets/images/no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image-coming-soon-sign-simple-nature-silhouette-in-frame-isolated-illustration-vector.jpg'">
			</div>

			<!-- Columna central: info del evento -->
			<div class="col-md-5 col-lg-7 col-xl-8 mt-3 mt-sm-3 mt-md-0 info-evento">
				<%
				if (evento != null) {
				%>
					<h1 class="mb-3"><%=evento.getNombre()%></h1>
					<p class="mb-2">
						<strong>Sigla:</strong> <%=evento.getSigla()%>
					</p>
					<p class="mb-2">
						<strong>Fecha de creación:</strong> <%=evento.getFechaAlta() != null ? evento.getFechaAlta().format(formatter) : ""%>
					</p>
					<p class="mb-4">
						<strong>Descripción:</strong> <%=evento.getDescripcion()%>
					</p>
					<p class="mb-2">
						<strong>Categorías:</strong> 
						<%
						if (evento.getCategorias() != null && !evento.getCategorias().isEmpty()) {
							String[] categoriasArray = evento.getCategorias().toArray(new String[0]);
							for (int i = 0; i < categoriasArray.length; i++) {
								out.print(categoriasArray[i]);
								if (i < categoriasArray.length - 1) {
									out.print(", ");
								}
							}
						}
						%>
					</p>
				<%
				}
				%>
			</div>
		</div>

		<!-- Sección de ediciones del evento -->
		<div class="container-fluid px-4 mt-5">
			<div class="d-flex align-items-center mb-2">
				<h4 class="titulo-ediciones mb-0">Ediciones del Evento</h4>
				<% if (esOrganizador) { %>
					<a href="<%=request.getContextPath()%>/detalleEdicion/altaEdicion?nombreEvento=<%=evento != null ? java.net.URLEncoder.encode(evento.getNombre(), "UTF-8") : ""%>"
					   class="btn btn-success btn-sm ms-2" title="Agregar edición">
						<i class="bi bi-plus-lg"></i>
					</a>
				<% } %>
			</div>
			<div class="row g-4">
				<%
				if (ediciones == null || ediciones.isEmpty()) {
				%>
					<div class="col-12">
						<div class="alert alert-info text-center">
							<i class="bi bi-info-circle"></i>
							No hay ediciones disponibles para este evento.
						</div>
					</div>
				<%
				} else {
					for (Map<String, Object> edicion : ediciones) {
						String nombreEdicion = (String) edicion.get("nombre");
						String ciudad = (String) edicion.get("ciudad");
						String pais = (String) edicion.get("pais");
						java.time.LocalDate fechaInicio = (java.time.LocalDate) edicion.get("fechaInicio");
						java.time.LocalDate fechaFin = (java.time.LocalDate) edicion.get("fechaFin");
						String imagenEdicion = (String) edicion.get("imagenEdicion");
						EstadoEdicion estado = (EstadoEdicion) edicion.get("estado");
						Boolean esOrganizadorDeEstaEdicion = (Boolean) edicion.get("esOrganizadorDeEstaEdicion");
				%>
					<div class="col-md-6">
						<a class="text-decoration-none text-reset"
							href="<%=request.getContextPath()%>/detalleEdicion?nombre=<%=java.net.URLEncoder.encode(nombreEdicion, "UTF-8")%>">
							<div class="carta p-4 
								<% if (esOrganizadorDeEstaEdicion != null && esOrganizadorDeEstaEdicion && estado != null) { %>
									<% if (estado == EstadoEdicion.Confirmada) { %>
										bg-success bg-opacity-10 bg-gradient border-success
									<% } else if (estado == EstadoEdicion.Rechazada) { %>
										bg-danger bg-opacity-10 bg-gradient border-danger
									<% } else if (estado == EstadoEdicion.Ingresada) { %>
										bg-warning bg-opacity-10 bg-gradient border-warning
									<% } %>
								<% } else { %>
									carta-edicion
								<% } %>
							">
								<div class="text-heading">
									<div class="search-text-heading"><%=nombreEdicion%></div>
								</div>
								<div class="d-flex flex-row justify-content-between align-items-start w-100">
									<div class="avatar-block d-flex">
										<div class="avatar me-3">
											<img class="shape-icon rounded" 
											     alt="Imagen de <%=nombreEdicion%>"
											     src="<%=imagenEdicion%>" 
											     width="120"
											     onerror="this.onerror=null;this.src='<%=request.getContextPath()%>/assets/images/no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image-coming-soon-sign-simple-nature-silhouette-in-frame-isolated-illustration-vector.jpg'">
										</div>
										<div class="info gap-1">
											<div class="d-flex align-items-center mb-1">
												<i class="bi bi-geo-alt-fill mx-2"></i>
												<div class="description"><%=ciudad%>, <%=pais%></div>
											</div>
											<div class="d-flex align-items-center">
												<i class="bi bi-calendar-fill mx-2"></i>
												<div class="description">
													<%=fechaInicio != null ? fechaInicio.format(formatter) : ""%> - 
													<%=fechaFin != null ? fechaFin.format(formatter) : ""%>
												</div>
											</div>
										</div>
									</div>
									<% if (esOrganizadorDeEstaEdicion != null && esOrganizadorDeEstaEdicion && estado != null) { %>
										<div class="button1 rounded-5 p-3 
											<% if (estado == EstadoEdicion.Confirmada) { %>
												bg-success bg-gradient bg-opacity-75 text-white
											<% } else if (estado == EstadoEdicion.Rechazada) { %>
												bg-danger bg-gradient bg-opacity-75 text-white
											<% } else if (estado == EstadoEdicion.Ingresada) { %>
												bg-warning bg-gradient bg-opacity-75 text-white
											<% } %>
										">
											<div class="header-button">
												<% if (estado == EstadoEdicion.Confirmada) { %>
													<i class="bi bi-check-circle-fill me-2"></i>Aceptada
												<% } else if (estado == EstadoEdicion.Rechazada) { %>
													<i class="bi bi-x-circle-fill me-2"></i>Rechazada
												<% } else if (estado == EstadoEdicion.Ingresada) { %>
													<i class="bi bi-hourglass-split me-2"></i>Pendiente
												<% } %>
											</div>
										</div>
									<% } %>
								</div>
							</div>
						</a>
					</div>
				<%
					}
				}
				%>
			</div>
		</div>
	</div>

</body>
</html>