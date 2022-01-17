package GESRE.aplication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import GESRE.controller.SignInController;

/**
 * class responsible for starting the application
 * 
 * @author Daniel Brizuela
 * @version 1.0
 */
public class GESREClient extends Application{
    //LOGGER
    private static final Logger LOG = Logger.getLogger(SignInController.class.getName());
    /**
     * This is the first window (SignIn)
     * 
     * @param primaryStage stage object (window)
     * @throws Exception throws an error if the start method fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            LOG.info("Starting SignIn window...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/SignIn.fxml"));
            Parent root = (Parent)loader.load();
            //Get controller
            SignInController signinController = ((SignInController)loader.getController()); 
            //Set the stage
            signinController.setStage(primaryStage);
            //initialize the window
            signinController.initStage(root);
        }catch(IOException ex){
            LOG.log(Level.SEVERE, "Error Starting SignIn window", ex);
        }
    }
    
    /**
    * Main class, start the application 
    * 
    * @param args the command line arguments
    */

    public static void main(String[] args) {
        launch(args);
    }
}
