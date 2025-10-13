<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.util.Set"%>
<%@page import="java.net.URLEncoder"%>

<!doctype html>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="x-ua-compatible" content="ie=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Alta de Patrocinio — Eventos.uy</title>
	
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
		rel="stylesheet">
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
		href="https://fonts.googleapis.com/css2?family=Inter&display=swap" />
	
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
	
	main
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

<body id="body-pd">
    <jsp:include page="/WEB-INF/templates/header.jsp" />

    <form id="altaPatrocinioForm" method="post" action="${pageContext.request.contextPath}/altaPatrocinio">
	<main class="contUser">
		<div class="container-xl">

			<!-- Header del formulario -->
			<section class="card mb-3">
				<div class="card-body d-flex align-items-center gap-3">
					<%
					    // determine edition from request param or session
					    String nombreEdicion = request.getParameter("nombreEdicion");
					    if (nombreEdicion == null || nombreEdicion.isEmpty()) {
					        Object s = request.getSession().getAttribute("nombreEdicion");
					        if (s != null) nombreEdicion = s.toString();
					    }
					%>
					<a href="<%= (nombreEdicion != null && !nombreEdicion.isEmpty()) ? (request.getContextPath() + "/detalleEdicion?nombre=" + URLEncoder.encode(nombreEdicion, "UTF-8")) : (request.getContextPath() + "/ServletUsuario") %>"
						class="btn btn-light d-flex align-items-center justify-content-center p-2"
						style="width: 40px; height: 40px; border-radius: 50%;"> 
						<i class='bx bx-chevron-left fs-4'></i>
					</a> <i class="bx bx-handshake fs-3"></i>
					<h2 class="mb-0 fw-bold">Alta de Patrocinio</h2>
				</div>
			</section>

			<!-- Messages -->
			<%
				String error = (String) request.getAttribute("error");
				// mensaje may come as request attribute (server-forward) or as URL parameter after redirect
				String mensaje = (String) request.getAttribute("mensaje");
				if (mensaje == null) mensaje = request.getParameter("mensaje");
				// no mostrar el bloque de permitir editar (se elimina)
			%>
			<% if (mensaje != null) { %>
				<div class="alert alert-success"><%=mensaje%></div>
			<% } %>
			<% if (error != null) { %>
				<div class="alert alert-danger"><%=error%></div>
			<% } %>

			<!-- Formulario -->
			<section class="card mb-3">
				 <%-- Hidden edicion field comes from session (set by previous flow) --%>
                    <input type="hidden" name="edicion" value="<%= nombreEdicion != null ? nombreEdicion : "" %>" />
                    <%-- include session fecha so the POST can send it back; servlet stores it in session as LocalDate --%>
                    <input type="hidden" name="fecha" value="<%= request.getAttribute("fecha") != null ? request.getAttribute("fecha") : "" %>" />

                    <!-- session fecha is kept as hidden input; not displayed to the user -->

                    <div class="mb-3">
					<label for="institucion" class="form-label">Seleccionar una institución</label>
				    <select class="form-control" id="institucion" name="institucion" required>
				        <option value="" disabled><%= request.getAttribute("institucion") == null ? "Seleccione una institución" : "Seleccione una institución" %></option>
				      	<%
				      		Set<String> instituciones = (Set<String>)request.getAttribute("instituciones");
					        String selInst = (String) request.getAttribute("institucion");
					        if(instituciones != null) {
					         for(String insti : instituciones) {
					      %>
					      <option value="<%=insti%>" <%= insti.equals(selInst) ? "selected" : "" %>><%=insti%></option>
					      
					      <%
					         }
					        }
				      	%>
				     
				    </select>
				</div>
				<div class="mb-3">
				    <label for="nivelPatrocinio" class="form-label">Nivel de patrocinio</label>
				    <select class="form-select" id="nivelPatrocinio" name="nivelPatrocinio" required>
				        <option value="">-- Seleccione nivel --</option>
				        <option value="Platino" <%= "Platino".equals(request.getAttribute("nivelPatrocinio"))?"selected":"" %>>Platino</option>
				        <option value="Oro" <%= "Oro".equals(request.getAttribute("nivelPatrocinio"))?"selected":"" %>>Oro</option>
				        <option value="Plata" <%= "Plata".equals(request.getAttribute("nivelPatrocinio"))?"selected":"" %>>Plata</option>
				        <option value="Bronce" <%= "Bronce".equals(request.getAttribute("nivelPatrocinio"))?"selected":"" %>>Bronce</option>
				    </select>
				</div>
				<div class="mb-3">
				    <label for="aporteEconomico" class="form-label">Aporte económico</label>
				    <input type="number" class="form-control" id="aporteEconomico" name="aporte" placeholder="0.00" min="0" step="0.01" value="<%= request.getAttribute("aporte") != null ? request.getAttribute("aporte") : "" %>" required>
				</div>
				<div class="mb-3">
				    <label for="tipoRegistroGratis" class="form-label">Tipo de registro gratuito (opcional)</label>
				    <select class="form-select" id="tipoRegistroGratis" name="tipoRegGratis">
				        <option value="">-- Ninguno --</option>
				        <%
				            Set<String> tipos = (Set<String>) request.getAttribute("tiposRegistro");
				            String selTipo = (String) request.getAttribute("tipoRegGratis");
				            if (tipos != null) {
				                for (String t : tipos) {
				        %>
				        <option value="<%=t%>" <%= t.equals(selTipo)?"selected":"" %>><%=t%></option>
				        <%
				                }
				            }
				        %>
				    </select>
				</div>
				<div class="mb-3">
				    <label for="registrosGratuitos" class="form-label">Cantidad de registros gratuitos</label>
				    <input type="number" class="form-control" id="registrosGratuitos" name="cantGratis" placeholder="0" min="0" value="<%= request.getAttribute("cantGratis") != null ? request.getAttribute("cantGratis") : "0" %>">
				    <div class="form-text">No puede superar el 20% del aporte económico.</div>
				</div>
				<div class="mb-3">
				    <label for="codigoPatrocinio" class="form-label">Código de patrocinio</label>
				    <input type="text" class="form-control" id="codigoPatrocinio" name="codigo" placeholder="Ingrese el código" value="<%= request.getAttribute("codigo") != null ? request.getAttribute("codigo") : "" %>">
				</div>
				<div class="d-flex gap-2">
                             <button type="submit" class="btn btn-primary">Registrar</button>
                             <a href="<%= (nombreEdicion != null && !nombreEdicion.isEmpty()) ? (request.getContextPath() + "/detalleEdicion?nombre=" + URLEncoder.encode(nombreEdicion, "UTF-8")) : (request.getContextPath() + "/ServletUsuario") %>" class="btn btn-secondary">Cancelar</a>
                         </div>
                </section>
            </div>
    </main>
    </form>
 
	<script>
    document.addEventListener('DOMContentLoaded', function() {
  // Ediciones por evento
  const edicionesPorEvento = {
    "1": [
      { value: "1", text: "Edición 1" },
      { value: "2", text: "Edición 2" }
    ],
    "2": [
      { value: "3", text: "Edición 3" }
    ],
    "3": [
      { value: "4", text: "Edición 4" },
      { value: "5", text: "Edición 5" }
    ]
  };

  // Tipos de registro por edición
  const tiposPorEdicion = {
    "1": [
      { value: "general", text: "General" },
      { value: "vip", text: "VIP" }
    ],
    "2": [
      { value: "early", text: "Early Bird" }
    ],
    "3": [
      { value: "general", text: "General" }
    ],
    "4": [
      { value: "vip", text: "VIP" },
      { value: "early", text: "Early Bird" }
    ],
    "5": [
      { value: "general", text: "General" }
    ]
  };

  const eventoSelect = document.getElementById('eventoSelect');
  const edicionSelect = document.getElementById('edicionSelect');
  const tipoRegistroSelect = document.getElementById('tipoRegistroSelect');
    const tipoRegistroWrapper = document.getElementById('tipoRegistroWrapper');

    // Evento cambia: actualiza ediciones y oculta tipo de registro
    eventoSelect.addEventListener('change', function() {
        const selectedEvento = eventoSelect.value;
        edicionSelect.innerHTML = '<option value="">-- Seleccione una edición --</option>';
        tipoRegistroSelect.innerHTML = '<option value="">-- Seleccione un tipo de registro --</option>';
        tipoRegistroWrapper.style.display = 'none';
        if (edicionesPorEvento[selectedEvento]) {
        edicionesPorEvento[selectedEvento].forEach(function(edicion) {
            const option = document.createElement('option');
            option.value = edicion.value;
            option.textContent = edicion.text;
            edicionSelect.appendChild(option);
        });
        edicionWrapper.style.display = '';
        } else {
        edicionWrapper.style.display = 'none';
        }
    });

    // Edición cambia: actualiza tipos de registro y muestra el campo si corresponde
    edicionSelect.addEventListener('change', function() {
        const selectedEdicion = edicionSelect.value;
        tipoRegistroSelect.innerHTML = '<option value="">-- Seleccione un tipo de registro --</option>';
        if (tiposPorEdicion[selectedEdicion]) {
        tiposPorEdicion[selectedEdicion].forEach(function(tipo) {
            const option = document.createElement('option');
            option.value = tipo.value;
            option.textContent = tipo.text;
            tipoRegistroSelect.appendChild(option);
        });
        tipoRegistroWrapper.style.display = '';
        } else {
        tipoRegistroWrapper.style.display = 'none';
        }
    });
    });
</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>