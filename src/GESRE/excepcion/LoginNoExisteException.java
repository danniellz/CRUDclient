package GESRE.excepcion;

/**
 *
 * @author Jonathan Viñan
 */
public class LoginNoExisteException extends Exception {

    /**
     * Constructor vacío.
     */
    public LoginNoExisteException() {
        super("El login que se ha buscado no existe en la base de datos");
    }
}
