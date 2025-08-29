package logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;


//TODO: Quitar main() y statics
public class CargaDatos {
	
	public static void cargarDatos() throws Exception {
		
		cargarInstituciones();
		cargarUsuarios();
		cargarCategorias();
		cargarEventos();
		
		System.out.println("Carga de datos finalizada");
		
	}
	
	
	
	
	
	
	//CARGAS
	public static void cargarUsuarios() throws Exception {
		BufferedReader brUsuarios = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/datosPrueba/2025Usuarios.csv"));

		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();

		String linea;
		brUsuarios.readLine(); // Saltear la primer linea (headers)
		while ((linea = brUsuarios.readLine()) != null) {
			if (linea.isBlank())
				continue;
			String[] campos = linea.split(";");

			String idUsr = campos[0];
			String tipoUsr = campos[1];
			String nickname = campos[2];
			String nombre = campos[3];
			String email = campos[4];
			if (tipoUsr.equals("A")) {
				String[] lineaAsist = buscarLinea(idUsr, "/datosPrueba/2025Usuarios-Asistentes.csv");
				String[] fechaNac = lineaAsist[2].split("/");
				String strFechaNac = new String(fechaNac[2] + "-" + fechaNac[1] + "-" + fechaNac[0]);
				ICU.ingresarAsistente(nickname, nombre, email, lineaAsist[0], LocalDate.parse(strFechaNac));
				
				if (lineaAsist.length == 4) {
					String[] lineaInst = buscarLinea(lineaAsist[3], "/datosPrueba/2025Instituciones.csv");
					ICU.agregarAsistente(nickname, lineaInst[1]);
				}
				
			} else {
				String[] lineaOrg = buscarLinea(idUsr, "/datosPrueba/2025Usuarios-Organizadores.csv");
				ICU.ingresarOrganizador(nickname, nombre, email, lineaOrg[1], lineaOrg.length == 3 ? lineaOrg[2] : "");
			}
			
		}
		System.out.println("Usuarios cargados");
		brUsuarios.close();
	}
	
	
	
	
	
	
	public static void cargarInstituciones() throws Exception {
		BufferedReader brInstituciones = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/datosPrueba/2025Instituciones.csv"));

		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
		
		String linea;
		brInstituciones.readLine(); // Saltear la primer linea (headers)
		while ((linea = brInstituciones.readLine()) != null) {
			if (linea.isBlank())
				continue;
			String[] campos = linea.split(";");
			ICU.ingresarInstitucion(campos[0], campos[1], campos[2]);
		}
		System.out.println("Instituciones cargadas");
		brInstituciones.close();
	}
	
	
	
	
	
	
	public static void cargarCategorias() throws Exception {
		BufferedReader brCategorias = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/datosPrueba/2025Categorias.csv"));

		IControllerEvento ICE = Factory.getInstance().getControllerEvento();
		
		String linea;
		brCategorias.readLine(); // Saltear la primer linea (headers)
		while ((linea = brCategorias.readLine()) != null) {
			if (linea.isBlank())
				continue;
			String[] campos = linea.split(";");
			ICE.ingresarCategoria(campos[1]);
		}
		System.out.println("Categorias cargadas");
		brCategorias.close();
	}
	
	
	
	
	
	
	public static void cargarEventos() throws Exception {
		BufferedReader brEventos = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/datosPrueba/2025Eventos.csv"));

		IControllerEvento ICE = Factory.getInstance().getControllerEvento();
		
		String linea;
		brEventos.readLine(); // Saltear la primer linea (headers)
		while ((linea = brEventos.readLine()) != null) {
			if (linea.isBlank())
				continue;
			String[] campos = linea.split(";");
			
			String idEv = campos[0];
			String nombre= campos[1];
			String descripcion = campos[2];
			String sigla = campos[3];
			String fechaAlta = campos[4];
			// Formatear fecha de dd/mm/yyyy a yyyy-mm-dd
			String[] fechaParts = fechaAlta.split("/");
			fechaAlta = new String(fechaParts[2] + "-" + fechaParts[1] + "-" + fechaParts[0]);
			
			HashSet<String> idCategorias = new HashSet<String>(Arrays.asList(campos[5].split(",")));
			
			HashSet<String> categorias = new HashSet<String>();
			for (String cat : idCategorias) {
				categorias.add(buscarLinea(cat, "/datosPrueba/2025Categorias.csv")[1]);
			}
			
			ICE.altaEvento(nombre, sigla, LocalDate.parse(fechaAlta), descripcion, categorias);
		}
	}
	
	
	
	
	
	
	
	//UTILS
	private static String[] buscarLinea(String id, String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + path));
		br.readLine(); // Saltear la primer linea (headers)
		String linea;
		while ((linea = br.readLine()) != null) {
			if (linea.isBlank())
				continue;
			String[] campos = linea.split(";");
			if (campos[0].equals(id)) {
				br.close();
				return campos;
			}
		}
		br.close();
		System.out.println("No se encontro id " + id + " en " + path);
		return null;
	}

}
