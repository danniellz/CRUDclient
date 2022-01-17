package GESRE.implementacion;

import GESRE.entidades.Pieza;
import GESRE.interfaces.PiezasManager;
import GESRE.rest.PiezaRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public class PiezasManagerImplementation implements PiezasManager{
    private PiezaRESTClient webClient;
    public PiezasManagerImplementation(){
        //webClient=new PiezaRESTful();
    }
    //LOGGER
    private static final Logger LOG = Logger.getLogger(PiezasManagerImplementation.class.getName());

    @Override
    public Collection<Pieza> findAllPiezaByTrabajadorId(Pieza pieza) {
         List<Pieza> piezas =null;
        try{
            LOG.log(Level.INFO, "PiezaManager: Buscando todas las piezas del trabajador {0} from REST service (XML).", pieza.getTrabajador().getFullName());
            //Ask webClient for all users' data.
            piezas = webClient.findAllPiezaByTrabajadorId_XML(new GenericType<List<Pieza>>() {});
        }catch(Exception ex){
            LOG.log(Level.SEVERE,"PiezasManager: Error al intentar buscar todas las piezas del trabajador",ex.getMessage());
        }
        return piezas;
    }

    @Override
    public Collection<Pieza> findAllPiezaByStock(Pieza pieza) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Pieza> findAllPiezaByName(Pieza pieza) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Pieza pieza) {
        try{
            LOG.log(Level.INFO, "PiezasManager: Creando Pieza {0}...", pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.create(pieza);
        }catch(Exception ex){
            LOG.log(Level.SEVERE,"PiezasManager: Error al intentar crear Pieza, {0}",ex.getMessage());
        }
    }

    @Override
    public void remove(Pieza pieza) {
        try{
            LOG.log(Level.INFO, "PiezasManager: Creando Pieza {0}...", pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.remove(pieza);
        }catch(Exception ex){
            LOG.log(Level.SEVERE,"PiezasManager: Error al intentar crear Pieza, {0}",ex.getMessage());
        }
    }

    @Override
    public void find(String nombre) {
        try{
            if(this.webClient.find_XML(Pieza.class, id)!=null){
                //Pieza Existe
            }
            LOG.log(Level.INFO, "PiezasManager: Buscando Pieza {0}...", id);
        }catch(Exception ex){
            LOG.log(Level.SEVERE,"PiezasManager: Error al intentar crear Pieza, {0}",ex.getMessage());
        }
    }

    @Override
    public void edit(Pieza pieza) {
        try{
            LOG.log(Level.INFO, "PiezasManager: Actualizando Pieza '{0}'...", pieza.getNombre());
            //enviar los datos de la pieza al cliente web para su creacion 
            webClient.update_XML(pieza);
        }catch(Exception ex){
            LOG.log(Level.SEVERE,"PiezasManager: Error al intentar crear Pieza, {0}",ex.getMessage());
        }
    }
    
}
