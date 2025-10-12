<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="logica.dataTypes.DataUsuario" %>

<!doctype html>

<%  @SuppressWarnings("unchecked")
    DataUsuario usuariO = (DataUsuario) request.getAttribute("usuario");
    
    @SuppressWarnings("unchecked")
    Set<DataUsuario> usuarios = (Set<DataUsuario>) request.getAttribute("usuarios"); %>

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
							<span><%= usuariO != null ? usuariO.getNombre() : "Usuario"%></span> 
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
						<%	if (usuarios.size() > 0) {
								for(DataUsuario usuario: usuarios){ %>
									<div class="card rounded-5 mb-3">
										<a href="<%=request.getContextPath()%>/detalleUsuario?action=detalleUsuario&usuario=<%=java.net.URLEncoder.encode(usuario.getNickname(), "UTF-8")%>"
											class="text-decoration-none d-block h-100 w-100">
											<div class="user row g-0 align-items-center">
												<div class="col-auto">
													<img src="../assets/images/IMG-<%= usuario.getNickname()  %>>.jpg" alt="<%= usuario.getNickname()  %>Foto"
														class="img-fluid rounded-circle m-3"
														style="width: 90p; height: 90px; object-fit: cover;">
												</div>
												<div class="col">
													<div class="card-body ">
														<h5 class="card-title mb-1"><%= usuario.getNickname()  %></h5>
														<p class="card-text text-secondary mb-0"><%= usuario.getTipo()  %>></p>
													</div>
												</div>
											</div>
										</a>
									</div>
								<% }
								} else {%>
								
								<div class="col-12">
									<div class="alert alert-info text-center">
										<i class="bi bi-info-circle"></i>
										No se encontraron usuarios.
									</div>
								</div>
								<% } %>
						</div>
					</div>
				</main>
			
				<script
					src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
		</body>


</html>
		
