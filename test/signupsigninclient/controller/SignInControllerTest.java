package signupsigninclient.controller;

import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import GESRE.aplication.GESREClient;

/**
 * Test for the SignIn Window IT
 *
 * @author Jonathan Viñan, Daniel Brizuela
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTest extends ApplicationTest {

    /**
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(GESREClient.class);
    }

    /**
     * Test the initial state of the window
     */
    //@Ignore
    @Test
    public void testA_start() {
        verifyThat("#userTxt", hasText(""));
        verifyThat("#passwordTxt", hasText(""));
        verifyThat("#loginBtn", isEnabled());
    }

    /**
     * Test if the SignUp window is visible pressing the Hyperlink
     */
    //@Ignore
    @Test
    public void testB_VisibleWindowSignUp() {
        clickOn("#signUpHl");
        verifyThat("#signUpPanel", isVisible());
    }

    /**
     * test the user and password limit characters
     */
    //@Ignore
    @Test
    public void testC_UserAndPassword25Characters() {
        clickOn("#signInHl");
        verifyThat("#signInPanel", isVisible());
        clickOn("#userTxt");
        for (int i = 0; i < 26; i++) {
            write("a");
        }
        verifyThat("#errorLbl", isEnabled());
        clickOn("#passwordTxt");
        for (int i = 0; i < 26; i++) {
            write("a");
        }
        doubleClickOn("#userTxt");
        eraseText(1);
        doubleClickOn("#passwordTxt");
        eraseText(1);
    }

    /**
     * Test if there is empry fields
     */
    //@Ignore
    @Test
    public void testD_EmptyFields() {
        //User field empty
        clickOn("#userTxt");
        write("Aloy");
        clickOn("#loginBtn");
        verifyThat("#errorLbl", LabeledMatchers.hasText("The fields have to be filled"));
        doubleClickOn("#userTxt");
        eraseText(1);

        //Password field empty
        clickOn("#passwordTxt");
        write("Aloy");
        clickOn("#loginBtn");
        verifyThat("#errorLbl", LabeledMatchers.hasText("The fields have to be filled"));
        doubleClickOn("#passwordTxt");
        eraseText(1);

        //All empty
        clickOn("#loginBtn");
        verifyThat("#errorLbl", LabeledMatchers.hasText("The fields have to be filled"));
    }

    /**
     * Test if the user password is wrong
     */
    //@Ignore
    @Test
    public void testE_PasswordError() {
        clickOn("#userTxt");
        write("pepeUser");
        clickOn("#passwordTxt");
        write("Aloy12");
        clickOn("#loginBtn");
        verifyThat("#errorLbl", LabeledMatchers.hasText("Incorrect Password"));
        doubleClickOn("#userTxt");
        eraseText(1);
        doubleClickOn("#passwordTxt");
        eraseText(1);
    }

    /**
     * Test ig the user doesn't exist in the database
     */
    //@Ignore
    @Test
    public void testF_UserError() {
        clickOn("#userTxt");
        write("Aloy");
        clickOn("#passwordTxt");
        write("aloy10");
        clickOn("#loginBtn");
        verifyThat("#errorLbl", LabeledMatchers.hasText("Incorrect Username"));
        doubleClickOn("#userTxt");
        eraseText(1);
        doubleClickOn("#passwordTxt");
        eraseText(1);
    }

    /**
     * Test if the user can successfully log in
     */
    //@Ignore
    @Test
    public void testG_LoginUserConnection() {
        clickOn("#userTxt");
        write("pepeUser");
        clickOn("#passwordTxt");
        write("123456");
        clickOn("#loginBtn");
        verifyThat("#logOutPanel", isVisible());
    }

    /**
     * Test if the is server off
     */
    @Ignore
    @Test
    public void testH_ErrorConection() {
        clickOn("#userTxt");
        write("pepeUser");
        clickOn("#passwordTxt");
        write("123456");
        clickOn("#loginBtn");
        verifyThat("Server Connection Error", isVisible());
    }

    /**
     * Test if database doesn't exist or if there is a problem with it
     */
    @Ignore
    @Test
    public void testI_DatabaseError() {
        clickOn("#userTxt");
        write("pepeUser");
        clickOn("#passwordTxt");
        write("123456");
        clickOn("#loginBtn");
        verifyThat("Database Connection Error", isVisible());
    }

    /**
     * Test if the maximum number of connection is reached
     */
    @Ignore
    @Test
    public void testJ_MaxConnectionReached() {
        clickOn("#userTxt");
        write("pepeUser");
        clickOn("#passwordTxt");
        write("123456");
        clickOn("#loginBtn");
        verifyThat("Max Connection Reached", isVisible());
    }

}
