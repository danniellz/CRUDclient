package GESRE.controller;

import GESRE.entidades.Cliente;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.ClienteManager;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            
            //Controls
            anadirBtn.setDisable(true);
            borrarBtn.setDisable(true);
            editarBtn.setDisable(true);
            buscarTxt.setDisable(true);
            
            //Definir columnas de la tabla trabajador
            usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            correoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            fechaRegistroColumn.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

            //Llenar la tabla de datos (Lista de todos los trabajadores)
            datosClientes = FXCollections.observableArrayList(clienteManager.findAllClientes());
            clientesTabla.setItems(datosClientes);
            
            //Show window (asynchronous)
            stage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Stage init error", e);
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
    
}
