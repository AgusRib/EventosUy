<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="logica.dataTypes.*" %>
<%@ page import="logica.dataTypes.DataUsuario.TipoUsuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Consulta Edición</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="../assets/css/ConsultaEvento.css">
<link rel="stylesheet" href="../assets/css/styles.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="icon" type="image/x-icon" href="../assets/icons/Logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
	rel="stylesheet">

</head>

<body>

	<jsp:include page="../templates/header.jsp"></jsp:include>

	<!-- Columna derecha: categorías (opcional) -->

	<div class="container-fluid px-4 mt-4">
		<div class="carta-de-eventos mb-4 d-flex justify-content-center">
			<input type="search" class="search" placeholder="Buscar ediciones...">
		</div>
	</div>
	<div class="container-fluid px-4 mt-3">
		<div class="row pb-4">
			<!-- Columna derecha: categorías estilo index -->
			<div class="col-md-3 col-lg-2 col-xl-2 mt-3 mt-md-0">
				<div class="carta-categorias align-items-center h-100">
					<h3 class="categorias fw-bold">Categorías</h3>
					<div class="form-check mb-2 d-flex justify-content-center">
						<input class="form-check-input" type="checkbox"
							id="checkboxEdiciones"> <label
							class="form-check-label ms-2" for="checkboxEdiciones">Ver
							ediciones</label>
					</div>
					<li class="list-group w-100 overflow-auto text-center" multiple>
						<a href="#" value="tecnologia"
						class="list-group-item categoria-link">Tecnología</a> <a href="#"
						value="innovacion" class="list-group-item categoria-link">Innovación</a>
						<a href="#" value="deporte" class="list-group-item categoria-link">Deporte</a>
						<a href="#" value="salud" class="list-group-item categoria-link">Salud</a>
					</li>
				</div>
				<!-- ...existing code... -->
				<script>
  // Redirige según el estado del checkbox al hacer click en una categoría
  document.addEventListener('DOMContentLoaded', function() {
    const checkboxEdiciones = document.getElementById('checkboxEdiciones');
    const categoriaLinks = document.querySelectorAll('.categoria-link');
    categoriaLinks.forEach(link => {
      link.addEventListener('click', function(e) {
        e.preventDefault();
        if (checkboxEdiciones.checked) {
          window.location.href = 'ListaEdiciones.html';
        } else {
          window.location.href = 'ListarEventos.html';
        }
      });
    });
  });
