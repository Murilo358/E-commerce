package com.order.service.utils;

import lombok.Setter;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class I18nUtils {

    private static final ResourceBundle bundle;

    @Setter
    private static Locale locale = Locale.of("pt", "BR");


    static {
        bundle = ResourceBundle.getBundle("i18n", locale);
    }

    public static String getI18nValue(String key){
        return Optional.of(bundle.getString(key)).orElse(key);
    }
}
