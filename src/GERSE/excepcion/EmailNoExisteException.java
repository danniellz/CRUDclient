/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GERSE.excepcion;

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
