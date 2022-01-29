/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.test;

import GESRE.aplication.GESREClient;
import GESRE.entidades.Trabajador;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.codehaus.groovy.runtime.SwingGroovyMethods;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author Jonathan Viñan
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GestionTrabajadorViewControllerTest extends ApplicationTest {

    private static final String TEXT_50 = "XXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXX";

    private static final String TEXT_25 = "XXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXX";

    private Pane paneGeneralTrabajador;
    private Pane fxPane;

    //********BARRA MENU********
    private MenuBar menuBar;
    private MenuItem mnCerrarSecion;
    private MenuItem mnSalir;

    //********LABEL********
    private Label lblTitulo;
    private Label lblNombreCompleto;
    private Label lblEmail;
    private Label lblPrecioHora;
    private Label lblFechaContrato;
    private Label lblNombreUsuario;
    private Label lblContrasenia;
    private Label lblRepiteContrasenia;

    //********LABEL DE ERROR********
    private Label lblErrorNombreCompleto;
    private Label lblErrorEmail;
    private Label lblErrorPrecioHora;
    private Label lblErrorFechaContrato;
    private Label lblErrorNombreUsuario;
    private Label lblErrorContrasenia;
    private Label lblErrorRepiteContrasenia;
    private Label lblErrorBuscar;

    //********TEXT FIEL********
    private TextField txtNombreCompleto;
    private TextField txtEmail;
    private TextField txtPrecioHora;

    private TextField txtNombreUsuario;
    private TextField txtBuscar;

    //********PASSWORD FIEL********
    private PasswordField txtContrasenia;
    private PasswordField txtRepiteContrasenia;

    //********DATE PICKER********
    private DatePicker datePikerFechaContrato;

    //********COMBOBOX********
    private ComboBox<String> cbxFiltro;

    //********BUTTON********
    private Button btnAnadir;
    private Button btnModificar;
    private Button btnEliminar;
    private Button btnLimpiar;
    private Button btnBuscar;
    private Button btnInforme;
    private Button btnGestionarClientes;

    //********TABLE********
    private TableView<Trabajador> tablaTrabajadores;

    //********TABLE COLUM********
    private TableColumn<Trabajador, String> nombreUsuarioCL;
    private TableColumn<Trabajador, String> nombreCompletoCL;
    private TableColumn<Trabajador, Date> fechaContratoCL;
    private TableColumn<Trabajador, String> PrecioHoraCL;

    /**
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(GESREClient.class);
    }

    public void star(Stage stage) throws Exception {
        new GESREClient().start(stage);

        txtNombreCompleto = lookup("#txtNombreCompleto").query();
        txtEmail = lookup("#txtEmail").query();
        txtNombreUsuario = lookup("#txtEmail").query();
        txtPrecioHora = lookup("#txtPrecioHora").query();
        txtBuscar = lookup("#txtBuscar").query();
        txtContrasenia = lookup("#txtContrasenia").query();
        txtRepiteContrasenia = lookup("#txtRepiteContrasenia").query();
        datePikerFechaContrato = lookup("#datePikerFechaContrato").query();
        tablaTrabajadores = lookup("#tablaTrabajadores").query();
    }

    @Test
    public void test00_signIn() {
        clickOn("#userTxt");
        write("admin");
        clickOn("#passwordTxt");
        write("1234");
        clickOn("#loginBtn");
        verifyThat("#paneGeneralTrabajador", isVisible());

    }

    @Test
    public void test01_inicioStage() {

        verifyThat("#txtNombreCompleto", hasText(""));
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#txtNombreUsuario", hasText(""));
        verifyThat("#txtPrecioHora", hasText(""));
        verifyThat("#txtBuscar", hasText(""));
        verifyThat("#txtContrasenia", hasText(""));
        verifyThat("#txtRepiteContrasenia", hasText(""));
        verifyThat("#lblErrorNombreCompleto", hasText(""))   ;
 

        verifyThat(
                "#datePikerFechaContrato", isVisible());
        verifyThat(
                "#btnAnadir", isVisible());
        verifyThat(
                "#btnModificar", isVisible());
        verifyThat(
                "#btnEliminar", isVisible());
        verifyThat(
                "#btnLimpiar", isVisible());
        verifyThat(
                "#btnBuscar", isVisible());
        verifyThat(
                "#btnInforme", isVisible());
        verifyThat(
                "#tablaTrabajadores", isVisible());

        verifyThat(
                "#txtNombreCompleto", (TextField t) -> t.isFocused());
    }

    /**
     * Method to test that each field has a limit of characters
     */
    @Test
    public void test02_charLimit() {
        clickOn("#txtNombreCompleto");
        write(TEXT_50);
        verifyThat("#lblErrorNombreCompleto", isVisible());
        clickOn("#txtEmail");
        write(TEXT_50);
        verifyThat("#lblErrorEmail", isVisible());
        clickOn("#txtNombreUsuario");
        write(TEXT_50);
        verifyThat("#lblErrorNombreUsuario", isVisible());
        clickOn("#txtPrecioHora");
        write("XXX");
        verifyThat("#lblErrorPrecioHora", isVisible());
        clickOn("#txtContrasenia");
        write(TEXT_25);
        verifyThat("#lblErrorContrasenia", isVisible());
        clickOn("#txtRepiteContrasenia");
        write(TEXT_25);
        verifyThat("#lblErrorRepiteContrasenia", isVisible());
        verifyThat("#btnLimpiar", isVisible());
        clickOn("#btnLimpiar");
    }
}
