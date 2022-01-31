package GESRE.entidades;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad Incidencia
 *
 * @author Aritz Arrieta
 */
@XmlRootElement
public class Incidencia implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * integer identificador de la Indidencia
     */
    private Integer id;

    /**
     * integer estrellas para medir valoraciones
     */
    private Integer estrellas;
    /**
     * integer Horas para saber cuanto ha tardado el trabajador
     */

    private Integer horas;

    /**
     * Precio Final de la Incidencia
     */
    private Double precio;

    private Set<Recoge> recoge;

    private Cliente cliente;

    private Pieza pieza;
    /**
     * enumeracion del Tipo de las Incidencias
     */
    private TipoIncidencia tipoIncidencia;

    /**
     * enumeracion del Estado de las Incidencias
     */
    private EstadoIncidencia estado;

    //@XmlTransient//Si la quitas la anotacion  aparencen toda su informcaion que esta relacionada con el cliente
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * recive el numero de estrellas
     *
     * @return estrellas
     */
    public Integer getEstrellas() {
        return estrellas;
    }

    /**
     * recive el numero de Horas
     *
     * @return horas
     */
    public Integer getHoras() {
        return horas;
    }

    /**
     * recive el precio de la Incidencia
     *
     * @return precio
     */
    public Double getPrecio() {
        return precio;
    }

    public Set<Recoge> getRecoge() {
        return recoge;
    }

    public Pieza getPieza() {
        return pieza;
    }

    /**
     * recive de la enumeracion el Tipo de Incidencia
     *
     * @return tipoInc
     */
    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    /**
     * recive de la enumeracion el Estado de Incidencia
     *
     * @return estado
     */
    public EstadoIncidencia getEstado() {
        return estado;
    }

    /**
     * Recive el Id de Incidencia
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRecoge(Set<Recoge> recoge) {
        this.recoge = recoge;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    //setters
    /**
     * Introduce el numero de estrellas
     *
     * @param estrellas
     */
    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    /**
     * Introduce el numero de horas
     *
     * @param horas
     */
    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    /**
     * Introduce el precio de la Incidencia
     *
     * @param precio
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Introduce el tipo de Incidencia
     *
     * @param tipoIncidencia
     */
    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    /**
     * Introduce el Estado de la Incidencia
     *
     * @param estado
     */
    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    /**
     * Introduce el Id de la Incidencia
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Hashcode
     *
     * @return devuelve el hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);

        return hash;
    }

    /**
     * Metodo equals para comparar si dos objetos son iguales
     *
     * @param obj el otro objeto a comparar
     * @return devuelve un booleano si son o no iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Incidencia other = (Incidencia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.estrellas, other.estrellas)) {
            return false;
        }
        if (!Objects.equals(this.horas, other.horas)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
            return false;
        }
        if (!Objects.equals(this.recoge, other.recoge)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.pieza, other.pieza)) {
            return false;
        }
        if (this.tipoIncidencia != other.tipoIncidencia) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        return true;
    }

    /**
     * Metodo para obtener la informacion de Incidencia
     *
     * @return devuelve una representacion en forma de texto de Incidencia
     */
    @Override
    public String toString() {
        return "Incidencia{" + "id=" + id + ", estrellas=" + estrellas + ", horas=" + horas + ", precio=" + precio + ", tipoIncidencia=" + tipoIncidencia + ", estado=" + estado + '}';
    }

}
