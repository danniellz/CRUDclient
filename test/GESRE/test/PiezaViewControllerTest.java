package GESRE.test;

import GESRE.aplication.GESREClient;
import GESRE.controller.PiezaViewController;
import GESRE.entidades.Pieza;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * Clase test de la ventana Pieza
 *
 * @author Daniel Brizuela
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PiezaViewControllerTest extends ApplicationTest {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(PiezaViewController.class.getName());

    private static final String TEXT_50 = "XXXXXXXXXXXXXXXXXXXXXXXXX" + "XXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String TEXT_255 = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    private static final String TEXT_25 = "XXXXXXXXXXXXXXXXXXXXXXXXXXX";

    //********LABELS********
    private Label messageLbl;

    //********TEXT FIELDS********
    private TextField txtNombre;
    private TextArea txtADescripcion;
    private TextField txtStock;
    private TextField txtNombreFiltro;

    //********COMBOBOX********
    private ComboBox<String> cbxFiltro;

    //********TABLE********
    private TableView<Pieza> tablaPiezas;

    @Override
    public void start(Stage stage) throws Exception {
        new GESREClient().start(stage);
        txtNombre = lookup("#txtNombre").query();
        txtADescripcion = lookup("#txtADescripcion").query();
        txtStock = lookup("#txtStock").query();
        txtNombreFiltro = lookup("#txtNombreFiltro").query();
        tablaPiezas = lookup("#tablaPiezas").queryTableView();
        cbxFiltro = lookup("#cbxFiltro").queryComboBox();
    }

    @Test
    @Ignore
    public void testA_signIn() {
        clickOn("#userTxt");
        write("admin");
        clickOn("#passwordTxt");
        write("123456");
        clickOn("#loginBtn");
        verifyThat("#piezaPanel", isVisible());

    }

    @Test
    public void testB_estadoInicialVentana() {
        //Comprobar que esta en la ventana Gestion de piezas
        verifyThat("#piezaPanel", isVisible());
        //Comprobar si el campo Nombre esta en focus
        verifyThat("#txtNombre", (TextField t) -> t.isFocused());
        //Verificar si los campos estan vacios
        verifyThat("#txtNombre", hasText(""));
        verifyThat("#txtADescripcion", hasText(""));
        verifyThat("#txtStock", hasText(""));
        verifyThat("#txtNombreFiltro", hasText(""));

        //Comprobar si los botones CRUD y el campo NombreFiltro estan deshabilitados
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnBorrar", isDisabled());
        verifyThat("#btnEditar", isDisabled());
        verifyThat("#txtNombreFiltro", isDisabled());

        //Comprobar si la tabla y columnas estan visibles
        verifyThat("#tablaPiezas", isVisible());
        verifyThat("#nombreCl", isVisible());
        verifyThat("#descripcionCl", isVisible());
        verifyThat("#stockCl", isVisible());
        
        //Comprobar si el combobox esta visible y tiene como opcion default "Todo"
        verifyThat("#cbxFiltro", isVisible());
        verifyThat("Todo", isVisible());
    }
    
    @Test
    public void testC_comprobarBotonesHabilitados(){
        //Comprobar si los botones estan deshabilitados hasta que esten informados
        clickOn("#txtNombre");
        write("Tornillo");
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnBorrar", isDisabled());
        verifyThat("#btnEditar", isDisabled());
        
        clickOn("#txtADescripcion");
        write("Este es un buen tornillo");
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnBorrar", isDisabled());
        verifyThat("#btnEditar", isDisabled());
        
        clickOn("#txtStock");
        write("10");
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("#btnBorrar", isEnabled());
        verifyThat("#btnEditar", isEnabled());
        
        limpiarCampos();
        
    }
    
    @Test
    public void testD_longitudMaximaCampos(){
        clickOn("#txtNombre");
        write(TEXT_25);
        verifyThat("Límite de 25 caracteres alcanzado", isVisible());
        clickOn("#txtADescripcion");
        write(TEXT_255);
        verifyThat("Límite de 255 caracteres alcanzado", isVisible());
        clickOn("#txtStock");
        write("12345");
        assertEquals(txtStock.getLength(), 3);
        clickOn("#cbxFiltro");
    }
    
    /**
     * Llamar a este metodo limpiará todos los campos
     */
    public void limpiarCampos(){
        clickOn("#btnLimpiar");
        verifyThat("#txtNombre", hasText(""));
        verifyThat("#txtADescripcion", hasText(""));
        verifyThat("#txtStock", hasText(""));
        verifyThat("#txtNombreFiltro", hasText(""));
    }

}
