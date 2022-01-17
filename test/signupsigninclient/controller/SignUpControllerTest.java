package signupsigninclient.controller;

import java.util.Random;
import java.util.concurrent.TimeoutException;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.CONTROL;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import signupsigninclient.SignUpSignInClient;

/**
 * Test for the SignUp Window IT
 * 
 * @author Aritz Arrieta, Daniel Brizuela y Jonathan Viñan
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    /**
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(SignUpSignInClient.class);

    }

    /**
     * Method to test if the SignUp Window is visible
     */
    @Test
    public void testA_start() {
        doubleClickOn("#signUpHl");
        verifyThat("#signUpPanel", isVisible());
    }

    /**
     * Method to test The initial state of the window
     */
    @Test
    public void testB_InitalState() {
        clickOn("#userTxt");
        verifyThat("#userTxt", isFocused());
        verifyThat("#userTxt", hasText(""));
        verifyThat("#fullNameTxt", hasText(""));
        verifyThat("#emailTxt", hasText(""));
        verifyThat("#passwordTxt", hasText(""));
        verifyThat("#repeatPasswordTxt", hasText(""));
        verifyThat("#registerBtn", isDisabled());
        verifyThat("#signInHl", isEnabled());
    }

    /**
     * Method to test if the resgister Button is disable with incorrect data
     */
    @Test
    public void testC_RegisterIsDisabled() {
        clickOn("#userTxt");
        write("123456789123456789123456789123456789");
        clickOn("#fullNameTxt");
        write("fullname23");
        clickOn("#emailTxt");
        write("email@gmail.om");
        clickOn("#passwordTxt");
        write("password");
        clickOn("#repeatPasswordTxt");
        write("password");
        verifyThat("#registerBtn", isDisabled());
        clickOn("#registerBtn");
        clearText();
    }

    /**
     * Method to test that each field has a limit of characters
     */
    @Test
    public void testD_charLimit() {
        clickOn("#userTxt");
        write("123456789123456789123456789123456789");
        verifyThat("#userErrorLbl", isVisible());
        clickOn("#fullNameTxt");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verifyThat("#fullNameErrorLbl", isVisible());
        clickOn("#emailTxt");
        write("123456789123456789123456789123456789@12345678912345678912345678912345.commm");
        verifyThat("#emailErrorLbl", isVisible());
        clickOn("#passwordTxt");
        write("123456789123456789123456789123456789");
        verifyThat("#passwordErrorLbl", isVisible());
        clickOn("#repeatPasswordTxt");
        write("123456789123456789123456789123456789");
        verifyThat("#repeatPasswordErrorLbl", isVisible());
        clearText();
    }

    /**
     * Method to test that the FullName field doesn't allow numbers or special characters
     */
    @Test
    public void testE_FullNameNumber_SpecialCharacters() {
        clickOn("#fullNameTxt");
        write("Jamiro2");
        clickOn("#emailTxt");
        verifyThat("#fullNameErrorLbl", isVisible());
        doubleClickOn("#fullNameTxt");
        this.push(CONTROL, A);
        eraseText(1);
        clickOn("#fullNameTxt");
        write("Jamiro@");
        clickOn("#emailTxt");
        verifyThat("#fullNameErrorLbl", isVisible());
        doubleClickOn("#fullNameTxt");
        this.push(CONTROL, A);
        eraseText(1);
    }

    /**
     * Method to test that the Password and RepeatPassword field have a minimum of characters (6)
     */
    @Test
    public void testF_PasswordMinimunLength() {
        clickOn("#passwordTxt");
        write("1234");
        clickOn("#repeatPasswordTxt");
        verifyThat("#passwordErrorLbl", isVisible());
        doubleClickOn("#passwordTxt");
        this.push(CONTROL, A);
        eraseText(1);
    }

    /**
     * Method to test if the control for the email format works
     */
    @Test
    public void testG_InvalidEmailFormat() {
        clickOn("#emailTxt");
        write("prueba@.");
        clickOn("#passwordTxt");
        verifyThat("#emailErrorLbl", isVisible());
        doubleClickOn("#emailTxt");
        this.push(CONTROL, A);
        eraseText(1);
        write("prueba@.com");
        clickOn("#passwordTxt");
        verifyThat("#emailErrorLbl", isVisible());
        doubleClickOn("#emailTxt");
        this.push(CONTROL, A);
        eraseText(1);
    }

    /**
     * Method to test if a User that already exist in the Db is introduced
     */
    @Test
    public void testH_RegisterUserExist() {

        clickOn("#userTxt");
        write("Jamiro");
        clickOn("#fullNameTxt");
        write("Jamiro Reamie");
        clickOn("#emailTxt");
        write("jamiro@gmail.com");
        clickOn("#passwordTxt");
        write("password");
        clickOn("#repeatPasswordTxt");
        write("password");
        verifyThat("#registerBtn", isEnabled());
        clickOn("#registerBtn");
        verifyThat("#userErrorLbl", isVisible());
        clearText();
    }

    /**
     * Method to test if the user introduced can be registered successfully
     */
    @Test
    public void testI_RegisterOk() {
        clickOn("#userTxt");
        Random rn = new Random();
        int numR = rn.nextInt(200);

        write("Jamiro" + numR);
        clickOn("#fullNameTxt");
        write("Jamiro Reamie");
        clickOn("#emailTxt");
        write("jamiro@gmail.com");
        clickOn("#passwordTxt");
        write("password");
        clickOn("#repeatPasswordTxt");
        write("password");
        verifyThat("#registerBtn", isEnabled());
        clickOn("#registerBtn");
        verifyThat("User registered successfully!", isVisible());
        clickOn("Aceptar");
        verifyThat("#signInPanel", isVisible());

    }

    /**
     * Test if the is server off
     */
    @Ignore
    @Test
    public void testJ_ErrorConnection() {
        clickOn("#userTxt");
        Random rn = new Random();
        int numR = rn.nextInt(200);

        write("Jamiro" + numR);
        clickOn("#fullNameTxt");
        write("Jamiro Reamie");
        clickOn("#emailTxt");
        write("jamiro@gmail.com");
        clickOn("#passwordTxt");
        write("password");
        clickOn("#repeatPasswordTxt");
        write("password");
        verifyThat("#registerBtn", isEnabled());
        clickOn("#registerBtn");
        verifyThat(".alert", NodeMatchers.isVisible());
    }

    /**
     * Method that is called to clear all fields
     */
    public void clearText() {
        doubleClickOn("#userTxt");
        this.push(CONTROL, A);
        eraseText(1);
        doubleClickOn("#fullNameTxt");
        this.push(CONTROL, A);
        eraseText(1);
        doubleClickOn("#emailTxt");
        this.push(CONTROL, A);
        eraseText(1);
        doubleClickOn("#passwordTxt");
        this.push(CONTROL, A);
        eraseText(1);
        doubleClickOn("#repeatPasswordTxt");
        this.push(CONTROL, A);
        eraseText(1);

    }
}
