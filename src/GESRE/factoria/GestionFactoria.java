/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.factoria;

import GESRE.implementacion.TrabajadorGestionImplementation;
import GESRE.interfaces.TrabajadorGestion;
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

    public static TrabajadorGestion getTrabajadorGestion() {
        LOGGER.info("GestionFactoria: Creando la implementacion de Trabajador");

        TrabajadorGestion trabajadorGestion = new TrabajadorGestionImplementation();

        return ;
    }
}
