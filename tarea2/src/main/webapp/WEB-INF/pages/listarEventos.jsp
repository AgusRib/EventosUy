<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="logica.dataTypes.DataUsuario" %>

<%
    @SuppressWarnings("unchecked")
    Set<String> eventos = (Set<String>) request.getAttribute("eventos");
    @SuppressWarnings("unchecked")
    Set<Map<String, Object>> eventosInfo = (Set<Map<String, Object>>) request.getAttribute("eventosInfo");
    @SuppressWarnings("unchecked")
    Set<String> categorias = (Set<String>) request.getAttribute("categorias");
    String categoriaSeleccionada = (String) request.getAttribute("categoriaSeleccionada");
    String nombreBusqueda = (String) request.getAttribute("nombreBusqueda");
    Integer totalEventos = (Integer) request.getAttribute("totalEventos");
    Integer eventosFiltrados = (Integer) request.getAttribute("eventosFiltrados");
    
    // Obtener información del usuario de la sesión
    DataUsuario usuario = (DataUsuario) request.getSession().getAttribute("usuario");
%>

<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Buscar eventos - Eventos.uy</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/styles.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
	crossorigin="anonymous"></script>
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/icons/Logo.png">
</head>

<header>
	<nav class="navbar bg-white shadow-sm">
		<div class="text-center align-items-center">
			<a class="fw-bold text-dark fs-2 m-4 text-decoration-none"
				href="<%=request.getContextPath()%>/eventos"><b>Eventos.uy</b></a>
		</div>
		<div class="d-flex justify-content-end align-items-center">
			<div class="dropdown">
				<a class="d-flex align-items-center text-decoration-none gap-2 m-3"
					href="#" id="userMenuDropdown" data-bs-toggle="dropdown"
					aria-expanded="false" aria-haspopup="true"> 
					<img src="<%=request.getContextPath()%>/assets/images/IMG-US04.jpeg" alt="Usuario"
					class="rounded-circle"
					style="width: 38px; height: 38px; object-fit: cover; border: 1px solid rgba(0, 0, 0, .06);">
					<span><%=usuario != null ? usuario.getNombre() : "Usuario"%></span> 
					<i class="bi bi-chevron-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-menu-end shadow-sm"
					aria-labelledby="userMenuDropdown">
					<li><a class="dropdown-item" href="<%=request.getContextPath()%>/usuario?accion=perfil">Mi perfil</a></li>
					<li>
						<hr class="dropdown-divider">
					</li>
					<li><a class="dropdown-item text-danger"
						href="<%=request.getContextPath()%>/autenticator?accion=logout"
						style="color: #dc3545 !important;">Cerrar sesión</a></li>
				</ul>
			</div>
		</div>
	</nav>
</header>

<body>

	<div class="container">
		<div class="m-4">
			<h2 class="fw-bold my-3">Buscar eventos</h2>
			
			<!-- Formulario de búsqueda -->
			<jsp:include page="../templates/searchbarevento.jsp" />
			
			<div class="row row-cols-2 w-100 justify-content-center my-4 gap-5">
				<div class="align-items-center col-12 col-xl-3 my-3">
					<h3 class="categorías fw-bold text-center">Categorías</h3>
					<jsp:include page="../templates/categorias-sidebar.jsp" />
				</div>

				<div class="row container m-2 col-12 col-xl-8">

					<!-- Mostrar información de filtros -->
					<%if (eventosFiltrados != null && totalEventos != null) { %>
						<div class="col-12 mb-3">
							<p class="text-muted">
								Mostrando <%=eventosFiltrados%> de <%=totalEventos%> eventos
								<%if (categoriaSeleccionada != null && !categoriaSeleccionada.isEmpty() && !"todas".equals(categoriaSeleccionada)) { %>
									en la categoría "<%=categoriaSeleccionada%>"
								<%} %>
								<%if (nombreBusqueda != null && !nombreBusqueda.isEmpty()) { %>
									que contienen "<%=nombreBusqueda%>"
								<%} %>
							</p>
						</div>
					<%} %>

					<div class="row row-cols-1 gx-0 gy-3 col-12 mt-0 gap-3">
						<%
						if (eventosInfo == null || eventosInfo.isEmpty()) {
						%>
							<div class="col-12">
								<div class="alert alert-info text-center">
									<i class="bi bi-info-circle"></i>
									No se encontraron eventos que coincidan con los criterios de búsqueda.
								</div>
							</div>
						<%
						} else {
							for (Map<String, Object> eventoInfo : eventosInfo) {
								String nombreEvento = (String) eventoInfo.get("nombre");
								String descripcionEvento = (String) eventoInfo.get("descripcion");
								String imagenEvento = (String) eventoInfo.get("imagenEvento");
						%>
							<!-- Card de evento dinámico -->
							<div class="col m-0">
								<a href="<%=request.getContextPath()%>/detalleEvento?nombre=<%=java.net.URLEncoder.encode(nombreEvento, "UTF-8")%>"
									class="text-decoration-none text-dark">
									<div class="carta p-4">
										<div class="text-heading">
											<div class="search-text-heading"><%=nombreEvento%></div>
										</div>
										<div class="d-flex flex-row justify-content-between align-items-start w-100">
											<div class="avatar-block">
												<div class="avatar">
													<img class="shape-icon" alt="Imagen de <%=nombreEvento%>"
														src="<%=request.getContextPath()%><%=imagenEvento%>"
														onerror="this.onerror=null;this.src='<%=request.getContextPath()%>/assets/images/no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image-coming-soon-sign-simple-nature-silhouette-in-frame-isolated-illustration-vector.jpg'">
												</div>
												<div class="info gap-1">
													<%if (descripcionEvento != null && !descripcionEvento.trim().isEmpty()) { %>
														<div class="d-flex align-items-center">
															<i class="bi bi-info-circle mx-2"></i>
															<div class="description"><%=descripcionEvento.length() > 100 ? descripcionEvento.substring(0, 100) + "..." : descripcionEvento%></div>
														</div>
													<%} %>
												</div>
											</div>
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

		</div>

	</div>

	<script src="<%=request.getContextPath()%>/assets/js/main.js"></script>
</body>

</html>