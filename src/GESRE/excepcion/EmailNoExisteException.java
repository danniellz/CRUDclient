package GESRE.excepcion;

/**
 *
 * @author Jonathan Viñan
 */
public class EmailNoExisteException extends Exception {

    /**
     * Constructor vacío.
     */
    public EmailNoExisteException() {
        super("El email que se ha buscado no existe en la base de datos");
    }
}
