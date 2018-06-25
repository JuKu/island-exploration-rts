package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;

public class MainMenuScreen implements IScreen {

    protected Stage stage = null;
    protected GameAssetManager assetManager = GameAssetManager.getInstance();
    protected Skin skin = null;
    protected Skin skin2 = null;
    protected ScreenManager<IScreen> screenManager = null;
    protected Pixmap labelColor = null;

    protected static final String bgPath = "data/misc/wallpaper/ocean/Ocean_large.png";
    protected static final String logoPath = "data/misc/wallpaper/pirateship/edited/ship_scaled.png";

    //images
    protected Image screenBG = null;
    protected Image logo = null;

    //label
    protected Label versionLabel = null;

    protected TextButton[] buttons;

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void onStop(Game game) {

    }

    @Override
    public void onResume(Game game) {

    }

    @Override
    public void onPause(Game game) {

    }

    @Override
    public void onResize(int width, int height) {

    }

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return false;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void draw(Game game) {

    }

}
