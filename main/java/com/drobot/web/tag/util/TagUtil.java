package com.drobot.web.tag.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class TagUtil {

    private static final String LOCALE_SPLIT_REGEX = "_";
    private static final String RESOURCE_BUNDLE_NAME = "locale/pagecontent";

    private TagUtil() {
    }

    public static ResourceBundle getMessageBundle(String stringLocale) {
        String[] localeArr = stringLocale.split(LOCALE_SPLIT_REGEX);
        Locale locale = new Locale(localeArr[0], localeArr[1]);
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
        return bundle;
    }
}
