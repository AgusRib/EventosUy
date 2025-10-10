<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- %@page errorPage="/WEB-INF/errorPages/500.jsp"% -->
<!--  %@page import="ServletUsuario" %> -->
<%@page import="WEB-INF.lib.logica.IControllerUsuario" %>
<%@page import="dataTypes.DataUsuario" %>
		
<!doctype html>
<html lang="es">
<head>
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Modificar Perfil — Eventos.uy</title>
<link rel="stylesheet" href="../assets/css/styles.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Titillium+Web:wght@300;400;600&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="../assets/css/ConsultaEvento.css">


<style>
:root {
	--bg: #f4f6f8;
	--surface: #ffffff;
	--text: #1f2937;
	--muted: #6b7280;
	--border: #e6e8eb;
	--accent: #6d5dfc;
	--accent-weak: #edeaff;
	--radius: 8px;
	--shadow: 0 6px 18px rgba(15, 23, 42, .06);
}

body {
	background: var(--bg);
	color: var(--text);
	margin: 0;
	padding-left: var(--sidebar-w);
	transition: padding-left .2s ease;
}

main.contUser {
	padding: 18px;
}

.container-xl {
	max-width: 980px;
}

.card {
	border: 1px solid var(--border);
	border-radius: var(--radius);
	box-shadow: var(--shadow);
}

.form-label {
	font-weight: 600;
}

.avatar-preview {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	object-fit: cover;
	border: 2px solid var(--accent-weak);
}
</style>
</head>

<body id="body-pd">

	<% DataUsuario usuario = (DataUsuario)
		request.getAttribute("usuarios"); 
	String tipo = usuario.getTipo(); %>

	<main class="contUser">
		<div class="container-xl">

			<!-- Header del formulario -->
			<section class="card mb-3">
				<div class="card-body d-flex align-items-center gap-3">
					<a href="../pagesOrganizador/MiPerfil-misEventos.html"
						class="btn btn-light d-flex align-items-center justify-content-center p-2"
						style="width: 40px; height: 40px; border-radius: 50%;"> <i
						class='bx bx-chevron-left fs-4'></i>
					</a> <i class="bx bx-user fs-3"></i>
					<h2 class="mb-0 fw-bold">Modificar Perfil</h2>
				</div>
			</section>

			<!-- Formulario de edición -->
			<section class="card mb-3">
				<div class="card-body">
					<form id="modificarPerfilForm">
						<div class="mb-3 text-center">
							<img id="avatarPreview" src="/lab2/assets/images/IMG-<% usuario.getNickname(); %>>>.jpeg"
								alt="Avatar" class="avatar-preview mb-2"> <input
								class="form-control" type="file" id="avatarInput"
								accept="image/*">
						</div>

						<div class="row">
							<div class="col-md-12 mb-3">
								<label for="nombre" class="form-label">Nombre</label> <input
									type="text" class="form-control" id="nombre" value="MisEventos"
									required>
							</div>
						</div>
						
						<% if ( usuario.getTipo()=="Asistente") { %>
							<div class="col-md-6 mb-3">
									<label for="apellido" class="form-label">Apellido</label> <input
										type="text" class="form-control" id="apellido" value="<% usuario.getApellido(); %>"
										required>
								</div>
							</div>
						<% } %>

						<% if ( usuario.getTipo()=="Organizador") { %>
						
							<div class="mb-3">
								<label for="descripcion" class="form-label">Descripción</label>
								<textarea class="form-control" id="descripcion" rows="3"> <% usuario.getDescripcion(); %> </textarea>
							</div>
	
							<div class="mb-3">
								<label for="web" class="form-label">Sitio web</label> <input
									type="url" class="form-control" id="web" placeholder="https://"
									value=" <% usuario.getWeb(); %> ">
							</div>

						<% } else {  %>
	
							<div class="mb-3">
								<label for="fechaNacimiento" class="form-label">Fecha de
									nacimiento</label> <input type="date" class="form-control"
									id="fechaNacimiento" value=" <% usuario.getFechaNacimiento(); %> ">
							</div>
							
						<% } %>
	
	
						<div class="mb-3">
							<label class="form-label">Nickname</label> <input type="text"
								class="form-control" value=" <% usuario.getNickname(); %> " disabled>
						</div>

						<div class="mb-3">
							<label class="form-label">Correo electrónico</label> <input
								type="email" class="form-control"
								value=" <% usuario.getEmail(); %> " disabled>
						</div>

						<hr>

						<div class="mb-3">
							<label for="password" class="form-label">Nueva contraseña</label>
							<input type="password" class="form-control" id="password"
								placeholder="Dejar en blanco si no cambia">
						</div>

						<div class="d-flex gap-2">
							<button type="submit" class="btn btn-primary">Guardar</button>
							<a href="MiPerfil-misEventos.html" class="btn btn-secondary">Cancelar</a>
						</div>
					</form>
				</div>
			</section>

		</div>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

	<script>
  const avatarInput = document.getElementById('avatarInput');
  const avatarPreview = document.getElementById('avatarPreview');

  avatarInput.addEventListener('change', (e) => {
    const file = e.target.files[0];
    if(file){
      avatarPreview.src = URL.createObjectURL(file);
    }
  });

  document.getElementById('modificarPerfilForm').addEventListener('submit', (e) => {
    e.preventDefault();
    // Aquí enviarías los datos al backend mediante fetch/AJAX
    alert('Perfil actualizado correctamente.');
  });
</script>
</body>
</html>

		
		
		