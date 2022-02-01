package GESRE.rest;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Cliente REST de Jersey generado para el recurso REST:PiezaRESTClient Contiene
 * métodos necesarios para invocar un servicio web RESTful de Pieza que reside
 * en un servidor Glassfish
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
     * Constructor REST pieza con conexión al servidor
     *
     */
    public PiezaRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entidades.pieza");
    }

    /**
     * Método para buscar todas las piezas de un trabajador
     *
     * @param <T> clase generica
     * @param responseType entidad pieza
     * @param idUsuario identificador del trabajador
     * @return devuelve una coleccion de todas las piezas de un trabajador
     * @throws ClientErrorException
     */
    public <T> T findAllPiezaByTrabajadorId_xml(GenericType<T> responseType, Integer idUsuario) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("idUsuario/{0}", new Object[]{idUsuario}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Método para editar una pieza
     *
     * @param requestEntity entidad pieza
     * @param id identificador de pieza
     * @throws ClientErrorException exepcion que salta si hay un error con el
     * cliente
     */
    public void edit_xml(Object requestEntity, Integer id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Método para buscar piezas por nombre entre todos los trabajadores
     *
     * @param <T> clase generica
     * @param responseType entidad pieza
     * @param nombre nombre de la pieza
     * @return devuelve toda la informacion de la pieza del nombre introducido
     * @throws ClientErrorException exepcion que salta si hay un error con el
     * cliente
     */
    public <T> T findAllPiezaByName_xml(GenericType<T> responseType, String nombre) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("nombre/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Método para buscar una pieza
     *
     * @param <T> clase generica
     * @param responseType entidad pieza
     * @param id identificador de la pieza
     * @return devuelve toda la informacion de una pieza
     * @throws ClientErrorException exepcion que salta si hay un error con el
     * cliente
     */
    public <T> T find_xml(Class<T> responseType, Integer id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Método para crear nueva pieza
     *
     * @param requestEntity entidad pieza
     * @throws ClientErrorException exepcion que salta si hay un error con el
     * cliente
     */
    public void create_xml(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Método para buscar todas las piezas en stock de un trabajador
     *
     * @param <T> clase generica
     * @param responseType entidad pieza
     * @param idUsuario identidicador del trabajador
     * @return devuelve todas las piezas en stock del trabajador
     * @throws ClientErrorException exepcion que salta si hay un error con el
     * cliente
     */
    public <T> T findAllPiezaInStock_xml(GenericType<T> responseType, Integer idUsuario) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("stock/{0}", new Object[]{idUsuario}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Método para buscar todas las piezas
     *
     * @param <T> clase generica
     * @param responseType entidad pieza
     * @return devuelve una coleccion de todas las piezas
     * @throws ClientErrorException exepcion que salta si hay un error con el
     * cliente
     */
    public <T> T findAll_xml(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Método para borrar una pieza
     *
     * @param id identificador de la pieza
     * @throws ClientErrorException exepcion que salta si hay un error con el
     * cliente
     */
    public void remove_xml(Integer id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Cerrar cliente
     */
    public void close() {
        client.close();
    }

}
