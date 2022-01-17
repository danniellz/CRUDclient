package GESREClient.logic;

import exceptions.ConnectionException;
import exceptions.DatabaseNotFoundException;
import exceptions.IncorrectPasswordException;
import exceptions.MaxConnectionException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Accion;
import message.Message;
import signable.Signable;
import user.User;

/**
 * Class that implements the Signable Interface to request a SignIn or SingUp
 * and connect with a server project
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public class SignableImplement implements Signable {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(SignableImplement.class.getName());

    /**
     * Request a Sign In
     *
     * @param user the user object containing the user data
     * @throws exceptions.UserNotFoundException if the user doesn't exist, error
     * @throws exceptions.IncorrectPasswordException if the password is
     * incorrect, error
     * @throws exceptions.UserAlreadyExistException if the user already exist,
     * error message
     * @throws exceptions.DatabaseNotFoundException if an error occurred with
     * the DB, error message
     * @throws exceptions.ConnectionException if an error occurred between the
     * client an server, error message
     * @throws exceptions.MaxConnectionException if there is no more thread
     * available, error message
     * @return the User object with all data form DB
     */
    @Override
    public User signIn(User user) throws UserNotFoundException, IncorrectPasswordException, UserAlreadyExistException, DatabaseNotFoundException, ConnectionException, MaxConnectionException {
        try {
            LOG.log(Level.INFO, "Starting SignIn Process for {0}...", user.getLogin());
            Message msg = new Message();
            msg.setUser(user);
            msg.setAccion(Accion.SIGNIN);

            user = serverConnection(msg);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignableImplement.class.getName()).log(Level.SEVERE, "Class not found error in signIn - SignableImplement", ex);
        }

        return user;
    }

    /**
     * Request a Sign Up
     *
     * @param user the user object containing the user data
     * @throws exceptions.UserAlreadyExistException if the user already exist,
     * error message the DB, error message
     * @throws exceptions.DatabaseNotFoundException if there is an error with
     * the Database, error
     * @throws exceptions.ConnectionException if an error occurred between the
     * client an server, error message available, error message
     * @throws exceptions.MaxConnectionException if the max coneection is
     * reached, error
     * @throws exceptions.IncorrectPasswordException if the password is
     * incorrect, error
     * @throws exceptions.UserNotFoundException if the user doesn't exist, error
     * @return a user object
     */
    @Override
    public User signUp(User user) throws UserAlreadyExistException, DatabaseNotFoundException, ConnectionException, MaxConnectionException, IncorrectPasswordException, UserNotFoundException {
        try {
            LOG.log(Level.INFO, "Starting SignUp Process for {0}...", user.getLogin());
            Message msg = new Message();
            msg.setUser(user);
            msg.setAccion(Accion.SIGNUP);

            user = serverConnection(msg);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignableImplement.class.getName()).log(Level.SEVERE, "Class not found error in signUp - SignableImplement", ex);
        }

        return user;

    }

    /**
     * Connect with the server project sending a user object and the type of
     * request (SignIn or SignUp)
     *
     * @param message the message class contains the user and the request type
     * @throws exceptions.ConnectionException if an error occurred between the
     * client an server, error message
     * @throws exceptions.UserNotFoundException if the user doesn't exist, error
     * @throws exceptions.IncorrectPasswordException if the password is incorrect, error
     * @throws exceptions.UserAlreadyExistException if the user already exist,
     * error message
     * @throws exceptions.DatabaseNotFoundException if an error occurred with
     * the DB, error message
     * @throws exceptions.MaxConnectionException if there is no more thread
     * available, error message
     * @throws java.lang.ClassNotFoundException general exception is the class
     * do not exist
     * @return A message with the User object containing the data from DB
     */
    public User serverConnection(Message message) throws ConnectionException, UserNotFoundException, IncorrectPasswordException, UserAlreadyExistException, DatabaseNotFoundException, ClassNotFoundException, MaxConnectionException {
        //local host, data can be change in the configuration file (config.properties)
        final int PORT = Integer.parseInt(ResourceBundle.getBundle("signupsigninclient.file/config").getString("PORT"));
        final String SERVER = ResourceBundle.getBundle("signupsigninclient.file/config").getString("SERVER");
        ObjectInputStream inO;
        ObjectOutputStream outO;
        Message mes = null;
        try {
            LOG.info("Initializing Client...");
            try {
                //Connect with Server
                Socket clientSc = new Socket(SERVER, PORT);
                LOG.info("Client > Initialized");
                //Send message
                outO = new ObjectOutputStream(clientSc.getOutputStream());
                outO.writeObject(message);

                //Message sent log
                if (message.getAccion() == Accion.SIGNIN) {
                    LOG.info("Client > SIGN IN REQUEST SENT");
                }
                if (message.getAccion() == Accion.SIGNUP) {
                    LOG.info("Client > SIGN UP REQUEST SENT");
                }

                //Receive message
                inO = new ObjectInputStream(clientSc.getInputStream());
                mes = (Message) inO.readObject();

                //Receive the Accion message from Server to know how went the process
                switch (mes.getAccion()) {
                    case USERNOTFOUND:
                        throw new UserNotFoundException();
                    case INVALIDPASSWORD:
                        throw new IncorrectPasswordException();
                    case USERALREADYEXIST:
                        throw new UserAlreadyExistException();
                    case DATABASENOTFOUND:
                        throw new DatabaseNotFoundException();
                    case CONNECTIONNOTFOUND:
                        throw new ConnectionException();
                    case MAXCONNECTION:
                        throw new MaxConnectionException();
                    case OK:
                        LOG.info("Process gone Well!");
                        break;
                }

                LOG.info("MESSAGED RECEIVED FROM Server For " + mes.getUser().getFullName() + " With Result " + mes.getAccion().toString());

                //Close Channels
                inO.close();
                outO.close();
            } catch (IOException ex) {
                throw new ConnectionException();
            }
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "An error occurred in serverConnection", ex);
        }
        return mes.getUser();
    }
}
