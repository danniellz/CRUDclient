/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.interfaces;

import GESRE.entidades.Usuario;
import java.util.Collection;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author Jonathan Viñan
 */
public interface UsuarioManager {

    /**
     * Método que añade un nuevo trabajador creado.
     *
     * @param usuario Objeto trabajadior que se va a añadir.
     */
    public void createUsuario(Usuario usuario);

    /**
     * Método que actualiza la información de un trabajador existente.
     *
     * @param usuario Objeto trabajador que se va a actualizar.
     */
    public void editUsuario(Usuario usuario);

    /**
     * Método que elimina un Trabajador existente.
     *
     * @param usuario El objeto Trabajador que se va a eliminar.
     */
    public void removeUsuario(Usuario usuario);

    public Usuario find_XML(Usuario usuario, Integer id);

    public void remove_XML(Usuario usuario);

    public Collection<Usuario> buscarUserPorLogin_XML(String login);

    public Usuario buscarUsuarioPorEmail_XML(String correo);

    public Collection<Usuario> buscarUsuarioPorLoginYContraseniav1_XML(String login, String password);

    public Usuario resetPasswordByLogin_XML(String email);

    public Collection<Usuario> buscarTodosLosTrabajadores_XML();

    public Usuario findAll_XML();

    public Collection <Usuario> buscarUsuarioParaEnviarMailRecuperarContrasenia_XML(Usuario usuario);

}
