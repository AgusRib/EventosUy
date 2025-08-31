package excepciones;

public class NombreEdicionExistenteExcepcion extends Exception {
    public NombreEdicionExistenteExcepcion() {
        super("El nombre de la edición ya existe");
    }

    public NombreEdicionExistenteExcepcion(String message) {
        super(message);
    }

    public NombreEdicionExistenteExcepcion(String message, Throwable cause) {
        super(message, cause);
    }

    public NombreEdicionExistenteExcepcion(Throwable cause) {
        super(cause);
    }
}