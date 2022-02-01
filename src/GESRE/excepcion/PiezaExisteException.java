package GESRE.excepcion;

/**
 * Clase Exception para pieza ya existe en la base de datos
 *
 * @author Daniel Brizuela
 */
public class PiezaExisteException extends Exception {

    /**
     * Constructor vacío.
     */
    public PiezaExisteException() {
        super("La Pieza ya existe en la base de datos");
    }
}
