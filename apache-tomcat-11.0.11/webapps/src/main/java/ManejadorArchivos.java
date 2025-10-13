package main.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

public class ManejadorArchivos {

	public static String buscarArchivo(String nombreArchivo, String carpeta) {
		File dir = new File(carpeta);
        if(!dir.exists() || !dir.isDirectory()) return null;

        for(File f : dir.listFiles()) {
            String baseName = f.getName();
            System.out.println("Revisando archivo: " + baseName);
            int dot = baseName.lastIndexOf(".");
            baseName = baseName.substring(0, dot); // quita extensión
            if(baseName.equals(nombreArchivo)) {
                return f.getName(); // devuelve el nombre con extensión
            }
        }
        return null; // no encontrado
	}
	
	public static void guardarArchivo(Part imagen, String nombre, String carpeta, ServletContext context) throws IOException {
		if (imagen != null && imagen.getSize() > 0) {
        	
        	File destino = new File(context.getRealPath("/uploads/" + carpeta) + File.separator + nombre.toLowerCase() + ".jpg");
        	
            try (InputStream input = imagen.getInputStream();
                    FileOutputStream output = new FileOutputStream(destino)) {
                   input.transferTo(output);
                   System.out.println("Imagen subida y guardada en: " + destino.getAbsolutePath());
               }
        }
	}

}


