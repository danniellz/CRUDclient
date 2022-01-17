package GESREClient.controller;

//import exceptions.ConnectionException;
//import exceptions.DatabaseNotFoundException;
//import exceptions.IncorrectPasswordException;
//import exceptions.MaxConnectionException;
//import exceptions.UserAlreadyExistException;
//import exceptions.UserNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;
//import signable.Signable;
import GESRE.factoria.SignableFactory;
//import user.User;

/**
 * FXML Controller class the view SingUp
 *
 * @author Jonathan Viñan , Aritz Arrieta
 * @version 1.0
 */
public class SignUpController {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(SignUpController.class.getName());

    //Attributes
    /**
     * this is the pattern structures the form of the Email
     */
    public static final Pattern VALIDEMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private Stage stage;

    //PANEL
    @FXML
    private Pane signUpPanel;

    //LABEL
    @FXML
    private Label signUpLbl;
    @FXML
    private Label userLbl;
    @FXML
    private Label fullNameLbl;
    @FXML
    private Label emailLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label repeatPasswordLbl;

    //ERROR LABELS
    @FXML
    private Label userErrorLbl;
    @FXML
    private Label fullNameErrorLbl;
    @FXML
    private Label emailErrorLbl;
    @FXML
    private Label passwordErrorLbl;
    @FXML
    private Label repeatPasswordErrorLbl;

    //HIPERLINK
    @FXML
    private Hyperlink signInHl;

    //TEXTFIELDS
    @FXML
    private TextField userTxt;
    @FXML
    private TextField fullNameTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextField repeatPasswordTxt;

    //BUTTON
    @FXML
    private Button registerBtn;

    /**
     * Defines which view is going to show up when the application executes.
     *
     * @param primaryStage the view that will show from the main application.
     */
    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Initializes the view.
     *
     * @param root loads all the nodes that descend from root.
     */
    public void initStage(Parent root) {

        LOG.info("Initializing stage...");
        //Creates a new Scene
        Scene scene = new Scene(root);
        //Associate the scene to window(stage)
        stage.setScene(scene);
        //Window properties
        stage.setTitle("SING UP");
        stage.setResizable(false);
        //Controls
        registerBtn.setDisable(true);
        userErrorLbl.setVisible(false);
        fullNameErrorLbl.setVisible(false);
        emailErrorLbl.setVisible(false);
        passwordErrorLbl.setVisible(false);
        repeatPasswordErrorLbl.setVisible(false);

        charlimit();
        fullNameTxt.focusedProperty().addListener(this::focusLostEspChar);
        emailTxt.focusedProperty().addListener(this::domainControl);
        stage.setOnCloseRequest(this::closeProgramSingUp);

        disableButtonWhenTextFieldsEmpty();
        signInHl.addEventHandler(ActionEvent.ACTION, this::clickHyperlink);
//        registerBtn.setOnAction(this::registerValidation);
        passwordTxt.focusedProperty().addListener(this::focusChanged);
        repeatPasswordTxt.focusedProperty().addListener(this::focusChangeRepeatPassword);
        //Show window (asynchronous)
        stage.show();
    }

