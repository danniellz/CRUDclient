package GESRE.interfaces;

import GESRE.entidades.Pieza;
import GESRE.excepcion.PiezaExisteException;
import GESRE.excepcion.ServerDesconectadoException;
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
     * @param pieza entidad Pieza
     * @param idTrabajador identidicador del trabajador
     * @throws GESRE.excepcion.ServerDesconectadoException si hay una conexion
     * con el servidor, salta una excepcion
     * @return devuelve la colección de pieza de un trabajador con todos sus
     * datos
     */
    public Collection<Pieza> findAllPiezaByTrabajadorId(Pieza pieza, Integer idTrabajador) throws ServerDesconectadoException;

    /**
     * Devuelve una coleccion de Pieza de un trabajador que contiene todos los
     * datos de las piezas en Stock
     *
     * @param pieza entidad pieza
     * @param idTrabajador identidicador del trabajador
     * @throws GESRE.excepcion.ServerDesconectadoException si hay una conexion
     * con el servidor, salta una excepcion
     * @return devuelve la colección de pieza en stock de un trabajador con
     * todos sus datos
     */
    public Collection<Pieza> findAllPiezaByStock(Pieza pieza, Integer idTrabajador) throws ServerDesconectadoException;

    /**
     * Devuelve la Pieza con el nombre introducido de cualquier trabajador
     *
     * @param pieza entidad pieza
     * @param nombre nombre de la pieza
     * @throws GESRE.excepcion.ServerDesconectadoException si hay una conexion
     * con el servidor, salta una excepcion
     * @return devuelve la pieza con el nombre introducido si existe
     */
    public Collection<Pieza> findAllPiezaByName(Pieza pieza, String nombre) throws ServerDesconectadoException;

    /**
     * Método para comprobar si existe una pieza antes de crearla
     *
     * @param pieza entidad pieza
     * @param nombre nombre de la pieza
     * @throws GESRE.excepcion.PiezaExisteException si hay una conexion con el
     * servidor, salta una excepcion
     * @return devuelve la pieza introducida si existe
     */
    public Collection<Pieza> piezaExiste(Pieza pieza, String nombre) throws PiezaExisteException;

    /**
     * Método para crear una pieza
     *
     * @param pieza entidad pieza
     * @throws GESRE.excepcion.ServerDesconectadoException si hay una conexion
     * con el servidor, salta una excepcion
     */
    public void createPieza(Pieza pieza) throws ServerDesconectadoException;

    /**
     * Método para borrar una pieza
     *
     * @param id identificador de la pieza a borrar
     * @throws GESRE.excepcion.ServerDesconectadoException si hay una conexion
     * con el servidor, salta una excepcion
     */
    public void removePieza(Integer id) throws ServerDesconectadoException;

    /**
     * Método para buscar una pieza
     *
     * @param id identificador de la pieza a buscar
     * @throws GESRE.excepcion.ServerDesconectadoException si hay una conexion
     * con el servidor, salta una excepcion
     * @return devuelve toda la informacion de la pieza buscada
     */
    public Pieza findPieza(Integer id) throws ServerDesconectadoException;

    /**
     * Método para Editar/Actualizar una pieza
     *
     * @param pieza entidad pieza
     * @param id identificador de la pieza a editar
     * @throws GESRE.excepcion.ServerDesconectadoException si hay una conexion
     * con el servidor, salta una excepcion
     */
    public void editPieza(Pieza pieza, Integer id) throws ServerDesconectadoException;

}
