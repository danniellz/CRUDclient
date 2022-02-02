/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.interfaces;

import GESRE.entidades.Incidencia;
import java.util.Collection;

/**
 *
 * @author Aritz Arrieta
 */
public interface IncidenciaManager {

    /**
     *
     * @param incidencia
     * @return
     */
    public Collection<Incidencia> findAll();

    /**
     *
     * @param incidencia
     * @param idUsuario
     * @return
     */
    public Collection<Incidencia> findIncidenciaAcabadaDeUnUsuario(Incidencia incidencia, String idUsuario);

    /**
     *
     * @param incidencia
     * @param idUsuario
     * @return
     */
    public Collection<Incidencia> findIncidenciaDeUnTrabajador(Incidencia incidencia, String idUsuario);

    /**
     *
     * @param incidencia
     * @param idUsuario
     * @return
     */
    public Collection<Incidencia> findIncidenciaDeUnCliente(Incidencia incidencia, String idUsuario);

    /**
     *
     * @param incidencia
     */
    public void createIncidencia(Incidencia incidencia);

    /**
     *
     * @param incidencia
     */
    public void removeIncidencia(String id);

    /**
     *
     * @param incidencia
     */
    public void editIncidencia(Incidencia incidencia, String id);
    
    /**
     *
     * @param incidencia
     * @param id
     */
    public void findIncidencia(Incidencia incidencia, String id);
}
