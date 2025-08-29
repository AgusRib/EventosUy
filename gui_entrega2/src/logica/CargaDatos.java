package logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;


//TODO: Quitar main() y statics
public class CargaDatos {
	
	public static void cargarDatos() throws Exception {
		
		cargarInstituciones();
		cargarUsuarios();
		
		System.out.println("Carga de datos finalizada");
		
	}
	
	//CARGAS
	public static void cargarUsuarios() throws Exception {
		BufferedReader brUsuarios = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/datosPrueba/2025Usuarios.csv"));

		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();

		String linea;
		brUsuarios.readLine(); // Saltear la primer linea (headers)
		while ((linea = brUsuarios.readLine()) != null) {
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
	}
	
	public static void cargarInstituciones() throws Exception {
		BufferedReader brInstituciones = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/datosPrueba/2025Instituciones.csv"));

		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
		
		String linea;
		brInstituciones.readLine(); // Saltear la primer linea (headers)
		while ((linea = brInstituciones.readLine()) != null) {
			String[] campos = linea.split(";");
			ICU.ingresarInstitucion(campos[0], campos[1], campos[2]);
		}
	}
	
	//UTILS
	private static String[] buscarLinea(String id, String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + path));
		br.readLine(); // Saltear la primer linea (headers)
		String linea;
		while ((linea = br.readLine()) != null) {
			System.out.println("Buscando id " + id + " en linea: " + linea);
			String[] campos = linea.split(";");
			System.out.println(campos[0].equals(id));
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
