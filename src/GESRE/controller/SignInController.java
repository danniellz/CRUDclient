package GESRE.controller;

import static GESRE.controller.GestionTrabajadorViewController.LOGGER;
import GESRE.entidades.Cliente;
import GESRE.entidades.Trabajador;
import GESRE.entidades.Usuario;
import GESRE.excepcion.LoginNoExisteException;
import GESRE.excepcion.ServerDesconectadoException;
import GESRE.excepcion.UsuarioNoExisteException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.UsuarioManager;
import java.io.IOException;
import java.util.Collection;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * SignIn window controller class
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public class SignInController {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(SignInController.class.getName());

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
    private Hyperlink contraHL;
    @FXML
    private Label errorLbl;
    private String username, password;

    /**
     * Set the primary stage
     *
     * @param primaryStage contains the stage value
     */
    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    /**
     * Initialize the window
     *
     * @param root Parent value containing the FXML
     */
    public void initStage(Parent root) {
        try {
            LOG.info("Initializing Stage...");
            //Creates a new Scene
            Scene scene = new Scene(root);
            //Associate the scene to window(stage)
            stage.setScene(scene);
            //Window properties
            stage.setTitle("Sign In");
            stage.setResizable(false);
            stage.setOnCloseRequest(this::handleCloseRequest);
            //Controls
            loginBtn.addEventHandler(ActionEvent.ACTION, this::handleButtonLogin);
            userTxt.textProperty().addListener(this::handleUserControl);
            passwordTxt.textProperty().addListener(this::handlePasswordControl);
            contraHL.addEventHandler(ActionEvent.ACTION, this::handleRecuperarContraseniaHyperLink);
            signUpHl.addEventHandler(ActionEvent.ACTION, this::handleSignUpHyperLink);
            errorLbl.setVisible(false);
            errorLbl.setStyle("-fx-text-fill: red");
            //Show window (asynchronous)
            stage.show();
            LOG.info("CURRENT WINDOW: SignIn");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Stage init error", ex);
        }

    }

    //Pressing the login button
    /**
     * Calling this method the SignIn action will be executed to grant access to
     * the app (LogOut window)
     *
     * @param buttonPress Action event at pressing the login button
     */
    private void handleButtonLogin(ActionEvent buttonPress) {

        LOG.info("SignIn Controlador: Pulsado boton Iniciar sesion");

        try {

            UsuarioManager usuarioGestion = GestionFactoria.getUsuarioManager();
            //Comprueba si existe el login
            LOG.info("SignIn Controlador: Comprobando si existe el login");

            Collection<Usuario> usuario = usuarioGestion.buscarUserPorLoginSignIn(userTxt.getText());

            //Comprueba si el login y la contraseña están bien
            LOG.info("SignIn Controlador: Comprobando login y contraseña");

            usuarioGestion.buscarUsuarioPorLoginYContrasenia_Usuario(userTxt.getText(), passwordTxt.getText());

            for (Usuario user : usuario) {
                user.getPrivilege();

                switch (user.getPrivilege()) {
                    case ADMIN:
                        //Abre la vista de Trabajador
                        LOG.info("SignIn Controlador: Abriendo la vista TrabajadorView");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/TrabajadorView.fxml"));
                        Parent root = (Parent) loader.load();
                        GestionTrabajadorViewController controller = ((GestionTrabajadorViewController) loader.getController());
                        controller.setStage(stage);
                        controller.initStage(root);
                        break;
                    case TRABAJADOR:
                        //Abre la vista de Piezas
                        LOG.info("SignIn Controlador: Abriendo la vista IncidenciaViewT");
                        FXMLLoader loaderP = new FXMLLoader(getClass().getResource("/GESRE/vistas/PiezaView.fxml"));
                        Parent rootP = (Parent) loaderP.load();
                        PiezaViewController controllerP = ((PiezaViewController) loaderP.getController());
                        controllerP.setStage(stage, user.getIdUsuario());

                        controllerP.initStage(rootP);
                        break;
                    case CLIENTE:
                        LOGGER.info(user.toString());
                        //Abre la vista de Incidencias Cliente
                        LOG.info("SignIn Controlador: Abriendo la vista IncidenciaViewT");
                        FXMLLoader loaderIC = new FXMLLoader(getClass().getResource("/GESRE/vistas/IncidenciaViewC.fxml"));
                        Parent rootIC = (Parent) loaderIC.load();
                        IncidenciaCLViewController controllerIC = ((IncidenciaCLViewController) loaderIC.getController());
                       controllerIC.setStage(stage);
                        
                       // if (user instanceof Cliente) {
                            
                        //  controllerIC.setStage(stage, (Cliente) user);
                      //  }
                        controllerIC.initStage(rootIC);
                        break;
                }
            }
        } catch (LoginNoExisteException lne) {
            LOG.severe(lne.getMessage());
            errorLbl.setText("Usuario no encontrado");
            errorLbl.setTextFill(Color.web("#FF0000"));
        } catch (UsuarioNoExisteException une) {
            LOG.severe(une.getMessage());
            errorLbl.setText("Contraseña incorrecta");
            errorLbl.setTextFill(Color.web("#FF0000"));
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        } catch (ServerDesconectadoException sde) {
            LOG.log(Level.SEVERE, "Stage init error", sde);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR SERVIDOR");
            alert.setContentText("No hay conecxion con el servidor. Intentalo mas tarde");
            alert.showAndWait();
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
     * Calling this method will open the SignUp window
     *
     * @param HyperLinkPress Action event at pressing the HyperLink
     */
    private void handleSignUpHyperLink(ActionEvent HyperLinkPress) {
        /* try {
            //Call the method to open the SignUp Window
            LOG.info("SignUp Hyper Link Pressed");
            startSignUpWindow();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "HyperLink Error", ex);
        }*/
        try {
            LOG.info("Starting SignUp Window...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/SignUp.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            SignUpController signUpController = ((SignUpController) loader.getController());
            //Set the stage
            signUpController.setStage(stage);
            //initialize the window
            signUpController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting SignUp Window", ex);
        }
    }

    /**
     * Calling this method will open the SignUp window
     *
     * @param HyperLinkPress Action event at pressing the HyperLink
     */
    private void handleRecuperarContraseniaHyperLink(ActionEvent HyperLinkPress) {
        try {
            //Call the method to open the SignUp Window
            LOG.info("SignUp Hyper Link Pressed");
            startRecuperarContraseniaWindow();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "HyperLink Error", ex);
        }
    }

    /**
     * Open the SignUp window
     *
     * @param primaryStage stage object (window)
     * @throws IOException Throws an error if the SignUp window fails to open
     */
    private void startSignUpWindow() throws IOException {
        try {
            LOG.info("Starting SignUp Window...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/SignUp.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            SignUpController signUpController = ((SignUpController) loader.getController());
            //Set the stage
            signUpController.setStage(stage);
            //initialize the window
            signUpController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting SignUp Window", ex);
        }
    }

    /**
     * Open the SignUp window
     *
     * @param primaryStage stage object (window)
     * @throws IOException Throws an error if the SignUp window fails to open
     */
    private void startRecuperarContraseniaWindow() throws IOException {
        try {
            LOG.info("Starting SignUp Window...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/ResetContra.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            ResetContraController signUpController = ((ResetContraController) loader.getController());
            //Set the stage
            signUpController.setStage(stage);
            //initialize the window
            signUpController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting SignUp Window", ex);
        }
    }

    /**
     * Calling this method sets the user field controls (textProperty())
     *
     * @param observable targeted field whose value changed
     * @param oldValue previous value before change
     * @param newValue last value typed
     */
    private void handleUserControl(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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

    /**
     * Calling this method sets the password field controls (textProperty())
     *
     * @param observable targeted field whose value changed
     * @param oldValue previous value before change
     * @param newValue last value type
     */
    private void handlePasswordControl(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            //if a 26 character its typed, take first character to 25 and set it to the field(spaces not allowed)
            if (passwordTxt.getText().length() > 25) {
                passwordTxt.setText(passwordTxt.getText().substring(0, 25));
            }
            //Control empty spaces
            if (passwordTxt.getText().contains(" ")) {
                passwordTxt.setText(passwordTxt.getText().replaceAll(" ", ""));
            }
            //Show error label
            errorLbl.setVisible(false);
            userTxt.setStyle("");
            passwordTxt.setStyle("");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting Password field control", ex);
        }
    }
}
