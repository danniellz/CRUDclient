package GESRE.excepcion;

/**
 *
 * @author Jonathan Viñan
 */
public class PiezaNoExisteException extends Exception {

    
    /**
     * Constructor vacío.
     */
    public PiezaNoExisteException() {
        super("La Pieza No existe en la base de datos");
    }
}
