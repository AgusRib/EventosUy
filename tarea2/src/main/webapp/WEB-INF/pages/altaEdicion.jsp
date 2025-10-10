<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Alta de Edición — Eventos.uy</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
<link
	href="https://fonts.googleapis.com/css2?family=Titillium+Web:wght@300;400;600&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="../assets/css/ConsultaEvento.css">
<link rel="stylesheet" href="../assets/css/styles.css">
<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
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
	font-family: system-ui, -apple-system, "Segoe UI", Roboto,
		"Titillium Web", Arial, sans-serif;
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
</style>
</head>

<jsp:include page="../templates/header.jsp"></jsp:include>

<body id="body-pd">
	<main class="contUser">
		<div class="container-xl">

			<!-- Header del formulario con botón volver -->
			<section class="card mb-3">
				<div class="card-body d-flex align-items-center gap-3">
					<a href="MiPerfil-misEventos.html"
						class="btn btn-light d-flex align-items-center justify-content-center p-2"
						style="width: 40px; height: 40px; border-radius: 50%;"> <i
						class='bx bx-chevron-left fs-4'></i>
					</a> <i class="bx bx-calendar fs-3"></i>
					<h2 class="mb-0 fw-bold">Alta de Edición</h2>
				</div>
			</section>

			<!-- Formulario -->
			<section class="card mb-3">
				<div class="card-body">
					<form id="altaEdicionForm" action="${pageContext.request.contextPath}/AltaEdicion" method="post">
						<div class="mb-3">
							<label for="nombreEdicion" class="form-label">Nombre de
								la edición</label> <input type="text" class="form-control"
								id="nombreEdicion" name="nombre" placeholder="Ingrese el nombre" required>
							<div class="invalid-feedback">Este nombre de edición ya
								existe. Por favor ingrese otro.</div>
						</div>

						<div class="mb-3">
							<label for="siglaEdicion" class="form-label">Sigla</label> <input
								type="text" class="form-control" id="siglaEdicion" name="sigla"
								placeholder="Ej: EVT2025-1" required>
						</div>

						<div class="mb-3">
							<label for="ciudadEdicion" class="form-label">Ciudad</label> <input
								type="text" class="form-control" id="ciudadEdicion" name="ciudad"
								placeholder="Ingrese la ciudad" required>
						</div>

						<div class="mb-3">
							<label for="paisEdicion" class="form-label">País</label> <input
								type="text" class="form-control" id="paisEdicion" name="pais"
								placeholder="Ingrese el país" required>
						</div>

						<div class="mb-3">
							<label for="fechaInicio" class="form-label">Fecha de
								inicio</label> <input type="date" class="form-control" id="fechaInicio" name="fechaInicio"
								required>
						</div>

						<div class="mb-3">
							<label for="fechaFin" class="form-label">Fecha de fin</label> <input
								type="date" class="form-control" id="fechaFin" name="fechaFin" required>
						</div>

						<div class="mb-3">
							<label for="imagenEdicion" class="form-label">Imagen de
								la edición (opcional)</label> <input class="form-control" type="file"
								id="imagenEdicion" name="imagen" accept="image/*">
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

</body>
</html>
