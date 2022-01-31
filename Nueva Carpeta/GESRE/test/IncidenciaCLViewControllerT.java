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
import javafx.stage.Stage;
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
 * @author Aritz
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

        Hor_TxtLabel = lookup("#Hor_txtLabel").query();
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
    public void testB_EstadoInicialVentana() {
        //comprobar que la ventana es Incidencia
        verifyThat("#incidenciaCPanel", isVisible());

        //comprobar que los campos esta todos vacios
//        verifyThat("#Hor_txtLabel", hasText(""));
//        verifyThat("#Estr_txtLabel", hasText(""));
        //comprobacion que los ComboBox estas rellenos con los datos correctos
        verifyThat("#cbxTipoIncidencia", ComboBoxMatchers.containsItems("ELECTRICO","FACHADA","FONTANERIA","GAS","HUMEDAD","CERRAJERO","REVISION"));
        verifyThat("#cbxEstadoIncidencia", ComboBoxMatchers.containsItems("PENDIENTE", "PROCESO", "CERRADO"));

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

    }

    public void limpiarCampos() {
        clickOn("#btnLimpiar");
        verifyThat("#Estr_TxtLabel", hasText(""));
        verifyThat("#Hor_TxtLabel", hasText(""));
        verifyThat("#cbxTipoIncidencia", hasSelectedItem(""));
        verifyThat("#cbxEstadoIncidencia", hasSelectedItem(""));
    }
}
