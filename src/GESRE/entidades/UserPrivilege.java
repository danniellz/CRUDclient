package GESRE.entidades;

/**
 * Clase que declara el privilegio del usuario.
 *
 * @author Jonathan Viñan
 */
public enum UserPrivilege {

    /**
     * El usuario es un usuario corriente.
     */
    USER,
    /**
     * El usuario es un administrador.
     */
    ADMIN,
    /**
     * El usuario es un cliente
     */
    CLIENTE,
    /**
     * El usuario es un Trabajador
     */
    TRABAJADOR
}
