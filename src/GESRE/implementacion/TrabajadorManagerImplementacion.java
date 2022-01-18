/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.implementacion;

import GESRE.entidades.Trabajador;
import GESRE.interfaces.TrabajadorManager;
import GESRE.rest.TrabajadorRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 * Esta clase implementa la interfaz de lógica (TrabajadorManager) usando un web
 * Client RESTful para acceder a la lógica de negocio en un servidor de aplicaciones Java EE.
 * de aplicaciones Java EE.
 *
 * @author Jonathan Viñan
 */
public class TrabajadorManagerImplementacion implements TrabajadorManager {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger("implementaciones.TrabajadorManagerImplementation");

    /**
     * Cliente web de REST TRABAJADOR.
     */
    private TrabajadorRESTClient webClient;

    /**
     * Crea un objeto TrabajadorManagerImplementation. Construye un cliente web
     * para acceder al servicio REST del lado servidor.
     */
    public TrabajadorManagerImplementacion() {
        webClient = new TrabajadorRESTClient();
    }

    /**
     * Añade un nuevo trabajador creado mandando una peticion POST al servicio
     * web RESTful.
     *
     * @param trabajador El objeto Trabajador se añadira
     * @throws ClientErrorException Si hay algun error durante el proceso.
     */
    @Override
    public void createTrabajador(Trabajador trabajador) {
        try {
            LOGGER.info("TrabajadorManagerImplementation: Creando Trabajador");
            // Enviar datos de usuario al webClient para su creación.
            webClient.create_XML(trabajador);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Modifica un trabajador mandando una peticion PUT al servicio web RESTful.
     *
     * @param trabajador El objeto Trabajador que se modificará.
     * @throws ClientErrorException Si hay algun error durante el proceso.
     */
    @Override
    public void editTrabajador(Trabajador trabajador) {
        try {
            LOGGER.info("TrabajadorManagerImplementation: Editando Trabajador");
            //Enviar datos editados a webClient para modificar los nuevos datos al trabajdor
            this.webClient.edit_XML(trabajador);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Elimina un trabajador mandando una peticion DELETE al servicio web
     * RESTful.
     *
     * @param trabajador El objeto Trabajador que se eliminará.
     * @throws ClientErrorException Si hay algun error durante el proceso.
     */
    @Override
    public void removeTrabajador(Trabajador trabajador) {
        try {
            LOGGER.info("TrabajadorManagerImplementation: Eliminando Trabajador");
            //Solicitar a webClient eliminar todos los datos del trabajador mediante el id
            this.webClient.remove(trabajador.getIdUsuario());
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Obtiene un trabajadior mandando una peticion GET al servicio web RESTful.
     *
     * @param id El id del objeto trabajador que se buscará.
     * @return Un objeto Trabajador con los datos.
     * @throws ClientErrorException Si hay algun error durante el proceso.
     */
    @Override
    public Trabajador findTrabajador(Integer id) {
        Trabajador trabajador = null;
        try {
            LOGGER.info("TrabajadorManagerImplementation: Buscando Trabajador por id");
            //Solicitar a webClient los datos del trabajador por su id.
            trabajador = this.webClient.find_XML(Trabajador.class, id);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return trabajador;
    }

    /**
     * Obtiene todos los trabajadores mandando una petición GET al servicio web
     * RESTful.
     *
     * @return Una colección de objetos Trabajador con los datos.
     * @throws ClientErrorException Si hay algun error durante el proceso.
     */
    @Override
    public Collection<Trabajador> buscarTodosLosTrabajadores() {
        List<Trabajador> trabajadores = null;
        try {
            LOGGER.info("TrabajadorManagerImplementation: Buscando todos los trabajadores");
            //Solicitar a webClient los datos de todos los trabajadores de la bace de datos
            trabajadores = webClient.buscarTodosLosTrabajadores_XML(new GenericType<List<Trabajador>>() {
            });
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return trabajadores;
    }

    /**
     * Obtiene todos los trabajadores mandando una petición GET al servicio web
     * RESTful.
     *
     * @return Una colección de objetos Trabajador con los datos.
     * @throws ClientErrorException Si hay algun error durante el proceso.
     */
    @Override
    public Collection<Trabajador> trabajadoresSinIncidencias() {
        List<Trabajador> trabajadores = null;
        try {
            LOGGER.info("TrabajadorManagerImplementation: Buscando todos los trabajadores sin Incidencias");
            //Solicitar a webClient los datos de todos los trabajadores sin incidencas.
            trabajadores = webClient.buscarTrabajadoresSinIncidencias_XML(new GenericType<List<Trabajador>>() {
            });
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return trabajadores;

    }

    /**
     * Obtiene un Trabajador mandando una peticion GET al servicio web RESTful.
     *
     *
     * @param name El Nombre del objeto trabajador que se buscará.
     * @return Una colección de objetos Trabajador con los datos.
     */
    @Override
    public Collection<Trabajador> buscarTrabajadorPorNombre(String name) {
        List<Trabajador> trabajadores = null;
        try {
            LOGGER.info("TrabajadorManagerImplementation: Buscando trabajador por el nombre");
            //Solicitar a webClient los datos de del trabajador buscadon por su nombre.
            trabajadores = webClient.buscarTrabajadorPorNombre_XML(new GenericType<List<Trabajador>>() {
            }, name);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return trabajadores;
    }

}
