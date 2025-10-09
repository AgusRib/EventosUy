<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- %@page errorPage="/WEB-INF/errorPages/500.jsp"% -->
<%@page import="ServletUsuario" %>
<%@page import="logica.IControllerUsuario" %>
<%@page import="dataTypes.DataUsuario" %>


<!doctype html>

<% DataUsuario usuario = (Usuario)
		request.getAttribute("usuarios"); %>

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

<body id="body-pd">
	<header>
		<nav class="navbar bg-white shadow-sm">
			<div class="text-center align-items-center">
				<a class="fw-bold text-dark fs-2 m-4 text-decoration-none"
					href="index.html"><b>Eventos.uy</b></a>
			</div>
			<div class="d-flex justify-content-end align-items-center">
				<div class="dropdown">
					<a class="d-flex align-items-center text-decoration-none gap-2 m-3"
						href="#" id="userMenuDropdown" data-bs-toggle="dropdown"
						aria-expanded="false" aria-haspopup="true"> <img
						src="../assets/images/IMG-US04.jpeg" alt="JA"
						class="rounded-circle"
						style="width: 38px; height: 38px; object-fit: cover; border: 1px solid rgba(0, 0, 0, .06);">
						<span>usuario.getNombre()</span> <i class="bi bi-chevron-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-menu-end shadow-sm"
						aria-labelledby="userMenuDropdown">
						<li><a class="dropdown-item" href="MiPerfil-misEventos.html">Mi
								perfil</a></li>
						<li>
							<hr class="dropdown-divider">
						</li>
						<li><a class="dropdown-item text-danger"
							href="../pagesVisitante/index.html"
							style="color: #dc3545 !important;">Cerrar sesión</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>


	<!-- Contenido -->
	<main class="contUser" id="mi-cuenta-user">
		<div class="container-xl ">
			<div class="row">
				<!--pa dejar dos cartas en una sola columna y la otra a su izq-->
				<div class="col-6">

					<!-- Header perfil -->
					<section class="card mb-3 bg-light ">
						<div class="card-body">
							<div
								class="d-flex align-items-center justify-content-between gap-4">
								<!-- izq: foto mas rol -->
								<div class="d-flex flex-column align-items-center">
									<div class="contenedor-fotoPerfil mb-2">
										<img class="foto-usuario" src="../assets/images/IMG-US04.jpeg"
											alt="miseventosFoto" style="height: 127px;">
									</div>
									<div class="contenedor-NickRolUser text-center">
										<div class="nickname">
											<b>miseventos</b>
										</div>
										<div class="rol">Organizador</div>
									</div>
								</div>
								<!-- der: atributos -->
								<div class="d-flex flex-column contenedor-datosUsuario">
									<div class="datosUsuario">
										<div class="nombre">
											<u>Nombre:</u> MisEventos
										</div>
										<div class="email">
											<u>Email:</u> contacto@miseventos.com
										</div>
										<div class="fechaNacimiento">
											<u>Descripción:</u> Empresa de organizacion de eventos.
										</div>
										<div class="institucion">
											<u>Web:</u> <a href="https://miseventos.com" target="_blank">https://miseventos.com</a>
										</div>
									</div>
								</div>
							</div>
							<!-- abajo: CONSULTA REGISTRO -->
							<div class="mt-4">
								<div class="contenedor-ediciones">

									<div class="mb-2">
										<a class="action-card" href="ListaEdiciones.html"
											data-hotkey="2" role="button">
											<div
												class="action-icon d-flex align-items-center justify-content-center rounded"
												style="width: 44px; height: 44px; border: 1px solid var(--border); background: #fff">
												<i class="bi bi-collection-fill"></i>
											</div>
											<div class="flex-fill ms-2">
												<h3 class="mb-0 h6 fw-bold">Monitorear ediciones</h3>
												<p class="mb-0 small text-muted">Permite ver estado de
													las ediciones y consultar detalles.</p>
											</div> <i class="bx bx-right-arrow-alt fs-4 text-secondary"></i>
										</a>
									</div>

								</div>
							</div>
						</div>
					</section>

				</div>
				<!-- cambio de columna -->
				<div class="col-6 d-flex flex-column gap-3">


					<!-- MODIFICAR USUARIO card -->
					<section class="card mb-3  bg-light">
						<div class="card-body p-0">
							<ul class="list-unstyled m-0">
								<li class="border-top"><a
									href="ModificarUsuarioOrganizador.html"
									class="d-grid gap-2 g-0 text-decoration-none p-3 align-items-center list-link"
									id="mi-perfil-card">
										<div class="d-flex align-items-center">
											<div class="flex-fill">
												<i class="bi bi-pencil-square"></i><strong>
													Modificar mi usuario</strong>

												<div class="text-muted small">Ver y editar datos
													personales, direccion, web y más.</div>
											</div>
											<div class="ms-2 text-secondary" aria-hidden="true">
												<svg xmlns="http://www.w3.org/2000/svg" width="18"
													height="18" fill="currentColor" viewBox="0 0 16 16">
                      <path
														d="M6 12.796V3.204L11.481 8 6 12.796zm.659.753 5.48-4.796a1 1 0 0 0 0-1.506L6.66 2.451C6.011 1.885 5 2.345 5 3.204v9.592a1 1 0 0 0 1.659.753z" />
                    </svg>
											</div>
										</div>
								</a></li>
							</ul>
						</div>
					</section>

					<!-- Acciones (redirigen a otras pages) -->
					<section class="card mb-3 bg-light">
						<div class="card-body">
							<div class="mb-3">
								<h2 class="h6 mb-0 fw-bold">Acciones</h2>
								<p class="text-muted small mb-0">Registrate a cualquier
									edición existente.</p>
							</div>

							<!-- Grid de acciones -->
							<div class="col actions-grid" role="navigation"
								aria-label="Acciones de perfil">


								<div class="row-4 mb-2">
									<a class="action-card" href="AltaEvento.html" data-hotkey="3"
										role="button">
										<div
											class="action-icon d-flex align-items-center justify-content-center rounded"
											style="width: 44px; height: 44px; border: 1px solid var(--border); background: #fff">
											<i class='bx bx-calendar-plus fs-4' aria-hidden="true"></i>
										</div>
										<div class="flex-fill ms-2">
											<h3 class="mb-0 h6 fw-bold">Alta evento</h3>
											<p class="mb-0 small text-muted">Crea un nuevo evento con
												sus datos básicos.</p>
										</div> <i class="bx bx-right-arrow-alt fs-4 text-secondary"></i>
									</a>
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
