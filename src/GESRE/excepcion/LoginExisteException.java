package GESRE.excepcion;

/**
 *
 * @author Jonathan Viñan
 */
public class LoginExisteException  extends Exception{
     /**
     * Constructor vacío.
     */
    public LoginExisteException() {
        super("El login que se ha buscado ya existe en la base de datos");
    }
}
