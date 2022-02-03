/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.interfaces;

import GESRE.entidades.Usuario;
import GESRE.excepcion.EmailExisteException;
import GESRE.excepcion.EmailNoExisteException;
import GESRE.excepcion.LoginExisteException;
import GESRE.excepcion.LoginNoExisteException;
import GESRE.excepcion.ServerDesconectadoException;
import GESRE.excepcion.UsuarioNoExisteException;
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

    public Usuario findUsuario(Usuario usuario, Integer id);

    /**
     * Método que elimina un Trabajador existente.
     *
     * @param usuario El objeto Trabajador que se va a eliminar.
     */
    public void removeUsuario(Usuario usuario);

    public Collection<Usuario> buscarUsuarioPorLoginCrear(String login) throws LoginExisteException,ServerDesconectadoException;

    public Collection<Usuario> buscarUserPorLoginSignIn(String login) throws LoginNoExisteException;

    public Collection<Usuario> buscarUsuarioPorEmailCrear(String correo) throws EmailExisteException,ServerDesconectadoException;

    public Collection<Usuario> buscarUsuarioPorLoginYContrasenia_Usuario(String login, String password) throws UsuarioNoExisteException;

    public void resetPasswordByLogin_Usuario(String email);

    public Collection<Usuario> buscarTodosLosUsuarios_Usuario();

    public void buscarUsuarioParaEnviarMailRecuperarContrasenia_Usuario(Usuario usuario);

    public Usuario findAll_Usuarios();

}
