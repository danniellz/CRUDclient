package GESRE.excepcion;

/**
 * Clase Exception para la manipulacion de una pieza que pertenece a otro
 * trabajador
 *
 * @author Daniel Brizuela
 */
public class PiezaDeOtroTrabajadorException extends Exception {

    /**
     * Constructor vacío.
     *
     * @param msg mensaje de error
     */
    public PiezaDeOtroTrabajadorException(String msg) {
        super(msg);
    }
}
