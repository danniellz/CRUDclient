/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.controller;

import GESRE.entidades.Cliente;
import GESRE.entidades.EstadoIncidencia;
import GESRE.entidades.Incidencia;
import GESRE.entidades.TipoIncidencia;
import GESRE.entidades.Usuario;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.IncidenciaManager;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Hyperlink;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Aritz Arrieta
 */
public class IncidenciaCLViewController {
    //LOGGER

    private static final Logger LOG = Logger.getLogger(IncidenciaCLViewController.class.getName());

    IncidenciaManager incidenciaManager = GestionFactoria.getIncidenciaManager();

    private Stage stage;

    private Incidencia incidencia = null;

    //**************Controlles de Campos******************
    private static final int MAX_LENGHT_EST = 1;
    private static final int MAX_LENGHT_HOR = 2;

    public static final Pattern VALID_EST = Pattern.compile("[0-5]", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_HOR = Pattern.compile("-?([0-9]*)?", Pattern.CASE_INSENSITIVE);

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

    //////////////////////////////////////////////////////////////
    //********COMBOBOX********
    @FXML
    private ComboBox<TipoIncidencia> cbxTipoIncidencia;
    @FXML
    private ComboBox<EstadoIncidencia> cbxEstadoIncidencia;

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
    //******** PANE ********

    //////////////////////////////////////////////////////////////
    @FXML
    private Pane incidenciaPanel;

    //////////////////////////////////////////////////////////////
    //**********TextLabel*****************
    @FXML
    private TextField Estr_TxtLabel;
    @FXML
    private TextField Hor_TxtLabel;

    ////////////////////////////////////////////////////////////
    //**********MenuItem******************
    @FXML
    private MenuItem mnCerrarSecion;
    @FXML
    private MenuItem mnSalir;

    @FXML
    private Hyperlink hpl_Perfil;

    private ObservableList<Incidencia> IncidenciaList;

    //   Cliente cliente = null;
    private Integer idCliente;

    /**
     * @param args the command line arguments
     *
     * public void setStage(Stage primaryStage) { stage = primaryStage; }
     */
    Cliente c;

    public void setStage(Stage piezaViewStage, Cliente cliente) {
        stage = piezaViewStage;

        c = cliente;
    }

    /*  public void setStage(Stage primaryStage) {
      //  LOGGER.info("Trabajador Controlador: Estableciendo stage");
        this.stage = primaryStage;
        //c = cliente;
    }*/
    public void initStage(Parent root) {
        try {
            LOG.info("Iniciando Stage...");
            //Crear la nueva escena
            Scene scene = new Scene(root);
            // stage.setScene(scene);
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

            cbxEstadoIncidencia.setItems(estados);
            cbxTipoIncidencia.setItems(tipoIn);

            //*********estado inicial de la ventana*********
            lblError.setVisible(false);
            habilitarBotones();

            //**************-Controles de los campos de Texto-*****************
            Estr_TxtLabel.textProperty().addListener(this::handleControlTama);
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
            estrellasCL.setCellValueFactory(new PropertyValueFactory<>("estrellas"));
            horasCL.setCellValueFactory(new PropertyValueFactory<>("horas"));
            //  LOG.info(usuario.toString());

            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findIncidenciaDeUnCliente(incidencia, c.getIdUsuario().toString()));

            tablaIncidencias.setItems(IncidenciaList);

            btnLimpiar.setOnAction(this::handleLimpiarFormulario);
            btnAnadir.setOnAction(this::handleAnadir);
            btnInforme.setOnAction(this::handleInforme);
            btnModificar.setOnAction(this::handleModificar);
            btnEliminar.setOnAction(this::handleEliminar);
            //el boton de Busqueda funciona con un toogle button
            btnToogleFiltro.setOnAction(this::handleFiltro);
            hpl_Perfil.addEventHandler(ActionEvent.ACTION, this::handleMiPerfilperLink);

            //Añade acciones a los menuItems de la barra menu
            mnCerrarSecion.setOnAction(this::handleCerrarSesion);
            mnSalir.setOnAction(this::handleSalir);

            //mostrar la Ventana
            stage.show();
        } catch (Exception e) {
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


    private void handleLimpiarFormulario(ActionEvent event) {
        Estr_TxtLabel.setText("");
        Hor_TxtLabel.setText("");
        cbxTipoIncidencia.getSelectionModel().select(-1);
        cbxEstadoIncidencia.getSelectionModel().select(-1);
        habilitarBotones();
    }

    private void handleInforme(ActionEvent event) {
        try {
            LOG.info("Comenzandoa imprimir los datos de la tabla Trabajador...");
            JasperReport report = JasperCompileManager.compileReport(getClass()
                    .getResourceAsStream("/GESRE/archivos/incidenciaCReportGesre.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Incidencia>) tablaIncidencias.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Imprimir");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("Las Incidencias no han podido ser Imprimidas");
            alert.show();
            LOG.log(Level.SEVERE,
                    "IncidenciaCLController: Error printing report: {0}",
                    ex.getMessage());
        }
    }

    private void handleTablaIncidenciasSelection(ObservableValue observable, Object oldValue, Object newValue) {
        //Si una fila esta seleccionada mover los datos de la fila a los campos
        if (newValue != null) {
            incidencia = (Incidencia) newValue;
            cbxTipoIncidencia.getSelectionModel().select(incidencia.getTipoIncidencia());
            cbxEstadoIncidencia.getSelectionModel().select(incidencia.getEstado());
            Estr_TxtLabel.setText(incidencia.getEstrellas().toString());
            Hor_TxtLabel.setText(incidencia.getHoras().toString());

            LOG.info("Información de Incidencia: " + incidencia.toString());
        } else {
            LOG.info("Fila no seleccionada");
            //Si no hay fila seleccionada limpiar los campos
            Estr_TxtLabel.setText("");
            Hor_TxtLabel.setText("");
            cbxTipoIncidencia.getSelectionModel().select(-1);
            cbxEstadoIncidencia.getSelectionModel().select(-1);
        }
        //Focus login field
        Hor_TxtLabel.requestFocus();
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

    private void handleAnadir(ActionEvent anadirEvent) {

        try {
            if (camposCorrectos()) {

                //Solo se admiten numeros no negativos
                LOG.info("Añadiendo la Incidencia");
                incidencia = new Incidencia();
                incidencia.setId(2);
                incidencia.setTipoIncidencia(cbxTipoIncidencia.getSelectionModel().getSelectedItem());
                incidencia.setEstado(cbxEstadoIncidencia.getSelectionModel().getSelectedItem());
                incidencia.setEstrellas(new Integer(Estr_TxtLabel.getText()));
                incidencia.setHoras(new Integer(Hor_TxtLabel.getText()));
                incidencia.setCliente(c);
                incidenciaManager.createIncidencia(incidencia);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("CREAR");
                alert.setHeaderText(null);
                alert.setResizable(false);
                alert.setContentText("La incidencia ha sido creado con existo");
                alert.show();
                LOG.info(incidencia.toString());
                //Agregar nueva pieza a tabla
                tablaIncidencias.getItems().add(incidencia);

                //Actualizar tabla
                tablaIncidencias.refresh();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("CREAR");
                alert.setHeaderText(null);
                alert.setResizable(false);
                alert.setContentText("La incidencia NO ha sido creado con existo");
                alert.show();
                //introducir texto en el mensaje de error
                //mostrar mensaje de Error
                lblError.setVisible(true);
            }

        } catch (Exception e) {
            LOG.severe("NO FUNCIONA EL CREATE " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CREAR");
            alert.setHeaderText(null);
            alert.setResizable(false);
            alert.setContentText("La incidencia NO ha sido creado con existo(deben ser numeros)");
            alert.show();
        }
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

            } else {
                lblError.setText("No se ha podido eliminar la Incidencia");
                lblError.setTextFill(Color.web("#FF0000"));
            }
        }
    }

    private void handleFiltro(ActionEvent Event) {
        if (btnToogleFiltro.isSelected()) {
            btnToogleFiltro.setText("Todas Incidencias");
            //Cambiar el hardcode de la consulta
            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findIncidenciaAcabadaDeUnUsuario(incidencia, c.getIdUsuario().toString()));

            tablaIncidencias.setItems(IncidenciaList);
        } else {
            btnToogleFiltro.setText("Incidencias Acabadas");
            IncidenciaList = FXCollections.observableArrayList(incidenciaManager.findIncidenciaDeUnCliente(incidencia, c.getIdUsuario().toString()));
            tablaIncidencias.setItems(IncidenciaList);
        }
    }

    private void handleModificar(ActionEvent Event) {
        boolean campCor = camposCorrectos();
        if (campCor) {

            LOG.info("Modificando la Incidencia " + camposCorrectos() + " HHHHHHHHHHHHHHHHHHHHH");
            incidencia = new Incidencia();

            Incidencia incidenciaSelec = tablaIncidencias.getSelectionModel().getSelectedItem();

            incidenciaSelec.setTipoIncidencia(cbxTipoIncidencia.getSelectionModel().getSelectedItem());
            incidenciaSelec.setEstado(cbxEstadoIncidencia.getSelectionModel().getSelectedItem());
            incidenciaSelec.setEstrellas(new Integer(Estr_TxtLabel.getText()));
            incidenciaSelec.setHoras(new Integer(Hor_TxtLabel.getText()));
            incidenciaSelec.setCliente(c);
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

        matcher = VALID_EST.matcher(Estr_TxtLabel.getText());
        if (!matcher.find() || Estr_TxtLabel.getText().contains("-") || Estr_TxtLabel.getText().contains("[a-zA-Z]*")) {
            LOG.info("PATRON DE ESTRELLAS: ESTA MAL" + matcher.find() + "PATRON es " + VALID_EST + "EL texto " + Estr_TxtLabel.getText());
            lblError.setText("Las Estrellas son medidas del 0 al 5");
            estrPatron = false;
        } else {
            LOG.info("PATRON DE ESTRELLAS: ESTA BIEN" + matcher.find() + "PATRON es " + VALID_EST + "EL texto " + Estr_TxtLabel.getText());

            estrPatron = true;
        }
        matcher = VALID_HOR.matcher(Hor_TxtLabel.getText());
        boolean lag = matcher.find();
        if (!lag || Hor_TxtLabel.getText().contains("-") || Hor_TxtLabel.getText().contains("[a-zA-Z]*")) {
            LOG.info("PATRON DE HORAS: ESTA MAL" + lag + "PATRON es " + VALID_HOR + "EL texto " + Hor_TxtLabel.getText());

            lblError.setText("Solo se permiten 2 digitos en las Horas");

            horasPatron = false;
        } else {
            LOG.info("PATRON DE HORAS: ESTA BIEN" + lag + VALID_HOR + "EL texto " + Hor_TxtLabel.getText());

            horasPatron = true;
        }
        if (horasPatron && estrPatron) {
            patronesTextoBien = true;
            lblError.setVisible(false);
            lblError.setText("");
        } else {
            lblError.setVisible(true);

            lblError.setTextFill(Color.web("#FF0000"));
            patronesTextoBien = false;
        }
        return patronesTextoBien;
    }

    private void handleControlTama(ObservableValue observable, String oldValue, String newValue) {

        if (Estr_TxtLabel.getText().length() > MAX_LENGHT_EST) {
            String estrellas = Estr_TxtLabel.getText().substring(0, MAX_LENGHT_EST);
            Estr_TxtLabel.setText(estrellas);
            lblError.setVisible(true);
            lblError.setText("Las Estrellas son medidas del 0 al 5");
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
            btnAnadir.setDisable(false);
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);

        } else {
            btnAnadir.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
        }
    }

    private boolean camposTextoVacios() {
        if (Estr_TxtLabel.getText().isEmpty() || Hor_TxtLabel.getText().isEmpty() || cbxTipoIncidencia.getSelectionModel().getSelectedIndex() == -1 || cbxEstadoIncidencia.getSelectionModel().getSelectedIndex() == -1) {
            LOG.info("los campos estan vacios");
            return true;
        } else {
            LOG.info("los campos NO ESTAN vacios");
            return false;
        }

    }

    private void handleMiPerfilperLink(ActionEvent HyperLinkPress) {

        try {
            LOG.info("Starting SignUp Window...");
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/PerfilClienteView.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            PerfilClienteController perfil = ((PerfilClienteController) loader.getController());
            //Set the stage
            perfil.setStage(stage, c);
            //initialize the window
            perfil.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting SignUp Window", ex);
        }
    }
}
