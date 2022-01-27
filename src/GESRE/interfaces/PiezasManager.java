package GESRE.interfaces;

import GESRE.entidades.Pieza;
import GESRE.excepcion.PiezaExisteException;
import java.util.Collection;

/**
 * Interfaz de lógica para gestionar piezas
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public interface PiezasManager {

    /**
     * Devuelve una coleccion de Pieza de un trabajador que contiene todos los
     * datos de las piezas
     *
     * @param pieza
     * @param idTrabajador
     * @return devuelve la colección de pieza de un trabajador con todos sus
     * datos
     */
    public Collection<Pieza> findAllPiezaByTrabajadorId(Pieza pieza, Integer idTrabajador);

    /**
     * Devuelve una coleccion de Pieza de un trabajador que contiene todos los
     * datos de las piezas en Stock
     *
     * @param pieza
     * @param idTrabajador
     * @return devuelve la colección de pieza en stock de un trabajador con
     * todos sus datos
     */
    public Collection<Pieza> findAllPiezaByStock(Pieza pieza, Integer idTrabajador);

    /**
     *
     * @param pieza
     * @param nombre
     * @return
     */
    public Collection<Pieza> findAllPiezaByName(Pieza pieza, String nombre);
    
    /**
     *
     * @param pieza
     * @param nombre
     * @return
     * @throws GESRE.excepcion.PiezaExisteException
     */
    public Collection<Pieza> piezaExiste(Pieza pieza, String nombre) throws PiezaExisteException;

    /**
     *
     * @param pieza
     */
    public void createPieza(Pieza pieza);

    /**
     *
     * @param pieza
     */
    public void removePieza(Integer id);

    /**
     *
     * @param pieza
     */
    public Pieza findPieza(Integer id);

    /**
     *
     * @param pieza
     */
    public void editPieza(Pieza pieza, Integer id);

}
