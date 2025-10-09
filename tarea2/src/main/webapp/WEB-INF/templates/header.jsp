<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import=logica.enumerators.* %>
<header>
	
		<nav class="navbar bg-white shadow-sm" style="height: 86px;">
			
			<%
			RolUsuario rol = (RolUsuario) session.getAttribute("rol");
			
			if(rol == RolUsuario.VISITANTE) {
			%>
			<div class="header-auth m-3 d-flex justify-content-end"
				style="width: 100%;">
				<a href="ServletAutenticator" class="text-decoration-none">
					<button type="button" class="button1 rounded-3">
						<div class="header-button">Iniciar sesión</div>
					</button>
				</a> <a href="ServletAutenticator" class="text-decoration-none">
					<button type="button" class="button2 rounded-3">
						<div class="header-button">Regístrarse</div>
					</button>
				</a>
			<%
			} else {
				String nick = (String) session.getAttribute("nickname");			
				
			%>
			<header>
				<nav class="navbar bg-white shadow-sm">
					<div class="d-flex justify-content-end align-items-center w-100">
						<div class="dropdown">
							<a class="d-flex align-items-center text-decoration-none gap-2 m-3"
								href="#" id="userMenuDropdown" data-bs-toggle="dropdown"
								aria-expanded="false" aria-haspopup="true"> <img
								src="../assets/images/IMG-US04.jpeg" alt="JA"
								class="rounded-circle"
								style="width: 38px; height: 38px; object-fit: cover; border: 1px solid rgba(0, 0, 0, .06);">
								<span><%=nick%></span> <i class="bi bi-chevron-down"></i>
							</a>
							<ul class="dropdown-menu dropdown-menu-end shadow-sm"
								aria-labelledby="userMenuDropdown">
								<!-- TODO:falta poner que cargue dinamicamente las fotos de perfil -->
								<li><a class="dropdown-item" href="usuarios">Mi
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
			<%
			}
			%>
			
			</div>
		</nav>
</header>