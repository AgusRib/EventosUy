<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>

<%
    @SuppressWarnings("unchecked")
    Set<String> categorias = (Set<String>) request.getSession().getAttribute("categorias");
    String categoriaSeleccionada = (String) request.getAttribute("categoriaSeleccionada");
    String nombreBusqueda = (String) request.getAttribute("nombreBusqueda");
%>

<!-- Componente de Categorías Lateral Reutilizable -->
<div class="carta-categorias">
    <div class="form-check mb-2 d-flex justify-content-center">
        <input class="form-check-input" type="checkbox" id="checkboxEdiciones"> 
        <label class="form-check-label ms-2" for="checkboxEdiciones">Ver ediciones</label>
    </div>
    
    <!-- Lista de categorías -->
    <ul class="list-group w-100 overflow-auto text-center">
        <!-- Opción "Todas las categorías" -->
        <a href="<%=request.getContextPath()%>/eventos<%=nombreBusqueda != null && !nombreBusqueda.isEmpty() ? "?nombre=" + java.net.URLEncoder.encode(nombreBusqueda, "UTF-8") : ""%>" 
           class="list-group-item list-group-item-action categoria-link <%=categoriaSeleccionada == null || categoriaSeleccionada.isEmpty() || "todas".equals(categoriaSeleccionada) ? "active" : ""%>">
            Todas las categorías
        </a>
        
        <!-- Lista de categorías dinámicas -->
        <%
        if (categorias != null && !categorias.isEmpty()) {
            for (String categoria : categorias) {
                String href = request.getContextPath() + "/eventos?categoria=" + java.net.URLEncoder.encode(categoria, "UTF-8");
                if (nombreBusqueda != null && !nombreBusqueda.isEmpty()) {
                    href += "&nombre=" + java.net.URLEncoder.encode(nombreBusqueda, "UTF-8");
                }
                boolean isActive = categoria.equals(categoriaSeleccionada);
        %>
            <a href="<%=href%>" 
               class="list-group-item list-group-item-action categoria-link <%=isActive ? "active" : ""%>">
                <%=categoria%>
            </a>
        <%
            }
        } else {
        %>
            <li class="list-group-item text-muted">
                <i class="bi bi-info-circle"></i> No hay categorías disponibles
            </li>
        <%
        }
        %>
    </ul>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const checkboxEdiciones = document.getElementById('checkboxEdiciones');
    
    // Manejar el checkbox de ediciones
    if (checkboxEdiciones) {
        checkboxEdiciones.addEventListener('change', function() {
            if (this.checked) {
                // Redirigir a lista de ediciones (cuando esté implementado)
                window.location.href = '<%=request.getContextPath()%>/ediciones';
            }
        });
    }
});
</script>