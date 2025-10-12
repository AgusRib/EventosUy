<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="logica.dataTypes.DataUsuario" %>
    <%@ page import="logica.dataTypes.DataUsuario.TipoUsuario" %>
    <%@ page import="logica.dataTypes.DTDetalleEdicion" %>
    <%@ page import="java.util.Set" %>
<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Buscar ediciones</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="stylesheet" href="../assets/css/styles.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body>
</head>

<body>
	<jsp:include page="../templates/header.jsp"></jsp:include>

	<div class="container">
		<div class="m-4">
		<% 
		DataUsuario user = (DataUsuario) request.getAttribute("usuario");
		Set<DTDetalleEdicion> ediciones = (Set<DTDetalleEdicion>) request.getAttribute("ediciones");
		%>
			<h2 class="fw-bold my-3">Mis ediciones</h2>
	
		
				<div class="row container m-2 col-12 col-xl-8">
					<div class="row row-cols-1 gx-0 gy-3 col-12 mt-0">
						<!-- Card Evento 1 -->
						<% for (DTDetalleEdicion edicion : ediciones) { %>
						<div class="col-12">
							<a href="ConsultaDeEdicionMaraton2024.html"
								class="text-decoration-none text-reset">
								<div class="carta p-4">
									<div class="text-heading">
										<div class="search-text-heading"><%= edicion.getNombre() %></div>
									</div>
									<div
										class="d-flex flex-row justify-content-between align-items-start w-100">
										<div class="avatar-block">
											<div class="avatar">
												<img class="shape-icon" alt=""
													src=<%= request.getAttribute("imagen" + edicion.getNombre()) %>>
											</div>
											<div class="info gap-1">
												<div class="d-flex align-items-center">
													<i class="bi bi-geo-alt-fill mx-2"></i>
													<div class="description"><%= edicion.getCiudad() %>, <%= edicion.getPais() %></div>
												</div>
												<div class="d-flex align-items-center">
													<i class="bi bi-calendar-fill mx-2"></i>
													<div class="description"><%= edicion.getFechaInicio() %> / <%= edicion.getFechaFin() %></div>
												</div>
											</div>
										</div>
									</div>

								</div>
						</div> <% } %>


						<nav class="d-flex justify-content-center mt-5">
							<ul class="pagination">
								<li class="page-item disabled"><a class="page-link"
									href="#">Anterior</a></li>
								<li class="page-item active"><a class="page-link" href="#">1</a></li>
								<li class="page-item"><a class="page-link" href="#">2</a></li>
								<li class="page-item"><a class="page-link" href="#">3</a></li>
								<li class="page-item"><a class="page-link" href="#">Siguiente</a></li>
							</ul>
						</nav>

					</div>



				</div>


			</div>


		</div>


	</div>

	<script src="../assets/js/main.js"></script>
	<script>
    // Redirige segÃºn el estado del checkbox al hacer click en una categorÃ­a
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
</body>

</html>