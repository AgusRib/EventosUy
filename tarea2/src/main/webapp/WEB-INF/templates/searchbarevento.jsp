<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Obtener el valor de búsqueda actual para mantenerlo en el campo
    String valorBusqueda = request.getParameter("nombre");
    if (valorBusqueda == null) {
        valorBusqueda = (String) request.getAttribute("nombreBusqueda");
    }
    if (valorBusqueda == null) {
        valorBusqueda = "";
    }
%>

<!-- Componente de Barra de Búsqueda Reutilizable -->
<div class="carta-de-eventos mb-4 d-flex justify-content-center">
    <form action="<%=request.getContextPath()%>/eventos" method="get" class="d-flex w-100 justify-content-center">
        <input type="search" name="nombre" class="search"
               placeholder="Buscar eventos..." 
               value="<%=valorBusqueda%>"
               style="width:100%; min-width: 300px;">
    </form>
</div>