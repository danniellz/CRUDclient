package GESRE.interfaces;

import GESRE.entidades.Pieza;
import java.util.Collection;

/**
 * Interfaz de lógica para gestionar piezas
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public interface PiezasManager {

    /**
     * Devuelve una coleccion de Pieza de un trabajador que contiene todos los datos de las piezas
     * @param pieza
     * @return devuelve la colección de pieza de un trabajador con todos sus datos
     */
    public Collection<Pieza> findAllPiezaByTrabajadorId(Pieza pieza);

    /**
     * Devuelve una coleccion de Pieza de un trabajador que contiene todos los datos de las piezas en Stock
     * @param pieza
     * @return devuelve la colección de pieza en stock de un trabajador con todos sus datos
     */
    public Collection<Pieza> findAllPiezaByStock(Pieza pieza);

    /**
     *
     * @param pieza
     * @return
     */
    public Collection<Pieza> findAllPiezaByName(Pieza pieza);

    /**
     *
     * @param pieza
     */
    public void create(Pieza pieza);

    /**
     *
     * @param pieza
     */
    public void remove(Pieza pieza);

    /**
     *
     * @param pieza
     */
    public void find(String nombre);

    /**
     *
     * @param pieza
     */
    public void edit(Pieza pieza);

}
