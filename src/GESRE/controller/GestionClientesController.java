package GESRE.controller;

import GESRE.entidades.Cliente;
import GESRE.entidades.UserPrivilege;
import static GESRE.entidades.UserPrivilege.CLIENTE;
import static GESRE.entidades.UserStatus.ENABLED;
import GESRE.excepcion.LoginExisteException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.ClienteManager;
import GESRE.interfaces.UsuarioManager;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Mikel Matilla
 */
public class GestionClientesController {

    //
    public static final Pattern PATRON_USUARIO = Pattern.compile("^[A-Z0-9]+$", Pattern.CASE_INSENSITIVE);

    public static final Pattern PATRON_NOMBRE = Pattern.compile("^[A-Z\\s]+$", Pattern.CASE_INSENSITIVE);

    public static final Pattern PATRON_EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern PATRON_CONTRA = Pattern.compile("^[A-Z0-9]+$", Pattern.CASE_INSENSITIVE);

    //LOGGER
    private static final Logger LOG = Logger.getLogger(GestionClientesController.class.getName());

    //Stage
    private Stage stage;

    @FXML
    private TextField usuarioTxt;
    @FXML
    private TextField nombreTxt;
    @FXML
    private TextField correoTxt;
    @FXML
    private TextField contrasenaTxt;
    @FXML
    private TextField repiteContrasenaTxt;
    @FXML
    private DatePicker fechaRegistroDate;

    @FXML
    private Button anadirBtn;
    @FXML
    private Button borrarBtn;
    @FXML
    private Button editarBtn;
    @FXML
    private Button limpiarBtn;

    @FXML
    private ComboBox buscarCombo;
    @FXML
    private TextField buscarTxt;
    @FXML
    private Button buscarBtn;

    @FXML
    private Button trabajadoresBtn;

    @FXML
    private TableView clientesTabla;

    @FXML
    private TableColumn<Cliente, String> usuarioColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> correoColumn;
    @FXML
    private TableColumn<Cliente, Date> fechaRegistroColumn;

    private ObservableList<Cliente> datosClientes;

    private final ClienteManager clienteManager = GestionFactoria.createClienteManager();
    private final UsuarioManager usuarioManager = GestionFactoria.createUsuarioManager();

    public void setStage(Stage gestionClientesStage) {
        stage = gestionClientesStage;
    }

