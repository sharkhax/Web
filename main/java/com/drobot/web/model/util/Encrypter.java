package com.drobot.web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class Encrypter {

    private static final Logger LOGGER = LogManager.getLogger(Encrypter.class);

    private Encrypter() {
    }

    public static String encrypt(String value) {
        String salt = BCrypt.gensalt(10);
        String hashedValue = BCrypt.hashpw(value, salt);
        LOGGER.log(Level.DEBUG, "Value has been encrypted");
        return hashedValue;
    }

    public static boolean check(String value, String encValue) {
        boolean result = BCrypt.checkpw(value, encValue);
        LOGGER.log(Level.DEBUG, "Value has been checked: " + result);
        return result;
    }
}
