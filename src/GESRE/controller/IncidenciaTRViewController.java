/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.controller;

import static GESRE.controller.GestionTrabajadorViewController.LOGGER;
import GESRE.entidades.EstadoIncidencia;
import GESRE.entidades.Incidencia;
import GESRE.entidades.Pieza;
import GESRE.entidades.Recoge;
import GESRE.entidades.TipoIncidencia;
import GESRE.entidades.Trabajador;
import GESRE.entidades.Usuario;
import GESRE.excepcion.ServerDesconectadoException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.IncidenciaManager;
import GESRE.interfaces.PiezasManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class IncidenciaTRViewController {
    //LOGGER

    private static final Logger LOG = Logger.getLogger(IncidenciaTRViewController.class.getName());

    IncidenciaManager incidenciaManager = GestionFactoria.getIncidenciaManager();
    PiezasManager piezaManager = GestionFactoria.getPiezaManager();

    private Stage stage;

    private Incidencia incidencia = null;
    private Pieza pieza = null;

    //**************Controlles de Campos******************
    private static final int MAX_LENGHT_EST = 1;
    private static final int MAX_LENGHT_HOR = 2;

    public static final Pattern VALID_EST = Pattern.compile("[0-5]", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_HOR = Pattern.compile("[0-99]", Pattern.CASE_INSENSITIVE);

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
    @FXML
    private Button btnGestionPiezas;

    //////////////////////////////////////////////////////////////
    //********COMBOBOX********
    @FXML
    private ComboBox<TipoIncidencia> cbxTipoIncidencia;
    @FXML
    private ComboBox<EstadoIncidencia> cbxEstadoIncidencia;
    @FXML
    private ComboBox<Pieza> cbxPieza;

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

    @FXML
    private TableColumn<Incidencia, Float> precioCL;
//________________Recoge____________ 
    @FXML
    private TableColumn<Recoge, String> fechaRecogidaCL;

//________________Cliente____________
    @FXML
    private TableColumn<Incidencia, String> clienteCL;
//________________Pieza____________
    @FXML
    private TableColumn<Pieza, String> piezaCL;

    //******** PANE ********
    //////////////////////////////////////////////////////////////
    @FXML
    private Pane incidenciaPanel;

    //////////////////////////////////////////////////////////////
    @FXML
    private TextField Prec_TxtLabel;
    @FXML
    private TextField Hor_TxtLabel;
    ////////////////////////////////////////////////////////////
    //**********MenuItem******************
    @FXML
    private MenuItem mnCerrarSecion;
    @FXML
    private MenuItem mnSalir;

    private ObservableList<Incidencia> IncidenciaList;

    Trabajador trabajador;

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

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

            ObservableList<Pieza> piezas = FXCollections.observableArrayList(piezaManager.findAllPiezaByTrabajadorId(pieza, 3));
            LOG.info(piezas.get(piezas.size() - 1).getNombre());

            cbxEstadoIncidencia.setItems(estados);
            cbxTipoIncidencia.setItems(tipoIn);
            cbxPieza.setItems(piezas);
            //*********estado inicial de la ventana*********

            lblError.setVisible(false);
            btnAnadir.setDisable(true);
            habilitarBotones();

            //**************-Controles de los campos de Texto-*****************
            Prec_TxtLabel.textProperty().addListener(this::handleControlTama);
            Hor_TxtLabel.textProperty().addListener(this::handleControlTama);
            cbxTipoIncidencia.getSelectionModel().selectedItemProperty().addListener((observable) -> {
                habilitarBotones();
            });
            cbxEstadoIncidencia.getSelectionModel().selectedItemProperty().addListener((observable) -> {
                habilitarBotones();
            });

            //Establecer los valores que aparecen dentro de cada celda
            tipoIncidenciaCL.setCellValueFactory(new PropertyValueFactory<>("tipoIncidencia"));
            estadoCL.setCellValueFactory(new PropertyValueFactory<>("estado"));
            precioCL.setCellValueFactory(new PropertyValueFactory<>("precio"));

            horasCL.setCellValueFactory(new PropertyValueFactory<>("horas"));
            estrellasCL.setCellValueFactory(new PropertyValueFactory<>("estrellas"));
            //________Entidad de Cliente___________
//            clienteCL.setCellValueFactory(new PropertyValueFactory<>("cliente"));
            //______Entidad de Pieza_______
            piezaCL.setCellValueFactory(new PropertyValueFactory<>("pieza_id"));
            // piezaCL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPieza().getNombre()));

            //______Entidad de Recoge______
            fechaRecogidaCL.setCellValueFactory(new PropertyValueFactory<>("fechaRecogida"));

            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findAll());
            tablaIncidencias.setItems(IncidenciaList);

            btnLimpiar.setOnAction(this::handleLimpiarFormulario);
            //btnAnadir.setOnAction(this::handleAnadir);

            btnModificar.setOnAction(this::handleModificar);
            btnEliminar.setOnAction(this::handleEliminar);
            //el boton de Busqueda funciona con un toogle button
            btnToogleFiltro.setOnAction(this::handleFiltro);

            //boton para abrir la gestion de incidencia
            //*********VACIO******
            btnGestionPiezas.setOnAction(this::handlePiezas);

            //Añade acciones a los menuItems de la barra menu
            mnCerrarSecion.setOnAction(this::handleCerrarSesion);
            mnSalir.setOnAction(this::handleSalir);

            //mostrar la Ventana
            stage.show();
        } catch (ServerDesconectadoException e) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/view/IncidenciaViewT.fxml"));
            Parent root = (Parent) loader.load();
            //Obtiene el controlador
            IncidenciaTRViewController incidenciaTRViewController = ((IncidenciaTRViewController) loader.getController());
            //Establece el Stage
            incidenciaTRViewController.setStage(primaryStage);
            //Inicializa la ventana
            incidenciaTRViewController.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al intentar abrir la ventana de Gestion de Incidencia", ex);
        }
    }

    private void handleLimpiarFormulario(ActionEvent event) {
        Prec_TxtLabel.setText(" ");
        Hor_TxtLabel.setText(" ");
        cbxTipoIncidencia.getSelectionModel().select(-1);
        cbxEstadoIncidencia.getSelectionModel().select(-1);
        cbxPieza.getSelectionModel().select(-1);
        habilitarBotones();
    }

    /**
     * Llamar a este método abrirá la ventana de PiezaView (Gestion de piezas
     * del trabajador)
     *
     * @param primaryStage objeto Stage (Ventana)
     * @throws IOException salta una excepcion si la ventana de PiezaView falla
     * en abrirse
     */
    private void handlePiezas(ActionEvent even) {
        
        try {
            LOG.info("PiezaViewController: Abriendo ventana IncidenciaViewTWindow...");
            FXMLLoader loaderP = new FXMLLoader(getClass().getResource("/GESRE/vistas/PiezaView.fxml"));
            Parent rootP = (Parent) loaderP.load();
            PiezaViewController controllerP = ((PiezaViewController) loaderP.getController());
            controllerP.setStage(stage, trabajador.getIdUsuario()); //falla
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "PiezaViewController: Error al intentar abrir la ventana de IncidenciaViewT", ex);
        }

    }

    /**
     * Cuadro de diálogo que se abre al pulsar la menuItem Salir de la pantalla
     * para confirmar si se quiere cerrar la aplicación.
     *
     * @param event El evento de acción.
     */
    private void handleSalir(ActionEvent event) {
        try {
            LOG.info("Trabajador Controlador: Salir programa");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Salir");
            alert.setHeaderText(null);
            alert.setContentText("¿Seguro que quieres cerrar la ventana?");

            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get().equals(ButtonType.OK)) {
                LOG.info("Trabajador Controlador: Cerrando aplicacion");
                stage.close();
                Platform.exit();
            } else {
                event.consume();
                alert.close();
            }
        } catch (Exception ex) {

            LOG.severe("UI GestionUsuariosController: Error printing report: {0}"
                    + ex.getMessage());
        }

    }

    /**
     * Al pulsar el menuItem Cerrar Secion cierra la venta de gestion trabajador
     * y abre SignIn
     *
     * @param event El evento de acción.
     */
    private void handleCerrarSesion(ActionEvent event) {

        LOG.info("Trabajador Controlador: Salir programa para SignIn");

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Salir");
            alert.setHeaderText(null);
            alert.setContentText("¿Seguro que quieres cerrar la ventana?");

            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get().equals(ButtonType.OK)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/SignIn.fxml"));
                Parent root = (Parent) loader.load();
                SignInController controller = ((SignInController) loader.getController());
                controller.setStage(stage);
                controller.initStage(root);
            } else {
                event.consume();
                alert.close();
            }
        } catch (Exception ex) {

            LOG.severe("UI GestionUsuariosController: Error printing report: {0}"
                    + ex.getMessage());
        }

    }

    private void handleTablaIncidenciasSelection(ObservableValue observable, Object oldValue, Object newValue) {
        //Si una fila esta seleccionada mover los datos de la fila a los campos
        if (newValue != null) {
            incidencia = (Incidencia) newValue;
            cbxTipoIncidencia.getSelectionModel().select(incidencia.getTipoIncidencia());
            cbxEstadoIncidencia.getSelectionModel().select(incidencia.getEstado());
            cbxPieza.getSelectionModel().select(incidencia.getPieza());

            Prec_TxtLabel.setText(incidencia.getPrecio().toString());
            Hor_TxtLabel.setText(incidencia.getHoras().toString());

            LOG.info("Información de Incidencia: " + incidencia.toString());
        } else {
            LOG.info("Fila no seleccionada");
            //Si no hay fila seleccionada limpiar los campos
            Prec_TxtLabel.setText("");
            Hor_TxtLabel.setText("");
            cbxTipoIncidencia.getSelectionModel().select(-1);
            cbxEstadoIncidencia.getSelectionModel().select(-1);
            cbxPieza.getSelectionModel().select(-1);
        }
        //Focus login field
        Hor_TxtLabel.requestFocus();
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

                //camposTextoVacios();
            } else {
                /*  lblError.setText("No se ha podido eliminar la Incidencia");
                lblError.setTextFill(Color.web("#FF0000"));*/
            }
        }
    }

    private void handleFiltro(ActionEvent Event) {
        if (btnToogleFiltro.isSelected()) {
            btnToogleFiltro.setText("Todas Incidencias");
            //Cambiar el hardcode de la consulta
            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findIncidenciaDeUnTrabajador(incidencia, "3"));
            tablaIncidencias.setItems(IncidenciaList);
        } else {
            btnToogleFiltro.setText("Incidencias Acabadas");
            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findAll());
            tablaIncidencias.setItems(IncidenciaList);
        }
    }

    private void handleModificar(ActionEvent Event) {
        boolean campCor = camposCorrectos();
        if (campCor) {

            LOG.info("Modificando la Incidencia " + camposCorrectos());
            incidencia = new Incidencia();

            Incidencia incidenciaSelec = tablaIncidencias.getSelectionModel().getSelectedItem();

            incidenciaSelec.setTipoIncidencia(cbxTipoIncidencia.getSelectionModel().getSelectedItem());
            incidenciaSelec.setEstado(cbxEstadoIncidencia.getSelectionModel().getSelectedItem());
            incidenciaSelec.setPrecio(new Double(Prec_TxtLabel.getText()));
            incidenciaSelec.setHoras(new Integer(Hor_TxtLabel.getText()));
            incidenciaSelec.setCliente(incidencia.getCliente());

            incidenciaSelec.setPieza(cbxPieza.getSelectionModel().getSelectedItem());

            incidenciaManager.editIncidencia(incidenciaSelec, String.valueOf(incidenciaSelec.getId()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificar");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("Se ha modificado la Incidencia");
            alert.show();
            //Agregar nueva pieza a tabla
            //Actualizar tabla
            tablaIncidencias.refresh();
        } else {
            LOG.info("NO Modificando la Incidencia" + camposCorrectos());
            //introducir texto en el mensaje de error
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificar");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("NO Se ha modificado la Incidencia");
            alert.show();
            //mostrar mensaje de Error
            lblError.setVisible(true);

        }
    }

    private boolean camposCorrectos() {
        boolean patronesTextoBien = true;
        boolean estrPatron = true;
        boolean horasPatron = true;
        Matcher matcher = null;

        matcher = VALID_HOR.matcher(Prec_TxtLabel.getText());
        if (!matcher.find()) {
            LOG.info("PATRON DE PRECIO: ESTA MAL" + matcher.find() + "PATRON es " + VALID_HOR + "EL texto " + Prec_TxtLabel.getText());
            lblError.setVisible(true);
            lblError.setText("El Precio solo puede ser de 2 digitos");
            lblError.setTextFill(Color.web("#FF0000"));
            estrPatron = false;
        } else {
            LOG.info("PATRON DE ESTRELLAS: ESTA BIEN" + matcher.find() + "PATRON es " + VALID_HOR + "EL texto " + Prec_TxtLabel.getText());
            lblError.setVisible(false);
            lblError.setText("");
            estrPatron = true;
        }

        matcher = VALID_HOR.matcher(Hor_TxtLabel.getText());
        boolean lag = matcher.find();
        if (!lag) {
            LOG.info("PATRON DE HORAS: ESTA MAL" + lag + "PATRON es " + VALID_HOR + "EL texto " + Hor_TxtLabel.getText());
            lblError.setVisible(true);
            lblError.setText("Solo se permiten 2 digitos en las Horas");
            lblError.setTextFill(Color.web("#FF0000"));
            horasPatron = false;
        } else {
            LOG.info("PATRON DE HORAS: ESTA BIEN" + lag + VALID_HOR + "EL texto " + Hor_TxtLabel.getText());
            // lblError.setVisible(false);
            // lblError.setText("");
            horasPatron = true;
        }
        if (horasPatron && estrPatron) {
            patronesTextoBien = true;
        } else {
            patronesTextoBien = false;
        }
        return patronesTextoBien;
    }

    private void handleControlTama(ObservableValue observable, String oldValue, String newValue) {

        if (Prec_TxtLabel.getText().length() > MAX_LENGHT_HOR) {
            String estrellas = Prec_TxtLabel.getText().substring(0, MAX_LENGHT_HOR);
            Prec_TxtLabel.setText(estrellas);
            lblError.setVisible(true);
            lblError.setText("El Precio solo puede ser de 2 digitos");
            lblError.setTextFill(Color.web("#FF0000"));

        }
        if (Hor_TxtLabel.getText().length() > MAX_LENGHT_HOR) {
            String horas = Hor_TxtLabel.getText().substring(0, MAX_LENGHT_HOR);
            Hor_TxtLabel.setText(horas);
            lblError.setVisible(true);
            lblError.setText("Solo se permiten 2 digitos en las Horas");
            lblError.setTextFill(Color.web("#FF0000"));

        }

        habilitarBotones();
    }

    private void habilitarBotones() {

        if (!camposTextoVacios()) {
            // btnAnadir.setDisable(false);
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);

        } else {
            //btnAnadir.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
        }
    }

    private boolean camposTextoVacios() {
        if (Prec_TxtLabel.getText().isEmpty() || Hor_TxtLabel.getText().isEmpty() || cbxTipoIncidencia.getSelectionModel().getSelectedIndex() == -1 || cbxEstadoIncidencia.getSelectionModel().getSelectedIndex() == -1) {
            LOG.info("los campos estan vacios");
            return true;
        } else {
            LOG.info("los campos NO ESTAN vacios");
            return false;
        }
    }
}
