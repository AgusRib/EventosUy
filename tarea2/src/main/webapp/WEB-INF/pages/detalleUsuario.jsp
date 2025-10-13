<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="logica.dataTypes.DataUsuario" %>
<%@ page import="logica.dataTypes.DTOrganizador" %>
<%@ page import="logica.dataTypes.DTAsistente" %>

<!doctype html>

<html lang="es">
<head>
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Detalle de Usuario - Eventos.uy</title>


<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Titillium+Web:wght@300;400;600&display=swap"
	rel="stylesheet">

<link rel="stylesheet" href="../assets/css/ConsultaEvento.css">
<link rel="stylesheet" href="../assets/css/styles.css">


<link rel="stylesheet"
	href="../assets/css/RolVisitante_listarUsuarios.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link rel="icon" type="image/x-icon" href="assets/icons/Logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Bootstrap Icons CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">

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

/* main container spacing */
main.contUser {
	padding: 18px;
}

.container-xl {
	max-width: 1200px;
}

/* card tweaks */
.card {
	border: 1px solid var(--border);
	border-radius: var(--radius);
	box-shadow: var(--shadow);
}

/* avatar */
.avatar {
	width: 64px;
	height: 64px;
	border-radius: 50%;
	border: 2px solid var(--accent-weak);
	object-fit: cover;
	background: #fff;
}

/* actions grid */
.action-card {
	display: flex;
	gap: 12px;
	align-items: center;
	background: #fcfcff;
	border: 1px solid var(--border);
	border-radius: 10px;
	padding: 12px;
	text-decoration: none;
	color: inherit;
	transition: background .12s ease, transform .06s ease;
}

.action-card:hover {
	background: #f6f7ff;
	border-color: #dfe3ff;
	transform: translateY(-1px);
}

/* collapsed styles */
body.with-collapsed {
	padding-left: var(--sidebar-w-collapsed);
}

.sidebar.collapsed .nav-text {
	display: none;
}

.sidebar.collapsed .nav-link {
	justify-content: center;
}

.sidebar.collapsed .nav-icon {
	margin-right: 0 !important;
}

/* responsive */
@media ( max-width : 767.98px) {
	body {
		padding-left: var(--sidebar-w-collapsed);
	}
	.sidebar {
		width: var(--sidebar-w-collapsed);
	}
}

.action-card {
	min-width: 0;
}
/*.actions-grid .col-12 { display:flex; }*/
.action-card {
	width: 100%;
}

.row {
	display: flex !important;
	flex-wrap: nowrap !important; /* evita que salten de línea */
}

.row>[class*="col-"] {
	flex: 0 0 auto; /* que respeten su ancho */
}
</style>
</head>

	<% DataUsuario usuario = (DataUsuario) request.getAttribute("usuarios"); 
	
	if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/usuario?accion=listar");
        return;
    }
	
	DataUsuario.TipoUsuario tipo = usuario.getTipo();
	DTAsistente asis = null;
	DTOrganizador org = null;
	
	if (request.getAttribute("usuarios") instanceof DTOrganizador) {
		org = (DTOrganizador) request.getAttribute("usuarios");
	}
	else {
		asis = (DTAsistente) request.getAttribute("usuarios");
	}%>

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
							<span><%= usuario != null ? usuario.getNombre() : "Usuario"%></span> 
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

<body id="body-pd">
	
	<!-- Contenido -->
	<main class="contUser" id="cuenta-user">
		<div class="container-xl ">
			<div class="row">
				<!--pa dejar dos cartas en una sola columna y la otra a su izq-->
				<div class="miau">

					<!-- Header perfil -->
					<section class="card mb-3 bg-light ">
						<div class="card-body">
							<div
								class="d-flex align-items-center justify-content-between gap-4">
								<!-- izq: foto mas rol -->
								<div class="d-flex flex-column align-items-center">
									<div class="contenedor-fotoPerfil mb-2">
										<img class="foto-usuario" src="../assets/images/IMG-<%= usuario.getNickname() %>>.jpeg"
											alt="miseventosFoto" style="height: 127px;">
									</div>
									<div class="contenedor-NickRolUser text-center">
										<div class="nickname">
											<b><%= usuario.getNickname() %></b>
										</div>
										<div class="rol"><%= usuario.getTipo() %></div>
									</div>
								</div>
								<!-- der: atributos -->
								<div class="d-flex flex-column contenedor-datosUsuario">
									<div class="datosUsuario">
										<div class="nombre">
											<u>Nombre:</u> <%= usuario.getNombre() %>
										</div>
										<div class="email">
											<u>Email:</u> <%= usuario.getEmail() %>
										</div>
										
										<% if (org != null) { %>
												<div class="fechaNacimiento">
													<u>Descripción:</u> <%= org.getDescripcion() %>
												</div>
												<div class="institucion">
													<u>Web:</u> <a href=" <%= org.getWeb() %>" target="_blank"> <%= org.getWeb() %></a>
												</div>
										<% } else if (asis != null) {  %>
												<div class="fechaNacimiento">
													<u>Fecha de Nacimiento:</u> <%= asis.getFechaNacimiento() %>
												</div>
												<!-- FALTA IMPLEMENTAR ESTA FUNCION
												
												
												
												 <div class="institucion">
													<u>Institución:</u> < %= asis.getInstitucion() %>
												</div> 
												
												
												
												
												-->
										<% } %>
									</div>
								</div>
							</div>
						</div>
					</section>

				</div>
				
			</div>
		</div>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

	<script>
    (function () {
      const toggle = document.getElementById('header-toggle');
      const sidebar = document.getElementById('nav-bar-perfil');
      const body = document.getElementById('body-pd');

      if (toggle && sidebar) {
        toggle.addEventListener('click', () => {
          const isCollapsed = sidebar.classList.toggle('collapsed');
          // keep body padding in sync for layout
          document.body.classList.toggle('with-collapsed');
          // aria
          toggle.setAttribute('aria-expanded', String(!isCollapsed));
        });
      }

      // Optional: keyboard hotkeys 1..6 to focus respective action cards
      document.addEventListener('keydown', (e) => {
        if ((e.key >= '1' && e.key <= '6') && !e.altKey && !e.ctrlKey && !e.metaKey) {
          const selector = `[data-hotkey="${e.key}"]`;
          const el = document.querySelector(selector);
          if (el) el.click();
        }
      });
    }());
  </script>
</body>
</html>
