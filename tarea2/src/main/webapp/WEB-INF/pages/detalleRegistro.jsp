<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="logica.data_types.DTRegistro"%>
<%
  String ctx = request.getContextPath();
  DTRegistro reg = (DTRegistro) request.getAttribute("registro");
  if (reg == null) {
%>
<h2>No hay datos de registro para mostrar.</h2>
<%
    return;
  }

  String imgUsuario  = (String) request.getAttribute("imagenUsuario");
  String imgEdicion  = (String) request.getAttribute("imagenEdicion");
  if (imgUsuario == null || imgUsuario.isBlank()) imgUsuario = "uploads/usuarios/default.jpg";
  if (imgEdicion == null || imgEdicion.isBlank()) imgEdicion = "uploads/ediciones/default.jpg";

  String nickUsuario   = (String) request.getAttribute("usuario");
  String nombreEdicion = reg.getNombreEdicion(); 
  String tipoReg       = reg.getTipoRegistro().getNombre();
  boolean asistencia   = reg.getAsistencia();  
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Detalle de Registro - Eventos.uy</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/detalleregistro.css">
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/assets/icons/Logo.png">

<style>
  .detalle-registro-card {
    border-radius: 1rem;
  }

  .dr-title {
    font-size: clamp(1.6rem, 3.5vw, 2.25rem);
    font-weight: 800;
    letter-spacing: .2px;
  }

  .dr-avatars img {
    width: 140px;
    height: 140px;
    object-fit: cover;
  }
  @media (min-width: 768px) {
    .dr-avatars img {
      width: 170px;
      height: 170px;
    }
  }
  .badge-xl {
    font-size: 1rem;
    padding: .65rem .9rem;
    border-radius: .75rem;
  }
  .btn-block {
    width: 100%;
  }
  .dr-list .list-group-item {
    padding: .9rem 1rem;
    font-size: 1.05rem;
  }
  .detalle-registro-card .card-body {
    padding: 1.25rem;
  }
  @media (min-width: 768px) {
    .detalle-registro-card .card-body {
      padding: 2rem;
    }
  }
</style>
</head>
<body>
  <jsp:include page="/WEB-INF/templates/header.jsp" />

  <div class="container-sm d-flex flex-column align-items-stretch justify-content-center py-4">
    <div class="card shadow detalle-registro-card mx-auto" style="max-width: 820px;">
      <div class="card-body">
        <!-- Avatares: en mobile apilados; en md+ lado a lado -->
        <div class="dr-avatars row g-3 align-items-center justify-content-center mb-3 mb-md-4">
          <div class="col-12 col-md-auto d-flex justify-content-center">
            <img src="<%= ctx %>/<%= imgUsuario %>" alt="Foto Usuario"
                 class="img-fluid rounded-circle shadow-sm">
          </div>
          <div class="col-12 col-md-auto d-flex justify-content-center">
            <img src="<%= ctx %>/<%= imgEdicion %>" alt="Foto Edición"
                 class="img-fluid rounded shadow-sm">
          </div>
        </div>

        <div class="info-evento text-center"
             data-usuario="<%= nickUsuario %>"
             data-edicion="<%= (nombreEdicion == null ? "" : nombreEdicion) %>">

          <h1 class="dr-title mb-3">Detalle de Registro</h1>

          <!-- Datos del registro en list-group, mejor lectura en mobile -->
          <ul class="list-group dr-list text-start mx-auto mb-3" style="max-width: 640px;">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="text-muted">Usuario</span>
              <strong class="ms-3"><%= nickUsuario %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="text-muted">Edición</span>
              <strong class="ms-3"><%= (nombreEdicion == null ? "" : nombreEdicion) %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="text-muted">Tipo de registro</span>
              <strong class="ms-3"><%= (nombreEdicion == null ? "" : tipoReg) %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="text-muted">Fecha de registro</span>
              <strong class="ms-3"><%= reg.getFechaRegistro() %></strong>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <span class="text-muted">Costo</span>
              <strong class="ms-3">$<%= reg.getCosto() %></strong>
            </li>
          </ul>

          <!-- botoncito para cofnirmar asietncia -->
          <div class="mt-2">
            <div class="d-flex flex-column flex-sm-row align-items-center justify-content-center gap-3">
              <div>
                <span class="me-2 fw-bold">Asistencia:</span>
                <span id="asistenciaEstado"
                      class="badge badge-xl <%= asistencia ? "bg-success" : "bg-secondary" %>">
                  <%= asistencia ? "Confirmada" : "Sin confirmar" %>
                </span>
              </div>

              <div class="w-100 w-sm-auto">
                <button class="btn <%= asistencia ? "btn-success disabled" : "btn-success" %> btn-lg btn-block"
                        id="btnConfirmarAsistencia"
                        <%= asistencia ? "disabled" : "" %>>
                  <%= asistencia ? "Asistencia confirmada" : "Confirmar asistencia" %>
                </button>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>

  <!-- modal para confirmar la confirmación de asistencia -->
  <div class="modal fade" id="confirmAsistenciaModal" tabindex="-1" aria-labelledby="confirmAsistenciaLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="confirmAsistenciaLabel">Confirmar asistencia</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
        </div>
        <div class="modal-body">¿Querés confirmar tu asistencia a este evento?</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancelar</button>
          <button type="button" class="btn btn-primary" id="btnModalConfirmar">Confirmar</button>
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
  (function(){
    const container = document.querySelector('.info-evento');
    const usuario = container?.dataset?.usuario || '';
    const edicion = container?.dataset?.edicion || '';

    const btn   = document.getElementById('btnConfirmarAsistencia');
    const badge = document.getElementById('asistenciaEstado');
    const modalEl = document.getElementById('confirmAsistenciaModal');
    const modal   = new bootstrap.Modal(modalEl);
    const btnOk   = document.getElementById('btnModalConfirmar');

    if (btn && !btn.disabled) {
      btn.addEventListener('click', () => modal.show());
    }

    btnOk.addEventListener('click', async () => {
      try {
        const body = new URLSearchParams({ usuario, edicion });
        const resp = await fetch('<%= ctx %>/confirmar-asistencia', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
          body
        });

        const data = await resp.json();
        if (!resp.ok || !data.ok) throw new Error(data.error || 'Error desconocido');

        badge.textContent = 'Confirmada';
        badge.className = 'badge badge-xl bg-success';
        btn.textContent = 'Asistencia confirmada';
        btn.classList.add('disabled');
        btn.setAttribute('disabled', 'disabled');

        modal.hide();
      } catch (e) {
        alert('No se pudo confirmar la asistencia: ' + (e.message || e));
      }
    });
  })();
  </script>
</body>
</html>
