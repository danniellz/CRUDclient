/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.controller;

import GESRE.entidades.Trabajador;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.TrabajadorManager;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Jonathan Viñan
 */
public class GestionTrabajadorViewController {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    public static final Logger LOGGER = Logger.getLogger("controladores.GestionTrabajadorController");
    /**
     * Atributo estático y constante que guarda los caracteres máximos admitidos
     * en los campos de texto.
     */
    private static final int MAX_LENGHT = 50;
    /**
     * Atributo estático y constante que guarda los caracteres máximos admitidos
     * en los campos de nombre de usuario.
     */
    private static final int MAX_LENGHT_USER = 25;
    /**
     * Atributo estático y constante que guarda los caracteres máximos admitidos
     * en los campos de precio/hora.
     */
    private static final int MAX_LENGHT_CANTIDAD = 2;
    /**
     * Atributo estático y constante que guarda el patron correcto del nombre
     * completo.
     */
    public static final Pattern VALID_NOMBRE_COMPLETO = Pattern.compile("^[A-Z\\s]+$", Pattern.CASE_INSENSITIVE);
    /**
     * Atributo estático y constante que guarda el patron correcto del email.
     */
    public static final Pattern VALID_EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Atributo estático y constante que guarda el patron correcto del usuario.
     */
    public static final Pattern VALID_USUARIO = Pattern.compile("^[A-Z0-9]+$", Pattern.CASE_INSENSITIVE);

    //********PANE********
    /**
     * Elemento tipo pane importado de la vista FXML.
     */
    @FXML
    private Pane paneGeneralTrabajador;
    /**
     * Elemento tipo anchorpane importado de la vista FXML.
     */
    @FXML
    private Pane paneSuperior;

    //********LABEL********
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblNombreCompleto;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPrecioHora;
    @FXML
    private Label lblFechaContrato;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Label lblContrasenia;
    @FXML
    private Label lblRepiteContrasenia;

    //********LABEL DE ERROR********
    @FXML
    private Label lblErrorNombreCompleto;
    @FXML
    private Label lblErrorEmail;
    @FXML
    private Label lblErrorPrecioHora;
    @FXML
    private Label lblErrorFechaContrato;
    @FXML
    private Label lblErrorNombreUsuario;
    @FXML
    private Label lblErrorContrasenia;
    @FXML
    private Label lblErrorRepiteContrasenia;
    @FXML
    private Label lblErrorBuscar;

    //********TEXT FIEL********
    @FXML
    private TextField txtNombreCompleto;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPrecioHora;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private TextField txtRepiteContrasenia;
    @FXML
    private TextField txtBuscar;

    //********DATE PICKER********
    @FXML
    private DatePicker datePikerFechaContrato;

    //********COMBOBOX********
    @FXML
    private ComboBox<String> cbxFiltro;

    //********BUTTON********
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnGEstionarClientes;

    //********TABLE********
    @FXML
    private TableView<Trabajador> tablaTrabajadores;

    //********TABLE COLUM********
    @FXML
    private TableColumn<Trabajador, String> nombreUsuarioCL;
    @FXML
    private TableColumn<Trabajador, String> nombreCompletoCL;
    @FXML
    private TableColumn<Trabajador, String> fechaContratoCL;
    @FXML
    private TableColumn<Trabajador, Float> PrecioHoraCL;

    private ObservableList<Trabajador> trabajadoresObservableList ;
    /**
     * Variable que hace una llamada al método que gestiona los grupos de la
     * factoría.
     */
    TrabajadorManager trabajadorGestion = GestionFactoria.getTrabajadorGestion();
    /**
     * Variable de tipo stage que se usa para visualizar la ventana.
     */
    private Stage stage;

    /**
     * Método que establece el escenario como escenario principal.
     *
     * @param primaryStage El escenario de UIAlumno.
     */
    public void setStage(Stage primaryStage) {
        LOGGER.info("Trabajador Controlador: Estableciendo stage");
        this.stage = primaryStage;
    }

    /**
     * Método que inicializa el escenario y los componentes de la vista.
     *
     * @param root El objeto padre que representa el nodo root.
     */
    public void initStage(Parent root) {
        try {
            LOGGER.info("Trabajador Controlador: Iniciando stage");

            //Iniciando ventana
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestion de trabajadores");
            stage.setResizable(false);

            stage.onCloseRequestProperty().set(this::cerrarVentana);

            // Estado inicial de los controles
            btnAnadir.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            btnLimpiar.setDisable(true);
            btnBuscar.setDisable(true);
            btnLimpiar.setDisable(true);
            txtNombreCompleto.requestFocus();

            //Gargar filtros. MIRAR PARA DESPUES
            // Añade listeners a propiedades de cambio de texto
            txtNombreCompleto.textProperty().addListener(this::handleValidarTexto);
            txtEmail.textProperty().addListener(this::handleValidarTexto);
            txtPrecioHora.textProperty().addListener(this::handleValidarTexto);
            txtNombreUsuario.textProperty().addListener(this::handleValidarTexto);
            txtContrasenia.textProperty().addListener(this::handleValidarTexto);
            txtRepiteContrasenia.textProperty().addListener(this::handleValidarTexto);
            txtBuscar.textProperty().addListener(this::handleValidarTexto);

            //Definir columnas de la tabla trabajador
            nombreUsuarioCL.setCellValueFactory(new PropertyValueFactory<>("login"));
            nombreCompletoCL.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            fechaContratoCL.setCellValueFactory(new PropertyValueFactory<>("fechaContrato"));
            PrecioHoraCL.setCellValueFactory(new PropertyValueFactory<>("precioHora"));

            //Llenar la tabla de datos (Lista de todos los trabajadores)
            trabajadoresObservableList= FXCollections.observableArrayList(trabajadorGestion.buscarTodosLosTrabajadores());
            tablaTrabajadores.setItems(trabajadoresObservableList);

            //Añade acciones a los botones
            btnLimpiar.setOnAction(this::handleBtnLimpiar);
            btnAnadir.setOnAction(this::handleBtnAnadir);
            // btnModificar.setOnAction(this::handleBtnModificar);
            // btnEliminar.setOnAction(this::handleBtnEliminar);
            //btnBuscar.setOnAction(this::handleBtnBuscar);
            //mnCerrarSesion.setOnAction(this::handleCerrarSesion);

            //Muestra la ventana
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Stage init error", e);
        }

    }

