package GESRE.controller;

import GESRE.entidades.Cliente;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.ClienteManager;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Mikel Matilla
 */
public class GestionClientesController {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(GestionClientesController.class.getName());

    private static final int MAX_LENGHT_50 = 50;

    private Stage stage;

    @FXML
    private Pane gestionClientesTxt;
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
    private TableColumn usuarioColumn;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn correoColumn;
    @FXML
    private TableColumn fechaRegistroColumn;

    private ObservableList<Cliente> datosClientes;

    private ClienteManager clienteManager = GestionFactoria.createClienteManager();

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
            anadirBtn.setDisable(true);
            borrarBtn.setDisable(true);
            editarBtn.setDisable(true);
            buscarTxt.setDisable(true);
            fechaRegistroDate.setEditable(false);
            fechaRegistroDate.setValue(LocalDate.now());

            //Definir columnas de la tabla cliente
            usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            correoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            fechaRegistroColumn.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

            //Llenar la tabla de datos (Lista de todos los trabajadores)
            datosClientes = FXCollections.observableArrayList(clienteManager.findAllClientes());
            clientesTabla.setItems(datosClientes);

            //Listeners
            limpiarBtn.setOnAction(this::handleBtnLimpiar);
            anadirBtn.setOnAction(this::handleBtnAnadir);
            editarBtn.setOnAction(this::handleBtnEditar);
            borrarBtn.setOnAction(this::handleBtnBorrar);
            buscarBtn.setOnAction(this::handleBtnBuscar);

            usuarioTxt.textProperty().addListener(this::handleValidarTexto);
            nombreTxt.textProperty().addListener(this::handleValidarTexto);
            correoTxt.textProperty().addListener(this::handleValidarTexto);
            contrasenaTxt.textProperty().addListener(this::handleValidarTexto);
            repiteContrasenaTxt.textProperty().addListener(this::handleValidarTexto);
            
            clientesTabla.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);

            //Show window (asynchronous)
            stage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Stage init error", e);
        }

    }
    
    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
       
        if(newValue!=null){
            Cliente cliente = (Cliente) newValue;
            LOG.info(cliente.getLogin());
            usuarioTxt.setText(cliente.getLogin());
            nombreTxt.setText(cliente.getFullName());
            correoTxt.setText(cliente.getEmail());
            
            /*cbDepartamentos.getSelectionModel().select(user.getDepartamento());
            if(user.getPerfil().equals(Profile.ADMIN))tgPerfil.selectToggle(rbAdmin);
            else tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(false);
            btModificar.setDisable(false);
            btEliminar.setDisable(false);*/
        }else{
        //If there is not a row selected, clean window fields 
        //and disable create, modify and delete buttons
            usuarioTxt.setText("");
            nombreTxt.setText("");
            correoTxt.setText("");
            //cbDepartamentos.getSelectionModel().clearSelection();
            /*tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
            btEliminar.setDisable(true);*/
        }
        //Focus login field
        //usuarioTxt.requestFocus();
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
                maxLenght = 50;
                break;
        }

        if (changedTextField.getText().length() > maxLenght) {
            String text = changedTextField.getText().substring(0, maxLenght);
            changedTextField.setText(text);
        }

        if (camposInformados()) {
            deshabilitarBotones(false);
        } else {
            deshabilitarBotones(true);
        }
    }

    private void handleBtnLimpiar(ActionEvent limpiarEvent) {
        usuarioTxt.setText("");
        nombreTxt.setText("");
        correoTxt.setText("");
        contrasenaTxt.setText("");
        repiteContrasenaTxt.setText("");
        fechaRegistroDate.setValue(LocalDate.now());
        anadirBtn.setDisable(true);
        borrarBtn.setDisable(true);
        editarBtn.setDisable(true);
        usuarioTxt.requestFocus();
    }

    private void handleBtnBuscar(ActionEvent buscarEvent) {

    }

    private void handleBtnAnadir(ActionEvent anadirEvent) {

    }

    private void handleBtnBorrar(ActionEvent borrarEvent) {

    }

    private void handleBtnEditar(ActionEvent editarEvent) {

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
