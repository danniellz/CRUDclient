package GESRE.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que declara el estado SignIn del usuario
 *
 * @author Jonathan Viñan
 */
@XmlRootElement
public class SignIn implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id del signIn. Es la clave primaria de la tabla "signIn".
     */
    private int id;
    /**
     * Fecha del último acceso del usuario.
     */
    private Date lastSignIn;

    /**
     * Método que obtiene el id del signIn.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Método que establece el id del signIn.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método que obtiene el último acceso del usuario.
     *
     * @return el último acceso que se va a mostrar.
     */
    public Date getLastSignIn() {
        return lastSignIn;
    }

    /**
     * Método que establece el último acceso del usuario.
     *
     * @param LastSignIn el último acceso que se va a guardar.
     */
    public void setLastSignIn(Date lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.lastSignIn);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
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
        final SignIn other = (SignIn) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.lastSignIn, other.lastSignIn)) {
            return false;
        }
        return true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "SignIn{" + "id=" + id + ", lastSignIn=" + lastSignIn + '}';
    }

}
