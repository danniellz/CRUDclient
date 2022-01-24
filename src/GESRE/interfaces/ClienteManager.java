/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.interfaces;

import GESRE.entidades.Cliente;
import java.util.Collection;

/**
 *
 * @author Mikel Matilla
 */
public interface ClienteManager {
    
    public Collection<Cliente> findAllClientes();
    
    public Collection<Cliente> findAllClienteWithIncidencia(Cliente cliente);
    
    public Cliente findClienteByFullName(String fullName);
    
    public void createCliente(Cliente cliente);
    
    public void editCliente(Cliente cliente, String id);
    
    public void deleteCliente(String id);
    
}
