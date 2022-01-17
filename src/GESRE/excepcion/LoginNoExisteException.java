/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
