package com.jukusoft.rts.core.i18n;

import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

import java.util.Locale;

public class Translator {

    protected static final I18n i18n = I18nFactory.getI18n(Translator.class, Locale.GERMAN);

    public static I18n get () {
        return i18n;
    }

}
