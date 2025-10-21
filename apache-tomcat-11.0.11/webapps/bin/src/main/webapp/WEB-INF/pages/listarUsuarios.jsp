<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="logica.data_types.DataUsuario" %>

<!doctype html>

<%  @SuppressWarnings("unchecked")
    DataUsuario usuariO = (DataUsuario) request.getAttribute("usuario");
    
    @SuppressWarnings("unchecked")
    Set<DataUsuario> usuarios = (Set<DataUsuario>) request.getAttribute("usuarios");
    if (usuarios == null) usuarios = java.util.Collections.emptySet();

    @SuppressWarnings("unchecked")
    Map<String,String> imgsUsuarios = (Map<String,String>) request.getAttribute("imgsUsuarios");
    if (imgsUsuarios == null) imgsUsuarios = java.util.Collections.emptyMap();

    String q = (String) request.getAttribute("q");
    if (q == null) q = "";

    String ctx = request.getContextPath();
%>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Listar usuarios</title>
		<link rel="stylesheet" href="<%= ctx %>/assets/css/styles.css">
		<link rel="stylesheet"
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
		<link rel="stylesheet"
			href="<%= ctx %>/assets/css/RolVisitante_listarUsuarios.css">
		<link rel="stylesheet"
			href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
		<link rel="icon" type="image/x-icon" href="<%= ctx %>/assets/icons/Logo.png">
	</head>
	
	<body>

		<jsp:include page="../templates/header.jsp"></jsp:include>
	
		<main class="container my-5">
			<div class="row justify-content-center mb-4 align-items-center">
				<div class="col-md-6">
					<form method="get" action="<%= ctx %>/listarUsuarios" role="search">
						<div class="input-group rounded-pill overflow-hidden shadow-sm">
							<!-- Only show the search input; submit on Enter. -->
							<input type="search" name="q" class="form-control form-control-lg border-0"
								placeholder="Buscar usuarios registrados"
								value="<%= q %>" aria-label="Buscar usuarios"
								onkeydown="if(event.key === 'Enter'){ this.form.submit(); }">
						</div>
					</form>
				</div>
			</div>

			<div id="listar" class="row justify-content-center">
				<div class="col-md-8">
					<% if (usuarios.isEmpty()) { %>
						<div class="alert alert-info text-center">
							<i class="bi bi-info-circle"></i>
							No se encontraron usuarios.
						</div>
					<% } else {
						for (DataUsuario usuario : usuarios) {
							String nick = usuario.getNickname();
							String imgRel = imgsUsuarios.get(nick);
							if (imgRel == null || imgRel.isBlank()) imgRel = "uploads/usuarios/default.jpg";
							String detalleUrl = ctx + "/detalleUsuario?usuarios=" + URLEncoder.encode(nick, "UTF-8");
						%>
							<div class="card rounded-5 mb-3">
								<a href="<%= detalleUrl %>" class="text-decoration-none d-block h-100 w-100">
									<div class="user row g-0 align-items-center">
										<div class="col-auto">
											<img src="<%= ctx %>/<%= imgRel %>" alt="<%= nick %>"
											class="img-fluid rounded-circle m-3"
											style="width: 90px; height: 90px; object-fit: cover;">
										</div>
										<div class="col">
											<div class="card-body ">
												<h5 class="card-title mb-1 text-dark"><%= nick %></h5>
												<p class="card-text text-secondary mb-0"><%= usuario.getTipo() %></p>
											</div>
										</div>
									</div>
								</a>
							</div>
						<% }
						} %>
				</div>
			</div>
		</main>
		
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	</body>

</html>