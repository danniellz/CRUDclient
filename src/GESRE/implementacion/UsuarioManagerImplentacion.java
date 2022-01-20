/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.implementacion;

import GESRE.entidades.Usuario;
import GESRE.interfaces.UsuarioManager;
import GESRE.rest.UsuarioRESTClient;
import java.util.Collection;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;

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
            LOGGER.info("TrabajadorManagerImplementation: Creando Trabajador");
            // Enviar datos de usuario al webClient para su creación.
            webClient.create_XML(usuario);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void editUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario find_XML(Usuario usuario, Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove_XML(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Usuario> buscarUserPorLogin_XML(String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario buscarUsuarioPorEmail_XML(String correo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Usuario> buscarUsuarioPorLoginYContraseniav1_XML(String login, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario resetPasswordByLogin_XML(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Usuario> buscarTodosLosTrabajadores_XML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario findAll_XML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Usuario> buscarUsuarioParaEnviarMailRecuperarContrasenia_XML(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
