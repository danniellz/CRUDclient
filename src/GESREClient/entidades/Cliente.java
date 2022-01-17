package GESREClient.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase de la entidad Cliente
 *
 * @author Mikel Matilla
 */
@XmlRootElement
public class Cliente extends Usuario implements Serializable {

    //Atributos
    /**
     * Relacion de incidencias
     */
    private Set<Incidencia> incidencias;

    /**
     * Fecha de registro del cliente
     */
    private Date fechaRegistro;

    //Getters & Setters
    /**
     * Metodo getter de incidencias
     *
     * @return Incidencias
     */
    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    /**
     * Metodo setter de incidencias
     *
     * @param incidencias Incidencias
     */
   //@XmlTransient
    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    /**
     * Metodo getter de la fecha de registro
     *
     * @return fecha de registro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Metodo setter de la fecha de registro
     *
     * @param fechaRegistro Fecha de registro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.incidencias, other.incidencias)) {
            return false;
        }
        return Objects.equals(this.fechaRegistro, other.fechaRegistro);
    }

    /**
     * Metodo para obtener la informacion de Cliente
     *
     * @return devuelve una representacion en forma de texto de Cliente
     */
    @Override
    public String toString() {
        return "Cliente{" + "incidencias=" + incidencias + ", fechaRegistro=" + fechaRegistro + '}';
    }

}
