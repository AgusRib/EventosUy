<%@page import="com.gamebook.model.Usuario"%>
<%@page import="java.util.Collection"%>
<%@page import="logica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/WEB-INF/errorPages/500.jsp"%>


<!doctype html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>listarUsuarios</title>
		<link rel="stylesheet" href="../assets/css/styles.css">
		<link rel="stylesheet"
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
		<link rel="stylesheet"
			href="../assets/css/RolVisitante_listarUsuarios.css">
		<link rel="stylesheet"
			href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
		<link rel="icon" type="image/x-icon" href="assets/icons/Logo.png">
	
	</head>
	
	<body>

	<!-- PENDIENTE CABEZAL INCLUIRLO -->

		<main class="container my-5">
			<div class="row justify-content-center mb-4">
				<div class="col-md-6">
					<input type="search" class="form-control form-control-lg"
						placeholder="Buscar usuarios registrados">
				</div>
			</div>
			<div id="listar" class="row justify-content-center">
			
						<div class="col-md-8">
						<% 
							HashSet<Usuario> usuarios = (HashSet<Usuario>)
									request.getAttribute("usuarios");
			
							for(Usuario usuario: usuarios){
						%>
						
							<div class="card rounded-5 mb-3">
								<a href="ConsultaUsuarios.jsp"
									class="text-decoration-none d-block h-100 w-100">
									<div class="user row g-0 align-items-center">
										<div class="col-auto">
											<img src="../assets/images/IMG-<% usuario.getNickname() %>>.jpg" alt="<% usuario.getNickname() %>Foto"
												class="img-fluid rounded-circle m-3"
												style="width: 90px; height: 90px; object-fit: cover;">
										</div>
										<div class="col">
											<div class="card-body ">
												<h5 class="card-title mb-1"><% usuario.getNickname() %></h5>
												<p class="card-text text-secondary mb-0">Asistente</p>
											</div>
										</div>
									</div>
								</a>
							</div>
			
							<div class="card rounded-5 mb-3">
								<a href="MiPerfil-misEventos.html">
									<div class="user row g-0 align-items-center">
										<div class="col-auto">
											<img src="../assets/images/IMG-US04.jpeg" alt="miseventosFoto"
												class="img-fluid rounded-circle m-3"
												style="width: 90px; height: 90px; object-fit: cover;">
										</div>
										<div class="col">
											<div class="card-body">
												<h5 class="card-title mb-1">miseventos</h5>
												<p class="card-text text-secondary mb-0">Organizador</p>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>
					</div>
				</main>
			
				<script
					src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
		</body>


</html>


	<!-- <div id="listar" class="main">
		< % 
				HashSet<Usuario> usuarios = (HashSet<Usuario>)
						request.getAttribute("usuarios");

				for(Usuario usuario: usuarios){
			%>
		<div class="usuario">
			<img src="media/images/defecto.gif" alt="foto" />

			<div class="derecha">
				<a class="nombre" href="?usuario=<%= usuario.getEmail()  %>"> <%= usuario.getNombre() %>
				</a> <span class="email"> <%= usuario.getEmail() %>
				</span>
			</div>
		</div>
		< % } %>
	</div>


	<jsp:include page="/WEB-INF/template/footer.jsp" /> -->





	<!-- TODO: linkear cada usuario a su respectivo perfil usando href sobre el texto del nickname-->
	

		
