<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- %@page errorPage="/WEB-INF/errorPages/500.jsp"% -->
<!--  %@page import="ServletUsuario" %> -->
<%@page import="WEB-INF.lib.logica.IControllerUsuario" %>
<%@page import="dataTypes.DataUsuario" %>


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
							Set<DataUsuario> usuarios = (Set<Usuario>)
									request.getAttribute("usuarios");
			
							for(DataUsuario usuario: usuarios){
								
								
						%>
						
							<div class="card rounded-5 mb-3">
								<a href="ConsultaUsuarios.jsp"
									class="text-decoration-none d-block h-100 w-100">
									<div class="user row g-0 align-items-center">
										<div class="col-auto">
											<img src="../assets/images/IMG-<% usuario.getNickname(); %>>.jpg" alt="<% usuario.getNickname(); %>Foto"
												class="img-fluid rounded-circle m-3"
												style="width: 90px; height: 90px; object-fit: cover;">
										</div>
										<div class="col">
											<div class="card-body ">
												<h5 class="card-title mb-1"><% usuario.getNickname(); %></h5>
												<p class="card-text text-secondary mb-0"><% usuario.getTipo(); %>></p>
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
		
