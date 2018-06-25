package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.version.Version;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.utils.SkinFactory;
import com.teamunify.i18n.I;

public class MainMenuScreen implements IScreen {

    protected Stage stage = null;
    protected GameAssetManager assetManager = GameAssetManager.getInstance();
    protected Skin skin = null;
    protected Skin skin2 = null;
    protected ScreenManager<IScreen> screenManager = null;
    protected Pixmap labelColor = null;

    protected static final String BG_PATH = "data/misc/wallpaper/ocean/Ocean_large.png";
    protected static final String SHIP_PATH = "data/misc/wallpaper/pirateship/edited/ship_scaled.png";

    //images
    protected Image screenBG = null;
    protected Image shipBG = null;

    //label
    protected Label versionLabel = null;

    protected TextButton[] buttons;

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        this.screenManager = screenManager;

        //create skin
        String atlasFile = "data/misc/skins/default/uiskin.atlas";
        String jsonFile = "data/misc/skins/default/uiskin.json";
        LocalLogger.print("create skin, atlas file: " + atlasFile + ", json file: " + jsonFile);
        this.skin = SkinFactory.createSkin(jsonFile);

        this.skin2 = SkinFactory.createSkin("./data/misc/skins/libgdx/uiskin.json");

        //create UI stage
        this.stage = new Stage();
    }

    @Override
    public void onStop(Game game) {
        this.skin.dispose();
        this.skin = null;

        this.skin2.dispose();
        this.skin2 = null;
    }

    @Override
    public void onResume(Game game) {
        //load texures
        assetManager.load(BG_PATH, Texture.class);
        assetManager.load(SHIP_PATH, Texture.class);

        assetManager.finishLoading();

        Texture bgTexture = assetManager.get(BG_PATH, Texture.class);
        this.screenBG = new Image(bgTexture);

        Texture logoTexture = assetManager.get(SHIP_PATH, Texture.class);
        this.shipBG = new Image(logoTexture);

        //add widgets to stage
        stage.addActor(screenBG);
        stage.addActor(shipBG);

        buttons = new TextButton[10];

        //create buttons
        TextButton newGameBtn = new TextButton(I.tr("New Game"), this.skin);
        stage.addActor(newGameBtn);
        buttons[0] = newGameBtn;

        //get client version
        Version version = Version.getInstance();

        this.versionLabel = new Label("Version: " + version.getFullVersion(), this.skin2);

        //set label background color
        labelColor = new Pixmap((int) this.versionLabel.getWidth(), (int) this.versionLabel.getHeight(), Pixmap.Format.RGBA8888);
        labelColor.setColor(Color.valueOf("#36581a"));
        labelColor.fill();
        this.versionLabel.getStyle().background = new Image(new Texture(labelColor)).getDrawable();
        stage.addActor(versionLabel);

        //set input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onPause(Game game) {
        assetManager.unload(BG_PATH);
        assetManager.unload(SHIP_PATH);

        labelColor.dispose();
    }

    @Override
    public void onResize(int width, int height) {
        System.out.println("onResize: " + width + "x" + height);

        stage.getViewport().setScreenWidth(width);
        stage.getViewport().setScreenHeight(height);
        stage.getViewport().update(width, height, true);
        stage.getViewport().setScreenPosition(0, 0);
        stage.getViewport().setScreenSize(width, height);

        //make the background fill the screen
        screenBG.invalidate();

        shipBG.setX(0);
        shipBG.setY(0);
        shipBG.invalidate();

        versionLabel.setX(20);
        versionLabel.setY(20);
    }

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return true;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void draw(Game game) {
        //show the main menu screen
        stage.act();
        stage.draw();
    }

}
