/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GESRE.test;

import GESRE.aplication.GESREClient;
import GESRE.controller.GestionClientesController;
import GESRE.entidades.Cliente;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author Mikel Matilla
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GestionClientesControllerTest extends ApplicationTest {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(GestionClientesControllerTest.class.getName());

    private static final String TEXT_26 = "XXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String TEXT_51 = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    private TextField usuarioTxt;
    private TextField nombreTxt;
    private TextField correoTxt;
    private PasswordField contrasenaTxt;
    private PasswordField repiteContrasenaTxt;
    private DatePicker fechaRegistroDate;

    private ComboBox buscarCombo;
    private TextField buscarTxt;

    private TableView clientesTabla;

    /**
     * Iniciar ventana y preparar componentes a utilizar
     *
     * @param stage objeto Stage
     * @throws Exception salta una excepcion si ocurre un error al iniciar la
     * ventana
     */
    @Override
    public void start(Stage stage) throws Exception {
        iniciarVentanaPieza(stage);
        usuarioTxt = lookup("#usuarioTxt").query();
        nombreTxt = lookup("#nombreTxt").query();
        correoTxt = lookup("#correoTxt").query();
        contrasenaTxt = lookup("#contrasenaTxt").query();
        repiteContrasenaTxt = lookup("#repiteContrasenaTxt").query();
        fechaRegistroDate = lookup("#fechaRegistroDate").query();
        buscarCombo = lookup("#buscarCombo").queryComboBox();
        buscarTxt = lookup("#buscarTxt").query();
        clientesTabla = lookup("#clientesTabla").queryTableView();
    }

    @Test
    public void testA_botonesHabilitados() {
        verifyThat("#anadirBtn", isDisabled());
        verifyThat("#borrarBtn", isDisabled());
        verifyThat("#editarBtn", isDisabled());

        clickOn("#usuarioTxt");
        write("prueba");
        clickOn("#nombreTxt");
        write("prueba prueba");
        clickOn("#correoTxt");
        write("prueba@prueba.com");
        clickOn("#contrasenaTxt");
        write("1234");
        clickOn("#repiteContrasenaTxt");
        write("1234");

        verifyThat("#anadirBtn", isEnabled());
        verifyThat("#borrarBtn", isDisabled());
        verifyThat("#editarBtn", isDisabled());
    }

    @Test
    public void testB_limiteCampos() {
        clickOn("#limpiarBtn");

        clickOn("#usuarioTxt");
        write(TEXT_26);
        assertEquals(usuarioTxt.getLength(), 25);

        clickOn("#nombreTxt");
        write(TEXT_51);
        assertEquals(nombreTxt.getLength(), 50);

        clickOn("#correoTxt");
        write(TEXT_51);
        assertEquals(correoTxt.getLength(), 50);

        clickOn("#contrasenaTxt");
        write(TEXT_26);
        assertEquals(contrasenaTxt.getLength(), 25);

        clickOn("#repiteContrasenaTxt");
        write(TEXT_26);
        assertEquals(repiteContrasenaTxt.getLength(), 25);

        clickOn("#buscarCombo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#buscarTxt");
        write(TEXT_51);
        assertEquals(buscarTxt.getLength(), 50);
    }

    @Test
    public void testC_crear() {
        clickOn("#limpiarBtn");
        String caracter = "prueba";
        Random rn = new Random();
        caracter += (char) (rn.nextInt(122 - 97) + 97);
        caracter += (char) (rn.nextInt(122 - 97) + 97);
        caracter += (char) (rn.nextInt(122 - 97) + 97);

        clickOn("#usuarioTxt");
        write(caracter);
        clickOn("#nombreTxt");
        write("prueba prueba");
        clickOn("#correoTxt");
        write(caracter+"@prueba.com");
        clickOn("#contrasenaTxt");
        write("1234");
        clickOn("#repiteContrasenaTxt");
        write("1234");

        clickOn("#anadirBtn");
        verifyThat("Cliente añadido correctamente", isVisible());
        clickOn(isDefaultButton());
    }

    @Test
    public void testD_seleccionar() {
        int filas = clientesTabla.getItems().size();
        assertNotEquals("La tabla no tiene datos", filas, 0);

        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La fila es nula, no contiene datos", fila);
        clickOn(fila);

        Cliente clienteSeleccionado = (Cliente) clientesTabla.getSelectionModel().getSelectedItem();
        verifyThat("#usuarioTxt", hasText(clienteSeleccionado.getLogin()));
        verifyThat("#nombreTxt", hasText(clienteSeleccionado.getFullName()));
        verifyThat("#correoTxt", hasText(clienteSeleccionado.getEmail()));

        verifyThat("#anadirBtn", isDisabled());
        verifyThat("#borrarBtn", isEnabled());
        verifyThat("#editarBtn", isEnabled());

        press(KeyCode.CONTROL);
        clickOn(fila);
        release(KeyCode.CONTROL);
    }

    @Test
    public void testF_modificar() {
        int filas = clientesTabla.getItems().size();
        assertNotEquals("La tabla no tiene datos", filas, 0);

        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La fila es nula, no contiene datos", fila);
        clickOn(fila);

        clickOn("#usuarioTxt");
        write("a");
        clickOn("#nombreTxt");
        write("a");
        clickOn("#correoTxt");
        write("a");

        clickOn("#editarBtn");

        verifyThat("¿Esta seguro de que desea modifarlo?", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void testG_borrar() {
        int filas = clientesTabla.getItems().size();
        assertNotEquals("La tabla no tiene datos", filas, 0);

        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La fila es nula, no contiene datos", fila);
        clickOn(fila);

        clickOn("#borrarBtn");
        verifyThat("¿Esta seguro de que desea eliminarlo?", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void testI_limpiarCampos() {
        clickOn("#limpiarBtn");
        verifyThat("#usuarioTxt", hasText(""));
        verifyThat("#nombreTxt", hasText(""));
        verifyThat("#correoTxt", hasText(""));
        verifyThat("#contrasenaTxt", hasText(""));
        verifyThat("#repiteContrasenaTxt", hasText(""));
    }

    @Test
    public void testK_filtrar() {
        clickOn("#buscarCombo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#buscarTxt");
        write("prueba prueba");
        clickOn("#buscarBtn");
    }

    @Test
    public void testL_abrirGestionTrabajadores() {
        clickOn("#trabajadoresBtn");
        verifyThat("#paneGeneralTrabajador", isVisible());

        clickOn("#btnGestionarClientes");
        verifyThat("#clientesPanel", isVisible());
    }
    
    public void iniciarVentanaPieza(Stage stage) throws Exception {
        try {
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GESRE/vistas/GestionClientesView.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller
            GestionClientesController controlador = ((GestionClientesController) loader.getController());
            //Set the stage
            controlador.setStage(stage);
            //initialize the window
            controlador.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error Starting SignIn window", ex);
        }
    }

}