    /**
     * this method puts a limit in the textLabels (25 limit except email
     * textLabel)
     */
    private void charlimit() {
        //USER field control
        userTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //Control of 25 characters
                if (userTxt.getText().length() > 25) {
                    LOG.warning("25 characters limit reached in User field");
                    userErrorLbl.setText("25 characters limit reached");
                    userTxt.deletePreviousChar();
                    userTxt.setStyle("-fx-border-color: #DC143C	; -fx-border-width: 1.5px ;");
                    userErrorLbl.setVisible(true);
                    userErrorLbl.setStyle("-fx-text-fill: #DC143C");
                } else {
                    userTxt.setStyle("-fx-border-color: White;");
                    userErrorLbl.setVisible(false);
                    userErrorLbl.setStyle(" ");
                }
                //Control empty spaces
                if (userTxt.getText().contains(" ")) {
                    userTxt.setText(userTxt.getText().replaceAll(" ", ""));
                }
            }
        });
        //FULLNAME field control
        fullNameTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //control of 25 characters
                if (fullNameTxt.getText().length() > 25) {
                    LOG.warning("25 characters limit reached in FullName field");
                    fullNameErrorLbl.setText("25 characters limit reached");
                    fullNameTxt.deletePreviousChar();
                    fullNameTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                    fullNameErrorLbl.setVisible(true);
                    fullNameErrorLbl.setStyle("-fx-text-fill: #DC143C");
                } else {
                    fullNameErrorLbl.setVisible(false);
                    fullNameErrorLbl.setStyle("");
                }
            }
        });
        //EMAIL field control
        emailTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //control of 50 characters
                if (emailTxt.getText().length() > 50) {
                    LOG.warning("50 characters limit reached in Email field");
                    emailErrorLbl.setText("50 characters limit reached");
                    emailTxt.deletePreviousChar();
                    emailErrorLbl.setVisible(true);
                    emailTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                    emailErrorLbl.setVisible(true);
                    emailErrorLbl.setStyle("-fx-text-fill: #DC143C");
                } else {
                    emailErrorLbl.setVisible(false);
                    emailErrorLbl.setStyle("");
                }

            }
        });
        //PASSWORD field control
        passwordTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //control of 25 characters
                if (passwordTxt.getText().length() > 25) {
                    LOG.warning("25 characters limit reached in Password field");
                    passwordErrorLbl.setText("25 characters limit reached");
                    passwordTxt.deletePreviousChar();
                    passwordErrorLbl.setVisible(true);
                    passwordTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                    passwordErrorLbl.setVisible(true);
                    passwordErrorLbl.setStyle("-fx-text-fill: #DC143C");
                } else {
                    passwordErrorLbl.setVisible(false);
                    passwordErrorLbl.setStyle("");
                }

                if (passwordTxt.getText().isEmpty()) {
                    LOG.warning("Null value in Password field");
                    passwordErrorLbl.setText("Please, enter a password");
                    passwordErrorLbl.setVisible(true);
                }

            }
        });
        //REPEATPASSWORD field control
        repeatPasswordTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //control of 25 characters 
                if (repeatPasswordTxt.getText().length() > 25) {
                    LOG.warning("25 characters limit reached in RepeatPassword field");
                    repeatPasswordErrorLbl.setText("25 characters limit reached");
                    repeatPasswordTxt.deletePreviousChar();
                    repeatPasswordErrorLbl.setVisible(true);
                    repeatPasswordTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                    repeatPasswordErrorLbl.setVisible(true);
                    repeatPasswordErrorLbl.setStyle("-fx-text-fill: #DC143C");
                } else {
                    repeatPasswordErrorLbl.setVisible(false);
                    repeatPasswordErrorLbl.setStyle("");
                }
            }
        });
    }

    /**
     * this method is a focus lost action, that test if the full name don´t puts
     * any especial characteres
     *
     * @param observable is the field that have the focus action
     * @param oldValue is a boolean to know where was the focus
     * @param newValue is a boolean to know where is the focus
     */
    public void focusLostEspChar(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        String comp = "[^A-Za-zÀ-ȕ\\s]";
        Pattern espChar = Pattern.compile(comp);
        Matcher matcher = espChar.matcher(fullNameTxt.getText().trim());

        //este codigo solo se ejecuta cuando se pierde el Foco
        if (oldValue) {
            if (matcher.find()) {
                LOG.warning("Specials characters are not allowed");
                fullNameErrorLbl.setText("Numbers or special characters are not allowed");
                fullNameTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                fullNameErrorLbl.setVisible(true);
                fullNameErrorLbl.setStyle("-fx-text-fill: #DC143C");
            } else {
                fullNameTxt.setStyle("");
                fullNameErrorLbl.setVisible(false);
            }
        } else if (newValue) {
            LOG.info("Focus gained on Full Name field");
        }
    }

    /**
     * domain control checks if there is a "@" and "." as the domain of email
     *
     * @param observable is the field that have the focus action
     * @param oldValue is a boolean to know where was the focus
     * @param newValue is a boolean to know where is the focus
     */
    private void domainControl(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            LOG.info("");
            Matcher matcher = VALIDEMAIL.matcher(emailTxt.getText());
            if (matcher.find()) {
                emailErrorLbl.setVisible(false);
                emailTxt.setStyle("");
                emailErrorLbl.setStyle("-fx-border-color: WHITE;");

            } else {
                LOG.warning("Invalid email format");
                emailErrorLbl.setText("Please, enter a valid email");
                emailTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                emailErrorLbl.setVisible(true);
                emailErrorLbl.setStyle("-fx-text-fill: #DC143C");

            }

            if (emailTxt.getText().isEmpty()) {
                LOG.warning("Null value in Email field");
                emailErrorLbl.setText("Please, enter a email");
                emailErrorLbl.setVisible(true);
            }
        } else if (newValue) {
            LOG.info("Focus gained on Email field");
        }
    }

    /**
     * Control de cambio de foco checks if the password textField has a minimum
     * of 6 chars
     *
     * @param observable is the field that have the focus action
     * @param oldValue is a boolean to know where was the focus
     * @param newValue is a boolean to know where is the focus
     *
     */
    private void focusChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        //control valida cuando el foco se cambia
        if (newValue) {
            LOG.info("Focus gained on Password field");
        } else if (oldValue) {
            LOG.info("Focus in Password field");
            if (passwordTxt.getText().length() < 6) {
                passwordErrorLbl.setText("A minimum of 6 characters is required");
                String password = passwordTxt.getText();
                passwordTxt.setText(password);
                passwordTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                passwordErrorLbl.setVisible(true);
                passwordErrorLbl.setTextFill(Color.web("#ff0000"));
            } else {
                passwordTxt.setStyle("");
                passwordErrorLbl.setVisible(false);
            }

            if (passwordTxt.getText().isEmpty()) {
                LOG.warning("Null value in Password field");
                passwordErrorLbl.setText("Please, enter a password");
                passwordErrorLbl.setVisible(true);
            }
        }
    }

    /**
     * Control de cambio de foco checks if the password textField and the
     * repeatpassword textfield has the same text
     *
     * @param observable is the field that have the focus action
     * @param oldValue is a boolean to know where was the focus
     * @param newValue is a boolean to know where is the focus
     */
    private void focusChangeRepeatPassword(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            LOG.info("Focus gained on Repeat Password field");
        } else if (oldValue) {
            LOG.info("Focus in RepeatPassword field");
            if (!passwordTxt.getText().equals(repeatPasswordTxt.getText())) {
                repeatPasswordErrorLbl.setText("Passwords don't match");
                repeatPasswordTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
                repeatPasswordErrorLbl.setVisible(true);
                repeatPasswordErrorLbl.setTextFill(Color.web("#FF0000"));
            } else {
                repeatPasswordTxt.setStyle("");
                repeatPasswordErrorLbl.setVisible(false);
            }
        }
    }

    /**
     * Checks if the two passwords are equal.
     *
     * @return "error" boolean if the two passwords don't match.
     */
    private boolean checkPasswordsEqual() {
        LOG.info("Checking if passwords match...");
        boolean error = false;

        if (!passwordTxt.getText().equals(repeatPasswordTxt.getText())) {
            LOG.warning("Password don't match");
            repeatPasswordErrorLbl.setText("Passwords don't match");
            repeatPasswordTxt.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
            repeatPasswordErrorLbl.setTextFill(Color.web("#FF0000"));

            error = true;
        }
        return error;
    }

    /**
     * Disables Register button if there's at least one Text Field empty.
     *
     * @param observable observes the changes that happen in the Text Field.
     * @param oldValue the value that was in the Text Field before changes
     * happened.
     * @param newValue the value that is in the Text Field after changes
     * happenes.
     */
    private void disableButtonWhenTextFieldsEmpty() {
        LOG.info("Verify that button");
        //Se comprueba cuando el boton debe estar desabilitado
        registerBtn.disableProperty().bind(
                userTxt.textProperty().isEmpty()
                        .or(fullNameTxt.textProperty().isEmpty())
                        .or(emailTxt.textProperty().isEmpty())
                        .or(passwordTxt.textProperty().isEmpty())
                        .or(repeatPasswordTxt.textProperty().isEmpty())
                        //errorLbl
                        .or(userErrorLbl.visibleProperty())
                        .or(fullNameErrorLbl.visibleProperty())
                        .or(emailErrorLbl.visibleProperty())
                        .or(passwordErrorLbl.visibleProperty())
                        .or(repeatPasswordErrorLbl.visibleProperty())
        );
    }

    /**
     * Executes action when Sign Up button pressed.
     *
     * @param event determines which event has happened.
     *
    private void registerValidation(ActionEvent event) {
        LOG.info("Clicked on button register");
        boolean errorPassEqual = false;
        errorPassEqual = checkPasswordsEqual();

        User user = new User();
        user.setLogin(userTxt.getText());
        user.setEmail(emailTxt.getText());
        user.setFullName(fullNameTxt.getText());
        user.setPassword(passwordTxt.getText());

        try {

            Signable sign = new SignableFactory().getSignable();
            sign.signUp(user);

            //Show an alert confirming the user correct registration
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SignUp");
            alert.setHeaderText("Sign Up Confirmed!");
            alert.setContentText("User registered successfully!");
            alert.showAndWait();

            //Open SignIn window after a correct registration
            openSignInWindow();
            LOG.info("User Registered, returning to SignIn window...");

        } catch (UserAlreadyExistException ex) {
            //Show an error label if the user already exist, LOG SEVERE included with Exception
            LOG.info("UserAlreadyExistException");
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, "User Already exist", ex);
            userErrorLbl.setText("User already exist, try another");
            userErrorLbl.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
            userErrorLbl.setVisible(true);
            userErrorLbl.setStyle("-fx-text-fill: #DC143C");
        } catch (UserNotFoundException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, "User not Found ", ex);
        } catch (IncorrectPasswordException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, "Incorrect Password", ex);
        } catch (DatabaseNotFoundException ex) {
            //Show an error Alert if there is a problem with the DataBase, LOG SEVERE included with Exception
            LOG.info("DatabaseNotFoundException");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Database Connection Error");
            alert.setContentText("Database is not available, please, try again later");
            alert.showAndWait();
        } catch (ConnectionException ex) {
            //Show an error Alert if there is not a connection with the Server, LOG SEVERE included with Exception
            LOG.info("ConnectionException");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecion Error");
            alert.setHeaderText("Server Connection Error");
            alert.setContentText("Server is not available, please, try again later");
            alert.showAndWait();
        } catch (MaxConnectionException ex) {
            //Show an error Alert if the max connection is reached, LOG SEVERE included with Exception
            LOG.info("MaxConnectionException");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connection Limit Warning");
            alert.setHeaderText("Max Connection Reached");
            alert.setContentText("The Server is not available because the limit connection has been reached, please try again later");
            alert.showAndWait();
        }
    }

    /**
     * is the method that opens the SignIn pane
     *
     * @param event is event when we click on the Hyperlink signIn
     *
     */
    private void clickHyperlink(ActionEvent HyperLinkPress) {
        try {
            LOG.info("SignUp Hyper Link Pressed");
            startSignInWindow(stage);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "HyperLink Error", ex);
        }
    }

    /**
     * this method create the sigIn pane
     *
     * @param primaryStage
     * @throws IOException
     */
    private void startSignInWindow(Stage primaryStage) throws IOException {
        try {
            LOG.info("Starting SignIn window...");
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
            LOG.log(Level.SEVERE, "Error Starting SignIn window", ex);
        }
    }

    /**
     *
     * this method confirm if you want to exit the app
     *
     * @param closeEvent is the event to close the pane
     */
    @FXML
    public void closeProgramSingUp(WindowEvent closeEvent) {
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
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Close request error", ex);
        }
    }

    //This method open the SignIn window
    private void openSignInWindow() {
        try {
            LOG.info("Starting LogIn Window...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupsigninclient/view/SignIn.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            SignInController signIn = ((SignInController) loader.getController());
            //Set the stage
            signIn.setStage(stage);
            //initialize the window
            signIn.initStage(root);

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting LogOut Window", ex);
        }
    }

}
