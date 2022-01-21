package GESRE.controller;

import GESRE.entidades.Pieza;
import GESRE.entidades.Trabajador;
import GESRE.excepcion.PiezaNoExisteException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.PiezasManager;
import GESRE.interfaces.TrabajadorManager;
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
    private Integer idTrabajador;
    private Pieza pieza = null;
    private Trabajador trabajador = null;

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
    TrabajadorManager trabajadorManager = GestionFactoria.getTrabajadorGestion();

    /**
     * Establece el Stage de PiezaView
     *
     * @param piezaViewStage valor del Stage pieazViewStage
     * @param id
     */
    public void setStage(Stage piezaViewStage, Integer id) {
        stage = piezaViewStage;
        idTrabajador = id;
    }

    /**
     * Inicializar Ventana
     *
     * @param root Contiene el FXML
     */
    public void initStage(Parent root) {
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
            desabilitarBtnCamposVacios();
            txtNombreFiltro.setDisable(true);
            //Establecer valores del combobox
            ObservableList<String> filtros = FXCollections.observableArrayList("Todo", "Nombre", "Stock");
            cbxFiltro.setItems(filtros);
            //Si el filtro seleccionado es "Nombre" se activa el campo para buscar por Nombre
            controlComboBox();
            stage.setOnCloseRequest(this::handleSalir);

            //Listeners de los TextField al cambiar el texto, textProperty
            txtNombre.textProperty().addListener(this::limitNombreTextField);
            txtADescripcion.textProperty().addListener(this::limitDescripcionTextField);
            txtStock.textProperty().addListener(this::limitStockTextField);
            txtNombreFiltro.textProperty().addListener(this::limitNombreFiltroTextField);
            tablaPiezas.getSelectionModel().selectedItemProperty().addListener(this::handleTablaPiezaSelection);

            //Establecer el valor que aparecera dentro de cada celda
            nombreCl.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            descripcionCl.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            stockCl.setCellValueFactory(new PropertyValueFactory<>("stock"));

            //Llenar la tabla de datos con todas las piezas de un trabajador
            datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByTrabajadorId(pieza, idTrabajador));
            tablaPiezas.setItems(datosPieza);

            //Listeners
            btnLimpiar.setOnAction(this::handleLimpiar);
            btnAnadir.setOnAction(this::handleBtnAnadir);
            btnEditar.setOnAction(this::handleBtnEditar);
            btnBorrar.setOnAction(this::handleBtnBorrar);
            btnBuscar.setOnAction(this::handleBtnBuscar);
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
     * Llamar a este método abrirá la ventana de IncidenciaViewT (Gestion de
     * incidencias del trabajador)
     *
     * @param primaryStage objeto Stage (Ventana)
     * @throws IOException salta una excepcion si la ventana de IncidenciaViewT
     * falla en abrirse
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
     * Llamar a este método buscará las piezas por le nombre introducido
     */
    private void handleBtnBuscar(ActionEvent buscarrEvent) {

        if (cbxFiltro.getSelectionModel().getSelectedIndex() == 0) {
            LOG.info("Filtro: Todo");
            datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByTrabajadorId(pieza, idTrabajador));
            tablaPiezas.setItems(datosPieza);
        }

        if (cbxFiltro.getSelectionModel().getSelectedIndex() == 1 && !(txtNombreFiltro.getText().isEmpty())) {
            LOG.log(Level.INFO, "Filtro: Nombre");
            datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByName(pieza, txtNombreFiltro.getText()));
            tablaPiezas.setItems(datosPieza);
        }

        if (cbxFiltro.getSelectionModel().getSelectedIndex() == 2) {
            LOG.info("Filtro: Stock");
            datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByStock(pieza, idTrabajador));
            tablaPiezas.setItems(datosPieza);
        }

        //Actualizar tabla
        tablaPiezas.refresh();
    }

    /**
     * Llamar a este método creará una nueva Pieza
     *
     * @param anadirEvent evento de accion
     */
    private void handleBtnAnadir(ActionEvent anadirEvent) {
        try {
            //Solo se admiten numeros no negativos
            if (txtStock.getText().matches("-?([0-9]*)?") && !txtStock.getText().contains("-")) {
                pieza = new Pieza();
                pieza.setNombre(txtNombre.getText());
                pieza.setDescripcion(txtADescripcion.getText());
                pieza.setStock(new Integer(txtStock.getText()));

                piezasManager.createPieza(pieza);

                //Agregar nueva pieza a tabla
                tablaPiezas.getItems().add(pieza);

                //Actualizar tabla
                tablaPiezas.refresh();
            } else {
                messageLbl.setText("Solo se admiten números (positivos)");
                txtADescripcion.setStyle("-fx-border-color: White;");
                txtNombre.setStyle("-fx-border-color: White;");
                messageLbl.setStyle("-fx-text-fill: #DC143C");
                txtStock.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                messageLbl.setVisible(true);
            }

        } catch (Exception e) {

        }

    }

    /**
     * Llamar a este método borrará una Pieza
     *
     * @param borrarEvent evento de accion
     */
    private void handleBtnBorrar(ActionEvent borrarEvent) {
        try {

            LOG.info("Borrando Pieza...");
            //Obtener la pieza seleccionada de la tabla
            pieza = tablaPiezas.getSelectionModel().getSelectedItem();

            //Ventana de confirmacion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Borrar la fila seleccionada?\n"
                    + "La operación no se puede revertir", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //Respuesta afirmativa
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Borrar Pieza del servidor
                piezasManager.removePieza(pieza);
                //Limpiar los campos
                txtNombre.setText("");
                txtADescripcion.setText("");
                txtStock.setText("");
                //Deseleccionar la fila
                tablaPiezas.getSelectionModel().clearSelection();
                //Eliminar de la tabla
                tablaPiezas.getItems().remove(pieza);
                //Actualizar tabla
                tablaPiezas.refresh();
            } else {
                //Activar label
                messageLbl.setText("La pieza no puede borrarse porque no existe");
                messageLbl.setStyle("-fx-text-fill: #DC143C");
                messageLbl.setVisible(true);
            }

        } catch (Exception e) {
            LOG.severe("Error al intentar borrar la Pieza");
        }
    }

    /**
     * Llamar a este método Actualizará una Pieza
     *
     * @param editarWEvent evento de accion
     */
    private void handleBtnEditar(ActionEvent editarEvent) {
        try {
            LOG.info("Actualizando Pieza...");
            //Obtener la pieza seleccionada de la tabla
            pieza = tablaPiezas.getSelectionModel().getSelectedItem();

            if (!pieza.getNombre().equals(txtNombre.getText())) {
                pieza.setNombre(txtNombre.getText());
                pieza.setDescripcion(txtADescripcion.getText());
                pieza.setStock(Integer.parseInt(txtStock.getText()));
                piezasManager.editPieza(pieza);
            }
            //Clean entry text fields
            txtNombre.setText("");
            txtADescripcion.setText("");
            txtStock.setText("");

            //Deseleccionar la fila
            tablaPiezas.getSelectionModel().clearSelection();

            //cambiar datos tabla
            pieza.setNombre(txtNombre.getText());
            pieza.setDescripcion(txtADescripcion.getText());
            pieza.setStock(Integer.parseInt(txtStock.getText()));
            //Refrescamos la tabla para que muestre los nuevos datos
            tablaPiezas.refresh();
        } catch (Exception e) {

        }
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
     * Llamar a este método cerrará la sesíón actual para volver a la ventana
     * deSignIn
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
     * Llamar a este método establece el control del campo Nombre
     * (textProperty())
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

            txtADescripcion.setStyle("-fx-border-color: White;");
            txtStock.setStyle("-fx-border-color: White;");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }

    /**
     * Llamar a este método establece el control del campo Descripcion
     * (textProperty())
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
            txtStock.setStyle("-fx-border-color: White;");
            messageLbl.setVisible(false);
            messageLbl.setStyle("");

            txtNombre.setStyle("-fx-border-color: White;");
            txtADescripcion.setStyle("-fx-border-color: White;");

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }

    /**
     * Llamar a este método establece el control del campo Stock
     * (textProperty())
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

            txtNombre.setStyle("-fx-border-color: White;");
            txtStock.setStyle("-fx-border-color: White;");

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }

    /**
     * Llamar a este método establece el control del campo NombreFiltro
     * (textProperty())
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

    /**
     * Llamar a este método deshabilitará los botones Añadir, Editar y Borrar
     */
    private void desabilitarBtnCamposVacios() {
        LOG.info("Deshabilitando botones Añadir, Editar y Borrar");
        //Se comprueba cuando el boton debe estar desabilitado
        btnAnadir.disableProperty().bind(
                txtNombre.textProperty().isEmpty()
                        .or(txtADescripcion.textProperty().isEmpty())
                        .or(txtStock.textProperty().isEmpty())
        );
        btnEditar.disableProperty().bind(
                txtNombre.textProperty().isEmpty()
                        .or(txtADescripcion.textProperty().isEmpty())
                        .or(txtStock.textProperty().isEmpty())
        );
        btnBorrar.disableProperty().bind(
                txtNombre.textProperty().isEmpty()
                        .or(txtADescripcion.textProperty().isEmpty())
                        .or(txtStock.textProperty().isEmpty())
        );
    }

    /**
     * Llamar a este método controlará la interacción con el comboBox
     */
    private void controlComboBox() {
        cbxFiltro.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Nombre")) {
                txtNombreFiltro.setDisable(false);

            } else {
                txtNombreFiltro.setDisable(true);
                txtNombreFiltro.setText("");
            }
        });
    }

    /**
     * Users table selection changed event handler. It enables or disables
     * buttons depending on selection state of the table.
     *
     * @param observable the property being observed: SelectedItem Property
     * @param oldValue old UserBean value for the property.
     * @param newValue new UserBean value for the property.
     */
    private void handleTablaPiezaSelection(ObservableValue observable, Object oldValue, Object newValue) {
        //Si una fila esta seleccionada mover los datos de la fila a los campos
        if (newValue != null) {
            pieza = (Pieza) newValue;
            txtNombre.setText(pieza.getNombre());
            txtADescripcion.setText(pieza.getDescripcion());
            txtStock.setText(pieza.getStock().toString());

            LOG.info("Información de Pieza: " + pieza.toString());
        } else {
            LOG.info("Fila no seleccionada");
            //Si no hay fila seleccionada limpiar los campos
            txtNombre.setText("");
            txtADescripcion.setText("");
            txtStock.setText("");
        }
        //Focus login field
        txtNombre.requestFocus();
    }

}
