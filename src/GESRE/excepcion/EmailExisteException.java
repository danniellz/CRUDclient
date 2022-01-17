package GESRE.excepcion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
