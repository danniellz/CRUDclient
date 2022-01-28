package GESRE.test;

import GESRE.aplication.GESREClient;
import GESRE.controller.PiezaViewController;
import GESRE.entidades.Pieza;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
import org.testfx.matcher.control.ComboBoxMatchers;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * Clase test de la ventana Pieza
 *
 * @author Daniel Brizuela
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PiezaViewControllerIT extends ApplicationTest {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(PiezaViewController.class.getName());

    private static final String TEXT_50 = "XXXXXXXXXXXXXXXXXXXXXXXXX" + "XXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String TEXT_255 = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
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

        //Comprobar si el combobox esta visible, tiene los items correspondientes y tiene como opcion default "Todo"
        verifyThat("#cbxFiltro", isVisible());
        verifyThat(cbxFiltro, ComboBoxMatchers.containsItems("Todo", "Nombre", "Stock"));
        verifyThat(cbxFiltro, hasSelectedItem("Todo"));
    }

    @Test
    @Ignore
    public void testC_comprobarBotonesHabilitados() {
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
    @Ignore
    public void testD_longitudMaximaCampos() {
        clickOn("#txtNombre");
        write(TEXT_25);
        verifyThat("Límite de 25 caracteres alcanzado", isVisible());
        clickOn("#txtADescripcion");
        write(TEXT_255);
        verifyThat("Límite de 255 caracteres alcanzado", isVisible());
        clickOn("#txtStock");
        write("12345");
        assertEquals(txtStock.getLength(), 3);

        //Seleccionar combobox y comprobar si el campo NombreFiltro esta activo y tiene una longitud maxima
        clickOn("#cbxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat(cbxFiltro, hasSelectedItem("Nombre"));
        verifyThat("#txtNombreFiltro", isEnabled());

        clickOn("#txtNombreFiltro");
        write(TEXT_25);
        verifyThat("Límite de 25 caracteres alcanzado", isVisible());

        limpiarCampos();
    }

    @Test
    @Ignore
    public void testE_controlNumeroCaracterEspecial() {
        //Controlar en  el campo Nombre que no acepte numeros o caracter especial y en Stock que solo acepte numeros no negativos
        //Verificar campo Nombre con numero
        clickOn("#txtNombre");
        write("Tornillo1");
        clickOn("#txtADescripcion");
        write("Buen tornillo");
        clickOn("#txtStock");
        write("10");
        clickOn("#btnAnadir");
        verifyThat("Los números y/o caracteres especiales no están permitidos", isVisible());
        //Verificar campo Nombre con caracter especial
        clickOn("#txtNombre");
        eraseText(1);
        write("@");
        clickOn("#btnAnadir");
        verifyThat("Los números y/o caracteres especiales no están permitidos", isVisible());

        //Verificar campo Stock con letra
        clickOn("#txtNombre");
        eraseText(1);
        doubleClickOn("#txtStock");
        eraseText(1);
        write("a");
        clickOn("#btnAnadir");
        verifyThat("Solo se admiten números (positivos)", isVisible());

        clickOn("#txtStock");
        write("1");
        clickOn("#btnAnadir");
        verifyThat("Solo se admiten números (positivos)", isVisible());

        //Verificar campo Stock con numero negativo
        clickOn("#txtStock");
        eraseText(2);
        write("-1");
        clickOn("#btnAnadir");
        verifyThat("Solo se admiten números (positivos)", isVisible());

        limpiarCampos();

        //Verificar campo NombreFiltro
        clickOn("#cbxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat(cbxFiltro, hasSelectedItem("Nombre"));
        verifyThat("#txtNombreFiltro", isEnabled());

        //Comprobar boton campo vacio
        clickOn("#btnBuscar");
        verifyThat("No has escrito un nombre para buscar", isVisible());

        limpiarCampos();

    }

    @Test
    public void testF_crearPiezaOK() {
        //Obtener el numero de filas
        int filas = tablaPiezas.getItems().size();
        //3 Caracteres Aleatorios para evitar que cree uno que exista (rango utilzado para letras minusculas)
        String caracter = "Tornillo";
        Random rn = new Random();
        caracter += (char) (rn.nextInt(122 - 97) + 97);
        caracter += (char) (rn.nextInt(122 - 97) + 97);
        caracter += (char) (rn.nextInt(122 - 97) + 97);
        //Crear pieza
        clickOn("#txtNombre");
        write(caracter);
        clickOn("#txtADescripcion");
        write("Buen tornillo");
        clickOn("#txtStock");
        write("10");
        //Verificar si se activa el boton y al presionar si se borran los campos y crea la pieza
        verifyThat("#btnAnadir", isEnabled());
        clickOn("#btnAnadir");
        verifyThat("La Pieza '" + caracter + "' se ha creado con exito!", isVisible());
        clickOn("Aceptar");
        verifyThat("#piezaPanel", isVisible());

        //Comprobar si los campos se borran al crear la pieza
        verifyThat("#txtNombre", hasText(""));
        verifyThat("#txtADescripcion", hasText(""));
        verifyThat("#txtStock", hasText(""));

        //Comprobar si se añadio a la tabla
        assertEquals("La pieza no ha sido añadida a la tabla!", filas + 1, tablaPiezas.getItems().size());
        //ver si la pieza esta en el modelo de tabla
        /*List<Pieza> piezas = tablaPiezas.getItems();
        assertEquals("La pieza no ha sido añadida a la tabla!", piezas.stream().filter(p->p.getNombre().equals(caracter)).count(), 1);*/

    }

    /**
     * Llamar a este metodo limpiará todos los campos
     */
    public void limpiarCampos() {
        clickOn("#btnLimpiar");
        verifyThat("#txtNombre", hasText(""));
        verifyThat("#txtADescripcion", hasText(""));
        verifyThat("#txtStock", hasText(""));
        verifyThat("#txtNombreFiltro", hasText(""));
    }

}
