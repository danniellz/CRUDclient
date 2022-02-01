package GESRE.controller;

import GESRE.entidades.Pieza;
import GESRE.entidades.Trabajador;
import GESRE.excepcion.PiezaDeOtroTrabajadorException;
import GESRE.excepcion.PiezaExisteException;
import GESRE.excepcion.ServerDesconectadoException;
import GESRE.factoria.GestionFactoria;
import GESRE.interfaces.PiezasManager;
import GESRE.interfaces.TrabajadorManager;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button btnInforme;
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
    private Alert alert = null;

    //********BARRA MENU********
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mnCerrarSesion;
    @FXML
    private MenuItem mnSalir;

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
     * @param id identificador del trabajador
     */
    public void setStage(Stage piezaViewStage, Integer id) {
        stage = piezaViewStage;
        idTrabajador = id;
    }

    /**
     * Inicializar Ventana
     *
     * @param root Contiene el FXML
     * @throws GESRE.excepcion.ServerDesconectadoException Suelta una excepcion
     * si no hay conexion con el servidor
     */
    public void initStage(Parent root) throws ServerDesconectadoException {
        try {
            LOG.info("PiezaViewController: Inicializando Stage...");
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
            cbxFiltro.getSelectionModel().select(0);
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
            //Establecer el valor de stock a la derecha
            stockCl.setStyle("-fx-alignment: CENTER-RIGHT;");

            //Llenar la tabla de datos con todas las piezas de un trabajador
            datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByTrabajadorId(pieza, idTrabajador));
            tablaPiezas.setItems(datosPieza);

            //Listeners
            btnLimpiar.setOnAction(this::handleLimpiar);
            btnAnadir.setOnAction((anadirEvent) -> {
                try {
                    this.handleBtnAnadir(anadirEvent);
                } catch (PiezaExisteException ex) {
                    Logger.getLogger(PiezaViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnEditar.setOnAction(this::handleBtnEditar);
            btnBorrar.setOnAction(this::handleBtnBorrar);
            btnBuscar.setOnAction(this::handleBtnBuscar);
            btnInforme.setOnAction(this::handleBtnInforme);
            //btnGestionIncidencia.setOnAction(this::startIncidenciaViewTWindow);

            //Añade acciones a los menuItems de la barra menu
            //mnCerrarSesion.setOnAction(this::handleCerrarSesion);
            //mnSalir.setOnAction(this::handleSalir);
            //Mostrar ventana (asincrona)
            stage.show();
        } catch (ServerDesconectadoException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR SERVIDOR");
            alert.setContentText("No hay conexión con el servidor. Intentalo más tarde.");
            alert.showAndWait();

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
            LOG.info("PiezaViewController: Abriendo ventana SignIn...");
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
            LOG.log(Level.SEVERE, "PiezaViewController: Error al intentar abrir la ventana de SignUp", ex);
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
            LOG.info("PiezaViewController: Abriendo ventana IncidenciaViewTWindow...");
            //Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/IncidenciaViewT.fxml"));
            Parent root = (Parent) loader.load();
            //Obtiene el controlador
            IncidenciaCLViewController controlador = ((IncidenciaCLViewController) loader.getController());
            //Establece el Stage
            controlador.setStage(primaryStage);
            //Inicializa la ventana
            controlador.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "PiezaViewController: Error al intentar abrir la ventana de IncidenciaViewT", ex);
        }

    }*/
    /**
     * Llamar a este método limpiara todos los campos si están informados
     */
    private void handleLimpiar(ActionEvent limpiarEvent) {
        LOG.info("PiezaViewController: Limpiando todos los campos");
        //Deseleccionar la fila
        tablaPiezas.getSelectionModel().clearSelection();
        txtNombre.setText("");
        txtADescripcion.setText("");
        txtStock.setText("");
        txtNombreFiltro.setText("");
        txtNombre.requestFocus();
    }

    /**
     * Llamar a este método buscará las piezas por le nombre introducido
     */
    private void handleBtnBuscar(ActionEvent buscarEvent) {
        try {
            LOG.info("PiezaViewController: Boton Buscar presionado");
            //Deseleccionar la fila
            tablaPiezas.getSelectionModel().clearSelection();
            messageLbl.setVisible(false);
            txtNombreFiltro.setStyle("-fx-border-color: White;");

            //Combobox TODO
            if (cbxFiltro.getSelectionModel().getSelectedIndex() == 0) {
                LOG.info("PiezaViewController: Filtro: Todo, buscando...");
                datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByTrabajadorId(pieza, idTrabajador));
            }
            //Combobox NOMBRE
            if (cbxFiltro.getSelectionModel().getSelectedIndex() == 1) {
                if (txtNombreFiltro.getText().isEmpty()) {
                    LOG.warning("PiezaViewController: No has escrito un nombre para buscar");
                    messageLbl.setText("No has escrito un nombre para buscar");
                    messageLbl.setStyle("-fx-text-fill: #DC143C");
                    messageLbl.setVisible(true);
                    txtNombreFiltro.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                } else {
                    LOG.info("Filtro: Nombre, buscando...");
                    datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByName(pieza, txtNombreFiltro.getText()));
                }

            }
            //ComboBox STOCK
            if (cbxFiltro.getSelectionModel().getSelectedIndex() == 2) {
                LOG.info("Filtro: Stock, buscando...");
                datosPieza = FXCollections.observableArrayList(piezasManager.findAllPiezaByStock(pieza, idTrabajador));
            }

            //Agregar datos a tabla
            tablaPiezas.setItems(datosPieza);
            //Actualizar tabla
            tablaPiezas.refresh();
        } catch (ServerDesconectadoException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR SERVIDOR");
            alert.setContentText("No hay conexión con el servidor. Intentalo más tarde.");
            alert.showAndWait();
        }
    }

    /**
     * Llamar a este método creará una nueva Pieza
     *
     * @param anadirEvent evento de accion
     */
    private void handleBtnAnadir(ActionEvent anadirEvent) throws PiezaExisteException {
        LOG.info("PiezaViewController: Boton Añadir presionado");
        //No se admiten caracteres especiales en Nombre
        if (SpCharControl()) {
            //Solo se admiten numeros no negativos en Stock
            if (txtStock.getText().matches("-?([0-9]*)?") && !txtStock.getText().contains("-")) {
                try {
                    List<Pieza> piezas;
                    //Comprobar si la pieza existe
                    LOG.info("PiezaViewController: Comprobando si la pieza ya existe...");
                    piezasManager.piezaExiste(pieza, txtNombre.getText());
                    //Datos del trabajador que crea la pieza
                    trabajador = trabajadorManager.findTrabajador(idTrabajador);
                    //Datos de la Pieza a Crear
                    pieza = new Pieza();
                    pieza.setNombre(txtNombre.getText());
                    pieza.setDescripcion(txtADescripcion.getText());
                    pieza.setStock(new Integer(txtStock.getText()));
                    pieza.setTrabajador(trabajador);

                    //Crear Pieza
                    piezasManager.createPieza(pieza);

                    //Buscar la pieza recien creada para obtener su id y agregarla en la tabla, (sino, el id es null y no se puede borrar al instante de crearla)
                    piezas = (List<Pieza>) piezasManager.findAllPiezaByName(pieza, txtNombre.getText());
                    pieza = piezas.get(0);
                    LOG.info("PiezaViewController: Pieza Creada: " + pieza.toString());

                    //Limpiar todos los campos
                    handleLimpiar(anadirEvent);

                    //Agregar nueva pieza a tabla
                    tablaPiezas.getItems().add(pieza);

                    //Actualizar tabla
                    tablaPiezas.refresh();

                    //Ventana de confirmacion
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("La Pieza '" + pieza.getNombre() + "' se ha creado con exito!");
                    alert.setTitle("Nueva Pieza");
                    alert.show();

                } catch (PiezaExisteException e) {
                    LOG.severe(e.getMessage());
                    messageLbl.setText("La Pieza ya existe");
                    messageLbl.setStyle("-fx-text-fill: #DC143C");
                    messageLbl.setVisible(true);
                    txtNombre.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                    txtNombre.requestFocus();
                } catch (ServerDesconectadoException e) {
                    LOG.severe(e.getMessage());
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("ERROR SERVIDOR");
                    alert.setContentText("No hay conexión con el servidor. Intentalo más tarde.");
                    alert.showAndWait();
                }
            } else {
                LOG.severe("Solo se admiten números (positivos)");
                messageLbl.setText("Solo se admiten números (positivos)");
                txtADescripcion.setStyle("-fx-border-color: White;");
                txtNombre.setStyle("-fx-border-color: White;");
                messageLbl.setStyle("-fx-text-fill: #DC143C");
                txtNombreFiltro.setStyle("-fx-border-color: White;");
                txtStock.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
                messageLbl.setVisible(true);
                txtStock.requestFocus();
            }

        }
    }

    /**
     * Llamar a este método borrará una Pieza
     *
     * @param borrarEvent evento de accion
     */
    private void handleBtnBorrar(ActionEvent borrarEvent) {
        LOG.info("PiezaViewController: Boton Borrar presionado");
        try {
            LOG.info("Borrando Pieza...");
            //Obtener la pieza seleccionada de la tabla
            pieza = tablaPiezas.getSelectionModel().getSelectedItem();

            if (pieza == null) {
                LOG.severe("PiezaViewController: La Pieza que quiere borrar no existe");
                //Ventana de error
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("La Pieza que quiere borrar no existe");
                alert.setTitle("Borrado de Pieza");
                alert.show();
            } else {
                if (txtNombre.isDisabled()) {
                    throw new PiezaDeOtroTrabajadorException("No puedes Borrar la pieza de otro trabajador");
                } else {
                    LOG.info("PiezaViewController: Confirmando borrado...");
                    //Ventana de confirmacion
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "¿Borrar la fila seleccionada?\n"
                            + "La operación no se puede revertir", ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> result = alert.showAndWait();
                    //Respuesta afirmativa
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        LOG.info("PiezaViewController: Borrado Confirmado de la Pieza " + pieza.getNombre());
                        pieza.setTrabajador(null);
                        piezasManager.editPieza(pieza, idTrabajador);
                        //Borrar Pieza del servidor
                        piezasManager.removePieza(pieza.getId());
                        //Limpiar todos los campos
                        handleLimpiar(borrarEvent);
                        //Deseleccionar la fila
                        tablaPiezas.getSelectionModel().clearSelection();
                        //Eliminar de la tabla
                        tablaPiezas.getItems().remove(pieza);
                        //Actualizar tabla
                        tablaPiezas.refresh();

                        //Ventana de confirmacion
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("La Pieza '" + pieza.getNombre() + "' se ha Borrado con exito!");
                        alert.setTitle("Borrado de Pieza");
                        alert.show();

                    } else {
                        LOG.info("PiezaViewController: Borrado Cancelado");
                    }
                }
            }

        } catch (ServerDesconectadoException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR SERVIDOR");
            alert.setContentText("No hay conexión con el servidor. Intentalo más tarde.");
            alert.showAndWait();
        } catch (PiezaDeOtroTrabajadorException ex) {
            LOG.severe("No puedes Borrar la pieza de otro trabajador");
            //Ventana de error sii la pieza es de otro trabajador
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No puedes Borrar la pieza de otro trabajador");
            alert.setTitle("Error de borrado");
            alert.show();
        }
    }

    /**
     * Llamar a este método Actualizará una Pieza
     *
     * @param editarWEvent evento de accion
     */
    private void handleBtnEditar(ActionEvent editarEvent) {
        LOG.info("PiezaViewController: Boton Editar presionado");
        try {
            LOG.info("PiezaViewController: Actualizando Pieza...");
            //Obtener la pieza seleccionada de la tabla
            pieza = tablaPiezas.getSelectionModel().getSelectedItem();
            if (pieza == null) {
                LOG.severe("PiezaViewController: La Pieza que quiere Actualizar no existe");
                //Ventana de error
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("La Pieza que quiere Actualizar no existe");
                alert.setTitle("Borrado de Pieza");
                alert.show();
            } else {
                if (txtNombre.isDisabled()) {
                    throw new PiezaDeOtroTrabajadorException("No puedes Actualizar la pieza de otro trabajador");
                } else {
                    if (txtNombre.getText().equals(pieza.getNombre()) && txtADescripcion.getText().equals(pieza.getDescripcion()) && txtStock.getText().equals(pieza.getStock().toString())) {
                        LOG.warning("PiezaViewController: No se han detectado cambios");
                        //Ventana de error si la pieza no se ha modificado
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("No se han detectado cambios");
                        alert.setTitle("Error al Actualizar");
                        alert.show();
                    } else {
                        //la pieza puede actualizarse sin cambiar el nombre
                        if (txtNombre.getText().equals(pieza.getNombre())) {
                            LOG.info("PiezaViewController: Actualizando pieza sin cambiar el nombre");
                        } else {
                            //si la pieza cambia de nombre se comprubea si ya existe
                            LOG.info("PiezaViewController: Comprobando si el nombre introducido ya existe");
                            piezasManager.piezaExiste(pieza, txtNombre.getText());
                        }

                        //Datos nuevos
                        pieza.setNombre(txtNombre.getText());
                        pieza.setDescripcion(txtADescripcion.getText());
                        pieza.setStock(new Integer(txtStock.getText()));
                        piezasManager.editPieza(pieza, pieza.getId());
                        LOG.info("PiezaViewController: Pieza Actualizada");

                        //Limpiar todos los campos
                        handleLimpiar(editarEvent);

                        //Deseleccionar fila
                        tablaPiezas.getSelectionModel().clearSelection();

                        //Actualizar tabla
                        tablaPiezas.refresh();

                        //Ventana de confirmacion
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("La Pieza '" + pieza.getNombre() + "' se ha actualizado con exito!");
                        alert.setTitle("Actualizar Pieza");
                        alert.show();
                    }
                }

            }

        } catch (PiezaExisteException e) {
            LOG.severe(e.getMessage());
            //Error si la pieza ya existe
            messageLbl.setText("La Pieza ya existe");
            messageLbl.setStyle("-fx-text-fill: #DC143C");
            messageLbl.setVisible(true);
            txtNombre.setStyle("-fx-border-color: #DC143C ; -fx-border-width: 1.5px ;");
            txtNombre.requestFocus();
        } catch (ServerDesconectadoException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR SERVIDOR");
            alert.setContentText("No hay conexión con el servidor. Intentalo más tarde.");
            alert.showAndWait();
        } catch (PiezaDeOtroTrabajadorException ex) {
            LOG.severe("No puedes Actualizar la pieza de otro trabajador");
            //Ventana de error si la pieza es de otro trabajador
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No puedes Actualizar la pieza de otro trabajador");
            alert.setTitle("Error al Actualizar");
            alert.show();
        }
    }

    /**
     * Llamar a este método cerrará la ventana
     *
     * @param salirWindowEvent evento de ventana
     */
    public void handleSalir(WindowEvent salirWindowEvent) {
        LOG.info("PiezaViewController: Salir presionado");
        try {
            LOG.info("PiezaViewController: Confirmando cierre de ventana...");
            //Ventana de confirmacion
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Estás seguro de querer Salir?");
            alert.setTitle("Salir");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LOG.info("PiezaViewController: Cerrando...");
                Platform.exit();
            } else {
                LOG.info("PiezaViewController: Cierre del programa cancelado");
                //Cancela el evento
                salirWindowEvent.consume();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "PiezaViewController: Error al intentar cerrar la ventana", e);
        }
    }

    /**
     * Llamar a este método cerrará la sesíón actual para volver a la ventana
     * deSignIn
     *
     * @param cerrarSesiontEvent Cerrar Sesion action event
     */
    public void handleCerrarSesion(ActionEvent cerrarSesiontEvent) {
        LOG.info("PiezaViewController: Cerrar Sesion presionado");
        try {
            //Ventana de confirmacion
            LOG.info("PiezaViewController: Confirmar Cerrar Sesion");
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Seguro que quieres cerrar sesión?");
            alert.setTitle("Cerrar Sesión");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LOG.info("PiezaViewController: Cerrando Sesión...");
                startSignInWindow(stage);
            } else {
                LOG.info("PiezaViewController: Cerrar Sesión Cancelado");
                //Cancela el evento
                cerrarSesiontEvent.consume();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "PiezaViewController: Error al intentar Cerrar Sesión", ex);
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
            txtNombreFiltro.setStyle("-fx-border-color: White;");
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
            //Control de espacio vacio (No permitir)
            if (cadena.contains(" ")) {
                txtStock.setText(cadena.replaceAll(" ", ""));
            }
            //si hay errores volver, esconder el label y el color del campo vuelve a la normalidad
            txtStock.setStyle("-fx-border-color: White;");
            messageLbl.setVisible(false);
            messageLbl.setStyle("");
            txtNombre.setStyle("-fx-border-color: White;");
            txtADescripcion.setStyle("-fx-border-color: White;");
            txtNombreFiltro.setStyle("-fx-border-color: White;");

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
            //si hay errores volver, esconder el label y el color del campo vuelve a la normalidad
            txtNombre.setStyle("-fx-border-color: White;");
            txtStock.setStyle("-fx-border-color: White;");
            txtNombreFiltro.setStyle("-fx-border-color: White;");

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

            //si hay errores volver, esconder el label y el color del campo vuelve a la normalidad
            txtStock.setStyle("-fx-border-color: White;");
            txtNombre.setStyle("-fx-border-color: White;");
            txtADescripcion.setStyle("-fx-border-color: White;");
            txtNombreFiltro.setStyle("-fx-border-color: White;");

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error Setting User field control", ex);
        }
    }

    /**
     * Llamar a este método deshabilitará los botones Añadir, Editar y Borrar
     * mientras los campos necesarios esten vacios
     */
    private void desabilitarBtnCamposVacios() {
        LOG.info("Deshabilitando botones Añadir, Editar y Borrar");
        //Se comprueba cuando el boton debe estar desabilitado (cuando esta vacio)
        //Boton añadir
        btnAnadir.disableProperty().bind(
                txtNombre.textProperty().isEmpty()
                        .or(txtADescripcion.textProperty().isEmpty())
                        .or(txtStock.textProperty().isEmpty())
        );
        //Boton Editar
        btnEditar.disableProperty().bind(
                txtNombre.textProperty().isEmpty()
                        .or(txtADescripcion.textProperty().isEmpty())
                        .or(txtStock.textProperty().isEmpty())
        );
        //Boton Borrar
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
                //Si se selecciona la opcion de buscar por Nombre del combobox, activar el campo NombreFiltro
                txtNombreFiltro.setDisable(false);
                txtNombreFiltro.requestFocus();
            } else {
                //Mientras la opcion del combobox no sea Nombre, el campo NombreFiltro estara deshabilitado
                txtNombreFiltro.setDisable(true);
                txtNombreFiltro.setText("");
            }
        });
    }

    /**
     * Control de selección de la tabla, al seleccionar una fila de la tabla los
     * datos se mostrarán en los campos correspondientes
     *
     * @param observable la fila seleccionada
     * @param oldValue valor anterior
     * @param newValue valor actual
     */
    private void handleTablaPiezaSelection(ObservableValue observable, Object oldValue, Object newValue) {
        //Si una fila esta seleccionada mover los datos de la fila a los campos
        if (newValue != null) {
            pieza = (Pieza) newValue;
            txtNombre.setText(pieza.getNombre());
            txtADescripcion.setText(pieza.getDescripcion());
            txtStock.setText(pieza.getStock().toString());
            //Bloquear los campos si la pieza es de otro trabajador
            if (pieza.getTrabajador().getIdUsuario() != idTrabajador) {
                LOG.info("Pieza de otro trabajador detectado, No tienes permiso para interactuar con esta pieza");
                txtNombre.setDisable(true);
                txtADescripcion.setDisable(true);
                txtStock.setDisable(true);
            }
            LOG.info("Información de Pieza: " + pieza.toString());
        } else {
            LOG.info("Fila no seleccionada");
            //Si no hay fila seleccionada limpiar los campos
            txtNombre.setText("");
            txtADescripcion.setText("");
            txtStock.setText("");
            txtNombre.setDisable(false);
            txtADescripcion.setDisable(false);
            txtStock.setDisable(false);
        }
        //Focus en campo Nombre
        txtNombre.requestFocus();
    }

    /**
     * LLamar a este método restringirá el uso de caracteres especiales
     *
     * @return devuelve un valor booleano si un numero o caracter especial es
     * detectado o no
     */
    public boolean SpCharControl() {
        LOG.info("Comrpobando si el campo Nombre contiene numeros o caracteres especiales...");
        boolean correcto = false;
        String comp = "[^A-Za-zÀ-ȕ\\s]";
        Pattern espChar = Pattern.compile(comp);
        Matcher matcher = espChar.matcher(txtNombre.getText());

        //Si un numero o caracter especial es detectado
        if (matcher.find()) {
            LOG.warning("Los caracteres especiales no estan permitidos");
            messageLbl.setText("Los números y/o caracteres especiales no están permitidos");
            txtNombre.setStyle("-fx-border-color: #DC143C; -fx-border-width: 1.5px ;");
            messageLbl.setVisible(true);
            messageLbl.setStyle("-fx-text-fill: #DC143C");
            txtNombre.requestFocus();
        } else {
            //si lo introducido es valido
            LOG.info("El campo nombre es válido");
            txtNombre.setStyle("");
            messageLbl.setVisible(false);
            correcto = true;
        }
        return correcto;

    }

    /**
     * Llamar a este método imprimirá la información de todas las piezas del
     * trabajador, JFrame que contiene el reporte
     *
     * @param event evento del objeto
     */
    private void handleBtnInforme(ActionEvent event) {
        try {
            LOG.info("imprimiendo los datos de la tabla Pieza...");
            //Compilar informe
            JasperReport reporte = JasperCompileManager.compileReport(getClass().getResourceAsStream("/GESRE/archivos/informePiezas.jrxml"));
            //Datos para el reporte, coleccion de piezas
            JRBeanCollectionDataSource datos = new JRBeanCollectionDataSource((Collection<Pieza>) this.tablaPiezas.getItems());
            //Map de parametros
            Map<String, Object> parametros = new HashMap<>();
            //Rellenar el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datos);
            //crear y mostrar la ventana de reporte, el valor booleano hace que no se cierre la ventana en false
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            //ventana de error si falla al imprimir reporte
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ha ocurrido un error al intentar imprimir el reporte de la tabla Piezas");
            alert.setTitle("Error de Impresión");
            alert.show();
            LOG.log(Level.SEVERE, "PiezaViewController: Error al imprimir el reporte de piezas {0}", ex.getMessage());
        }
    }
}
