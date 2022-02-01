package seguridad;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Clase que contiene el metodo para cifrar asimetricamente la contraseña que se
 * envia por la red al servidor.
 *
 * Usa el algoritmo RSA, el modo ECB y el padding PKCS1Padding.
 *
 * @author Nerea Aranguren
 */
public class Seguridad {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger("seguridad.CifradoAsimetrico");

    /**
     * Atributo que guarda la ruta de la clave publica del archivo de
     * propiedades.
     */
    private final static String PUBLIC_KEY_PATH = ResourceBundle.getBundle("archivos.ruta").getString("PUBLIC_KEY");

    /**
     * Metodo que cifra la contraseña del usuario con una clave publica.
     *
     *
     * @param contraseña La contraseña del usuario.
     * @return Un string con la contraseña cifrada en hexadecimal.
     */
    public String cifrarConClavePublica(String contraseña) {
        byte[] encodedMessage = null;

        try {
            LOGGER.info("CifradoAsimetrico: Cifrando con clave publica");

            byte fileKey[] = null;
            fileKey = getPublicFileKey();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            encodedMessage = cipher.doFinal(contraseña.getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOGGER.severe(e.getMessage());
        }
        return byteToHexadecimal(encodedMessage);
    }

    /**
     * Metodo que convierte un array de bytes en un string hexadecimal.
     *
     * @param bytes Array de bytes que llega.
     * @return Un String con valor hexadecimal.
     */
    private String byteToHexadecimal(byte[] bytes) {
        LOGGER.info("CifradoAsimetrico: Convirtiendo bytes a hexadecimal");

        String HEX = "";
        for (int i = 0; i < bytes.length; i++) {
            String h = Integer.toHexString(bytes[i] & 0xFF);
            if (h.length() == 1) {
                HEX += "0";
            }
            HEX += h;
        }
        return HEX.toUpperCase();
    }

    /**
     * Obtiene la clave publica de un archivo.
     *
     * @return La clave publica en array de bytes.
     */
    public byte[] getPublicFileKey() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            LOGGER.info("CifradoAsimetrico: Leyendo archivo");

            InputStream inputStream = Seguridad.class.getResourceAsStream(PUBLIC_KEY_PATH);
            byte[] buffer = new byte[1024];
            int len;
            // read bytes from the input stream and store them in buffer
            while ((len = inputStream.read(buffer)) != -1) {
                // write bytes from the buffer into output stream
                os.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return os.toByteArray();
    }
}