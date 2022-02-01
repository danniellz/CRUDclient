package GESRE.controller;

import GESRE.excepcion.ServerDesconectadoException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.UsuarioManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana de recuperación de contraseña
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public class ResetContraController {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(ResetContraController.class.getName());

    /**
     * patrón para el email
     */
    public static final Pattern VALIDEMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    //Attributes, @FXML allows interaction with controls from the FXML file
    private Stage stage;

    //********TEXT FIELDS********
    @FXML
    private Label messageLbl;

    //********TEXT FIELDS********
    @FXML
    private TextField txtCorreo;

    //********BOTONES*******
    @FXML
    private Button btnResetear;
    @FXML
    private Button btnVolver;
    /**
     * Variable que hace una llamada al método que gestiona los grupos de la
     * factoría.
     */
    UsuarioManager usuarioManager = GestionFactoria.getUsuarioGestion();

    /**
     * Establecer el Stage
     *
     * @param primaryStage contriene el valor del stage
     */
    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    /**
     * Inicializar la ventana
     *
     * @param root contiene el FXML
     */
    public void initStage(Parent root) {
        try {
            LOG.info("Inicializando Ventana...");
            //Crear Scene
            Scene scene = new Scene(root);
            //Asociar la Scene a la ventana
            stage.setScene(scene);
            //Propiedades de la ventana
            stage.setTitle("Resetear Contraseña");
            stage.setResizable(false);
            stage.setOnCloseRequest(this::handleCloseRequest);
            //Controles
            messageLbl.setVisible(false);
            btnResetear.setOnAction(this::handleBtnResetear);
            txtCorreo.textProperty().addListener(this::limitCorreoTextField);
            //Mostrar ventana
            stage.show();
            LOG.info("Ventana Actual: ResetContra");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Stage init error", ex);
        }

    }

    /**
     * Calling this method will close the app (EJ: Pressing the window exit
     * button)
     *
     * @param closeEvent A window event
     */
    private void handleCloseRequest(WindowEvent closeEvent) {
        try {
            LOG.info("Close Confirmation Window has Opened");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Are you sure you want to exit?");
            alert.setTitle("Exit");
            //Close if press OK, cancel if not
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LOG.info("Accept Button Press - Closing...");
                Platform.exit();
            } else {
                LOG.info("Cancel Button Pressed - Closing Canceled");
                //Cancel the event process
                closeEvent.consume();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Close request error", ex);
        }
    }

    /**
     * Abrir de la ventana de SignIn
     *
     * @param primaryStage Objeto Stage (ventana)
     * @throws IOException salta un error si la ventana falla en abrirse
     */
    private void startSignInWindow() throws IOException {
        try {
            LOG.info("Inicializando Ventana SignIn...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/SignIn.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            SignInController signinController = ((SignInController) loader.getController());
            //Set the stage
            signinController.setStage(stage);
            //initialize the window
            signinController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting SignUp Window", ex);
        }
    }

    /**
     * Botón que hará la operación de resetear la contraseña
     *
     * @param resetearEvent evento resetear contraseña
     */
    private void handleBtnResetear(ActionEvent resetearEvent) {
        Matcher matcher = VALIDEMAIL.matcher(txtCorreo.getText());
        if (txtCorreo.getText().isEmpty()) {
            LOG.warning("El campo Correo está vacio");
            messageLbl.setText("Porfavor, introduce un correo");
            messageLbl.setStyle("-fx-text-fill: #DC143C");
            txtCorreo.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
            messageLbl.setVisible(true);
            txtCorreo.requestFocus();
        } else if (matcher.find()) {
            messageLbl.setVisible(false);
            txtCorreo.setStyle("");
            messageLbl.setStyle("-fx-border-color: WHITE;");

            //Metodo buscar usuario por correo
        } else {
            LOG.warning("Formato de correo no válido");
            messageLbl.setText("Por Favor, Introduce un Correo valido");
            txtCorreo.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
            messageLbl.setVisible(true);
            messageLbl.setStyle("-fx-text-fill: #DC143C");
            txtCorreo.requestFocus();

        }
    }

    /* Llamar a este método establece el control del campo Correo (textProperty())
     *
     * @param observable campo objetivo cuyo valor cambia
     * @param oldValue valor previo al cambio
     * @param newValue ultimo valor introducido
     */
    private void limitCorreoTextField(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            //si un 26 caracter es introducido, se borra
            if (txtCorreo.getText().length() > 50) {
                //Preparar mensaje del label
                messageLbl.setText("Límite de 50 caracteres alcanzado");
                //Borrar ultimo caracter introducido
                txtCorreo.deleteNextChar();
                //Color del campo rojo para feedback visual
                txtCorreo.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                //Activar label
                messageLbl.setStyle("-fx-text-fill: #DC143C");
                messageLbl.setVisible(true);

            } else {
                //Mientras los caracteres introducidos sean menor a 50, se desactiva el label y el campo vuelve a su color normal
                txtCorreo.setStyle("-fx-border-color: White;");
                messageLbl.setVisible(false);
                messageLbl.setStyle("");
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }
}
