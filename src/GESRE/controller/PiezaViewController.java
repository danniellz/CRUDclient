package GESRE.controller;

import GESRE.entidades.Pieza;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.PiezasManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    //********LABELS********
    @FXML
    private Label messageLbl;

    //********TEXT FIELDS********
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtADescripcion;
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
            btnBuscar.setDisable(true);
            txtNombreFiltro.setDisable(true);
            stage.setOnCloseRequest(this::handleSalir);

            //Listeners de los TextField al cambiar el texto, textProperty
            txtNombre.textProperty().addListener(this::limitNombreTextField);
            txtADescripcion.textProperty().addListener(this::limitDescripcionTextField);
            txtStock.textProperty().addListener(this::limitStockTextField);
            txtNombreFiltro.textProperty().addListener(this::limitNombreFiltroTextField);

            //Establecer el valor que aparecera dentro de cada celda
            nombreCl.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            descripcionCl.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            stockCl.setCellValueFactory(new PropertyValueFactory<>("stock"));

            //Llenar la tabla de datos con todas las piezas de un trabajador
            datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByTrabajadorId(pieza, idTrabajador));
            tablaPiezas.setItems(datosPieza);

            //Listeners
            btnLimpiar.setOnAction(this::handleLimpiar);
            //btnAnadir.setOnAction(this::handleBtnAnadir);
            //btnModificar.setOnAction(this::handleBtnModificar);
            //btnEliminar.setOnAction(this::handleBtnEliminar);
            //btnBuscar.setOnAction(this::handleBtnBuscar);
            //cerrarSesion.setOnAction(this::handleCerrarSesion);
            //btnGestionIncidencia.setOnAction(this::startIncidenciaViewTWindow);
            //salir.setOnAction(this::handleSalir);
            //Mostrar ventana (asincrona)
            stage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al inicializar Stage", e);
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
     * Llamar a este método abrirá la ventana de IncidenciaViewT (Gestion de incidencias del trabajador)
     *
     * @param primaryStage objeto Stage (Ventana)
     * @throws IOException salta una excepcion si la ventana de IncidenciaViewT falla en abrirse
     */
    /*private void startIncidenciaViewTWindow(Stage primaryStage) throws IOException {
        try {
            LOG.info("Abriendo ventana IncidenciaViewTWindow...");
            //Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/IncidenciaViewT.fxml"));
            Parent root = (Parent) loader.load();
            //Obtiene el controlador
            IncidenciaCLViewController controlador = ((SignInController) loader.getController());
            //Establece el Stage
            controlador.setStage(primaryStage);
            //Inicializa la ventana
            controlador.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al intentar abrir la ventana de SignUp", ex);
        }

    }*/

    /**
     * Llamar a este método limpiara todos los campos si están informados
     */
    private void handleLimpiar(ActionEvent limpiarEvent) {
        txtNombre.setText("");
        txtADescripcion.setText("");
        txtStock.setText("");
        txtNombreFiltro.setText("");

        btnAnadir.setDisable(true);
        txtNombre.requestFocus();
    }
    
    /**
     * Llamar a este método cerrará la ventana
     *
     * @param salirWindowEvent evento de ventana
     */
    public void handleSalir(WindowEvent salirWindowEvent) {
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
                salirWindowEvent.consume();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al intentar cerrar la ventana", e);
        }
    }

    /**
     * Llamar a este método cerrará la sesíón actual para volver a la ventana de SignIn
     *
     * @param cerrarSesiontEvent Cerrar Sesion action event
     */
    public void handleCerrarSesion(ActionEvent cerrarSesiontEvent) {
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
                cerrarSesiontEvent.consume();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Log Out error", ex);
        }
    }

    /**
     * Comprueba si algún campo de texto está vacio
     *
     * @return devuelve un booleano si esta vacio o no
     */
    private boolean comprobarCampoVacio() {
        boolean vacio = false;
        if (txtNombre.getText().isEmpty() || txtADescripcion.getText().isEmpty() || txtStock.getText().isEmpty()) {
            vacio = true;
        }
        return vacio;
    }
    

    /**
     * Llamar a este método establece el control del campo Nombre (textProperty())
     *
     * @param observable campo objetivo cuyo valor cambia
     * @param oldValue valor previo al cambio
     * @param newValue ultimo valor introducido
     */
    private void limitNombreTextField(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            //si un 26 caracter es introducido, se borra
            if (txtNombre.getText().length() > 25) {
                //Preparar mensaje del label
                messageLbl.setText("Límite de 25 caracteres alcanzado");
                //Borrar ultimo caracter introducido
                txtNombre.deleteNextChar();
                //Color del campo rojo para feedback visual
                txtNombre.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                //Activar label
                messageLbl.setStyle("-fx-text-fill: #DC143C");
                messageLbl.setVisible(true);
                
            } else {
                //Mientras los caracteres introducidos sean menor a 26, se desactiva el label y el campo vuelve a su color normal
                txtNombre.setStyle("-fx-border-color: White;");
                messageLbl.setVisible(false);
                messageLbl.setStyle("");
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }
    
    /**
     * Llamar a este método establece el control del campo Descripcion (textProperty())
     *
     * @param observable campo objetivo cuyo valor cambia
     * @param oldValue valor previo al cambio
     * @param newValue ultimo valor introducido
     */
    private void limitStockTextField(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            String cadena = txtStock.getText();
            //si un 4 caracter es introducido, se borra
            if (cadena.length() > 3) {
                //Borrar ultimo caracter introducido
                txtStock.deleteNextChar();
            }
            //Control empty spaces
            if (cadena.contains(" ")) {
                txtStock.setText(cadena.replaceAll(" ", ""));
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }
    
    /**
     * Llamar a este método establece el control del campo Stock (textProperty())
     *
     * @param observable campo objetivo cuyo valor cambia
     * @param oldValue valor previo al cambio
     * @param newValue ultimo valor introducido
     */
    private void limitDescripcionTextField(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            String cadena = txtADescripcion.getText();
            //si un 256 caracter es introducido, se borra
            if (cadena.length() > 255) {
                messageLbl.setText("Límite de 255 caracteres alcanzado");
                //Borrar ultimo caracter introducido
                txtADescripcion.deleteNextChar();
                //Color del campo rojo para feedback visual
                txtADescripcion.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                //Activar label
                messageLbl.setStyle("-fx-text-fill: #DC143C");
                messageLbl.setVisible(true);
                
            } else {
                //Mientras los caracteres introducidos sean menor a 26, se desactiva el label y el campo vuelve a su color normal
                txtADescripcion.setStyle("-fx-border-color: White;");
                messageLbl.setVisible(false);
                messageLbl.setStyle("");
            }

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }
    
    /**
     * Llamar a este método establece el control del campo NombreFiltro (textProperty())
     *
     * @param observable campo objetivo cuyo valor cambia
     * @param oldValue valor previo al cambio
     * @param newValue ultimo valor introducido
     */
    private void limitNombreFiltroTextField(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            //si un 26 caracter es introducido, se borra
            if (txtNombreFiltro.getText().length() > 25) {
                //Preparar mensaje del label
                messageLbl.setText("Límite de 25 caracteres alcanzado");
                //Borrar ultimo caracter introducido
                txtNombreFiltro.deleteNextChar();
                //Color del campo rojo para feedback visual
                txtNombreFiltro.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                //Activar label
                messageLbl.setStyle("-fx-text-fill: #DC143C");
                messageLbl.setVisible(true);
                
            } else {
                //Mientras los caracteres introducidos sean menor a 26, se desactiva el label y el campo vuelve a su color normal
                txtNombreFiltro.setStyle("-fx-border-color: White;");
                messageLbl.setVisible(false);
                messageLbl.setStyle("");
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }

}
