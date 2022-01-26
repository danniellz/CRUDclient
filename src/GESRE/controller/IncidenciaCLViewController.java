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
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
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
    @FXML
    private Label lblError;

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

            tablaIncidencias.getSelectionModel().selectedItemProperty().addListener(this::handleTablaIncidenciasSelection);
            //Establecer valores del combobox

            ObservableList<TipoIncidencia> tipoIn = FXCollections.observableArrayList(TipoIncidencia.values());
            ObservableList<EstadoIncidencia> estados = FXCollections.observableArrayList(EstadoIncidencia.values());

            cbxEstadoIncidencia.setItems(estados);
            cbxTipoIncidencia.setItems(tipoIn);

            //*********estado inicial de la ventana*********
           
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            btnLimpiar.setDisable(true);
            btnAnadir.setDisable(true);
            btnLimpiar.setDisable(true);
            btnInforme.setDisable(true);
            lblError.setVisible(false);
            handleActionButtons();

            //Establecer los valores que aparecen dentro de cada celda
            tipoIncidenciaCL.setCellValueFactory(new PropertyValueFactory<>("tipoIncidencia"));
            estadoCL.setCellValueFactory(new PropertyValueFactory<>("estado"));
            estrellasCL.setCellValueFactory(new PropertyValueFactory<>("estrellas"));
            horasCL.setCellValueFactory(new PropertyValueFactory<>("horas"));

            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findAll());
            tablaIncidencias.setItems(IncidenciaList);

            btnLimpiar.setOnAction(this::handleLimpiarFormulario);
            btnAnadir.setOnAction(this::handleAnadir);

            //-------------btnModificar.setOnAction(this::handleModificar);
            btnEliminar.setOnAction(this::handleEliminar);
            //el boton de Busqueda funciona con un toogle button
            btnToogleFiltro.setOnAction(this::handleFiltro);
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
        if (cbxTipoIncidencia.getSelectionModel().getSelectedIndex() != -1 && cbxEstadoIncidencia.getSelectionModel().getSelectedIndex() != -1) {
            selectedIndex = true;
        } else {
            selectedIndex = false;
        }
        if (selectedIndex) {
            cbxTipoIncidencia.setDisable(false);
        }
    }

    private void handleLimpiarFormulario(ActionEvent event) {
        Estr_TxtLabel.setText(" ");
        Hor_TxtLabel.setText(" ");
        cbxTipoIncidencia.getSelectionModel().select(-1);
        cbxEstadoIncidencia.getSelectionModel().select(-1);
        handleActionButtons();
    }

    private void handleTablaIncidenciasSelection(ObservableValue observable, Object oldValue, Object newValue) {
        //Si una fila esta seleccionada mover los datos de la fila a los campos
        if (newValue != null) {
            incidencia = (Incidencia) newValue;
            cbxTipoIncidencia.getSelectionModel().select(incidencia.getTipoIncidencia());
            cbxEstadoIncidencia.getSelectionModel().select(incidencia.getEstado());
            Estr_TxtLabel.setText(incidencia.getEstrellas().toString());
            Hor_TxtLabel.setText(incidencia.getHoras().toString());

            LOG.info("Información de Incidencia: " + incidencia.toString());
        } else {
            LOG.info("Fila no seleccionada");
            //Si no hay fila seleccionada limpiar los campos
            Estr_TxtLabel.setText("");
            Hor_TxtLabel.setText("");
            cbxTipoIncidencia.getSelectionModel().select(-1);
            cbxEstadoIncidencia.getSelectionModel().select(-1);
        }
        //Focus login field
        Hor_TxtLabel.requestFocus();
    }

    private void handleAnadir(ActionEvent anadirEvent) {
        // try {
        //Solo se admiten numeros no negativos
        LOG.info("Añadiendo la Incidencia");
        incidencia = new Incidencia();
        incidencia.setId(2);
        incidencia.setTipoIncidencia(cbxTipoIncidencia.getSelectionModel().getSelectedItem());
        incidencia.setEstado(cbxEstadoIncidencia.getSelectionModel().getSelectedItem());
        incidencia.setEstrellas(new Integer(Estr_TxtLabel.getText()));
        incidencia.setHoras(new Integer(Hor_TxtLabel.getText()));

        incidenciaManager.createIncidencia(incidencia);
        LOG.info(incidencia.toString());
        //Agregar nueva pieza a tabla
        tablaIncidencias.getItems().add(incidencia);

        //Actualizar tabla
        tablaIncidencias.refresh();
        /* } else {
                //introducir texto en el mensaje de error
                Estr_TxtLabel.setStyle("-fx-border-color: White;");
                Hor_TxtLabel.setStyle("-fx-border-color: White;");
                cbxEstadoIncidencia.setStyle("-fx-border-color: White;");
                cbxTipoIncidencia.setStyle("-fx-border-color: White;");
                //mostrar mensaje de Error
            }*/

 /*} catch (Exception e) {
        LOG.severe("NO FUNICONA EL CREATE "+e.getLocalizedMessage());
        }*/
    }

    private void handleEliminar(ActionEvent Event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText("¿Seguro que quieres eliminar la incidencia?");

        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            Incidencia seleccion = ((Incidencia) tablaIncidencias.getSelectionModel().getSelectedItem());

            if (seleccion != null) {
                incidenciaManager.removeIncidencia(String.valueOf(seleccion.getId()));

                tablaIncidencias.getItems().remove(seleccion);
                tablaIncidencias.getSelectionModel().clearSelection();
                tablaIncidencias.refresh();

                camposTextoVacios();
            } else {
                lblError.setText("No se ha podido eliminar la Incidencia");
                lblError.setTextFill(Color.web("#FF0000"));
            }
        }
    }

    private void handleFiltro(ActionEvent Event) {
        if (btnToogleFiltro.isSelected()) {
            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findIncidenciaAcabadaDeUnUsuario(incidencia, "2"));
            tablaIncidencias.setItems(IncidenciaList);
        } else {
            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findAll());
            tablaIncidencias.setItems(IncidenciaList);
        }
    }

    /*  public static void main(String[] args) {
       launch(args);
    }*/

    private boolean camposTextoVacios() {
        if (Estr_TxtLabel.getText().isEmpty() || Hor_TxtLabel.getText().isEmpty()) {
            btnAnadir.setDisable(false);
            return true;
        } else {
            return false;
        }
    }
}
