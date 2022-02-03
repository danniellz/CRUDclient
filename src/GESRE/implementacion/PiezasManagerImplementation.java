package GESRE.implementacion;

import GESRE.entidades.Pieza;
import GESRE.excepcion.PiezaExisteException;
import GESRE.excepcion.ServerDesconectadoException;
import GESRE.interfaces.PiezasManager;
import GESRE.rest.PiezaRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 * Esta clase implementa la interfaz de lógica 'PiezaManager' utilizando un
 * Cliente Web RESTful
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public class PiezasManagerImplementation implements PiezasManager {

    private PiezaRESTClient webClient;

    /**
     * Constructor de la implementacion con conexion al servidor
     *
     * @throws GESRE.excepcion.ServerDesconectadoException suelta una excepcion
     * si no hay conexion con el servidor
     */
    public PiezasManagerImplementation()  {
        webClient = new PiezaRESTClient();
    }
    //LOGGER
    private static final Logger LOG = Logger.getLogger(PiezasManagerImplementation.class.getName());

    /**
     * Método que busca todas las piezas de un trabajador
     *
     * @param pieza entidad pieza
     * @param idTrabajador identificador del trabajador
     * @return devuelve todas las piezas de un trabajador
     * @throws ServerDesconectadoException
     */
    @Override
    public Collection<Pieza> findAllPiezaByTrabajadorId(Pieza pieza, Integer idTrabajador) throws ServerDesconectadoException {
        List<Pieza> piezas = null;
        try {
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas del trabajador con ID: " + idTrabajador);
            piezas = webClient.findAllPiezaByTrabajadorId_xml(new GenericType<List<Pieza>>() {
            }, idTrabajador);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar buscar todas las piezas del trabajador", ex.getMessage());
        } catch (Exception ex) {
            LOG.severe("Error en PiezasManagerImplementation");
            throw new ServerDesconectadoException("No hay conexión con el servidor");
        }
        return piezas;
    }

    /**
     * Método para buscar todas las piezas en stock del trabajador
     *
     * @param pieza entidad pieza
     * @param idTrabajador identificador del trabajador
     * @return devuelve todas las piezas en stock del trabajador
     * @throws ServerDesconectadoException suelta una excepcion si no hay
     * conexion con el servidor
     */
    @Override
    public Collection<Pieza> findAllPiezaByStock(Pieza pieza, Integer idTrabajador) throws ServerDesconectadoException {
        List<Pieza> piezas = null;
        try {
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas en Stock del trabajador con ID: " + idTrabajador);
            piezas = webClient.findAllPiezaInStock_xml(new GenericType<List<Pieza>>() {
            }, idTrabajador);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar buscar todas las piezas en stock", ex.getMessage());
        } catch (Exception ex) {
            LOG.severe("Error en PiezasManagerImplementation");
            throw new ServerDesconectadoException("No hay conexión con el servidor");
        }
        return piezas;
    }

    /**
     * Método para buscar todas las piezas con el nombre introducido entre todos
     * los trabajadores
     *
     * @param pieza entidad pieza
     * @param nombre nombre de la pieza
     * @return devuelve la informacion de las piezas con ese nombre
     * @throws ServerDesconectadoException suelta una excepcion si no hay
     * conexion con el servidor
     */
    @Override
    public Collection<Pieza> findAllPiezaByName(Pieza pieza, String nombre) throws ServerDesconectadoException {
        List<Pieza> piezas = null;
        try {
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas con el nombre: " + nombre);
            piezas = webClient.findAllPiezaByName_xml(new GenericType<List<Pieza>>() {
            }, nombre);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar buscar todas las piezas por nombre", ex.getMessage());
        } catch (Exception ex) {
            LOG.severe("Error en PiezasManagerImplementation");
            throw new ServerDesconectadoException("No hay conexión con el servidor");
        }
        return piezas;
    }

    /**
     * Método para comprobar si una pieza existe o no al intentar crear una
     * nueva pieza
     *
     * @param pieza entidad pieza
     * @param nombre nombre de la pieza
     * @return devuelve la informacion de la pieza si existe
     * @throws PiezaExisteException suelta una excepcion si la pieza Existe al
     * intentar crearla
     */
    @Override
    public Collection<Pieza> piezaExiste(Pieza pieza, String nombre) throws PiezaExisteException {
        List<Pieza> piezas = null;
        try {
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas con el nombre: " + nombre);
            piezas = webClient.findAllPiezaByName_xml(new GenericType<List<Pieza>>() {
            }, nombre);
            if (!piezas.isEmpty()) {
                throw new PiezaExisteException();
            }

        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar buscar todas las piezas por nombre", ex.getMessage());
        }
        return piezas;
    }

    /**
     * Método para crear una nueva pieza
     *
     * @param pieza entidad pieza
     * @throws ServerDesconectadoException suelta una excepcion si no hay
     * conexion con el servidor
     */
    @Override
    public void createPieza(Pieza pieza) throws ServerDesconectadoException {
        try {
            LOG.log(Level.INFO, "PiezasManager: Creando Pieza {0}...", pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.create_xml(pieza);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar crear Pieza, {0}", ex.getMessage());
        } catch (Exception ex) {
            LOG.severe("Error en PiezasManagerImplementation");
            throw new ServerDesconectadoException("No hay conexión con el servidor");
        }
    }

    /**
     * Método para borrar una pieza mediante su id
     *
     * @param id identificador de la pieza
     * @throws ServerDesconectadoException suelta una excepcion si no hay
     * conexion con el servidor
     */
    @Override
    public void removePieza(Integer id) throws ServerDesconectadoException {
        try {
            LOG.log(Level.INFO, "PiezasManager: Borrando Pieza ");
            //enviar los datos de la pieza al cliente web para su borrado
            webClient.remove_xml(id);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar crear Pieza, {0}", ex.getMessage());
        } catch (Exception ex) {
            LOG.severe("Error en PiezasManagerImplementation");
            throw new ServerDesconectadoException("No hay conexión con el servidor");
        }
    }

    /**
     * Método para buscar una pieza por su Id
     *
     * @param id identificador de la pieza
     * @return devuelve toda la informacion de una pieza
     * @throws ServerDesconectadoException suelta una excepcion si no hay
     * conexion con el servidor
     */
    @Override
    public Pieza findPieza(Integer id) throws ServerDesconectadoException {
        Pieza pieza = null;
        try {
            pieza = webClient.find_xml(Pieza.class, id);
            LOG.log(Level.INFO, "PiezasManager: Buscando Pieza con ID " + id);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar Buscar Pieza, {0}", ex.getMessage());
        } catch (Exception ex) {
            LOG.severe("Error en PiezasManagerImplementation");
            throw new ServerDesconectadoException("No hay conexión con el servidor");
        }
        return pieza;
    }

    /**
     * Método para editar una pieza
     *
     * @param pieza entidad pieza
     * @param id identificador de la pieza
     * @throws ServerDesconectadoException suelta una excepcion si no hay
     * conexion con el servidor
     */
    @Override
    public void editPieza(Pieza pieza, Integer id) throws ServerDesconectadoException {
        try {
            LOG.log(Level.INFO, "PiezasManager: Actualizando Pieza " + pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.edit_xml(pieza, id);
        } catch (ClientErrorException ex) {
            LOG.log(Level.SEVERE, "PiezasManager: Error al intentar crear Pieza, {0}", ex.getMessage());
        } catch (Exception ex) {
            LOG.severe("Error en PiezasManagerImplementation");
            throw new ServerDesconectadoException("No hay conexión con el servidor");
        }
    }

}
