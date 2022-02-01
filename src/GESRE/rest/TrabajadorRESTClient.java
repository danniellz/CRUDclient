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
 * Cliente REST de Jersey generado para el recurso REST:TrabajadorRESTClient [usuario].
 * Contiene métodos necesarios para invocar un servicio web RESTful de trabajador
 * que resida en un servidor Glassfish.< br >
 *
 * Jersey REST client generated for REST resource:TrabajadorFacadeREST
 * [entidades.trabajador]<br>
 * USAGE:
 * <pre>
 *        TrabajadorRESTClient client = new TrabajadorRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jonathan viñan
 */
public class TrabajadorRESTClient {

    private WebTarget webTarget;
    private Client client;
    /**
     * Coge el URI de un archivo de propiedades.
     */
    private static final String BASE_URI = ResourceBundle.getBundle("GESRE.archivos.config").getString("RESTFUL_URI");

    /**
     * Construye un TrabajadorRESTClient. Crea un cliente web RESTful y
     * establece la ruta del objeto webtarget asociado al cliente.
     */
    public TrabajadorRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entidades.trabajador");//entidades.trabajador
    }
                    
    /**
     * Crea una reperesentación XML de la entidad Trabajdor y la manda al
     * servicio web trabajador RESTful como petición para crearlo.
     *
     * @param requestEntity Objeto con los datos a ser creados.
     * @throws ClientErrorException Si hay un error durante el proceso. El error
     * va envuelto en una respuesta de error de HTTP.
     */
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea una reperesentación XML de la entidad Trabajador y la manda al
     * servicio web trabajador RESTful como petición para modificarlo.
     *
     * @param requestEntity Objeto con los datos a ser modificados.
     * @throws ClientErrorException Si hay un error durante el proceso. El error
     * va envuelto en una respuesta de error de HTTP.
     */
    public void edit_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Obtiene una reperesentación XML de la entidad Trabajador del servicio web
     * trabajdor RESTful y lo devuelve como un objeto de tipo genérico.
     *
     * @param <T> Clase de tipo generico.
     * @param responseType La clase objeto de la instancia de retorno.
     * @param id La id de la instancia del lado servidor.
     * @return El objeto con los datos.
     * @throws ClientErrorException Si hay un error durante el proceso. El error
     * va envuelto en una respuesta de error de HTTP.
     */
    public <T> T find_XML(Class<T> responseType, Integer id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Manda al servicio web trabajador RESTful una petición para eliminar un
     * trabajador identificado por el trabajador.
     *
     * @param id El id de la entidad trabajador que se eliminará.
     * @throws ClientErrorException Si hay un error durante el proceso. El error
     * va envuelto en una respuesta de error de HTTP.
     */
    public void remove(Integer id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Obtiene una lista de reperesentaciónes XML de la entidad Trabajador del
     * servicio web trabajaddor RESTful y lo devuelve como un objeto de tipo
     * genérico.
     *
     * @param <T> Clase de tipo generico.
     * @param responseType La clase objeto de la instancia de retorno.
     * @return Coleccion de objetos con los datos.
     * @throws ClientErrorException Si hay un error durante el proceso. El error
     * va envuelto en una respuesta de error de HTTP.
     */
    public <T> T buscarTodosLosTrabajadores_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("BuscarTodosLosTrabajadores");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de reperesentaciónes XML de la entidad Trabajador del
     * servicio web trabajador RESTful y lo devuelve como un objeto de tipo
     * genérico.
     *
     * @param <T> Clase de tipo generico.
     * @param responseType La clase objeto de la instancia de retorno.
     * @param fullName El Nombre completo de la instancia del lado servidor.
     * @return Coleccion de objetos con los datos.
     * @throws ClientErrorException Si hay un error durante el proceso. El error
     * va envuelto en una respuesta de error de HTTP.
     */
    public <T> T buscarTrabajadorPorNombre_XML(GenericType<T> responseType, String fullName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("BuscarUnTrabajador/{0}", new Object[]{fullName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de reperesentaciónes XML de la entidad Trabajador del
     * servicio web trabajaddor RESTful y lo devuelve como un objeto de tipo
     * genérico.
     *
     * @param <T> Clase de tipo generico.
     * @param responseType La clase objeto de la instancia de retorno.
     * @return Coleccion de objetos con los datos.
     * @throws ClientErrorException Si hay un error durante el proceso. El error
     * va envuelto en una respuesta de error de HTTP.
     */
    public <T> T buscarTrabajadoresSinIncidencias_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("BuscarTrabajadoresSinIncidencias");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Cierra el servicio web RESTful del cliente.
     */
    public void close() {
        client.close();
    }

}
