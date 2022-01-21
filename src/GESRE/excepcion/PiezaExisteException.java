package GESRE.excepcion;

/**
 *
 * @author Jonathan Viñan
 */
public class PiezaExisteException extends Exception {

    
    /**
     * Constructor vacío.
     */
    public PiezaExisteException() {
        super("La Pieza ya existe en la base de datos");
    }
}
