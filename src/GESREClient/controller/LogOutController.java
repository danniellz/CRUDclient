package GESREClient.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import user.User;

/**
 * Log Out Controller
 *
 * @author Mikel Matilla
 * @version 1.0
 */
public class LogOutController {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(LogOutController.class.getName());

    //Attributes
    private Stage stage;

    @FXML
    private Pane logOutPanel;
    @FXML
    private Label logOutLbl;
    @FXML
    private Label messageLbl;
    @FXML
    private MenuItem logOutItem;
    @FXML
    private MenuItem exitItem;

    /**
     * Set the log out stage
     *
     * @param logOutStage Log out stage value
     */
    public void setStage(Stage logOutStage) {
        stage = logOutStage;
    }

    /**
     * Initialize window
     *
     * @param root Contains the FXML
     * @param user
     */
    public void initStage(Parent root, User user) {
        try {
            LOG.info("Initializing stage...");
            //Creates a new Scene
            Scene scene = new Scene(root);
            //Associate the scene to window(stage)
            stage.setScene(scene);
            //Window properties
            stage.setTitle("Log Out");
            stage.setResizable(false);
            stage.setOnCloseRequest(this::handleCloseRequest);
            //Controls
            logOutItem.setOnAction(this::handleLogOut);
            exitItem.setOnAction(this::handleExit);
            messageLbl.setText("Hello " + user.getFullName() + ", you have succesfully logged in!!");
            messageLbl.setTextAlignment(TextAlignment.CENTER);
            //Show window (asynchronous)
            stage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Stage init error", e);
        }

    }

    /**
     * Calling this method will close the application
     *
     * @param closeEvent A window event
     */
    public void handleCloseRequest(WindowEvent closeEvent) {
        try {
            LOG.info("Confirm Closing");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Are you sure you want to exit?");
            alert.setTitle("Exit");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LOG.info("Closing...");
                Platform.exit();
            } else {
                LOG.info("Closing Canceled");
                //Cancel the event process
                closeEvent.consume();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Close request error", e);
        }
    }

    /**
     * Calling this method will close the application
     *
     * @param closeEvent Close action event
     */
    public void handleExit(ActionEvent closeEvent) {
        try {
            LOG.info("Exit button clicked");
            LOG.info("Confirm Closing");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Are you sure you want to exit?");
            alert.setTitle("Exit");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LOG.info("Closing...");
                Platform.exit();
            } else {
                LOG.info("Closing Canceled");
                //Cancel the event process
                closeEvent.consume();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Close request error", e);
        }
    }

    /**
     * Calling this method will log out
     *
     * @param logOutEvent Log Out action event
     */
    public void handleLogOut(ActionEvent logOutEvent) {
        try {
            //Pressing the LogOut option will show an Alert to confirm it
            LOG.info("Log Out button clicked");
            LOG.info("Confirm Log Out");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Are you sure you want to log out?");
            alert.setTitle("Log Out");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LOG.info("Logging out...");
                startSignInWindow(stage);
            } else {
                LOG.info("Log Out Canceled");
                //Cancel the event process
                logOutEvent.consume();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Log Out error", ex);
        }
    }

    /**
     * Calling this method will open the sign in window
     *
     * @param primaryStage stage object (window)
     * @throws IOException Throws an error if the SignUp window fails to open
     */
    private void startSignInWindow(Stage primaryStage) throws IOException {
        try {
            LOG.info("Starting SignIn Window...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupsigninclient/view/SignIn.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            SignInController signinController = ((SignInController) loader.getController());
            //Set the stage
            signinController.setStage(primaryStage);
            //initialize the window
            signinController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting SignUp Window", ex);
        }
    }
}
