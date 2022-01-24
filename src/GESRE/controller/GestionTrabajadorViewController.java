/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.controller;

import GESRE.entidades.Trabajador;
import static GESRE.entidades.UserPrivilege.TRABAJADOR;
import static GESRE.entidades.UserStatus.ENABLED;
import GESRE.excepcion.EmailExisteException;
import GESRE.excepcion.LoginExisteException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.TrabajadorManager;
import GESRE.interfaces.UsuarioManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import static javax.print.attribute.Size2DSyntax.MM;

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
    /**
     * Atributo estático y constante que guarda el patron correcto de isbn y
     * cantidad total.
     */
    public static final Pattern VALID_NUMERO = Pattern.compile("^[0-9]", Pattern.CASE_INSENSITIVE);

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
    private TextField txtBuscar;

    //********PASSWORD FIEL********
    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private PasswordField txtRepiteContrasenia;

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
    private TableColumn<Trabajador, Date> fechaContratoCL;
    @FXML
    private TableColumn<Trabajador, Float> PrecioHoraCL;

    //   private ObservableList<Trabajador> trabajadoresObservableList;
    /**
     * Variable que hace una llamada al método que gestiona los grupos de la
     * factoría.
     */
    TrabajadorManager trabajadorGestion = GestionFactoria.getTrabajadorGestion();

    UsuarioManager usuarioManager = GestionFactoria.getUsuarioGestion();

    Trabajador t = null;
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
            txtBuscar.setDisable(true);

            //Gargar filtros.
            ObservableList<String> cbxOptions = FXCollections.observableArrayList();
            cbxOptions.addAll("Todos", "Un trabajador", "Sin incidencias");
            cbxFiltro.setItems(cbxOptions);
            cbxFiltro.getSelectionModel().selectedItemProperty().addListener(this::cbListener);

            // Añade listeners a propiedades de cambio de texto
            txtNombreCompleto.textProperty().addListener(this::handleValidarTexto);
            txtEmail.textProperty().addListener(this::handleValidarTexto);
            txtPrecioHora.textProperty().addListener(this::handleValidarTexto);
            txtNombreUsuario.textProperty().addListener(this::handleValidarTexto);
            txtContrasenia.textProperty().addListener(this::handleValidarTexto);
            txtRepiteContrasenia.textProperty().addListener(this::handleValidarTexto);
            txtBuscar.textProperty().addListener(this::handleValidarTexto);

            noSeleccionarFechaAnterior(datePikerFechaContrato);

            //Añadir listener a la seleccion de la tabla 
            tablaTrabajadores.getSelectionModel().selectedItemProperty().addListener(this::handleTablaSeleccionada);
            //Definir columnas de la tabla trabajador
            nombreUsuarioCL.setCellValueFactory(new PropertyValueFactory<>("login"));
            nombreCompletoCL.setCellValueFactory(new PropertyValueFactory<>("fullName"));

            fechaContratoCL.setCellFactory(colum -> {
                TableCell<Trabajador, Date> cell = new TableCell<Trabajador, Date>() {
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
            fechaContratoCL.setCellValueFactory(new PropertyValueFactory<>("fechaContrato"));
            PrecioHoraCL.setCellValueFactory(new PropertyValueFactory<>("precioHora"));

            //Llenar la tabla de datos (Lista de todos los trabajadores)
            ObservableList<Trabajador> trabajadoresObservableList = FXCollections.observableArrayList(trabajadorGestion.buscarTodosLosTrabajadores());
            tablaTrabajadores.setItems(trabajadoresObservableList);

            //Añade acciones a los botones
            btnLimpiar.setOnAction(this::handleBtnLimpiar);
            btnAnadir.setOnAction(this::handleBtnAnadir);
            btnModificar.setOnAction(this::handleBtnModificar);
            btnEliminar.setOnAction(this::handleBtnEliminar);
            btnBuscar.setOnAction(this::handleBtnBuscar);
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
        if (txtNombreCompleto.getText().length() > MAX_LENGHT) {
            String nombre = txtNombreCompleto.getText().substring(0, MAX_LENGHT);
            txtNombreCompleto.setText(nombre);
        }

        if (txtEmail.getText().length() > MAX_LENGHT) {
            String email = txtEmail.getText().substring(0, MAX_LENGHT);
            txtEmail.setText(email);
        }

        if (txtNombreUsuario.getText().length() > MAX_LENGHT_USER) {
            String usuario = txtNombreUsuario.getText().substring(0, MAX_LENGHT_USER);
            txtNombreUsuario.setText(usuario);
        }

        if (txtContrasenia.getText().length() > MAX_LENGHT_USER) {
            String contrasenia = txtContrasenia.getText().substring(0, MAX_LENGHT_USER);
            txtContrasenia.setText(contrasenia);
        }

        if (txtRepiteContrasenia.getText().length() > MAX_LENGHT_USER) {
            String repiteContrasenia = txtRepiteContrasenia.getText().substring(0, MAX_LENGHT_USER);
            txtRepiteContrasenia.setText(repiteContrasenia);
        }

        if (txtPrecioHora.getText().length() > MAX_LENGHT_CANTIDAD) {
            String precioHora = txtPrecioHora.getText().substring(0, new Integer(MAX_LENGHT_CANTIDAD));
            txtPrecioHora.setText(precioHora);
        }

        habilitarBotones();
    }

    /**
     * Método que comprueba que si el datepicker está vacío.
     *
     * @return Variable que indica si el datepicker está vacío.
     */
    private boolean datePickerVacio() {
        boolean vacio = false;

        if (datePikerFechaContrato.getValue() == null) {
            lblErrorFechaContrato.setText("Tienes que introducir una fecha");
            lblErrorFechaContrato.setTextFill(Color.web("#FF0000"));

            vacio = true;
        } else {
            lblErrorFechaContrato.setText("");
        }

        return vacio;
    }

    private void cbListener(ObservableValue ov, String oldValue, String newValue) {
        btnBuscar.setDisable(false);
        if (cbxFiltro.getValue().equals("Todos")) {
            txtBuscar.setDisable(true);
            btnBuscar.setDisable(false);

        } else if (cbxFiltro.getValue().equals("Un trabajador")) {
            txtBuscar.setText("");
            txtBuscar.setDisable(false);
            btnBuscar.setDisable(false);
        } else if (cbxFiltro.getValue().equals("Sin incidencias")) {
            txtBuscar.setDisable(true);
            btnBuscar.setDisable(false);

        }
    }

    private void handleBtnBuscar(ActionEvent event) {

        if (cbxFiltro.getValue().equals("Todos")) {
            ObservableList<Trabajador> trabajadoresObservableList = FXCollections.observableArrayList(trabajadorGestion.buscarTodosLosTrabajadores());
            tablaTrabajadores.setItems(trabajadoresObservableList);
            tablaTrabajadores.refresh();

        }
        if (cbxFiltro.getValue().equals("Un trabajador")) {
            ObservableList<Trabajador> trabajadoresObservableList = FXCollections.observableArrayList(trabajadorGestion.buscarTrabajadorPorNombre(txtBuscar.getText()));
            tablaTrabajadores.setItems(trabajadoresObservableList);
            tablaTrabajadores.refresh();

        }
        if (cbxFiltro.getValue().equals("Sin incidencias")) {
            ObservableList<Trabajador> trabajadoresObservableList = FXCollections.observableArrayList(trabajadorGestion.trabajadoresSinIncidencias());
            tablaTrabajadores.setItems(trabajadoresObservableList);
            tablaTrabajadores.refresh();

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

        if (changedTextField.getText().length() > maxLenght) {
            String text = changedTextField.getText().substring(0, maxLenght);
            changedTextField.setText(text);
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
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
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
     * Quita en los campos txtNombreCompleto, txtEmail, datePikerFechaContrato,
     * txtNombreUsuario, txtContrasenia, txtRepiteContrasenia , txtBuscar que
     * esta escrito
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

        lblErrorContrasenia.setText("");
        lblErrorBuscar.setText("");
        lblErrorEmail.setText("");
        lblErrorFechaContrato.setText("");
        lblErrorNombreCompleto.setText("");
        lblErrorNombreUsuario.setText("");
        lblErrorPrecioHora.setText("");
        
        btnAnadir.setDisable(true);
        btnLimpiar.setDisable(true);

        txtNombreCompleto.requestFocus();
    }

    /**
     *
     * @param event
     */
    private void handleBtnAnadir(ActionEvent event) {
        boolean errorPatrones = patronesTextoBien();
        boolean errorDatePicker = datePickerVacio();

        if (patronesTextoBien() || !errorDatePicker) {
            try {

                //Comprueba si existe el login
                LOGGER.info("Usuario Controlador: Comprobando si existe el login");
                usuarioManager.buscarUsuarioPorLoginCrear(txtNombreUsuario.getText());
                //Comprueba si existe el email
                LOGGER.info("Usuario Controlador: Comprobando si existe el email");
                usuarioManager.buscarUsuarioPorEmailCrear(txtEmail.getText());

                Date date = new Date(System.currentTimeMillis());

                Trabajador nuvoTrabajador = new Trabajador();
                nuvoTrabajador.setFullName(txtNombreCompleto.getText());
                nuvoTrabajador.setEmail(txtEmail.getText());
                nuvoTrabajador.setPrecioHora(new Double(txtPrecioHora.getText()));
                nuvoTrabajador.setLogin(txtNombreUsuario.getText());
                nuvoTrabajador.setPassword(txtContrasenia.getText());
                nuvoTrabajador.setStatus(ENABLED);
                nuvoTrabajador.setPrivilege(TRABAJADOR);
                nuvoTrabajador.setLastPasswordChange(date);
                nuvoTrabajador.setFechaContrato(convertToDateViaSqlDate(datePikerFechaContrato.getValue()));

                trabajadorGestion.createTrabajador(nuvoTrabajador);

                tablaTrabajadores.getItems().add(nuvoTrabajador);
                tablaTrabajadores.refresh();

                limpiarCamposTexto();

            } catch (LoginExisteException le) {
                LOGGER.severe(le.getMessage());
                lblErrorNombreUsuario.setText("El login ya existe");
                lblErrorNombreUsuario.setTextFill(Color.web("#FF0000"));
            } catch (EmailExisteException ee) {
                LOGGER.severe(ee.getMessage());
                lblErrorEmail.setText("El email ya existe");
                lblErrorEmail.setTextFill(Color.web("#FF0000"));
            }
        }
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    private void handleBtnModificar(ActionEvent event) {
        boolean errorPatrones = patronesTextoBien();
        boolean errorDatePicker = datePickerVacio();
        if (patronesTextoBien() || !datePickerVacio()) {

            Trabajador tra = tablaTrabajadores.getSelectionModel().getSelectedItem();
            if (!tra.getLogin().equals(txtNombreUsuario)) {

                tra.setFullName(txtNombreCompleto.getText());
                tra.setEmail(txtEmail.getText());
                tra.setPrecioHora(new Double(txtPrecioHora.getText()));
                tra.setLogin(txtNombreUsuario.getText());
                //   tra.setFechaContrato(datePikerFechaContrato.getValue().toString());
                tra.setPassword(txtContrasenia.getText());

                trabajadorGestion.editTrabajador(tra);
                tablaTrabajadores.refresh();
                /*
                if (!trabajadorSeleccionado.getFullName().equals(txtNombreCompleto.getText())) {
                    if (comprobrarNombre(trabajadorSeleccionado.getFullName())) {
                        lblErrorBuscar.setText("El nombre ya existe");
                        lblErrorBuscar.setTextFill(Color.web("#FF0000"));
                    }
                } else {
              
                    trabajadorGestion.editTrabajador(trabajadorSeleccionado);

                  //  tablaTrabajadores.getSelectionModel().clearSelection();
                    tablaTrabajadores.refresh();
                    limpiarCamposTexto();
                }*/
            } else {
                lblErrorBuscar.setText("No se ha podido modificar el trabajador");
                lblErrorBuscar.setTextFill(Color.web("#FF0000"));
            }
        }

    }

    private void handleBtnEliminar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText("¿Seguro que quieres eliminar el trabajador?");

        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            Trabajador seleccion = ((Trabajador) tablaTrabajadores.getSelectionModel().getSelectedItem());

            if (seleccion != null) {
                trabajadorGestion.removeTrabajador(seleccion);

                tablaTrabajadores.getItems().remove(seleccion);
                tablaTrabajadores.refresh();

                limpiarCamposTexto();
            } else {
                lblErrorBuscar.setText("No se ha podido eliminar ningún alumno");
                lblErrorBuscar.setTextFill(Color.web("#FF0000"));
            }
        }
    }

    private boolean comprobrarNombre(String nombre) {
        Collection<Trabajador> trabajador = null;
        trabajador = trabajadorGestion.buscarTrabajadorPorNombre(nombre);

        if (trabajador.size() > 0) {
            for (Trabajador t : trabajador) {
                if (t.getFullName().equals(nombre)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cuando se selecciona una fila de la tabla se rellenan los campos de texto
     * con la informacion del libro y se desactiva el boton "btnAñadir".
     *
     * @param observable El objeto siendo observado.
     * @param oldValue El valor viejo de la propiedad.
     * @param newValue El valor nuevo de la propiedad.
     */
    private void handleTablaSeleccionada(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            datePikerFechaContrato.setDisable(true);
            Trabajador trabajador = (Trabajador) newValue;
            txtNombreCompleto.setText(trabajador.getFullName());
            txtEmail.setText(trabajador.getEmail());
            txtPrecioHora.setText(Double.toString(trabajador.getPrecioHora()));
            txtNombreUsuario.setText(trabajador.getLogin());
            txtContrasenia.setText(trabajador.getPassword());
            txtRepiteContrasenia.setText(trabajador.getPassword());
            datePikerFechaContrato.setValue(convertToLocalDateViaInstant(trabajador.getFechaContrato()));
            btnAnadir.setDisable(true);
            //btnModificar.setDisable(true);
            //btnEliminar.setDisable(true);
        } else {
            txtNombreCompleto.setText("");
            txtEmail.setText("");
            txtPrecioHora.setText("");
            txtNombreUsuario.setText("");
            txtContrasenia.setText("");
            txtRepiteContrasenia.setText("");
            btnAnadir.setDisable(false);
        }
        txtNombreCompleto.requestFocus();
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
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

    private boolean patronesTextoBien() {
        boolean patronesTextoBien = true;

        Matcher matcher = null;

        matcher = VALID_NOMBRE_COMPLETO.matcher(txtNombreCompleto.getText());
        if (!matcher.find()) {
            lblErrorNombreCompleto.setText("El Nombre completo sólo debe contener letras");
            lblErrorNombreCompleto.setTextFill(Color.web("#FF0000"));
            patronesTextoBien = false;
        } else {
            lblErrorNombreCompleto.setText("");
        }

        matcher = VALID_USUARIO.matcher(txtNombreUsuario.getText());
        if (!matcher.find()) {
            lblErrorNombreUsuario.setText("El usuario sólo debe contener letras y numeros");
            lblErrorNombreUsuario.setTextFill(Color.web("#FF0000"));
            patronesTextoBien = false;
        } else {
            lblErrorNombreUsuario.setText("");
        }
        matcher = VALID_EMAIL.matcher(txtEmail.getText());
        if (!matcher.find()) {
            lblErrorEmail.setText("Email es inválido ");
            lblErrorEmail.setTextFill(Color.web("#FF0000"));
            patronesTextoBien = false;
        } else {
            lblErrorEmail.setText("");
        }
        matcher = VALID_NUMERO.matcher(txtPrecioHora.getText());
        if (!matcher.find()) {
            lblErrorPrecioHora.setText("La cantidad sólo debe contener numeros");
            lblErrorPrecioHora.setTextFill(Color.web("#FF0000"));
            patronesTextoBien = false;
        } else {
            lblErrorPrecioHora.setText("");
        }
        return patronesTextoBien;
    }

    private void noSeleccionarFechaAnterior(DatePicker datePikerFechaContrato) {
        Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.compareTo(today) < 0);
                    }

                };
            }

        };
        datePikerFechaContrato.setDayCellFactory(callB);
    }

}
