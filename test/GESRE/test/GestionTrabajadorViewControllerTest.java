/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.test;

import GESRE.aplication.GESREClient;
import GESRE.entidades.Trabajador;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
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
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.V;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.eclipse.persistence.internal.helper.Helper;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
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
    private DatePicker datePickerFechaContrato;

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

    //***********************
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
        datePickerFechaContrato = lookup("#datePikerFechaContrato").query();
        tablaTrabajadores = lookup("#tablaTrabajadores").query();
        cbxFiltro = lookup("#cbxFiltro").queryComboBox();
    }

    @Test
    @Ignore
    public void test_A_signIn() {
        clickOn("#userTxt");
        write("admin");
        clickOn("#passwordTxt");
        write("1234");
        clickOn("#loginBtn");
        verifyThat("#paneGeneralTrabajador", isVisible());

    }

    @Test
    //@Ignore
    public void test_B_inicioStage() {

        verifyThat("#txtNombreCompleto", hasText(""));
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#txtNombreUsuario", hasText(""));
        verifyThat("#txtPrecioHora", hasText(""));
        verifyThat("#txtBuscar", isDisabled());
        verifyThat("#txtContrasenia", hasText(""));
        verifyThat("#txtRepiteContrasenia", hasText(""));
        verifyThat("#datePickerFechaContrato", isVisible());
        verifyThat("#btnAnadir", isVisible());
        verifyThat("#btnModificar", isVisible());
        verifyThat("#btnEliminar", isVisible());
        verifyThat("#btnLimpiar", isVisible());
        verifyThat("#btnBuscar", isVisible());
        verifyThat("#btnInforme", isVisible());
        //verifyThat("#btnGestionarClientes", isVisible()); Quitar cuando el boton tenga su funcion
        verifyThat("#btnInforme", isEnabled());
//        verifyThat("#btnGestionarClientes", isEnabled());

        verifyThat("#tablaTrabajadores", isVisible());
        verifyThat("#nombreUsuarioCL", isVisible());
        verifyThat("#nombreCompletoCL", isVisible());
        verifyThat("#fechaContratoCL", isVisible());
        verifyThat("#PrecioHoraCL", isVisible());

        verifyThat("#cbxFiltro", isVisible());
        verifyThat("#cbxFiltro", isEnabled());

        verifyThat("#txtNombreCompleto", (TextField t) -> t.isFocused());
    }

    /**
     * Method to test that each field has a limit of characters
     */
    @Test
    //@Ignore
    public void test_C_labelErrorVisibleMaxiimoCaracteres() {
        clickOn("#txtNombreCompleto");
        write(TEXT_50);
        verifyThat("#lblErrorNombreCompleto", isVisible());
        verifyThat("Maximo de caracteres alcanzados 50", isVisible());
        clickOn("#txtEmail");
        write(TEXT_50);
        verifyThat("#lblErrorEmail", isVisible());
        verifyThat("Maximo de caracteres alcanzados 50", isVisible());
        clickOn("#txtNombreUsuario");
        write(TEXT_50);
        verifyThat("#lblErrorNombreUsuario", isVisible());
        verifyThat("Maximo de caracteres alcanzados 25", isVisible());
        clickOn("#txtPrecioHora");
        write("XXX");
        verifyThat("#lblErrorPrecioHora", isVisible());
        verifyThat("Maximo de 2 cifras", isVisible());
        clickOn("#txtContrasenia");
        write(TEXT_25);
        verifyThat("#lblErrorContrasenia", isVisible());
        verifyThat("Maximo de caracteres alcanzados 25", isVisible());
        clickOn("#txtRepiteContrasenia");
        write(TEXT_25);
        verifyThat("#lblErrorRepiteContrasenia", isVisible());
        verifyThat("Maximo de caracteres alcanzados 25", isVisible());
        verifyThat("#btnLimpiar", isVisible());
        clickOn("#btnLimpiar");

    }

    @Test
    @Ignore
    public void test_D_ErrorLabelVisible_BotonAñadir() {
        String nombre = generarNombresAleatorios();
        int numero = generarNumeroAleatorio();
        String mismoNombre = nombre;

        String email = generarEmail();
        clickOn("#txtNombreCompleto");
        write(nombre + numero);
        clickOn("#txtEmail");
        write(mismoNombre + numero);
        clickOn("#txtNombreUsuario");
        write(mismoNombre + "@");
        clickOn("#txtPrecioHora");
        write(mismoNombre);
        clickOn("#txtContrasenia");
        write(mismoNombre);
        clickOn("#txtRepiteContrasenia");
        write(email);
        clickOn("#datePickerFechaContrato");
        this.push(CONTROL, A);
        eraseText(1);
        clickOn("#btnAnadir");
        verifyThat("#lblErrorNombreCompleto", isVisible());
        verifyThat("El Nombre completo sólo debe contener letras", isVisible());
        verifyThat("#lblErrorEmail", isVisible());
        verifyThat("Email es inválido", isVisible());
        verifyThat("#lblErrorNombreUsuario", isVisible());
        verifyThat("El usuario sólo debe contener letras y numeros", isVisible());
        verifyThat("#lblErrorPrecioHora", isVisible());
        verifyThat("Deben ser numeros", isVisible());
        verifyThat("#lblErrorRepiteContrasenia", isVisible());
        verifyThat("Las contraseñas no coinciden", isVisible());
        verifyThat("#lblErrorFechaContrato", isVisible());
        verifyThat("Introduce una fecha", isVisible());
    }

    @Test
    //@Ignore
    public void test_E_BotonAñadir() {
        //clickOn("#btnLimpiar");
        String nombre = generarNombresAleatorios();
        String usuario = generarUsuariosAleatorios();
        int numero = generarNumeroAleatorio();
        String email = generarEmail();
        String precio = generarNumeroAleatorioPrecio();
        String mismoNombre = nombre;
        clickOn("#txtNombreCompleto");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre);

        clickOn("#txtEmail");
        this.push(CONTROL, A);
        eraseText(1);
        write(email);

        clickOn("#txtNombreUsuario");
        this.push(CONTROL, A);
        eraseText(1);
        write(usuario + numero);

        clickOn("#txtPrecioHora");
        this.push(CONTROL, A);
        eraseText(1);
        write(precio);

        clickOn("#txtContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write(mismoNombre);
        clickOn("#txtRepiteContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write(mismoNombre);

        clickOn("#datePickerFechaContrato");

        this.push(CONTROL, A);
        eraseText(1);
        write(LocalDate.now().toString());

        clickOn("#btnAnadir");
        verifyThat("#lblErrorNombreCompleto", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorEmail", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorNombreUsuario", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorPrecioHora", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorRepiteContrasenia", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("Se ha creado correctamente", isVisible());

    }

    @Test
    // @Ignore
    public void test_E1_BotonAñadir_ErrorLogin_Y_ErrorEmail() {
        //Comprobamos el error de modificar con un login existente
        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", fila);
        clickOn(fila);

        doubleClickOn("#txtNombreUsuario");
        this.push(CONTROL, A);
        this.push(CONTROL, C);
        clickOn("#btnLimpiar");

        Node otraFila = lookup(".table-row-cell").nth(1).query();
        assertNotNull("Row is null: table has not that row. ", otraFila);

        clickOn(otraFila);
        doubleClickOn("#txtNombreUsuario");
        this.push(CONTROL, A);
        this.push(CONTROL, V);
        clickOn("#btnModificar");
        verifyThat("El login ya existe", isVisible());
        clickOn(isDefaultButton());
        clickOn("#btnLimpiar");

        //Comprobamos el error de modificar con un email existente
        Node filaE = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", filaE);
        clickOn(fila);

        doubleClickOn("#txtEmail");
        this.push(CONTROL, A);
        this.push(CONTROL, C);
        clickOn("#btnLimpiar");

        Node otraFilaE = lookup(".table-row-cell").nth(1).query();
        assertNotNull("Row is null: table has not that row. ", otraFilaE);

        clickOn(otraFila);
        doubleClickOn("#txtEmail");
        this.push(CONTROL, A);
        this.push(CONTROL, V);
        clickOn("#btnModificar");
        verifyThat("El emal ya existe", isVisible());
        clickOn(isDefaultButton());
        clickOn("#btnLimpiar");

    }

    @Test
    // @Ignore
    public void test_F_ErrorLabelVisible_BotonMofidicar() {

        String nombre = generarNombresAleatorios();
        int numero = generarNumeroAleatorio();
        String email = generarEmail();
        //    int rowCount = tablaTrabajadores.getItems().size();

        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", fila);
        clickOn(fila);

        clickOn("#txtNombreCompleto");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre + numero);
        clickOn("#txtEmail");
        this.push(CONTROL, A);
        eraseText(1);
        write(email + numero);
        clickOn("#txtNombreUsuario");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre + "@");
        clickOn("#txtPrecioHora");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre);
        clickOn("#txtContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre);
        clickOn("#txtRepiteContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write(email);
        clickOn("#datePickerFechaContrato");
        this.push(CONTROL, A);
        eraseText(1);

        clickOn("#btnModificar");
        verifyThat("#lblErrorNombreCompleto", isVisible());
        verifyThat("El Nombre completo sólo debe contener letras", isVisible());
        verifyThat("#lblErrorEmail", isVisible());
        verifyThat("Email es inválido", isVisible());
        verifyThat("#lblErrorNombreUsuario", isVisible());
        verifyThat("El usuario sólo debe contener letras y numeros", isVisible());
        verifyThat("#lblErrorPrecioHora", isVisible());
        verifyThat("Deben ser numeros", isVisible());
        verifyThat("#lblErrorRepiteContrasenia", isVisible());
        verifyThat("#lblErrorFechaContrato", isVisible());
        verifyThat("Introduce una fecha", isVisible());
        verifyThat("Error : No se puede modificar", isVisible());

    }

    @Test
    //@Ignore
    public void test_G_BotonMofidicar() {
        String nombre = generarNombresAleatorios();
        String usuario = generarUsuariosAleatorios();
        int numero = generarNumeroAleatorio();
        String email = generarEmail();
        String precio = generarNumeroAleatorioPrecio();

        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", fila);;
        clickOn(fila);

        clickOn("#txtNombreCompleto");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre);

        clickOn("#txtEmail");
        this.push(CONTROL, A);
        eraseText(1);
        write(email);

        clickOn("#txtNombreUsuario");
        this.push(CONTROL, A);
        eraseText(1);
        write(usuario);

        clickOn("#txtPrecioHora");
        this.push(CONTROL, A);
        eraseText(1);
        write(precio);

        doubleClickOn("#txtContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write("abcd*1234");
        doubleClickOn("#txtRepiteContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write("abcd*1234");

        clickOn("#datePickerFechaContrato");
        this.push(CONTROL, A);
        eraseText(1);
        write(LocalDate.now().toString());

        clickOn("#btnModificar");

        verifyThat("#lblErrorNombreCompleto", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorEmail", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorNombreUsuario", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorPrecioHora", isVisible());
        verifyThat("", isVisible());
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("Se ha modificado correctamente", isVisible());

    }

    @Test
    // @Ignore
    public void test_H_ErrorCancelar_BotonEliminar() {
        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", fila);;
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        // verifyThat(".alert", NodeMatchers.isVisible());
        verifyThat("¿Seguro que quieres eliminar el trabajador?", isVisible());
        clickOn(isCancelButton());
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("Borrado cancelado", isVisible());
        clickOn("#btnLimpiar");
    }

    @Test
    // @Ignore
    public void test_I_ErrorTrabajador_BotonEliminar() {
        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", fila);;
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Seguro que quieres eliminar el trabajador?", isVisible());
        clickOn(isDefaultButton());
        verifyThat("EL TRABAJADOR NO SE PUEDE BORRAR\n"
                + "Tiene incidencias aun sin terminar o tiene piezas creadas", isVisible());
        clickOn(isDefaultButton());
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("No se ha borrado el trabajador", isVisible());
    }

    @Test
    //@Ignore
    public void test_J_BorrarTrabajador_BotonEliminar() {
        Node fila = lookup(".table-row-cell").nth(2).query();
        assertNotNull("Row is null: table has not that row. ", fila);;
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Seguro que quieres eliminar el trabajador?", isVisible());
        clickOn(isDefaultButton());
        verifyThat("El trabajador ha sido borrado con exito", isVisible());
        clickOn(isDefaultButton());
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("Trabajador eliminado correctamente", isVisible());
    }

    @Test
    // @Ignore
    public void test_K_FiltroBuscarTodos() {
        clickOn("#cbxFiltro");
        clickOn("Todos");
        verifyThat("#txtBuscar", isDisabled());
        verifyThat("#btnBuscar", isEnabled());
        clickOn("#btnBuscar");
        tablaTrabajadores = lookup("#tablaTrabajadores").queryTableView();

    }

    @Test
    //  @Ignore
    public void test_L_FiltroBuscarTrabajadorErrorSinTexto() {
        clickOn("#cbxFiltro");
        clickOn("Un trabajador");
        verifyThat("#txtBuscar", isEnabled());
        verifyThat("#txtBuscar", isVisible());
        clickOn("#btnBuscar");
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("Error: Escribe un nombre a buscar", isVisible());
        tablaTrabajadores = lookup("#tablaTrabajadores").queryTableView();
    }

    @Test
    // @Ignore
    public void test_M_FiltroBuscarTrabajadorErrorTexto() {
        clickOn("#cbxFiltro");
        clickOn("Un trabajador");
        verifyThat("#txtBuscar", isEnabled());
        clickOn("#txtBuscar");
        this.push(CONTROL, A);
        eraseText(1);
        write("Jos3");
        clickOn("#btnBuscar");
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("El nombre sólo debe contener letras", isVisible());
        tablaTrabajadores = lookup("#tablaTrabajadores").queryTableView();
    }

    @Test
    //  @Ignore
    public void test_N_FiltroBuscarTrabajadorConTexto() {
        clickOn("#cbxFiltro");
        clickOn("Un trabajador");
        verifyThat("#txtBuscar", isEnabled());
        clickOn("#txtBuscar");
        this.push(CONTROL, A);
        eraseText(1);
        write("J");
        clickOn("#btnBuscar");
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("", isVisible());
        tablaTrabajadores = lookup("#tablaTrabajadores").queryTableView();
    }

    @Test
    //  @Ignore
    public void test_O_FiltroSinIncidencias() {
        clickOn("#cbxFiltro");
        clickOn("Sin incidencias");
        verifyThat("#txtBuscar", isDisabled());
        clickOn("#btnBuscar");
        verifyThat("#lblErrorBuscar", isVisible());
        verifyThat("", isVisible());
        tablaTrabajadores = lookup("#tablaTrabajadores").queryTableView();
    }

    //********************  T E S T   D E   E R R O R   D E   S E R V I D O R  ********************
    @Test
    @Ignore
    public void test_P_ErrorServidorBtnAnadir() {
        String nombre = generarNombresAleatorios();
        String usuario = generarUsuariosAleatorios();
        int numero = generarNumeroAleatorio();
        String email = generarEmail();
        String precio = generarNumeroAleatorioPrecio();
        String mismoNombre = nombre;
        clickOn("#txtNombreCompleto");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre);

        clickOn("#txtEmail");
        this.push(CONTROL, A);
        eraseText(1);
        write(email);

        clickOn("#txtNombreUsuario");
        this.push(CONTROL, A);
        eraseText(1);
        write(usuario + numero);

        clickOn("#txtPrecioHora");
        this.push(CONTROL, A);
        eraseText(1);
        write(precio);

        clickOn("#txtContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write(mismoNombre);
        clickOn("#txtRepiteContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write(mismoNombre);

        clickOn("#datePickerFechaContrato");

        this.push(CONTROL, A);
        eraseText(1);
        write(LocalDate.now().toString());

        clickOn("#btnAnadir");
        verifyThat("No hay conecxion con el servidor. Intentalo mas tarde", isVisible());
        clickOn(isDefaultButton());
    }

    @Test
    @Ignore
    public void test_Q_ErrorServidorBtnModificar() {
        String nombre = generarNombresAleatorios();
        String usuario = generarUsuariosAleatorios();
        int numero = generarNumeroAleatorio();
        String email = generarEmail();
        String precio = generarNumeroAleatorioPrecio();

        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", fila);;
        clickOn(fila);

        clickOn("#txtNombreCompleto");
        this.push(CONTROL, A);
        eraseText(1);
        write(nombre);

        clickOn("#txtEmail");
        this.push(CONTROL, A);
        eraseText(1);
        write(email);

        clickOn("#txtNombreUsuario");
        this.push(CONTROL, A);
        eraseText(1);
        write(usuario + numero);

        clickOn("#txtPrecioHora");
        this.push(CONTROL, A);
        eraseText(1);
        write(precio);

        doubleClickOn("#txtContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write("abcd*1234");
        doubleClickOn("#txtRepiteContrasenia");
        this.push(CONTROL, A);
        eraseText(1);
        write("abcd*1234");

        clickOn("#btnModificar");
        verifyThat("No hay conecxion con el servidor. Intentalo mas tarde", isVisible());
        clickOn(isDefaultButton());

    }

    @Test
    @Ignore
    public void test_R_ErrorServidorBtnEliminar() {

        Node fila = lookup(".table-row-cell").nth(2).query();
        assertNotNull("Row is null: table has not that row. ", fila);;
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Seguro que quieres eliminar el trabajador?", isVisible());
        clickOn(isDefaultButton());
        verifyThat("No hay conecxion con el servidor. Intentalo mas tarde", isVisible());
        clickOn(isDefaultButton());

    }

    public static String generarNombresAleatorios() {

        String[] nombres = {"Andrea", "Ana", "Aloy", "Alex", "David", "Baldomero", "Balduino", "Baldwin", "Baltasar", "Barry", "Bartolo",
            "Bartolomé", "Baruc", "Baruj", "Candelaria", "Cándida", "Canela", "Caridad", "Carina", "Carisa",
            "Caritina", "Carlota", "Baltazar"};
        String[] apellidos = {"Gomez", "Guerrero", "Cardenas", "Cardiel", "Cardona", "Cardoso", "Cariaga", "Carillo",
            "Carion", "Castiyo", "Castorena", "Castro", "Grande", "Grangenal", "Grano", "Grasia", "Griego",
            "Grigalva", "Cajamrca", "Matinez", "Aros"};

        Random rand = new Random();
        int num = (int) (Math.random() * 20 + 1);
        int unNombre = rand.nextInt(num);
        num = (int) (Math.random() * 20 + 1);
        int unApellido = rand.nextInt(num);

        String nombre = nombres[unNombre] + " " + apellidos[unApellido];
        return nombre;
    }

    public static int generarNumeroAleatorio() {
        Random rand = new Random();
        int num = (int) (Math.random() * 999 + 1);
        return num;
    }

    public static String generarNumeroAleatorioPrecio() {
        Random rand = new Random();
        int num = (int) (Math.random() * 999 + 1);
        String numero = "" + num;
        return numero;
    }

    private String generarEmail() {
        String[] nombres = {"Andrea", "Ana", "Aloy", "Alex", "David", "Baldomero", "Balduino", "Baldwin", "Baltasar", "Barry", "Bartolo",
            "Bartolomé", "Baruc", "Baruj", "Candelaria", "Cándida", "Canela", "Caridad", "Carina", "Carisa",
            "Caritina", "Carlota", "Baltazar"};
        String[] apellidos = {"Gomez", "Guerrero", "Cardenas", "Cardiel", "Cardona", "Cardoso", "Cariaga", "Carillo",
            "Carion", "Castiyo", "Castorena", "Castro", "Grande", "Grangenal", "Grano", "Grasia", "Griego",
            "Grigalva", "Cajamrca", "Matinez", "Aros"};

        Random rand = new Random();
        int num = (int) (Math.random() * 20 + 1);
        int unNombre = rand.nextInt(num);
        num = (int) (Math.random() * 20 + 1);
        int unApellido = rand.nextInt(num);

        String nombre = nombres[unNombre] + "@" + apellidos[unApellido] + ".com";
        return nombre;
    }

    private String generarUsuariosAleatorios() {
        String[] nombres = {"Andrea", "Ana", "Aloy", "Alex", "David", "Baldomero", "Balduino", "Baldwin", "Baltasar", "Barry", "Bartolo",
            "Bartolomé", "Baruc", "Baruj", "Candelaria", "Cándida", "Canela", "Caridad", "Carina", "Carisa",
            "Caritina", "Carlota", "Baltazar"};
        String[] apellidos = {"Gomez", "Guerrero", "Cardenas", "Cardiel", "Cardona", "Cardoso", "Cariaga", "Carillo",
            "Carion", "Castiyo", "Castorena", "Castro", "Grande", "Grangenal", "Grano", "Grasia", "Griego",
            "Grigalva", "Cajamrca", "Matinez", "Aros"};

        Random rand = new Random();
        int num = (int) (Math.random() * 20 + 1);
        int unNombre = rand.nextInt(num);
        num = (int) (Math.random() * 20 + 1);
        int unApellido = rand.nextInt(num);

        String nombre = nombres[unNombre] + apellidos[unApellido];
        return nombre;
    }

}
