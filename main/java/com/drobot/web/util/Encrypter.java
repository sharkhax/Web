package com.drobot.web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Class used to encrypt and check already encrypted passwords.
 *
 * @author Vladislav Drobot
 */
public class Encrypter {

    private static final Logger LOGGER = LogManager.getLogger(Encrypter.class);

    private Encrypter() {
    }

    /**
     * Encrypts a given password.
     *
     * @param value String object of the password to be encrypted.
     * @return String object of encrypted password.
     */
    public static String encrypt(String value) {
        String salt = BCrypt.gensalt(10);
        String hashedValue = BCrypt.hashpw(value, salt);
        LOGGER.log(Level.DEBUG, "Value has been encrypted");
        return hashedValue;
    }

    /**
     * Checks if an unencrypted password equals to an encrypted one.
     *
     * @param value    String object of unencrypted password.
     * @param encValue String object of encrypted password.
     * @return true if the unencrypted password equals to the encrypted one, false otherwise.
     */
    public static boolean check(String value, String encValue) {
        boolean result = BCrypt.checkpw(value, encValue);
        LOGGER.log(Level.DEBUG, "Value has been checked: " + result);
        return result;
    }
}
