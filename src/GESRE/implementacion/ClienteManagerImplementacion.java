/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.implementacion;

import GESRE.entidades.Cliente;
import GESRE.rest.ClienteRESTclient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Mikel Matilla
 */
public class ClienteManagerImplementacion implements GESRE.interfaces.ClienteManager {
    
    private ClienteRESTclient webClient;
    private static final Logger LOGGER=Logger.getLogger("");
    
    public ClienteManagerImplementacion(){
        webClient = new ClienteRESTclient();
    }

    @Override
    public Collection<Cliente> findAllClientes() {
        List<Cliente> clientes = null;
        
        try {
            clientes = webClient.findAll(new GenericType<List<Cliente>>() {});
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UsersManager: Exception finding all users, {0}", ex.getMessage());
        }
        
        return clientes;
    }

    @Override
    public Collection<Cliente> findAllClienteWithIncidencia() {
        List<Cliente> clientes = null;
        
        clientes = webClient.findAllClienteWithIncidencia(new GenericType<List<Cliente>>() {});
        
        return clientes;
    }

    @Override
    public Collection<Cliente> findClienteByFullName(String fullName) {
        List<Cliente> clientes = null;
        
        clientes = webClient.findClienteByFullName(new GenericType<List<Cliente>>() {}, fullName);
        
        return clientes;
    }

    @Override
    public void createCliente(Cliente cliente) {
        
        webClient.create(cliente);
        
    }

    @Override
    public void editCliente(Cliente cliente, int id) {
        
        webClient.edit(cliente, id);
        
    }

    @Override
    public void deleteCliente(int id) {
        
        webClient.remove(id);
        
    }
    
}
