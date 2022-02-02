package GESRE.factoria;

import GESRE.excepcion.ServerDesconectadoException;
import GESRE.implementacion.ClienteManagerImplementacion;
import GESRE.implementacion.IncidenciaManagerImplementation;
import GESRE.implementacion.PiezasManagerImplementation;
import GESRE.implementacion.TrabajadorManagerImplementacion;
import GESRE.interfaces.IncidenciaManager;
import GESRE.implementacion.UsuarioManagerImplentacion;
import GESRE.interfaces.ClienteManager;
import GESRE.interfaces.PiezasManager;
import GESRE.interfaces.TrabajadorManager;
import GESRE.interfaces.UsuarioManager;
import java.util.logging.Logger;

/**
 * Clase de factoría que gestiona las implementaciones.
 
 * @author Jonathan, Daniel, Arizt, Mikel
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

    public static IncidenciaManager getIncidenciaManager(){
        LOGGER.info("GestionFactoria: Creando la implementacion de Incidencias");
        IncidenciaManager incidenciaManager = new IncidenciaManagerImplementation();
        return incidenciaManager;
    }
    
    public static UsuarioManager createUsuarioManager() {
        LOGGER.info("GestionFactoria: Creando la implementacion de Usuario");
        UsuarioManager usuarioManager = new UsuarioManagerImplentacion();
        return usuarioManager;
    }
    
    public static ClienteManager createClienteManager() {
        
        ClienteManager clienteManager = new ClienteManagerImplementacion();
        
        return clienteManager;
    }
    
}
