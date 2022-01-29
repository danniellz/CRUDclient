/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.interfaces;

import GESRE.entidades.Trabajador;
import java.util.Collection;

/**
 * Interfaz de lógica que encapsula métodos para la gestión de trabajadores.
 *
 * @author Jonathan Viñan
 */
public interface TrabajadorManager {

    /**
     * Método que añade un nuevo trabajador creado.
     *
     * @param trabajdor Objeto trabajadior que se va a añadir.
     */
    public void createTrabajador(Trabajador trabajdor);

    /**
     * Método que actualiza la información de un trabajador existente.
     *
     * @param trabajador Objeto trabajador que se va a actualizar.
     */
    public void editTrabajador(Trabajador trabajador);

    /**
     * Método que elimina un Trabajador existente.
     *
     * @param trabajador El objeto Trabajador que se va a eliminar.
     */
    public void removeTrabajador(Trabajador trabajador);

    /**
     * Método que obtiene información de un Trabajador existente por nombre.
     *
     * @param id El id del trabajador del que se quiere obtener la
     * información.
     * @return Objeto Trabajador con la informacion del trabajador
     * buscado.
     */
    public Trabajador findTrabajador(Integer id);

    /**
     * Método que busca un Trabajador por el nombre.
     *
     * @param name
     * @return Colección de los trabajadores existentes.
     */
    public Collection<Trabajador> buscarTrabajadorPorNombre(String name) ;

    /**
     * Método que busca a todos los trabajadores.
     *
     * @return Colección de los trabajadores de la base de datos.
     */
    public Collection<Trabajador> buscarTodosLosTrabajadores();

    /**
     * Método que busca todos los trabajadores que no hayan recogido ninguna
     * incidencia.
     *
     * @return Colección de los trabajadores sin incidencas.
     */
    public Collection<Trabajador> trabajadoresSinIncidencias();
}