    public void initStage(Parent root) {
        try {
            //Creates a new Scene
            Scene scene = new Scene(root);

            //Associate the scene to window(stage)
            stage.setScene(scene);

            //Window properties
            stage.setTitle("Gestion Clientes");
            stage.setResizable(false);
            stage.setOnCloseRequest(this::handleCloseRequest);

            //Controles
            usuarioTxt.requestFocus();
            anadirBtn.setDisable(true);
            borrarBtn.setDisable(true);
            editarBtn.setDisable(true);
            buscarTxt.setDisable(true);
            buscarBtn.setDisable(true);
            fechaRegistroDate.setEditable(false);
            fechaRegistroDate.setValue(LocalDate.now());

            //Definir columnas de la tabla cliente
            usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            correoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            fechaRegistroColumn.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
            definirFormatoFecha();

            //Llenar la tabla con datos (Lista de todos los clientes)
            datosClientes = FXCollections.observableArrayList(clienteManager.findAllClientes());
            clientesTabla.setItems(datosClientes);

            //LLenar combo buscar
            ObservableList<String> filtros = FXCollections.observableArrayList();
            filtros.addAll("Todos", "Nombre", "Clientes Con Incidencia");
            buscarCombo.setItems(filtros);
            buscarCombo.setValue("Todos");

            //Botones
            limpiarBtn.setOnAction(this::handleBtnLimpiar);
            anadirBtn.setOnAction(this::handleBtnAnadir);
            editarBtn.setOnAction(this::handleBtnEditar);
            borrarBtn.setOnAction(this::handleBtnBorrar);
            buscarBtn.setOnAction(this::handleBtnBuscar);

            //Listeners
            usuarioTxt.textProperty().addListener(this::handleValidarTexto);
            nombreTxt.textProperty().addListener(this::handleValidarTexto);
            correoTxt.textProperty().addListener(this::handleValidarTexto);
            contrasenaTxt.textProperty().addListener(this::handleValidarTexto);
            repiteContrasenaTxt.textProperty().addListener(this::handleValidarTexto);
            buscarTxt.textProperty().addListener(this::handleBuscarTxt);
            clientesTabla.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
            buscarCombo.getSelectionModel().selectedItemProperty().addListener(this::handleFiltros);

            //Show window (asynchronous)
            stage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Stage init error", e);
        }

    }

    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {

        if (newValue != null) {
            Cliente cliente = (Cliente) newValue;
            usuarioTxt.setText(cliente.getLogin());
            nombreTxt.setText(cliente.getFullName());
            correoTxt.setText(cliente.getEmail());
            fechaRegistroDate.setValue(cliente.getFechaRegistro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            contrasenaTxt.setText("");
            contrasenaTxt.setDisable(true);
            repiteContrasenaTxt.setText("");
            repiteContrasenaTxt.setDisable(true);

            anadirBtn.setDisable(true);
            editarBtn.setDisable(false);
            borrarBtn.setDisable(false);

            buscarCombo.setValue(null);
            buscarTxt.setText("");
            buscarCombo.setDisable(true);
            buscarTxt.setDisable(true);
            buscarBtn.setDisable(true);
        } else {
            //If there is not a row selected, clean window fields and disable create, modify and delete buttons
            usuarioTxt.setText("");
            nombreTxt.setText("");
            correoTxt.setText("");
            contrasenaTxt.setDisable(false);
            repiteContrasenaTxt.setDisable(false);

            deshabilitarBotones(true);
            buscarCombo.setValue("Todos");
            buscarCombo.setDisable(false);
            buscarCombo.setDisable(false);
        }
        //Focus login field
        usuarioTxt.requestFocus();
    }

    private void handleValidarTexto(ObservableValue observable, String oldValue, String newValue) {
        StringProperty textProperty = (StringProperty) observable;
        TextField changedTextField = (TextField) textProperty.getBean();
        String changedTextFieldName = changedTextField.getId();

        //Limite de caracteres
        int maxLenght = 0;
        switch (changedTextFieldName) {
            case "usuarioTxt":
            case "contrasenaTxt":
            case "repiteContrasenaTxt":
                maxLenght = 25;
                break;
            case "nombreTxt":
            case "correoTxt":
            case "buscarTxt":
                maxLenght = 50;
                break;
        }

        if (changedTextField.getText().length() > maxLenght) {
            String text = changedTextField.getText().substring(0, maxLenght);
            changedTextField.setText(text);
        }

        if (camposInformados()) {
            anadirBtn.setDisable(false);
        } else {
            anadirBtn.setDisable(true);
        }
    }

    private void handleBtnLimpiar(ActionEvent limpiarEvent) {
        usuarioTxt.setText("");
        nombreTxt.setText("");
        correoTxt.setText("");
        contrasenaTxt.setText("");
        repiteContrasenaTxt.setText("");
        fechaRegistroDate.setValue(LocalDate.now());
        deshabilitarBotones(true);
        usuarioTxt.requestFocus();
        clientesTabla.getSelectionModel().clearSelection();
    }

    private void handleBtnBuscar(ActionEvent buscarEvent) {
        datosClientes = FXCollections.observableArrayList(clienteManager.findClienteByFullName(buscarTxt.getText()));
        clientesTabla.setItems(datosClientes);
    }

    private void handleBtnAnadir(ActionEvent anadirEvent) {
        if (patronesCorrectos()) {
            if (contrasenaCorrecta()) {
                if (contrasenaTxt.getText().equals(repiteContrasenaTxt.getText())) {
                    try {
                        usuarioManager.buscarUsuarioPorLoginCrear(usuarioTxt.getText());
                        
                        Cliente cliente = new Cliente();

                        cliente.setLogin(usuarioTxt.getText());
                        cliente.setFullName(nombreTxt.getText());
                        cliente.setEmail(correoTxt.getText());
                        cliente.setPassword(contrasenaTxt.getText());
                        cliente.setFechaRegistro(Date.from(fechaRegistroDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        cliente.setLastPasswordChange(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        cliente.setPrivilege(CLIENTE);
                        cliente.setStatus(ENABLED);

                        if (cliente != null) {
                            //Crear cliente
                            clienteManager.createCliente(cliente);

                            //Actualizar la tabla
                            datosClientes = FXCollections.observableArrayList(clienteManager.findAllClientes());
                            clientesTabla.setItems(datosClientes);
                            clientesTabla.refresh();

                            //Mensaje de informacion
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Info");
                            alert.setContentText("Cliente añadido correctamente");
                            alert.showAndWait();

                            //Limpiar campos
                            usuarioTxt.setText("");
                            nombreTxt.setText("");
                            correoTxt.setText("");
                            contrasenaTxt.setText("");
                            repiteContrasenaTxt.setText("");
                            fechaRegistroDate.setValue(LocalDate.now());
                            deshabilitarBotones(true);
                            usuarioTxt.requestFocus();
                        }
                    } catch (LoginExisteException ex) {
                        Logger.getLogger(GestionClientesController.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("El usuario ya existe");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Las contraseñas deben coincidir");
                    alert.showAndWait();
                }
            }
        }
    }

    private void handleBtnBorrar(ActionEvent borrarEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Esta seguro de que desea eliminarlo?");
            alert.setTitle("Borrar");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                Cliente clienteSeleccionado = (Cliente) clientesTabla.getSelectionModel().getSelectedItem();
                if (clienteSeleccionado != null) {
                    //Borrar cliente
                    clienteManager.deleteCliente(clienteSeleccionado.getIdUsuario());

                    //Limpiar datos
                    usuarioTxt.setText("");
                    nombreTxt.setText("");
                    correoTxt.setText("");
                    contrasenaTxt.setText("");
                    repiteContrasenaTxt.setText("");
                    fechaRegistroDate.setValue(LocalDate.now());
                    deshabilitarBotones(true);
                    usuarioTxt.requestFocus();
                    clientesTabla.getSelectionModel().clearSelection();

                    //Actuializar tabla
                    datosClientes = FXCollections.observableArrayList(clienteManager.findAllClientes());
                    clientesTabla.setItems(datosClientes);
                    clientesTabla.refresh();
                }
            } else {
                //Cancel the event process
                borrarEvent.consume();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Delete request error", e);
        }
    }

    private void handleBtnEditar(ActionEvent editarEvent) {
        if (patronesCorrectos()) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("¿Esta seguro de que desea modifarlo?");
                alert.setTitle("Editar");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get() == ButtonType.OK) {
                    Cliente clienteSeleccionado = (Cliente) clientesTabla.getSelectionModel().getSelectedItem();
                    
                    Cliente clienteModificado = clienteSeleccionado;
                    
                    clienteModificado.setLogin(usuarioTxt.getText());
                    clienteModificado.setFullName(nombreTxt.getText());
                    clienteModificado.setEmail(correoTxt.getText());
                    clienteModificado.setFechaRegistro(Date.from(fechaRegistroDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    
                    if (clienteSeleccionado!=null & clienteModificado!=null) {
                        //Borrar cliente
                        clienteManager.editCliente(clienteModificado, clienteSeleccionado.getIdUsuario());

                        //Limpiar datos
                        usuarioTxt.setText("");
                        nombreTxt.setText("");
                        correoTxt.setText("");
                        contrasenaTxt.setText("");
                        repiteContrasenaTxt.setText("");
                        fechaRegistroDate.setValue(LocalDate.now());
                        deshabilitarBotones(true);
                        usuarioTxt.requestFocus();
                        clientesTabla.getSelectionModel().clearSelection();

                        //Actuializar tabla
                        datosClientes = FXCollections.observableArrayList(clienteManager.findAllClientes());
                        clientesTabla.setItems(datosClientes);
                        clientesTabla.refresh();
                    }
                } else {
                    //Cancel the event process
                    editarEvent.consume();
                }
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Edit request error", e);
            }
        }
    }

    public void handleCloseRequest(WindowEvent closeEvent) {
        try {
            LOG.info("Confirm Closing");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Esta seguro de que desea salir?");
            alert.setTitle("Salir");
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

    public void handleFiltros(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            if (buscarCombo.getValue().equals("Todos")) {
                buscarTxt.setText("");
                buscarTxt.setDisable(true);
                buscarBtn.setDisable(true);
                datosClientes = FXCollections.observableArrayList(clienteManager.findAllClientes());
                clientesTabla.setItems(datosClientes);
            } else if (buscarCombo.getValue().equals("Nombre")) {
                buscarTxt.setDisable(false);
                buscarBtn.setDisable(true);
            } else if (buscarCombo.getValue().equals("Clientes Con Incidencia")) {
                buscarTxt.setText("");
                buscarTxt.setDisable(true);
                buscarBtn.setDisable(true);
                datosClientes = FXCollections.observableArrayList(clienteManager.findAllClienteWithIncidencia());
                clientesTabla.setItems(datosClientes);
            }
        } else {
            buscarTxt.setText("");
            buscarTxt.setDisable(true);
            buscarBtn.setDisable(true);
        }
    }

    public void handleBuscarTxt(ObservableValue observable, Object oldValue, Object newValue) {
        int maxLenght = 50;

        if (!buscarTxt.getText().equals("")) {
            buscarBtn.setDisable(false);

            if (buscarTxt.getText().length() > maxLenght) {
                String text = buscarTxt.getText().substring(0, maxLenght);
                buscarTxt.setText(text);
            }
        } else {
            buscarBtn.setDisable(true);
        }
    }

    private boolean patronesCorrectos() {
        Matcher matcher = null;
        String mensaje = "Los siguientes campos contienen caracteres no permitidos o el formato no es correcto: ";
        boolean correcto = true;

        matcher = PATRON_USUARIO.matcher(usuarioTxt.getText());
        if (!matcher.find()) {
            mensaje = mensaje + " usuario";
            correcto = false;
        }

        matcher = PATRON_NOMBRE.matcher(nombreTxt.getText());
        if (!matcher.find()) {
            if (correcto) {
                mensaje = mensaje + " nombre";
            } else {
                mensaje = mensaje + ", nombre";
            }
            correcto = false;
        }

        matcher = PATRON_EMAIL.matcher(correoTxt.getText());
        if (!matcher.find()) {
            if (correcto) {
                mensaje = mensaje + " correo";
            } else {
                mensaje = mensaje + ", correo";
            }
            correcto = false;
        }
        
        if (!correcto) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(mensaje);
            alert.showAndWait();
        }

        return correcto;
    }

    private boolean contrasenaCorrecta() {
        Matcher matcher = null;
        String mensaje = "El formato de la contraseña es incorrecto";
        boolean correcto = true;
        
        matcher = PATRON_CONTRA.matcher(contrasenaTxt.getText());
        if (!matcher.find()) {
            correcto = false;
        }
        
        if (!correcto) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
        
        return correcto;
    }
    
    private void definirFormatoFecha() {
        fechaRegistroColumn.setCellFactory(colum -> {
            TableCell<Cliente, Date> cell = new TableCell<Cliente, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
    }

    private boolean camposInformados() {
        if (usuarioTxt.getText().isEmpty() || nombreTxt.getText().isEmpty() || correoTxt.getText().isEmpty() || contrasenaTxt.getText().isEmpty() || repiteContrasenaTxt.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void deshabilitarBotones(Boolean deshabilitar) {
        anadirBtn.setDisable(deshabilitar);
        borrarBtn.setDisable(deshabilitar);
        editarBtn.setDisable(deshabilitar);
        buscarTxt.setDisable(deshabilitar);
    }
}
