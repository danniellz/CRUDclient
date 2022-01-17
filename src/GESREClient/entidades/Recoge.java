package GESREClient.entidades;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase que almacena el trabajador y la incidencia que ha escogido
 *
 * @author Aritz Arrieta
 */
@XmlRootElement
public class Recoge implements Serializable {

    private static final long serialVersionUID = 1L;
    private RecogeId idRecoge;
    private Trabajador trabajador;
    private Incidencia incidencia;
    private Integer horasEstimadas;
    private Date fechaRecogida;

    // getters
    /**
     * Obtiene de Id de Recoge
     *
     * @return Integer
     */
    public RecogeId getIdRecoge() {
        return idRecoge;
    }

    /**
     * Recoge la entidad Trabajador
     *
     * @return trabajador
     */
    @XmlTransient //Si la quitas la anotacion  aparencen toda su informcaion que esta relacionada con el trabajador
    public Trabajador getTrabajador() {
        return trabajador;
    }

    /**
     * Recoge la entidad Incidencia
     *
     * @return incidencia
     */
    @XmlTransient//Si la quitas la anotacion  aparencen toda su informcaion que esta relacionada con la incidencia
    public Incidencia getIncidencia() {
        return incidencia;
    }

    /**
     * Recoge las Horas estimadas
     *
     * @return Integer
     */
    public Integer getHorasEstimadas() {
        return horasEstimadas;
    }

    /**
     * Recoge la fecha de Recogida
     *
     * @return Timestamp
     */
    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    //setters
    /**
     * Establece el Id de Recoger
     *
     * @param idRecoge
     */
    public void setIdRecoge(RecogeId idRecoge) {
        this.idRecoge = idRecoge;
    }

    /**
     * Establece un Trabajdor
     *
     * @param trabajador
     */
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    /**
     * Establece una Incidencia
     *
     * @param incidencia
     */
    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

    /**
     * Establece las horas estimadas
     *
     * @param horasEstimadas
     */
    public void setHorasEstimadas(Integer horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    /**
     * Establece la Fecha Estimada
     *
     * @param fechaRecogida
     */
    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    /**
     * Hashcode
     *
     * @return devuelve el hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idRecoge);

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
        final Recoge other = (Recoge) obj;
        if (!Objects.equals(this.idRecoge, other.idRecoge)) {
            return false;
        }
        if (!Objects.equals(this.trabajador, other.trabajador)) {
            return false;
        }
        if (!Objects.equals(this.incidencia, other.incidencia)) {
            return false;
        }
        if (!Objects.equals(this.horasEstimadas, other.horasEstimadas)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecogida, other.fechaRecogida)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo para obtener la informacion de Recoge
     *
     * @return devuelve una representacion en forma de texto de Recoge
     */
    @Override
    public String toString() {
        return "Recoge{" + "idRecoge=" + idRecoge + ", trabajador=" + trabajador + ", incidencia=" + incidencia + ", horasEstimadas=" + horasEstimadas + ", fechaRecogida=" + fechaRecogida + '}';
    }

}
