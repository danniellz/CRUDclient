/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.factoria;

import GESRE.implementacion.ClienteManagerImplementacion;
import GESRE.implementacion.UsuarioManagerImplentacion;
import GESRE.interfaces.ClienteManager;
import GESRE.interfaces.UsuarioManager;
import java.util.logging.Logger;

/**
 * Clase de factoría que gestiona las implementaciones.
 *
 * @author Jonathan Viñan
 */
public class GestionFactoria {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger("implementaciones.GestionFactoria");
    
    public static ClienteManager createClienteManager() {
        
        ClienteManager clienteManager = new ClienteManagerImplementacion();
        
        return clienteManager;
    }
    
    public static UsuarioManager createUsuarioManager() {

        UsuarioManager usuarioManager = new UsuarioManagerImplentacion();

        return usuarioManager;
    }
    
}
