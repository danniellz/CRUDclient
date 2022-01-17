package GESRE.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana PiezaView
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public class PiezaViewController {
    //LOGGER
    private static final Logger LOG = Logger.getLogger(PiezaViewController.class.getName());

    //Atributos
    private Stage stage;

    /**
     * Establece el Stage de PiezaView
     *
     * @param piezaViewStage valor del Stage pieazViewStage
     */
    public void setStage(Stage piezaViewStage) {
        stage = piezaViewStage;
    }

    /**
     * Inicializar Ventana
     *
     * @param root Contiene el FXML
     */
    public void initStage(Parent root) {
        try {
            LOG.info("Inicializando Stage...");
            //Creates a new Scene
            Scene scene = new Scene(root);
            //Associate the scene to window(stage)
            stage.setScene(scene);
            //Window properties
            stage.setTitle("Gestión de Piezas");
            stage.setResizable(false);
            stage.setOnCloseRequest(this::handleCloseRequest);
            //Controls
            /*logOutItem.setOnAction(this::handleLogOut);
            exitItem.setOnAction(this::handleExit);*/
            //Show window (asynchronous)
            stage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al inicializar Stage", e);
        }

    }

    /**
     * Llamar a este método cerrará la ventana
     *
     * @param closeEvent evento de ventana
     */
    public void handleCloseRequest(WindowEvent closeEvent) {
        try {
            LOG.info("Confirmando cierre de ventana...");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Estás seguro de querer Salir?");
            alert.setTitle("Salir");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LOG.info("Cerrando...");
                Platform.exit();
            } else {
                LOG.info("Cierre del programa cancelado");
                //Cancel the event process
                closeEvent.consume();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al intentar cerrar la ventana", e);
        }
    }

    /**
     * Llamar a este método cerrará la sesíón actual para volver a la ventana de SignIn
     *
     * @param logOutEvent Log Out action event
     */
    public void handleLogOut(ActionEvent logOutEvent) {
        try {
            //Presionar en la opcion "Cerrar Sesión" mostrará una ventana de confirmación
            LOG.info("Confirmando 'Cerrar Sesión'...");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Estás seguro de querer Salir?");
            alert.setTitle("Cerrar Sesión");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                //confirma la accion
                LOG.info("Cerrando Sesión...");
                startSignInWindow(stage);
            } else {
                LOG.info("'Cerrar Sesión' cancelado");
                //Cancela el evento en proceso
                logOutEvent.consume();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al intentar Cerrar Sesión", ex);
        }
    }

    /**
     * Llamar a este método abrirá la ventana de SignIn
     *
     * @param primaryStage objeto Stage (Ventana)
     * @throws IOException salta una excepcion si la ventana de SignIn falla en abrirse
     */
    private void startSignInWindow(Stage primaryStage) throws IOException {
        try {
            LOG.info("Abriendo ventana SignIn...");
            //Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupsigninclient/view/SignIn.fxml"));
            Parent root = (Parent) loader.load();
            //Obtiene el controlador
            SignInController signinController = ((SignInController) loader.getController());
            //Establece el Stage
            signinController.setStage(primaryStage);
            //Inicializa la ventana
            signinController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al intentar abrir la ventana de SignUp", ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
