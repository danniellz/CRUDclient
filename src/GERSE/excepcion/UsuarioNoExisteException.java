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
public class UsuarioNoExisteException extends Exception {

    /**
     * Constructor vacío.
     */
    public UsuarioNoExisteException() {
        super("El usuario que se ha buscado no existe en la base de datos");
    }
}
