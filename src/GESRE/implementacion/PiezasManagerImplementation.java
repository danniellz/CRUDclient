package GESRE.implementacion;

import GESRE.entidades.Pieza;
import GESRE.interfaces.PiezasManager;
import GESRE.rest.PiezaRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 * Esta clase implementa la interfaz de lógica 'PiezaManager' utilizando un Cliente Web RESTful
 * 
 * @author Daniel Brizuela
 * @version 1.0
 */
public class PiezasManagerImplementation implements PiezasManager {

    private PiezaRESTClient webClient;

    /**
    * 
    */
    public PiezasManagerImplementation() {
        webClient = new PiezaRESTClient();
    }
    //LOGGER
    private static final Logger LOG = Logger.getLogger(PiezasManagerImplementation.class.getName());

    /**
     * 
     * @param pieza
     * @param idTrabajador
     * @return 
     */
    @Override
    public Collection<Pieza> findAllPiezaByTrabajadorId(Pieza pieza, Integer idTrabajador) {
        List<Pieza> piezas = null;
        try {
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas del trabajador con ID: "+idTrabajador);
            piezas = webClient.findAllPiezaByTrabajadorId_xml(new GenericType<List<Pieza>>() {}, idTrabajador);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar buscar todas las piezas del trabajador", ex.getMessage());
        }
        return piezas;
    }

    /**
     * 
     * @param pieza
     * @param idTrabajador
     * @return 
     */
    @Override
    public Collection<Pieza> findAllPiezaByStock(Pieza pieza, Integer idTrabajador) {
        List<Pieza> piezas = null;
        try {
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas en Stock del trabajador con ID: "+idTrabajador);
            piezas = webClient.findAllPiezaInStock_xml(new GenericType<List<Pieza>>() {}, idTrabajador);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar buscar todas las piezas en stock", ex.getMessage());
        }
        return piezas;
    }

    /**
     * 
     * @param pieza
     * @param nombre
     * @return 
     */
    @Override
    public Collection<Pieza> findAllPiezaByName(Pieza pieza, String nombre) {
        List<Pieza> piezas = null;
        try {
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas con el nombre: "+nombre);
            piezas = webClient.findAllPiezaByName_xml(new GenericType<List<Pieza>>() {}, nombre);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar buscar todas las piezas por nombre", ex.getMessage());
        }
        return piezas;
    }

    /**
     * 
     * @param pieza 
     */
    @Override
    public void createPieza(Pieza pieza) {
        try {
            LOG.log(Level.INFO, "PiezasManager: Creando Pieza {0}...", pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.create_xml(pieza);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar crear Pieza, {0}", ex.getMessage());
        }
    }

    /**
     * 
     * @param pieza 
     */
    @Override
    public void removePieza(Pieza pieza) {
        try {
            LOG.log(Level.INFO, "PiezasManager: Creando Pieza {0}...", pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.remove_xml(pieza.getId());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar crear Pieza, {0}", ex.getMessage());
        }
    }

    /**
     * 
     * @param id
     * @return 
     */
    @Override
    public Pieza findPieza(Integer id) {
        Pieza pieza = null;
        try {
            /*if(webClient.find_xml(Pieza.class, id)!=null){
                //Pieza Existe
            }*/
            LOG.log(Level.INFO, "PiezasManager: Buscando Pieza {0}...", id);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar crear Pieza, {0}", ex.getMessage());
        }
        return pieza;
    }

    /**
     * 
     * @param pieza 
     */
    @Override
    public void editPieza(Pieza pieza) {
        try {
            LOG.log(Level.INFO, "PiezasManager: Actualizando Pieza '{0}'...", pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.edit_xml(pieza);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar crear Pieza, {0}", ex.getMessage());
        }
    }

}
