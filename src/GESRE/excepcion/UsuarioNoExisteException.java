package GESRE.excepcion;

/**
 *
 * @author Jonathan Viñan
 */
public class UsuarioNoExisteException extends Exception {

    /**
     * Constructor vacío.
     */
    public UsuarioNoExisteException() {
        super("El usuario que se ha buscado no existe en la base de datos");
    }
}
