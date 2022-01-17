package GESREClient.logic;

import signable.Signable;

/**
 * Class that returns an Interface Signable with the SignableImplement
 *
 * @author Daniel Brizuela
 * @version 1.0
 */
public class SignableFactory {

    /**
     * Get a Signable interface with a new SignableImplement
     *
     * @return a new SignableImplement
     */
    public static Signable getSignable() {
        return new SignableImplement();
    }
}
