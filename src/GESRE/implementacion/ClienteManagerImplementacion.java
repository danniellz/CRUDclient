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
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Mikel Matilla
 */
public class ClienteManagerImplementacion implements GESRE.interfaces.ClienteManager {
    
    private ClienteRESTclient webClient;
    
    public ClienteManagerImplementacion(){
        webClient = new ClienteRESTclient();
    }

    @Override
    public Collection<Cliente> findAllClientes() {
        List<Cliente> clientes = null;
        
        clientes = webClient.findAll(new GenericType<List<Cliente>>() {});
        
        return clientes;
    }

    @Override
    public Collection<Cliente> findAllClienteWithIncidencia(Cliente cliente) {
        List<Cliente> clientes = null;
        
        clientes = webClient.findAllClienteWithIncidencia(new GenericType<List<Cliente>>() {});
        
        return clientes;
    }

    @Override
    public Cliente findClienteByFullName(String fullName) {
        Cliente cliente = null;
        
        webClient.findClienteByFullName(new GenericType<List<Cliente>>() {}, fullName);
        
        return cliente;
    }

    @Override
    public void createCliente(Cliente cliente) {
        
        webClient.create(cliente);
        
    }

    @Override
    public void editCliente(Cliente cliente, String id) {
        
        webClient.edit(cliente, id);
        
    }

    @Override
    public void deleteCliente(String id) {
        
        webClient.remove(id);
        
    }
    
}
