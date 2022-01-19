package GESRE.excepcion;

/**
 * Clase Exception para contraseña erronea
 * 
 * @author Daniel Brizuela
 */
public class ContraInvalidaException extends Exception {

    
    /**
     * Constructor vacío.
     */
    public ContraInvalidaException() {
        super("La contraseña introducida es erronea");
    }
}
