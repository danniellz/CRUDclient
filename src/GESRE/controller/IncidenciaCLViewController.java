/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Aritz Arrieta
 */
public class IncidenciaCLViewController {
    //LOGGER

    private static final Logger LOG = Logger.getLogger(IncidenciaCLViewController.class.getName());

    private Stage stage;

    /**
     * @param args the command line arguments
     */
    public void setStage(Stage incidenciaViewStage) {

        stage = incidenciaViewStage;
    }

    public void initStage(Parent root) {
        try {
            LOG.info("Iniciando Stage...");
            //Crear la nueva escena
            Scene scene = new Scene(root);

            stage.setScene(scene);

            //propiedades de la Ventana
            stage.setResizable(false);
            stage.setTitle("Gestion Incidencias");
            //acciones de la Ventana
            stage.setOnCloseRequest(this::handleCloseRequest);

            //mostrar la Ventana
            stage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Fallo al iniciar la Ventana de Gestion de Incidencias", e);
        }

    }

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

    private void startIncidenciaInWindow(Stage primaryStage) throws IOException {
        try {
            LOG.info("Abriendo ventana Incidencia...");
            //Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/view/IncidenciaViewC.fxml"));
            Parent root = (Parent) loader.load();
            //Obtiene el controlador
            IncidenciaCLViewController incidenciaCLViewController = ((IncidenciaCLViewController) loader.getController());
            //Establece el Stage
            incidenciaCLViewController.setStage(primaryStage);
            //Inicializa la ventana
            incidenciaCLViewController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al intentar abrir la ventana de Gestion de Incidencia", ex);
        }
    }

  /*  public static void main(String[] args) {
       launch(args);
    }*/

}
