package GESRE.controller;


import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

    //Attributes, @FXML allows interaction with controls from the FXML file
    private Stage stage;
    @FXML
    private TextField userTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Button loginBtn;
    @FXML
    private Hyperlink signUpHl;
    @FXML
    private Label errorLbl;
    private String username, password;

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
            errorLbl.setVisible(false);
            errorLbl.setStyle("-fx-text-fill: red");
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
     * Llamar a este método establecerá las propiedades del campo Correo (textProperty())
     *
     * @param observable targeted field whose value changed
     * @param oldValue previous value before change
     * @param newValue last value typed
     */
    private void handleCorreoControl(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            //if a 26 character its typed, take first character to 25 and set it to the field(spaces not allowed)
            if (userTxt.getText().length() > 25) {
                userTxt.setText(userTxt.getText().substring(0, 25));
            }
            //Control empty spaces
            if (userTxt.getText().contains(" ")) {
                userTxt.setText(userTxt.getText().replaceAll(" ", ""));
            }
            //Show error label
            errorLbl.setVisible(false);
            userTxt.setStyle("");
            passwordTxt.setStyle("");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }
}
