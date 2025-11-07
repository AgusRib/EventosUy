<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="webservices.*" %>
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

  String nickUsuario = (String) request.getAttribute("usuario");
  String nombreEdicion = reg.getNombreEdicion(); 
  String tipoReg = reg.getTipoRegistro().getNombre();
  boolean asistencia = reg.getAsistencia();
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
</head>
<body>
  <jsp:include page="/WEB-INF/templates/header.jsp" />

  <div class="container d-flex flex-column align-items-center justify-content-center py-4">
    <div class="card shadow detalle-registro-card">
      <div class="card-body">
        <div class="d-flex flex-row align-items-center justify-content-center gap-5 mb-4">
          <img src="<%= ctx %>/<%= imgUsuario %>" alt="Foto Usuario" class="img-fluid rounded-circle detalle-registro-img-usuario">
          <img src="<%= ctx %>/<%= imgEdicion %>" alt="Foto Edición" class="img-fluid rounded detalle-registro-img-edicion">
        </div>

        <div class="info-evento text-center" 
             data-usuario="<%= nickUsuario %>" 
             data-edicion="<%= (nombreEdicion == null ? "" : nombreEdicion) %>">
          <h1 class="mb-3">Detalle de Registro</h1>

          <div class="mb-2"><strong>Usuario:</strong> <%= nickUsuario %></div>
          <div class="mb-2"><strong>Edición de Evento:</strong> <%= (nombreEdicion == null ? "" : nombreEdicion) %></div>
          <div class="mb-2"><strong>Tipo de registro:</strong> <%= (nombreEdicion == null ? "" : tipoReg) %></div>
          <div class="mb-2"><strong>Fecha de Registro:</strong> <%= reg.getFechaRegistro() %></div>
          <div class="mb-2"><strong>Costo:</strong> $<%= reg.getCosto() %></div>

          <!-- botoncito de estado de la asistencia -->
          <div class="mt-3">
            <strong>Asistencia:</strong>
            <span id="asistenciaEstado" class="badge <%= asistencia ? "bg-success" : "bg-secondary" %>">
              <%= asistencia ? "Confirmada" : "Sin confirmar" %>
            </span>
          </div>

          <div class="mt-3">
            <button class="btn <%= asistencia ? "btn-success disabled" : "btn-success" %>" 
                    id="btnConfirmarAsistencia"
                    <%= asistencia ? "disabled" : "" %>>
              <%= asistencia ? "Asistencia confirmada" : "Confirmar asistencia" %>
            </button>
          </div>
        </div>

      </div>
    </div>
  </div>

  <!-- modalcito de confirmación -->
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
    const usuario = container?.dataset?.usuario;
    const edicion = container?.dataset?.edicion;

    const btn = document.getElementById('btnConfirmarAsistencia');
    const badge = document.getElementById('asistenciaEstado');
    const modalEl = document.getElementById('confirmAsistenciaModal');
    const modal = new bootstrap.Modal(modalEl);
    const btnOk = document.getElementById('btnModalConfirmar');

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

        // Reflejar en UI:
        badge.textContent = 'Confirmada';
        badge.className = 'badge bg-success';
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
