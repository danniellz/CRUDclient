package GESRE.entidades;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que contiene las IDs de la entidad Recoge
 *
 * @author Aritz Arrieta
 */
public class RecogeId implements Serializable {

    private Integer trabajador_idUsuario;
    private Integer incidencia_id;

    /**
     * Getter trabajador_idUsuario
     *
     * @return devuelve el id del trabajador
     */
    public Integer getTrabajador_idUsuario() {
        return trabajador_idUsuario;
    }

    /**
     * Setter trabajador_idUsuario
     *
     * @param trabajador_idUsuario id del trabajador
     */
    public void setTrabajador_idUsuario(Integer trabajador_idUsuario) {
        this.trabajador_idUsuario = trabajador_idUsuario;
    }

    /**
     * Getter incidencia_id
     *
     * @return devuelve el id de la incidencia
     */
    public Integer getIncidencia_id() {
        return incidencia_id;
    }

    /**
     * Setter incidencia_id
     *
     * @param incidencia_id id de la incidenia
     */
    public void setIncidencia_id(Integer incidencia_id) {
        this.incidencia_id = incidencia_id;
    }

    /**
     * Hashcode
     *
     * @return devuelve el hascode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.trabajador_idUsuario);
        hash = 59 * hash + Objects.hashCode(this.incidencia_id);
        return hash;
    }

    /**
     * Metodo para comparar si dos clases son iguales
     *
     * @param obj objeto a comparar
     * @return devuelve un booleano si es igual o no
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
        final RecogeId other = (RecogeId) obj;
        if (!Objects.equals(this.trabajador_idUsuario, other.trabajador_idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.incidencia_id, other.incidencia_id)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo para obtener la informacion de RecogeId
     *
     * @return devuelve una representacion en forma de texto de RecogeId
     */
    @Override
    public String toString() {
        return "RecogeId{" + "trabajador_idUsuario=" + trabajador_idUsuario + ", incidenciaId=" + incidencia_id + '}';
    }

}
