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
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
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
            },idUsuario);

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
            },idUsuario);

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
            },idUsuario);

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
            LOGGER.info("TrabajadorManagerImplementation: Eliminando Trabajador");
            this.webClient.remove(id);
       }catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void editIncidencia(Incidencia incidencia) {
       
    }

    @Override
    public void findIncidencia(Incidencia incidencia, String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
