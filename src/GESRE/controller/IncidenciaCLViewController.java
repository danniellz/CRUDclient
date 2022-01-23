/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.controller;

import GESRE.entidades.EstadoIncidencia;
import GESRE.entidades.Incidencia;
import GESRE.entidades.TipoIncidencia;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.IncidenciaManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Aritz Arrieta
 */
public class IncidenciaCLViewController {
    //LOGGER

    private static final Logger LOG = Logger.getLogger(IncidenciaCLViewController.class.getName());

    IncidenciaManager incidenciaManager = GestionFactoria.getIncidenciaManager();

    private Stage stage;

    private Incidencia incidencia = null;

    //**************label de Informacion *****************
    @FXML
    private Label lblTipoIncidencia;
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblEstrella;
    @FXML
    private Label lblHoras;
    @FXML
    private Label lblGestionIncidencia;

    //////////////////////////////////////////////////////////////
    //**********************BUTTON*************************
    @FXML
    private Button btnInforme;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;

    //////////////////////////////////////////////////////////////
    //********COMBOBOX********
    @FXML
    private ComboBox<TipoIncidencia> cbxTipoIncidencia;
    @FXML
    private ComboBox<EstadoIncidencia> cbxEstadoIncidencia;

    //////////////////////////////////////////////////////////////
    //*********ToogleButton******************
    @FXML
    private ToggleButton btnToogleFiltro;
    //********TABLE********
    @FXML
    private TableView<Incidencia> tablaIncidencias;

    //******** COLUMNAS DE LA TABLA ********
    @FXML
    private TableColumn<Incidencia, String> tipoIncidenciaCL;
    @FXML
    private TableColumn<Incidencia, String> estadoCL;
    @FXML
    private TableColumn<Incidencia, String> estrellasCL;
    @FXML
    private TableColumn<Incidencia, Float> horasCL;
    //******** PANE ********

    //////////////////////////////////////////////////////////////
    @FXML
    private Pane incidenciaPanel;

    //////////////////////////////////////////////////////////////
    @FXML
    private TextField Estr_TxtLabel;
    @FXML
    private TextField Hor_TxtLabel;

    private ObservableList<Incidencia> IncidenciaList;

    /**
     * @param args the command line arguments
     */
    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public void initStage(Parent root) {
        try {
            LOG.info("Iniciando Stage...");
            //Crear la nueva escena
            Scene scene = new Scene(root);
            stage.setScene(scene);

            //________________propiedades de la Ventana_____________________
            stage.setResizable(false);
            stage.setTitle("Gestion Incidencias");
            //acciones de la Ventana
            stage.setOnCloseRequest(this::handleCloseRequest);
            btnLimpiar.setOnAction(this::handleLimpiarFormulario);
            //Establecer valores del combobox

            ObservableList<TipoIncidencia> tipoIn = FXCollections.observableArrayList(TipoIncidencia.values());
            ObservableList<EstadoIncidencia> estados = FXCollections.observableArrayList(EstadoIncidencia.values());

            cbxEstadoIncidencia.setItems(estados);
            cbxTipoIncidencia.setItems(tipoIn);
            
            //*********estado inicial de la ventana*********
            //______________________________________No me funciona nada__________JAJAJA

            /*btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            btnLimpiar.setDisable(true);
            btnAnadir.setDisable(true);
            btnLimpiar.setDisable(true);
            btnInforme.setDisable(true);*/
            handleActionButtons();
            //limpiarFormulario();

            //Establecer los valores que aparecen dentro de cada celda
            tipoIncidenciaCL.setCellValueFactory(new PropertyValueFactory<>("tipoIncidencia"));
            estadoCL.setCellValueFactory(new PropertyValueFactory<>("estado"));
            estrellasCL.setCellValueFactory(new PropertyValueFactory<>("estrellas"));
            horasCL.setCellValueFactory(new PropertyValueFactory<>("horas"));

            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findAll());
            tablaIncidencias.setItems(IncidenciaList);

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

    public void handleActionButtons() {
        LOG.info("Deshabilitando botones Añadir, Editar y Borrar");
        //Se comprueba cuando el boton debe estar desabilitado
        btnAnadir.disableProperty().bind(
                Estr_TxtLabel.textProperty().isEmpty()
                        .or(Hor_TxtLabel.textProperty().isEmpty())
        );
        btnModificar.disableProperty().bind(
                Estr_TxtLabel.textProperty().isEmpty()
                        .or(Hor_TxtLabel.textProperty().isEmpty())
        );

        btnEliminar.disableProperty().bind(
                Estr_TxtLabel.textProperty().isEmpty()
                        .or(Hor_TxtLabel.textProperty().isEmpty())
        );
        boolean selectedIndex = false;
        if (cbxTipoIncidencia.getSelectionModel().getSelectedIndex() != -1 && cbxEstadoIncidencia.getSelectionModel().getSelectedIndex() != -1 ) {
            selectedIndex = true;
        } else {
            selectedIndex = false;
        }
        if (selectedIndex) {
            cbxTipoIncidencia.setDisable(false);
        }
    }

    private void handleLimpiarFormulario( ActionEvent event) {
        Estr_TxtLabel.setText(" ");
        Hor_TxtLabel.setText(" ");
        cbxTipoIncidencia.getSelectionModel().select(-1);
        cbxEstadoIncidencia.getSelectionModel().select(-1);
        handleActionButtons();
    }

    /*  public static void main(String[] args) {
       launch(args);
    }*/
    
}
