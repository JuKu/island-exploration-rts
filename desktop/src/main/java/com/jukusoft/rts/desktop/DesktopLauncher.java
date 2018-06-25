package com.jukusoft.rts.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.version.Version;
import com.jukusoft.rts.game.RTSGame;
import com.jukusoft.rts.gui.GameGUI;
import com.teamunify.i18n.I;
import com.teamunify.i18n.escape.EscapeFunction;
import com.teamunify.i18n.settings.GlobalLanguageSettingsProvider;
import com.teamunify.i18n.settings.LanguageSetting;

import java.io.IOException;

public class DesktopLauncher {

    public static void main (String[] args) throws IOException {
        //set version information
        Version.setInstance(new Version(DesktopLauncher.class));

        //initialize i18n localization
        initI18N();

        //set language
        I.setLanguage("en");

        //https://github.com/awkay/easy-i18n/wiki/Getting-Started
        //https://github.com/awkay/easy-i18n/wiki/Gettext-tutorial
        LocalLogger.print(I.tr("Startup game now."));

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        //load window config
        WindowConfig windowConfig = new WindowConfig("./config/window.cfg");
        windowConfig.fillConfig(config);

        // start game
        Game game = new RTSGame();
        new Lwjgl3Application(new GameGUI(game), config);

        System.exit(0);
    }

    protected static void initI18N() {
        //tell the system where to find translations
        LanguageSetting.translationPackage = "com.jukusoft.translations";

        //use the same settings for all threads...
        I.setLanguageSettingsProvider(new GlobalLanguageSettingsProvider());

        //do not escape translated characters at all
        I.setEscapeFunction(EscapeFunction.NoEscape);
    }

}
