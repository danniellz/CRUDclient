package GESRE.test;

import GESRE.aplication.GESREClient;
import GESRE.controller.IncidenciaCLViewController;
import GESRE.entidades.EstadoIncidencia;
import GESRE.entidades.Incidencia;
import GESRE.entidades.TipoIncidencia;
import javafx.scene.control.TextField;
import groovyjarjarasm.asm.Label;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.ComboBoxMatchers;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Clase test de la ventana de Incidencia Cliente
 *
 * @author Aritz Arrieta
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncidenciaCLViewControllerT extends ApplicationTest {

    //----------LOGGER----------
    private static final Logger LOG = Logger.getLogger(IncidenciaCLViewController.class.getName());

    //----------LABEL----------
    private Label lblError;
    //----------TEXTFIELDS-----
    private TextField Hor_TxtLabel;
    private TextField Estr_TxtLabel;
    //----------COMBOBOX-------
    private ComboBox<TipoIncidencia> cbxTipoIncidencia;

    private ComboBox<EstadoIncidencia> cbxEstadoIncidencia;

    //----------TABLE----------
    private TableView<Incidencia> tablaIncidencia;

    //---------TOOGLEBUTTON----
    private ToggleButton btnFiltro;

    @Override
    public void start(Stage stage) throws Exception {
        new GESREClient().start(stage);

        Hor_TxtLabel = lookup("#Hor_TxtLabel").query();
        Estr_TxtLabel = lookup("#Estr_TxtLabel").query();

        tablaIncidencia = lookup("#tablaIncidencia").queryTableView();
        cbxTipoIncidencia = lookup("#cbxTipoIncidencia").queryComboBox();
        cbxEstadoIncidencia = lookup("#cbxEstadoIncidencia").queryComboBox();
    }

    /* @Test
    @Ignore
    public void testA_signIn() {
        clickOn("#userTxt");
        write("admin");
        clickOn("#passwordTxt");
        write("123456");
        clickOn("#loginBtn");
        verifyThat("#piezaPanel", isVisible());

    }*/
    @Test
    @Ignore
    public void testB_EstadoInicialVentana() {
        //comprobar que la ventana es Incidencia
        verifyThat("#incidenciaCPanel", isVisible());

        //comprobar que los campos esta todos vacios
        verifyThat("#Hor_TxtLabel", hasText(""));
        verifyThat("#Estr_TxtLabel", hasText(""));

        //Comprobar si los botones CRUD estan desactivados  y el boton de Filtro esta Habilitado
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        //Comprobar si la tabla y columnas estan visibles
        verifyThat("#tablaIncidencias", isVisible());
        verifyThat("#tipoIncidenciaCL", isVisible());
        verifyThat("#estadoCL", isVisible());
        verifyThat("#estrellasCL", isVisible());
        verifyThat("#horasCL", isVisible());

        verifyThat("#cbxTipoIncidencia", isVisible());
        verifyThat("#cbxEstadoIncidencia", isVisible());
        verifyThat("#cbxTipoIncidencia", isEnabled());
        verifyThat("#cbxEstadoIncidencia", isEnabled());

        //comprobacion que los ComboBox estas rellenos con los datos correctos
        assertThat(cbxTipoIncidencia, ComboBoxMatchers.hasItems(7));
        verifyThat(cbxTipoIncidencia, ComboBoxMatchers.containsItems(TipoIncidencia.ELECTRICO, TipoIncidencia.FACHADA, TipoIncidencia.FONTANERIA, TipoIncidencia.GAS, TipoIncidencia.HUMEDAD, TipoIncidencia.CERRAJERO, TipoIncidencia.REVISION));
        assertThat(cbxEstadoIncidencia, ComboBoxMatchers.hasItems(3));
        verifyThat(cbxEstadoIncidencia, ComboBoxMatchers.containsItems(EstadoIncidencia.CERRADO, EstadoIncidencia.PENDIENTE, EstadoIncidencia.PROCESO));

    }

    @Test
    @Ignore
    public void testC_ControlCamposVacios() {
        //seleccionar algo en el cbxTipoIncidencia, como faltan campos los botones estan deshabilitados
        clickOn(cbxTipoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //seleccionar algo en el cbxEstadoIncidencia, como faltan campos los botones estan deshabilitados
        clickOn(cbxEstadoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //escribir en el campo Hor_TxtLabel, como faltan campos los botones estan deshabilitados
        clickOn("#Hor_TxtLabel");
        write("22");
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //escribir en el campo Estr_TxtLabel, como no faltan campos los botones estan habilitados
        clickOn("#Estr_TxtLabel");
        write("1");
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("#btnEliminar", isEnabled());
        verifyThat("#btnModificar", isEnabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        //verificamos que si los campos vuelven a estar vacios los botones se vuelven a deshabilitar
        vaciarCampos();
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
    }

    @Test
    @Ignore
    public void testD_TamanoCampos() {
        //seleccionar algo en el cbxTipoIncidencia, como faltan campos los botones estan deshabilitados
        clickOn(cbxTipoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //seleccionar algo en el cbxEstadoIncidencia, como faltan campos los botones estan deshabilitados
        clickOn(cbxEstadoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //escribir en el campo Hor_TxtLabel, como faltan campos los botones estan deshabilitados
        clickOn("#Hor_TxtLabel");
        write("22");
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //escribir en el campo Estr_TxtLabel, como no faltan campos los botones estan habilitados
        clickOn("#Estr_TxtLabel");
        write("1");
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("#btnEliminar", isEnabled());
        verifyThat("#btnModificar", isEnabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        clickOn("#Hor_TxtLabel");
        write("2");
        verifyThat("Solo se permiten 2 digitos en las Horas", isVisible());

        assertEquals(Hor_TxtLabel.getText().length(), 2);
        verifyThat("#btnToogleFiltro", isEnabled());
        clickOn("#Estr_TxtLabel");
        write("3");
        assertEquals(Estr_TxtLabel.getText().length(), 1);
        verifyThat("Las Estrellas son medidas del 0 al 5", isVisible());

    }

    @Test

    public void testE_ControlCaracteres() {
        //seleccionar algo en el cbxTipoIncidencia, como faltan campos los botones estan deshabilitados
        clickOn(cbxTipoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //seleccionar algo en el cbxEstadoIncidencia, como faltan campos los botones estan deshabilitados
        clickOn(cbxEstadoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //escribir en el campo Hor_TxtLabel, como faltan campos los botones estan deshabilitados
        clickOn("#Hor_TxtLabel");
        write("22");
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        clickOn("#Estr_TxtLabel");
        write("2");
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("#btnEliminar", isEnabled());
        verifyThat("#btnModificar", isEnabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        clickOn("#btnAnadir");
        verifyThat("La incidencia NO ha sido creado con existo", isVisible());

    }

    @Test
    @Ignore
    public void testF_seleccionTabla() {
       
    }

    @Test
    @Ignore
    public void testG_CrearIncidenciaOK() {

        //seleccionar algo en el cbxTipoIncidencia
        clickOn(cbxTipoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        //seleccionar algo en el cbxEstadoIncidencia
        clickOn(cbxEstadoIncidencia);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());
        //escribir en el campo Hor_TxtLabel, como faltan campos los botones estan deshabilitados
        clickOn("#Hor_TxtLabel");
        write("22");
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        //escribir en el campo Estr_TxtLabel
        clickOn("#Estr_TxtLabel");
        write("1");
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("#btnEliminar", isEnabled());
        verifyThat("#btnModificar", isEnabled());
        verifyThat("#btnToogleFiltro", isEnabled());

        clickOn("#btnAnadir");
        verifyThat("La incidencia ha sido creado con existo", isVisible());
    }

    public void vaciarCampos() {
        clickOn("#btnLimpiar");
        verifyThat("#Estr_TxtLabel", hasText(""));
        verifyThat("#Hor_TxtLabel", hasText(""));

    }
}
