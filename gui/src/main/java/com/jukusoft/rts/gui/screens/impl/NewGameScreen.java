package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.MapManager;
import com.jukusoft.rts.core.map.MapMeta;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.screens.Screens;
import com.teamunify.i18n.I;

import java.io.IOException;
import java.util.List;

public class NewGameScreen extends GUIScreen {

    protected TextButton startButton = null;

    @Override
    protected void start(Game game, ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    protected void stop(Game game) {
        //
    }

    @Override
    protected void resume(Game game) {
        //https://stackoverflow.com/questions/17298864/drop-down-in-libgdx

        //get available maps
        List<MapMeta> maps = MapManager.getInstance().listFreeMaps();

        // Set up the SelectionBox with content
        String[] blob = new String[maps.size()];

        for (int i = 0; i < blob.length; i++) {
            blob[i] = maps.get(i).getName();
        }

        final SelectBox<Object> sb = new SelectBox<>(skin);
        sb.setItems(blob);

        //add change listener
        sb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LocalLogger.print(((Label) sb.getSelected()).getText().toString());
            }
        });

        //for easier handling of Widgets
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(sb);
        stage.addActor(table);

        //create start button
        this.startButton = new TextButton(I.tr("Start Game"), this.skin);
        this.startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //get selected item
                int index = sb.getSelectedIndex();
                MapMeta selectedMap = maps.get(index);
                String selectedMapName = selectedMap.getName();

                LocalLogger.print(I.trf("select map: {0}", selectedMapName));

                //start game
                try {
                    game.reset();
                    game.createNewGame(selectedMap);
                } catch (IOException e) {
                    LocalLogger.printStacktrace(e);
                    System.exit(1);
                }

                LocalLogger.print("enter load game state.");

                //go to next screen
                screenManager.leaveAllAndEnter(Screens.LOAD_GAME);
            }
        });
        stage.addActor(this.startButton);
    }

    @Override
    protected void pause(Game game) {
        stage.clear();
    }

    @Override
    protected void resize(int width, int height) {
        this.startButton.setWidth(400);

        this.startButton.setX(screenBG.getWidth() / 2 - 200);
        this.startButton.setY(screenBG.getHeight() / 2 - 100);
    }

}
