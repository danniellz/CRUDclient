package GESRE.factoria;


import GESRE.excepcion.ServerDesconectadoException;
import GESRE.implementacion.PiezasManagerImplementation;
import GESRE.implementacion.TrabajadorManagerImplementacion;
import GESRE.implementacion.UsuarioManagerImplentacion;
import GESRE.interfaces.PiezasManager;
import GESRE.interfaces.TrabajadorManager;
import GESRE.interfaces.UsuarioManager;

import java.util.logging.Logger;

/**
 * Clase de factoría que gestiona las implementaciones.
 *
 * @author Jonathan, Daniel
 * @version 1.0
 */
public class GestionFactoria {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger("implementaciones.GestionFactoria");

    /**
     * Método que crea una nueva implementación para la gestión de profesores.
     *
     * @return la implementación.
     */
    public static TrabajadorManager getTrabajadorGestion() {
        LOGGER.info("GestionFactoria: Creando la implementacion de Trabajador");
        TrabajadorManager trabajadorGestion = new TrabajadorManagerImplementacion();
        return trabajadorGestion;
    }

    public static PiezasManager getPiezaManager() throws ServerDesconectadoException {
        LOGGER.info("GestionFactoria: Creando la implementacion de Piezas");
        PiezasManager piezaManager = new PiezasManagerImplementation();
        return piezaManager;
    }
    
     public static UsuarioManager getUsuarioGestion() {
        LOGGER.info("GestionFactoria: Creando la implementacion de Trabajador");

        UsuarioManager UsuarioGestion = new UsuarioManagerImplentacion();

        return UsuarioGestion;
    }
}
