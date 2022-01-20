/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.implementacion;

import static GESRE.controller.GestionTrabajadorViewController.LOGGER;
import GESRE.entidades.Incidencia;
import GESRE.interfaces.IncidenciaManager;
import GESRE.rest.IncidenciaRestCliente;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 * Esta clase es la Implementacion de la Interfaz logica de IncidenciaManager, que utiliza un Cliente RESTful
 *
 * 
 * @author Aritz Arrieta
 */
public class IncidenciaManagerImplementation implements IncidenciaManager {

    private IncidenciaRestCliente webClient;

    @Override
    public Collection<Incidencia> findAll() {
        List<Incidencia> incidencias = null;
        try {

            incidencias = this.webClient.findAll(new GenericType<List<Incidencia>>() {
            });

        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return incidencias;

    }

    @Override
    public Collection<Incidencia> findIncidenciaAcabadaDeUnUsuario(Incidencia incidencia, String idUsuario) {
        List<Incidencia> incidencias = null;
        try {

            incidencias = this.webClient.findIncidenciaAcabadaDeUnUsuario(new GenericType<List<Incidencia>>() {
            }, idUsuario);

        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return incidencias;

    }

    @Override
    public Collection<Incidencia> findIncidenciaDeUnTrabajador(Incidencia incidencia, String idUsuario) {
        List<Incidencia> incidencias = null;
        try {

            incidencias = this.webClient.findIncidenciaDeUnTrabajador(new GenericType<List<Incidencia>>() {
            }, idUsuario);

        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return incidencias;

    }

    @Override
    public Collection<Incidencia> findIncidenciaDeUnCliente(Incidencia incidencia, String idUsuario) {
        List<Incidencia> incidencias = null;
        try {

            incidencias = this.webClient.findIncidenciaDeUnUsuario(new GenericType<List<Incidencia>>() {
            }, idUsuario);

        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return incidencias;

    }

    @Override
    public void createIncidencia(Incidencia incidencia) {
        this.webClient.create(incidencia);
    }

    @Override
    public void removeIncidencia(String id) {
        try {
            LOGGER.info("IncidenciaManagerImplementation: Eliminando Incidencia");
            this.webClient.remove(id);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void editIncidencia(Incidencia incidencia, String id) {
        try {
            LOGGER.log(Level.INFO, "IncidenciaManager: Actualizando Incidencia '{0}'...", incidencia.getId());
            //enviar los datos de la pieza al cliente web para su creacion 
            this.webClient.edit(incidencia, id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "IncidenciaManager: Error al intentar crear Incidencia, {0}", ex.getMessage());
        }
    }

    @Override
    public void findIncidencia(Incidencia incidencia, String id) {
      try {
            LOGGER.log(Level.INFO, "IncidenciaManager: Actualizando Incidencia '{0}'...", incidencia.getId());
            //enviar los datos de la pieza al cliente web para su creacion 
            //this.webClient.find(incidencia, id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "IncidenciaManager: Error al intentar crear Incidencia, {0}", ex.getMessage());
        }
    }

}
