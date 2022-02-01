package GESRE.test;

import GESRE.aplication.GESREClient;
import GESRE.controller.PiezaViewController;
import GESRE.entidades.Pieza;
import java.util.Random;
import java.util.logging.Logger;
import javafx.scene.Node;
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
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import org.testfx.matcher.control.ComboBoxMatchers;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * Clase test de la ventana Pieza
 *
 * @author Daniel Brizuela
 * @version 1.0
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

    /**
     * Iniciar ventana y preparar componentes a utilizar
     *
     * @param stage objeto Stage
     * @throws Exception salta una excepcion si ocurre un error al iniciar la
     * ventana
     */
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

    /**
     * Iniciar sesion
     */
    @Test
    @Ignore
    public void testA_signIn() {
        verifyThat("#signInPanel", isVisible());
        clickOn("#userTxt");
        write("admin");
        clickOn("#passwordTxt");
        write("123456");
        clickOn("#loginBtn");
        verifyThat("#piezaPanel", isVisible());

    }

    /**
     * Estado inicial de los componentes de la ventana
     */
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

    /**
     * comprobar si los botones se habilitan y deshabilitan adecuadamente
     */
    @Test
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

    /**
     * comprobar el limite de longitud de los campos
     */
    @Test
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

    /**
     * Controlar que no se permitan caracteres especiales o numeros en los
     * campos que lo requieran
     */
    @Test
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

    }

    /**
     * Creacion de nueva pieza
     */
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
        //Verificar si se activa el boton y al presionar si se borran los campos, aparece la ventana de confirmacion y crea la pieza
        verifyThat("#btnAnadir", isEnabled());
        clickOn("#btnAnadir");
        verifyThat("La Pieza '" + caracter + "' se ha creado con exito!", isVisible());
        clickOn(isDefaultButton());
        verifyThat("#piezaPanel", isVisible());

        //Comprobar si los campos se borran al crear la pieza
        verifyThat("#txtNombre", hasText(""));
        verifyThat("#txtADescripcion", hasText(""));
        verifyThat("#txtStock", hasText(""));

        //Comprobar si se añadio a la tabla
        assertEquals("La pieza no ha sido añadida a la tabla!", filas + 1, tablaPiezas.getItems().size());
        //ver si la pieza esta en el modelo de tabla
        //List<Pieza> piezas = tablaPiezas.getItems();
        //assertEquals("La pieza no ha sido añadida a la tabla!", piezas.stream().filter(p->p.getNombre().equals(caracter)).count(), 1);

    }

    /**
     * Prueba de seleccion de la tabla
     */
    @Test
    public void testG_seleccionTabla() {
        //Obtener el tamaño de la tabla
        int filas = tablaPiezas.getItems().size();
        assertNotEquals("La tabla no tiene datos", filas, 0);

        //Seleccionar fila
        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La fila es nula, no contiene datos", fila);
        clickOn(fila);
        //Obtener los datos de la fila seleccionada
        Pieza piezaSeleccionada = (Pieza) tablaPiezas.getSelectionModel().getSelectedItem();
        //Comprobar si los datos estan en los campos y se habilitan los botones correspondientes
        verifyThat(txtNombre, hasText(piezaSeleccionada.getNombre()));
        verifyThat(txtADescripcion, hasText(piezaSeleccionada.getDescripcion()));
        verifyThat(txtStock, hasText(piezaSeleccionada.getStock().toString()));
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("#btnBorrar", isEnabled());
        verifyThat("#btnEditar", isEnabled());
        //deseleccionar fila
        press(KeyCode.CONTROL);
        clickOn(fila);
        release(KeyCode.CONTROL);
        //Comprobar que no hay datos al deseleccionar
        verifyThat(txtNombre, hasText(""));
        verifyThat(txtADescripcion, hasText(""));
        verifyThat(txtStock, hasText(""));
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnBorrar", isDisabled());
        verifyThat("#btnEditar", isDisabled());

    }

    /**
     * Comprobar que no pueda crearse una pieza con el mismo nombre
     */
    @Test
    public void testH_piezaExiste() {
        //Seleccioanr la primera fila
        Pieza piezaSeleccionada = seleccionarFila();

        //Verificar si los datos de la tabla aparecen en los campos
        verifyThat("#txtNombre", hasText(piezaSeleccionada.getNombre()));
        verifyThat("#txtADescripcion", hasText(piezaSeleccionada.getDescripcion()));
        verifyThat("#txtStock", hasText(piezaSeleccionada.getStock().toString()));

        //Comprobar al presionar el boron de Añadir si el mensaje aparece
        clickOn("#btnAnadir");
        verifyThat("#messageLbl", isEnabled());
        verifyThat("La Pieza ya existe", isVisible());

        limpiarCampos();
    }

    /**
     * Actualizacion de la pieza
     */
    @Test
    public void testI_editarPiezaOK() {
        //Obtener el numero de filas
        int filas = tablaPiezas.getItems().size();
        assertNotEquals("La tabla no tiene datos", filas, 0);
        //Variables
        String caracter = "Nueva Pieza ";
        String desc = "Nueva descripcion";
        String stock = "0";
        Random rn = new Random();
        caracter += (char) (rn.nextInt(122 - 97) + 97);
        caracter += (char) (rn.nextInt(122 - 97) + 97);
        caracter += (char) (rn.nextInt(122 - 97) + 97);

        //Seleccionar fila
        Pieza piezaSeleccionada = seleccionarFila();

        //Verificar si los datos de la tabla aparecen en los campos
        verifyThat("#txtNombre", hasText(piezaSeleccionada.getNombre()));
        verifyThat("#txtADescripcion", hasText(piezaSeleccionada.getDescripcion()));
        verifyThat("#txtStock", hasText(piezaSeleccionada.getStock().toString()));

        //Cambiar los datos de la pieza
        txtNombre.clear();
        clickOn("#txtNombre");
        write(caracter);
        txtADescripcion.clear();
        clickOn("#txtADescripcion");
        write("Nueva descripcion");
        txtStock.clear();
        clickOn("#txtStock");
        write("0");

        //Comprobar si se ha editado correctamente
        clickOn("#btnEditar");
        verifyThat("La Pieza '" + caracter + "' se ha actualizado con exito!", isVisible());

        clickOn(isDefaultButton());

        //Seleccionar fila para verificar si los datos de la son los que se han escrito
        seleccionarFila();

        verifyThat("#txtNombre", hasText(caracter));
        verifyThat("#txtADescripcion", hasText(desc));
        verifyThat("#txtStock", hasText(stock));

    }

    /**
     * Borrado de la pieza
     */
    @Test
    public void testJ_borrarPiezaOK() {
        //Obtener el numero de filas
        int filas = tablaPiezas.getItems().size();
        assertNotEquals("La tabla no tiene datos", filas, 0);

        Pieza piezaSeleccionada = seleccionarFila();

        //Borrar
        verifyThat("#btnBorrar", isEnabled());
        clickOn("#btnBorrar");
        verifyThat("¿Borrar la fila seleccionada?\n"
                + "La operación no se puede revertir", isVisible());

        //Verificar si la ventana de confirmacion aparece
        clickOn(isDefaultButton());
        verifyThat("La Pieza '" + piezaSeleccionada.getNombre() + "' se ha Borrado con exito!", isVisible());
        clickOn(isDefaultButton());
        //verificar si se ha eliminado de la tabla
        assertEquals("La fila no se ha borrado!", filas - 1, tablaPiezas.getItems().size());

        //Comprobar si los campos se borran al borrar la pieza
        verifyThat("#txtNombre", hasText(""));
        verifyThat("#txtADescripcion", hasText(""));
        verifyThat("#txtStock", hasText(""));

    }

    /**
     * Comprobar si la consulta por filtros funciona correctamente
     */
    @Test
    public void testK_comprobarBuscarPorFiltrado() {
        //Obtener el numero de filas
        int filas = tablaPiezas.getItems().size();
        //para comprobar el final del filtrado por todo, porque ne el camino "filas" cambia su valor a 1
        int filas2 = tablaPiezas.getItems().size();
        //Verificar buscar por NOMBRE
        clickOn("#cbxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat(cbxFiltro, hasSelectedItem("Nombre"));
        verifyThat("#txtNombreFiltro", isEnabled());

        //Seleccionar la primera fila (prueba para buscar esta pieza)
        Pieza piezaSeleccionada = seleccionarFila();

        String nombrePieza = piezaSeleccionada.getNombre();

        clickOn("#txtNombreFiltro");
        write(nombrePieza);

        clickOn("#btnBuscar");

        //Comprobar que solo aparece la pieza
        assertEquals("Pieza no encontrada", filas = 1, tablaPiezas.getItems().size());

        //Seleccionar la primera fila, comprobar si la que ha encontrado tiene el mismo nombre
        piezaSeleccionada = seleccionarFila();

        assertEquals("La pieza no es la que buscabaa", nombrePieza, piezaSeleccionada.getNombre());

        //Verificar buscar por STOCK
        clickOn("#cbxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat(cbxFiltro, hasSelectedItem("Stock"));
        verifyThat("#txtNombreFiltro", isDisabled());
        clickOn("#btnBuscar");

        /*List<Pieza> piezas = tablaPiezas.getItems();
        assertEquals("Se ha encontrado pieza sin stock", piezas.stream().filter(p->p.getStock()!=0));*/
        //Verificar buscar por TODO
        clickOn("#cbxFiltro");
        type(KeyCode.UP);
        clickOn("#cbxFiltro");
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        verifyThat(cbxFiltro, hasSelectedItem("Todo"));
        verifyThat("#txtNombreFiltro", isDisabled());
        clickOn("#btnBuscar");
        assertEquals("No se ha listado todas las piezas del trabajador", filas2, tablaPiezas.getItems().size());
    }

    /**
     * Comprobar que salte un error si se trata de borrar una pieza que no
     * existe
     */
    @Test
    public void testL_borrarPiezaError() {
        clickOn("#txtNombre");
        write("Pieza para borrar");

        clickOn("#txtADescripcion");
        write("Buena pieza");

        clickOn("#txtStock");
        write("0");

        verifyThat("#btnBorrar", isEnabled());
        clickOn("#btnBorrar");

        verifyThat("La Pieza que quiere borrar no existe", isVisible());
        clickOn(isDefaultButton());

    }

    /**
     * Comprobar que salte un error si se trata de Editar una pieza que no
     * existe
     */
    @Test
    public void testM_editarPiezaError() {
        clickOn("#txtNombre");
        write("Pieza para borrar");

        clickOn("#txtADescripcion");
        write("Buena pieza");

        clickOn("#txtStock");
        write("0");

        verifyThat("#btnEditar", isEnabled());

        clickOn("#btnEditar");
        verifyThat("La Pieza que quiere Actualizar no existe", isVisible());

        clickOn(isDefaultButton());

    }

    /**
     * Comprobar que al editar una pieza no pueda usarse un nombre ya existente
     * y que avise si no se han hecho cambios
     */
    @Test
    public void testN_editarPiezaSinCambiosYconNombreExistente() {
        //Obtener el numero de filas
        int filas = tablaPiezas.getItems().size();
        assertNotEquals("La tabla no tiene datos", filas, 0);

        //Seleccioanr la primera fila
        seleccionarFila();

        //Sin cambios
        clickOn("#btnEditar");
        verifyThat("No se han detectado cambios", isVisible());
        clickOn(isDefaultButton());

        //Nombre existente
        txtNombre.clear();
        clickOn("#txtNombre");
        write("Tornillo");
        verifyThat("#btnEditar", isEnabled());
        clickOn("#btnEditar");

        verifyThat("La Pieza ya existe", isVisible());

    }

    /**
     * Comprobar que un trabajador no pueda hacer cambios en piezas de otros
     * trabajadores al usar la busqueda por nombre
     */
    @Test
    public void testO_ErrorInteraccionConPiezasDeOtroTrabajador() {
        //Obtener el numero de filas
        int filas = tablaPiezas.getItems().size();
        //buscar por NOMBRE una pieza que pertenezca a otro trabajador
        clickOn("#cbxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat(cbxFiltro, hasSelectedItem("Nombre"));
        verifyThat("#txtNombreFiltro", isEnabled());

        clickOn("#txtNombreFiltro");
        write("Tornillo Pequeño");
        clickOn("#btnBuscar");

        //verificar si se ha encontrado la pieza
        assertEquals("La pieza no es lo que buscabas o no hay ninguna pieza con ese nombre!", filas = 1, tablaPiezas.getItems().size());

        //Seleccionar la fila
        Pieza piezaSeleccionada = seleccionarFila();

        //Verificar que los datos estan en los campos
        verifyThat("#txtNombre", hasText(piezaSeleccionada.getNombre()));
        verifyThat("#txtADescripcion", hasText(piezaSeleccionada.getDescripcion()));
        verifyThat("#txtStock", hasText(piezaSeleccionada.getStock().toString()));

        //Verificar si se han bloqueado los campos
        verifyThat("#txtNombre", isDisabled());
        verifyThat("#txtADescripcion", isDisabled());
        verifyThat("#txtStock", isDisabled());

        //Verificar si aparecen los mensajes de error
        clickOn("#btnAnadir");
        verifyThat("La Pieza ya existe", isVisible());
        clickOn("#btnBorrar");
        verifyThat("No puedes Borrar la pieza de otro trabajador", isVisible());
        clickOn(isDefaultButton());
        clickOn("#btnEditar");
        verifyThat("No puedes Actualizar la pieza de otro trabajador", isVisible());
        clickOn(isDefaultButton());

        limpiarCampos();
    }

    /**
     * Ir a la ventana de Incidencias del trabajador
     */
    @Test
    @Ignore
    public void testP_ventanaGestionIncidenciasSeAbre() {
        //Abrir la ventana de gestion de incidencias del trabajador
        verifyThat("#btnGestionIncidencia", isEnabled());
        clickOn("#btnGestionIncidencia");
        verifyThat("#incidenciaTPanel", isVisible());

        clickOn("#btnGestionPiezas");
        verifyThat("#piezaPanel", isVisible());
    }

    /**
     * Mostrar informe de la tabla piezas
     */
    @Test
    public void testQ_informePiezas() {
        verifyThat("#piezaPanel", isVisible());
        clickOn("#btnInforme");
    }

    /**
     * Error del servidor si estas dentro, el servidor esta apagado y quieres
     * utilzar los botones CRUD
     */
    @Test
    @Ignore
    public void testR_ErrorServidorVentanaGestionPiezas() {
        verifyThat("#piezaPanel", isVisible());
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

        //Añadir
        verifyThat("#btnAnadir", isEnabled());
        clickOn("#btnAnadir");

        //Verificar si la ventana de error del servidor salta en pantalla
        verifyThat("No hay conexión con el servidor. Intentalo más tarde.", isVisible());
        clickOn(isDefaultButton());

        //Borrar
        //Seleccionar Fila
        seleccionarFila();
        clickOn("#btnBorrar");
        //Verificar si la ventana de error del servidor salta en pantalla
        verifyThat("No hay conexión con el servidor. Intentalo más tarde.", isVisible());
        clickOn(isDefaultButton());

        //Editar
        clickOn("#btnEditar");
        //Verificar si la ventana de error del servidor salta en pantalla
        verifyThat("No hay conexión con el servidor. Intentalo más tarde.", isVisible());
        clickOn(isDefaultButton());

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

    /**
     * Llamar a este método sirve para seleccionar una fila
     *
     * @return devuelve la informacion de la pieza seleccionada
     */
    public Pieza seleccionarFila() {
        //Seleccionar la fila
        Node fila = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La fila es nula, no contiene datos", fila);
        clickOn(fila);
        //Obtener los datos de la fila seleccionada
        Pieza piezaSeleccionada = (Pieza) tablaPiezas.getSelectionModel().getSelectedItem();
        return piezaSeleccionada;
    }

}