    /**
     * Se ejecuta cuando un campo de texto ha sido modificado. Identifica el
     * campo de texto modificado, comprueba que no se pase de los caracteres
     * máximos y llama a la funcion encargada de habilitar o deshabilitar los
     * botones.
     *
     * @param observable El valor que se observa.
     * @param oldValue El valor antiguo del observable.
     * @param newValue El valor nuevo del observable.
     */
    private void handleValidarTexto(ObservableValue observable, String oldValue, String newValue) {
        StringProperty textProperty = (StringProperty) observable;
        TextField changedTextField = (TextField) textProperty.getBean();
        String changedTextFieldName = changedTextField.getId();

        textFieldOverMaxLength(changedTextField, changedTextFieldName);

        habilitarBotones();
    }

    /**
     * Cuadro de diálogo que se abre al pulsar la "X" de la pantalla para
     * confirmar si se quiere cerrar la aplicación.
     *
     * @param event El evento de acción.
     */
    private void cerrarVentana(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Salir");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que quieres cerrar la ventana?");

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.OK)) {
            LOGGER.info("Trabajador Controlador: Cerrando aplicacion");
            stage.close();
            Platform.exit();
        } else {
            event.consume();
            alert.close();
        }
    }

    private void textFieldOverMaxLength(TextField changedTextField, String changedTextFieldName) {
        int maxLenght = 200;

        switch (changedTextFieldName) {
            case "txtNombreCompleto":
                maxLenght = MAX_LENGHT;
                break;
            case "txtNombreUsuario":
                maxLenght = MAX_LENGHT_USER;
                break;
            case "txtPrecioHora":
                maxLenght = MAX_LENGHT_CANTIDAD;
                break;
            case "txtContrasenia":
                maxLenght = MAX_LENGHT_USER;
                break;
            case "txtRepetirContrasenia":
                maxLenght = MAX_LENGHT_USER;
                break;

        }
    }

    private void habilitarBotones() {
        btnBuscar.setDisable(txtBuscar.getText().isEmpty());

        if (!camposTextoVacios()) {
            btnAnadir.setDisable(false);
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
        } else {
            btnAnadir.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
        }
        if (camposTextoConTexto()) {
            btnLimpiar.setDisable(false);
        } else {
            btnLimpiar.setDisable(true);
        }
    }

    /**
     * Comprueba si algún campo de texto de Titulo, Autor, Editorial, Genero,
     * CantidadTotal o Isbn está vacio.
     *
     * @return Una variable indicando si hay algun campo de texto vacio o no.
     */
    private boolean camposTextoVacios() {
        if (txtNombreCompleto.getText().isEmpty() || txtEmail.getText().isEmpty()
                || txtPrecioHora.getText().isEmpty() || txtNombreUsuario.getText().isEmpty()
                || txtContrasenia.getText().isEmpty() || txtRepiteContrasenia.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si algún campo de texto contiene texto o un checkbox está
     * seleccionado.
     *
     * @return Variable indicando si hay algun campo de texto con texto o no.
     */
    private boolean camposTextoConTexto() {
        if (!txtNombreCompleto.getText().isEmpty() || txtEmail.getText().isEmpty()
                || txtPrecioHora.getText().isEmpty() || txtNombreUsuario.getText().isEmpty()
                || txtContrasenia.getText().isEmpty() || txtRepiteContrasenia.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Llama a un metodo para limpiar los campos de texto..
     *
     * @param event El evento de acción.
     */
    private void handleBtnLimpiar(ActionEvent event) {
        limpiarCamposTexto();
    }

    /**
     * Quita en los campos txtNombreGrupo, txtDescripcion lo que esta escrito
     *
     * @param event El evento de acción.
     */
    private void limpiarCamposTexto() {
        txtNombreCompleto.setText("");
        txtEmail.setText("");
        txtPrecioHora.setText("");
        datePikerFechaContrato.getEditor().clear();
        txtNombreUsuario.setText("");
        txtContrasenia.setText("");
        txtRepiteContrasenia.setText("");
        txtBuscar.setText("");

        btnAnadir.setDisable(true);
        btnLimpiar.setDisable(true);

        txtNombreCompleto.requestFocus();
    }

    /**
     *
     * @param event
     */
    private void handleBtnAnadir(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
