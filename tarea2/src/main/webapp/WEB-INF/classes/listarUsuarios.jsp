<%@page import="com.gamebook.model.Usuario"%>
<%@page import="java.util.Collection"%>
<%@page import="logica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/WEB-INF/errorPages/500.jsp"%>
<!doctype html>
<html>
<head>
<jsp:include page="/WEB-INF/template/head.jsp" />
<title>Usuarios :: Eventos.uy</title>
</head>
<body>
	<jsp:include page="/WEB-INF/template/header.jsp" />

	<div id="listar" class="main">
		<% 
				HashSet<Usuario> usuarios = (HashSet<Usuario>)
						request.getAttribute("usuarios");

				for(Usuario usuario: usuarios){
			%>
		<div class="usuario">
			<img src="media/images/defecto.gif" alt="foto" />

			<div class="derecha">
				<a class="nombre" href="?usuario=<%= usuario.getEmail()  %>"> <%= usuario.getNombre() %>
				</a> <span class="email"> <%= usuario.getEmail() %>
				</span>
			</div>
		</div>
		<% } %>
	</div>


	<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>