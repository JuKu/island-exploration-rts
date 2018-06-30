package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.core.utils.Platform;
import com.jukusoft.rts.core.version.Version;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.screens.Screens;
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
    protected static final String SOUND_PATH = "data/sound/menu_selection_click/menu_selection_click_16bit.wav";
    protected static final String MUSIC_PATH = "data/music/mainmenu/Song_of_the_Sea.ogg";

    //images
    protected Image screenBG = null;
    protected Image screenBG2 = null;
    protected Image shipBG = null;

    //label
    protected Label versionLabel = null;

    //sounds
    protected Sound hoverSound = null;

    //music
    protected Music music = null;

    protected TextButton[] buttons;

    protected float yOffset = 0;
    protected float speedY = 10;
    protected float maxOffset = 10;

    //how fast is background image scrolling?
    protected float skyBoxSpeed = 100;

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        this.screenManager = screenManager;

        //create skin
        String atlasFile = "data/misc/skins/default/uiskin.atlas";
        String jsonFile = "data/misc/skins/default/uiskin.json";
        LocalLogger.print("create skin, atlas file: " + atlasFile + ", json file: " + jsonFile);
        this.skin = SkinFactory.createSkin(jsonFile);

        this.skin2 = SkinFactory.createSkin("./data/misc/skins/libgdx/uiskin.json");

        //add tiled map loader
        this.assetManager.getLibGDXAssetManager().setLoader(TiledMap.class, new TmxMapLoader(new AbsoluteFileHandleResolver()));

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
        assetManager.load(SOUND_PATH, Sound.class);
        assetManager.load(MUSIC_PATH, Music.class);

        assetManager.finishLoading();

        Texture bgTexture = assetManager.get(BG_PATH, Texture.class);
        this.screenBG = new Image(bgTexture);
        this.screenBG2 = new Image(bgTexture);

        Texture logoTexture = assetManager.get(SHIP_PATH, Texture.class);
        this.shipBG = new Image(logoTexture);

        //add widgets to stage
        stage.addActor(screenBG);
        stage.addActor(screenBG2);
        stage.addActor(shipBG);

        this.hoverSound = assetManager.get(SOUND_PATH, Sound.class);

        buttons = new TextButton[10];

        //create buttons
        TextButton newGameBtn = new TextButton(I.tr("New Game"), this.skin);
        newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //enter new game screen
                Platform.runOnUIThread(() -> screenManager.leaveAllAndEnter(Screens.NEW_GAME));
            }
        });
        stage.addActor(newGameBtn);
        buttons[0] = newGameBtn;

        TextButton newCampaignGameBtn = new TextButton(I.tr("Campaign"), this.skin);
        stage.addActor(newCampaignGameBtn);
        buttons[1] = newCampaignGameBtn;

        TextButton loadGameBtn = new TextButton(I.tr("Load Game"), this.skin);
        stage.addActor(loadGameBtn);
        buttons[2] = loadGameBtn;

        TextButton optionsBtn = new TextButton(I.tr("Options"), this.skin);
        stage.addActor(optionsBtn);
        buttons[3] = optionsBtn;

        TextButton creditsBtn = new TextButton(I.tr("Credits"), this.skin);
        stage.addActor(creditsBtn);
        buttons[4] = creditsBtn;

        TextButton quitBtn = new TextButton(I.tr("Quit Game"), this.skin);
        quitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                LocalLogger.print(I.tr("Start new game selected."));

                Platform.runOnUIThread(() -> System.exit(0));
            }
        });
        stage.addActor(quitBtn);
        buttons[5] = quitBtn;

        //add hover sound
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                buttons[i].addListener(new ClickListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        super.enter(event, x, y, pointer, fromActor);

                        //play sound
                        hoverSound.play(1f, 1f, 0f);
                    }
                });
            }
        }

        //get client version
        Version version = Version.getInstance();

        this.versionLabel = new Label("Version: " + version.getFullVersion(), this.skin2);

        //set label background color
        labelColor = new Pixmap((int) this.versionLabel.getWidth(), (int) this.versionLabel.getHeight(), Pixmap.Format.RGBA8888);
        labelColor.setColor(Color.valueOf("#001f3f"));//#36581a, #0074D9, #001f3f
        labelColor.fill();
        this.versionLabel.getStyle().background = new Image(new Texture(labelColor)).getDrawable();
        stage.addActor(versionLabel);

        this.music = assetManager.get(MUSIC_PATH, Music.class);

        //start music
        this.music.play();

        //loop music
        this.music.setLooping(true);

        //set input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onPause(Game game) {
        assetManager.unload(BG_PATH);
        assetManager.unload(SHIP_PATH);
        assetManager.unload(SOUND_PATH);

        //stop music
        this.music.stop();
        this.music = null;

        assetManager.unload(MUSIC_PATH);

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
        screenBG2.invalidate();

        screenBG.setX(0);
        screenBG.setY(0);
        screenBG2.setX(screenBG.getWidth());
        screenBG2.setY(0);

        shipBG.setX(0);
        shipBG.setY(0);
        shipBG.invalidate();

        float startX = shipBG.getWidth() - 320;
        float startY = shipBG.getHeight() - 60;

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                buttons[i].setWidth(300);

                buttons[i].setX(startX);
                buttons[i].setY(startY - i * 50);

                buttons[i].invalidate();
            }
        }

        versionLabel.setX(20);
        versionLabel.setY(20);
    }

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return true;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {
        //move ship up and down (like waves)
        if (this.yOffset > maxOffset || this.yOffset < -maxOffset) {
            this.speedY = -speedY;
        }

        float skyBoxXPos = this.screenBG.getX() - GameTime.getInstance().getDelta() * this.skyBoxSpeed;

        if (skyBoxXPos < -this.screenBG.getWidth()) {
            skyBoxXPos += this.screenBG.getWidth();
        }

        this.screenBG.setX(skyBoxXPos);
        this.screenBG2.setX(skyBoxXPos + this.screenBG2.getWidth());

        this.yOffset += this.speedY * GameTime.getInstance().getDelta();

        shipBG.setY(-20 + this.yOffset);
    }

    @Override
    public void draw(Game game) {
        //show the main menu screen
        stage.act();
        stage.draw();
    }

}
