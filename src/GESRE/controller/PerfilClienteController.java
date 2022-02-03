package GESRE.controller;

import GESRE.entidades.Cliente;
import GESRE.excepcion.UsuarioNoExisteException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.ClienteManager;
import GESRE.interfaces.UsuarioManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Mikel Matilla
 */
public class PerfilClienteController {
    
    //LOGGER
    private static final Logger LOG = Logger.getLogger(PerfilClienteController.class.getName());
    
    private Stage stage;
    private Cliente cliente;
    
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mnCerrarSecion;
    @FXML
    private MenuItem mnSalir;
    
    @FXML
    private TextField usuarioTxt;
    @FXML
    private TextField nombreTxt;
    @FXML
    private TextField correoTxt;
    @FXML
    private PasswordField contrasenaTxt;
    @FXML
    private PasswordField nuevaContrasenaTxt;
    @FXML
    private PasswordField repiteContrasenaTxt;
    
    @FXML
    private Button volverBtn;
    @FXML
    private Button guardarBtn;
    
    private final ClienteManager clienteManager = GestionFactoria.createClienteManager();
    private final UsuarioManager usuarioManager = GestionFactoria.getUsuarioManager();

    /**
     * Metodo para definir el stage de la ventana
     * @param perfilClienteStage Stage de la ventana
     * @param cliente Cliente de la sesion
     */
    public void setStage(Stage perfilClienteStage, Cliente cliente) {
        stage = perfilClienteStage;
        this.cliente = cliente;
    }

    /**
     * Metodo para inicializar la ventana
     * @param root
     */
    public void initStage(Parent root) {
        LOG.info("Iniciando ventana Perfil de Cliente");
        try {
            //Creates a new Scene
            Scene scene = new Scene(root);

            //Associate the scene to window(stage)
            stage.setScene(scene);

            //Window properties
            stage.setTitle("Perfil Cliente");
            stage.setResizable(false);
            stage.setOnCloseRequest(this::handleCloseRequest);
            
            //Controles
            usuarioTxt.setText(cliente.getLogin());
            nombreTxt.setText(cliente.getFullName());
            correoTxt.setText(cliente.getEmail());
            
            usuarioTxt.setEditable(false);
            nombreTxt.setEditable(false);
            correoTxt.setEditable(false);
            
            guardarBtn.setDisable(true);
            
            contrasenaTxt.requestFocus();
            
            //Listeners
            volverBtn.setOnAction(this::handleBtnVolver);
            guardarBtn.setOnAction(this::handleBtnGuardar);
            
            usuarioTxt.textProperty().addListener(this::handleValidarTexto);
            nombreTxt.textProperty().addListener(this::handleValidarTexto);
            correoTxt.textProperty().addListener(this::handleValidarTexto);
            contrasenaTxt.textProperty().addListener(this::handleValidarTexto);
            nuevaContrasenaTxt.textProperty().addListener(this::handleValidarTexto);
            repiteContrasenaTxt.textProperty().addListener(this::handleValidarTexto);
            
            //Show window (asynchronous)
            stage.show();
        } catch (Exception e) {
            LOG.severe("No se ha podido iniciar la ventana");
        }
        
    }
    
    /**
     * Metodo para controlar la accion del boton volver
     * @param volverEvent Evento de volver
     */
    private void handleBtnVolver(ActionEvent volverEvent) {
        try {
            LOG.info("Abriendo ventana Gestion de Incidencias...");
            //Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/IncidenciaViewC.fxml"));
            Parent root = (Parent) loader.load();
            //Obtiene el controlador
            GestionClientesController controlador = ((GestionClientesController) loader.getController());
            //Establece el Stage
            controlador.setStage(stage);
            //Inicializa la ventana
            controlador.initStage(root);
        } catch (IOException ex) {
            LOG.severe("Error al intentar abrir la ventana");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al intentar abrir la ventana de GestionClientesView");
            alert.showAndWait();
        }
    }
    
    /**
     * Metodo para controlar la accion del boton guardar
     * @param guardarEvent Evento de guardar
     */
    private void handleBtnGuardar(ActionEvent guardarEvent) {
        if (contrasenaCorrecta()) {
            if (nuevaContrasenaTxt.getText().equals(repiteContrasenaTxt.getText())) {
                cliente.setPassword(nuevaContrasenaTxt.getText());
                clienteManager.editCliente(cliente, cliente.getIdUsuario());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("Contraseña modificada correctamente");
                alert.showAndWait();
                
                try {
                    LOG.info("Abriendo ventana Gestion de Incidencias...");
                    //Carga el archivo FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/IncidenciaViewC.fxml"));
                    Parent root = (Parent) loader.load();
                    //Obtiene el controlador
                    GestionClientesController controlador = ((GestionClientesController) loader.getController());
                    //Establece el Stage
                    controlador.setStage(stage);
                    //Inicializa la ventana
                    controlador.initStage(root);
                } catch (IOException ex) {
                    LOG.severe("Error al intentar abrir la ventana");
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setHeaderText(null);
                    alert2.setTitle("Error");
                    alert2.setContentText("Error al intentar abrir la ventana de GestionClientesView");
                    alert2.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Las contraseñas deben coincidir");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("La contraseña no es correcta");
            alert.showAndWait();
        }
    }
    
    /**
     * Metodo para validar el texto de los campos
     * @param observable Campo a observar
     * @param oldValue Valor antiguo
     * @param newValue Valor nuevo
     */
    private void handleValidarTexto(ObservableValue observable, String oldValue, String newValue) {
        StringProperty textProperty = (StringProperty) observable;
        TextField changedTextField = (TextField) textProperty.getBean();
        String changedTextFieldName = changedTextField.getId();

        //Limite de caracteres
        int maxLenght = 0;
        switch (changedTextFieldName) {
            case "usuarioTxt":
            case "contrasenaTxt":
            case "nuevaContrasenaTxt":
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
            guardarBtn.setDisable(false);
        } else {
            guardarBtn.setDisable(true);
        }
    }
    
    /**
     * Metodo para controlar el cierre de la aplicacion
     * @param closeEvent Evento de cerrar
     */
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
    
    /**
     * Metodo para comprobar que todos los campos estan informados
     * @return Devuelve si todos los campos estan informados o no
     */
    private boolean camposInformados() {
        return !(contrasenaTxt.getText().isEmpty() || nuevaContrasenaTxt.getText().isEmpty() || repiteContrasenaTxt.getText().isEmpty());
    }
    
    /**
     * Metodo para comprobar que la contraseña cumple con los patrones
     * @return Devuelve si la contraseña cumple con los patrones
     */
    private boolean contrasenaCorrecta() {
        boolean correcta = false;
        
        try {
            if (usuarioManager.buscarUsuarioPorLoginYContrasenia_Usuario(cliente.getLogin(), contrasenaTxt.getText())!=null) {
                correcta = true;
            }
        } catch (UsuarioNoExisteException ex) {
            Logger.getLogger(PerfilClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return correcta;
    }
    
}
