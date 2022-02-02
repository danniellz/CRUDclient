/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.implementacion;

import GESRE.entidades.Usuario;
import GESRE.excepcion.EmailExisteException;
import GESRE.excepcion.LoginExisteException;
import GESRE.excepcion.LoginNoExisteException;
import GESRE.excepcion.UsuarioNoExisteException;
import GESRE.interfaces.UsuarioManager;
import GESRE.rest.UsuarioRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Jonathan Viñan
 */
public class UsuarioManagerImplentacion implements UsuarioManager {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger("implementaciones.UsuarioManagerImplementation");

    /**
     * Cliente web de REST TRABAJADOR.
     */
    private UsuarioRESTClient webClient;

    /**
     * Crea un objeto TrabajadorManagerImplementation. Construye un cliente web
     * para acceder al servicio REST del lado servidor.
     */
    public UsuarioManagerImplentacion() {
        webClient = new UsuarioRESTClient();
    }

    @Override
    public void createUsuario(Usuario usuario) {
        try {
            LOGGER.info("UsuarioManagerImplementation: Creando Usuario");
            // Enviar datos de usuario al webClient para su creación.
            webClient.create_XML(usuario);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void editUsuario(Usuario usuario) {
        try {
            LOGGER.info("UsuarioManagerImplementation: Editando Trabajador");
            //Enviar datos editados a webClient para modificar los nuevos datos al trabajdor
            this.webClient.edit_XML(usuario);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public Usuario findUsuario(Usuario usuario, Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param login
     * @return
     * @throws GESRE.excepcion.LoginExisteException
     * @throws LoginNoExisteException
     */
    @Override
    public Collection<Usuario> buscarUsuarioPorLoginCrear(String login) throws LoginExisteException {
        List<Usuario> usuarios = null;
        try {
            LOGGER.info("TrabajadorManagerImplementation: Buscando trabajador por el nombre");
            //Solicitar a webClient los datos de del trabajador buscadon por su nombre.
            usuarios = webClient.buscarUserPorLogin_XML(new GenericType<List<Usuario>>() {
            }, login);
            if (!usuarios.isEmpty()) {
                throw new LoginExisteException();
            }
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return usuarios;
    }

    /**
     *
     * @param login
     * @return
     * @throws LoginNoExisteException
     */
    @Override
    public Collection<Usuario> buscarUserPorLoginSignIn(String login) throws LoginNoExisteException {
        List<Usuario> usuarios = null;
        try {
            LOGGER.info("TrabajadorManagerImplementation: Buscando trabajador por el nombre");
            //Solicitar a webClient los datos de del trabajador buscadon por su nombre.
            usuarios = webClient.buscarUserPorLogin_XML(new GenericType<List<Usuario>>() {
            }, login);
            if (usuarios.isEmpty()) {
                throw new LoginNoExisteException();
            }
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Collection<Usuario> buscarUsuarioPorEmailCrear(String email) throws EmailExisteException {
        List<Usuario> usuarios = null;
        try {
            LOGGER.info("UsuarioManagerImplementation: Buscando trabajador por el nombre");
            //Solicitar a webClient los datos de del trabajador buscadon por su nombre.
            usuarios = webClient.buscarUsuarioPorEmail_XML(new GenericType<List<Usuario>>() {
            }, email);

            if (!usuarios.isEmpty()) {
                throw new EmailExisteException();
            }
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Collection<Usuario> buscarUsuarioPorLoginYContrasenia_Usuario(String login, String password) throws UsuarioNoExisteException {
        List<Usuario> usuarios = null;
        try {
            LOGGER.info("UsuarioManagerImplementation: Buscando trabajador por login y contraseñas");
            usuarios = webClient.buscarUsuarioPorLoginYContrasenia_XML(new GenericType<List<Usuario>>() {
            }, login, password);
            if (!usuarios.isEmpty()) {
                throw new UsuarioNoExisteException();
            }
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Usuario resetPasswordByLogin_Usuario(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Usuario> buscarTodosLosUsuarios_Usuario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Usuario> buscarUsuarioParaEnviarMailRecuperarContrasenia_Usuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario findAll_Usuarios() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
