package com.jukusoft.rts.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.version.Version;
import com.jukusoft.rts.game.RTSGame;
import com.jukusoft.rts.gui.GameGUI;

import java.io.IOException;

public class DesktopLauncher {

    public static void main (String[] args) throws IOException {
        //set version information
        Version.setInstance(new Version(DesktopLauncher.class));

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        //load window config
        WindowConfig windowConfig = new WindowConfig("./config/window.cfg");
        windowConfig.fillConfig(config);

        // start game
        Game game = new RTSGame();
        new Lwjgl3Application(new GameGUI(game), config);

        System.exit(0);
    }

}