</script>
			</div>
			<!-- Imagen de la edición -->
			<% DTDetalleEdicion edi = (DTDetalleEdicion) request.getAttribute("edicion");%>
			<div class="col-md-4 col-lg-3 col-xl-2 px-5">
				<img src="<%= request.getAttribute("imagenEdicion") %>"
					alt="<%= request.getAttribute("nombre") %>" class="img-fluid rounded"
					style="width: 180px; height: 180px; object-fit: cover; aspect-ratio: 1/1;">
			</div>
			<!-- Información principal de la edición -->
			<div class="col-md-5 col-lg-7 col-xl-8 mt-3 mt-md-0 info-evento">
				<h1 class="mb-3"> <%= edi.getNombre() %></h1>
				<div class="mb-2 d-flex align-items-center">
					<strong class="me-2">Organizador:</strong> <a
						href="pagesVisitante/miseventos_ConsultaUsuario_vistaExterna.html"
						class="d-flex align-items-center text-decoration-none"> <img
						<% 
						String imagenOrganizador = (String) request.getAttribute("imagenOrganizador");
						String nombre = (String) request.getAttribute("nombre");
						%>
						src="<%= imagenOrganizador %>" alt="<%= edi.getOrganizador() %>"
						class="rounded-circle me-2"
						style="width: 32px; height: 32px; object-fit: cover; border: 1px solid rgba(0, 0, 0, .06);">
						<span class="fw-bold text-dark"><%= edi.getOrganizador() %></span>
					</a>
				</div>
				<div class="mb-2 d-flex align-items-center">
					<strong class="me-2">Sigla:</strong> <span
						class="fw-bold text-dark"><%= edi.getSigla() %></span>
				</div>
				<p class="mb-2">
					<strong>Fecha inicio:</strong> <%= edi.getFechaInicio() %>
				</p>
				<p class="mb-2">
					<strong>Fecha fin:</strong> <%= edi.getFechaFin() %>
				</p>
				<p class="mb-2">
					<strong>Fecha alta:</strong> <%= edi.getFechaAlta() %>
				</p>
				<p class="mb-2">
					<strong>País:</strong> <%= edi.getPais() %>
				</p>
				<p class="mb-2">
					<strong>Ciudad:</strong> <%= edi.getCiudad() %>
				</p>
				<!-- Tipos de registros -->
				<div class="mt-4">
				
					<div class="d-flex align-items-baseline gap-2">
						<h5>Tipos de Registro</h5>
						<% 
						
						DataUsuario user = (DataUsuario) session.getAttribute("usuario"); 
						if (user != null && user.getTipo() == TipoUsuario.ORGANIZADOR) {
						%>
						<a href="#" class="btn btn-success btn-sm"
							title="Agregar tipo de registro"> <i class="bi bi-plus-lg"></i>
						</a> <% } %>
					</div>
					<div class="accordion" id="accordionTiposRegistro">
					<% 
					@SuppressWarnings("unchecked")
					Set<DTTipoRegistro> trSet = (Set<DTTipoRegistro>) request.getAttribute("tiposRegistro");
					if (trSet.isEmpty()) {
						%> 	<div class="alert alert-secondary text-center mb-0" role="alert">
						Aún no existen tipos de registro para esta edición.</div>
					<%} else { 
						int i = 0;
						for (DTTipoRegistro tipoReg : trSet) {
					%>
						<div class="accordion-item">
							<h2 class="accordion-header" id="heading<%= i %>">
								<button class="accordion-button collapsed" type="button"
									data-bs-toggle="collapse" data-bs-target="#collapse<%= i %>"
									aria-expanded="false" aria-controls="collapse">
									<%= tipoReg.getNombre() %></button>
							</h2>
							<div id="collapse<%= i %>" class="accordion-collapse collapse"
								aria-labelledby="heading<%= i %>"
								data-bs-parent="#accordionTiposRegistro">
								<div class="accordion-body registro-info">
									<p>
										<strong>Costo:</strong> $<%= tipoReg.getCosto()%>
									</p>
									<p>
										<strong>Cupo:</strong> <%= tipoReg.getCupo() %>
									</p>
									<p>
										<strong>Descripción:</strong> <%= tipoReg.getDescripcion() %>
									</p>
								</div>
							</div>
						</div> <% i++;}} %>
					</div>
				</div>
				<!-- Patrocinios -->
				<div class="mt-4">
					<div class="d-flex align-items-baseline gap-2">
						<h5>Patrocinadores</h5>
						<% 
						if (user != null && user.getTipo() == TipoUsuario.ORGANIZADOR) {
						%>
						<a href="#" class="btn btn-success btn-sm"
							title="Agregar patrocinio"> <i class="bi bi-plus-lg"></i>
						</a> <% } %>
						
					</div>
					<div class="accordion" id="accordionPatrocinadores">
					<%
					@SuppressWarnings("unchecked")
					Set<DTPatrocinio> patSet = (Set<DTPatrocinio>) request.getAttribute("patrocinios");
					if (patSet.isEmpty()) {
					%> <div class="alert alert-secondary text-center mb-0" role="alert">
						Aún no existen patrocinios para esta edición.</div>
					<%} else { 
						for (DTPatrocinio patr : patSet) {
					%>
						<div class="accordion-item">
							<h2 class="accordion-header">
								<button class="accordion-button collapsed" type="button"
									data-bs-toggle="collapse" data-bs-target="#<%= patr.getCodigo() %>"
									aria-expanded="false" aria-controls="<%= patr.getCodigo() %>">
									<%= patr.getInstitucion() %></button>
							</h2>
							<div id="<%= patr.getCodigo() %>" class="accordion-collapse collapse"
								aria-labelledby="headingPatro"
								data-bs-parent="#accordionPatrocinadores">
								<div class="accordion-body registro-info">
									<div class="d-flex align-items-center mb-2">
										<span class="mb-0"
											style="font-weight: 600; font-size: 1.2rem;"><%= patr.getInstitucion() %></span>
									</div>
									<p>
										<strong>Nivel:</strong> <%= patr.getNivelPatrocinio() %>
									</p>
									<p>
										<strong>Tipo de Registro :</strong> <%= patr.getTipoRegistroGratis() %>
									</p>
									<p>
										<strong>Aporte:</strong> $<%= patr.getMonto() %>
									</p>
									<p>
										<strong>Fecha:</strong> <%= patr.getFecha() %>
									</p>
									<p>
										<strong>Registros gratis:</strong> <%= patr.getCantRegsGratis() %>
									</p>
									<p>
										<strong>Código:</strong><span class="text-success">
											<%= patr.getCodigo() %></span>
									</p>
								</div>
							</div>
						</div> <% }} %>
					</div>
				</div>

			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

