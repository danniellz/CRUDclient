package GESRE.controller;

import GESRE.entidades.Pieza;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.PiezasManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.application.Platform;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    //********TEXT FIELDS********
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtNombreFiltro;

    //********COMBOBOX********
    @FXML
    private ComboBox<String> cbxFiltro;

    //********BUTTON********
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnGestionIncidencia;

    //********TABLE********
    @FXML
    private TableView<Pieza> tablaPiezas;

    //********TABLE COLUM********
    @FXML
    private TableColumn<Pieza, String> nombreCl;
    @FXML
    private TableColumn<Pieza, String> descripcionCl;
    @FXML
    private TableColumn<Pieza, Integer> stockCl;

    private ObservableList<Pieza> datosPieza;

    //********STAGE********
    private Stage stage;

    //********MENU********
    @FXML
    private MenuItem cerrarSesion;
    @FXML
    private MenuItem salir;

    /**
     * Variable que hace una llamada al método que gestiona los grupos de la
     * factoría.
     */
    PiezasManager piezasManager = GestionFactoria.getPiezaManager();

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
     * @param idTrabajador
     */
    public void initStage(Parent root, Integer idTrabajador) {
        Pieza pieza = null;
        try {
            LOG.info("Inicializando Stage...");
            //Crear nueva Scene
            Scene scene = new Scene(root);
            //Asociar la Scene a una ventana
            stage.setScene(scene);
            //Propiedades de la ventana
            stage.setTitle("Gestión de Piezas");
            stage.setResizable(false);

            //Estado inicial de los controles
            btnAnadir.setDisable(true);
            btnEditar.setDisable(true);
            btnBorrar.setDisable(true);
            btnLimpiar.setDisable(true);
            btnBuscar.setDisable(true);
            btnLimpiar.setDisable(true);
            stage.setOnCloseRequest(this::handleSalir);

            //Listeners de los TextField al cambiar el texto, textProperty
            /*txtNombre.textProperty().addListener(this::handleValidarTexto);
            txtDescripcion.textProperty().addListener(this::handleValidarTexto);
            txtStock.textProperty().addListener(this::handleValidarTexto);
            txtNombreFiltro.textProperty().addListener(this::handleValidarTexto);*/
            //Definir columnas de la tabla trabajador
            
            //Establecer el valor que aparecera dentro de la celda
            nombreCl.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            descripcionCl.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            stockCl.setCellValueFactory(new PropertyValueFactory<>("stock"));

            //Llenar la tabla de datos con todas las piezas de un trabajador
            datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByTrabajadorId(pieza, idTrabajador));
            tablaPiezas.setItems(datosPieza);

            //Listeners
            //btnLimpiar.setOnAction(this::handleBtnLimpiar);
            //btnAnadir.setOnAction(this::handleBtnAnadir);
            //btnModificar.setOnAction(this::handleBtnModificar);
            //btnEliminar.setOnAction(this::handleBtnEliminar);
            //btnBuscar.setOnAction(this::handleBtnBuscar);
            //mnCerrarSesion.setOnAction(this::handleCerrarSesion);
            //cerrarSesion.setOnAction(this::handleCerrarSesion);
            //salir.setOnAction(this::handleSalir);

            //Mostrar ventana (asincrona)
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
    public void handleSalir(WindowEvent closeEvent) {
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
    public void handleCerrarSesion(ActionEvent logOutEvent) {
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
     * Llamar a este método abrirá la ventana de SignIn
     *
     * @param primaryStage objeto Stage (Ventana)
     * @throws IOException salta una excepcion si la ventana de SignIn falla en
     * abrirse
     */
    private void startSignInWindow(Stage primaryStage) throws IOException {
        try {
            LOG.info("Abriendo ventana SignIn...");
            //Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/SignIn.fxml"));
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

    /**
     * Llamar a este método limpiara todos los campos si están informados
     */
    private void limpiar() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtStock.setText("");
        txtNombreFiltro.setText("");

        btnAnadir.setDisable(true);
        btnLimpiar.setDisable(true);

        txtNombre.requestFocus();
    }

    /**
     * Comprueba si algún campo de texto está vacio
     *
     * @return devuelve un booleano si esta vacio o no
     */
    private boolean comprobarCampoVacio() {
        boolean vacio = false;
        if (txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtStock.getText().isEmpty()) {
            vacio = true;
        }
        return vacio;
    }

}
