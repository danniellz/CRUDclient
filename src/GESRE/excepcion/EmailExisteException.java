package GESRE.excepcion;

/**
 *
 * @author Jonathan Viñan
 */
public class EmailExisteException extends Exception {

    
    /**
     * Constructor vacío.
     */
    public EmailExisteException() {
        super("El email ya existe en la base de datos");
    }
}
