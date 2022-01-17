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

import GESRE.factoria.SignableFactory;
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
//            loginBtn.addEventHandler(ActionEvent.ACTION, this::handleButtonLogin);
            signUpHl.addEventHandler(ActionEvent.ACTION, this::handleSignUpHyperLink);
            userTxt.textProperty().addListener(this::handleUserControl);
            passwordTxt.textProperty().addListener(this::handlePasswordControl);
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
  /*  private void handleButtonLogin(ActionEvent buttonPress) {
        username = userTxt.getText();
        password = passwordTxt.getText();
        try {
            LOG.info("Login Button Pressed");
            //if the user or password fields are empty, show the error label
            if ((username.equals("") || password.equals(""))
                    || (username.equals("") && password.equals(""))) {
                LOG.info("Null Value In User or Password Field");

                //Show Error label for Empty fields
                errorLbl.setText("The fields have to be filled");
                errorLbl.setVisible(true);
                userTxt.setStyle("-fx-border-color: #DC143C	; -fx-border-width: 1.5px ;");
                passwordTxt.setStyle("-fx-border-color: #DC143C	; -fx-border-width: 1.5px ;");
            } else {
                LOG.info("Proccesing User Info...");
                //User user = new User();
                //user.setLogin(username);
               // user.setPassword(password);

                //Get the SignableImplement from the factory and save it into the Signable interface
               // Signable sign = SignableFactory.getSignable();

                //Save user data and send it to the signIn method then return the user object with all the data
               // user = sign.signIn(user);
                LOG.info("User data retrieved!");

                //Open LogOut Window if the SignIn Process is well
                try {
                    LOG.info("Starting LogOut Window...");
                    //Load the FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupsigninclient/view/LogOut.fxml"));
                    Parent root = (Parent) loader.load();
                    //Get controller
                    LogOutController logOutController = ((LogOutController) loader.getController());
                    //Set the stage
                    logOutController.setStage(stage);
                    //initialize the window
                 //   LOG.info("Sending data for: " + user.getFullName());
                 //   logOutController.initStage(root, user);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, "Error Starting LogOut Window", ex);
                }
            }
        } catch (UserNotFoundException ex) {
            //Show an error label if the username is incorrect, LOG SEVERE included with Exception
            LOG.severe("User doesn't exist");
            errorLbl.setText("Incorrect Username");
            errorLbl.setVisible(true);
            userTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px;");
            passwordTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px;");
        } catch (IncorrectPasswordException ex) {
            LOG.severe("Incorrect password");
            //Show an error label if the password is incorrect, LOG SEVERE included with Exception
            errorLbl.setText("Incorrect Password");
            errorLbl.setVisible(true);
            userTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px;");
            passwordTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px;");
        } catch (ConnectionException ex) {
            LOG.severe("Server Connection Error");
            //Show an error Alert if there is not connection with the server, LOG SEVERE included with Exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecion Error");
            alert.setHeaderText("Server Connection Error");
            alert.setContentText("Server is not available, please, try again later");
            alert.showAndWait();
        } catch (MaxConnectionException ex) {
            LOG.severe("Max Connection Reached");
            //Show an error Alert if the Max connection with the server is reached, LOG SEVERE included with Exception
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connection Limit Warning");
            alert.setHeaderText("Max Connection Reached");
            alert.setContentText("The Server is not available because the limit connection has been reached, please try again later");
            alert.showAndWait();
        } catch (DatabaseNotFoundException ex) {
            LOG.severe("Database Error");
            //Show an error Alert if there is a problem with the DataBase, LOG SEVERE included with Exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Database Connection Error");
            alert.setContentText("Database is not available, please, try again later");
            alert.showAndWait();
        } catch (UserAlreadyExistException ex) {
            LOG.log(Level.SEVERE, "User already exist Error", ex);
        } catch (Exception ex) {
            //Show an error Alert if an unknow error occured
            LOG.log(Level.SEVERE, "An Unknown error has occurred", ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unknown error");
            alert.setContentText("An unknow error has occured, please, try again later");
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
        try {
            //Call the method to open the SignUp Window
            LOG.info("SignUp Hyper Link Pressed");
            startSignUpWindow();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupsigninclient/view/SignUp.fxml"));
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
