/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.rest;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Cliente REST de Jersey generado para el recurso REST:PiezaRESTClient
 * Contiene métodos necesarios para invocar un servicio web RESTful de Pieza que reside en un servidor Glassfish
 * 
 * Jersey REST client generated for REST resource:PiezaFacadeREST
 * [entidades.pieza]<br>
 * USAGE:
 * <pre>
 *        PiezaRESTClient client = new PiezaRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Daniel Brizuela
 */
public class PiezaRESTClient {

    private WebTarget webTarget;
    private Client client;
    /**
     * Coge el URI de un archivo de propiedades.
     */
    private static final String BASE_URI = ResourceBundle.getBundle("GESRE.archivos.config").getString("RESTFUL_URI");

    /**
     *
     */
    public PiezaRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entidades.pieza");
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param idUsuario
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAllPiezaByTrabajadorId_xml(GenericType<T> responseType, Integer idUsuario) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("idUsuario/{0}", new Object[]{idUsuario}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void edit_xml(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param nombre
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAllPiezaByName_xml(GenericType<T> responseType, String nombre) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("nombre/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException
     */
    public <T> T find_xml(Class<T> responseType, Integer id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create_xml(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param idUsuario
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAllPiezaInStock_xml(GenericType<T> responseType, Integer idUsuario) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("stock/{0}", new Object[]{idUsuario}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAll_xml(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param id
     * @throws ClientErrorException
     */
    public void remove_xml(Integer id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     *
     */
    public void close() {
        client.close();
    }
    
}
