package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.map.MapManager;
import com.jukusoft.rts.core.map.MapMeta;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;

import java.util.List;

public class NewGameScreen extends GUIScreen {

    @Override
    protected void start(Game game, ScreenManager<IScreen> screenManager) {

    }

    @Override
    protected void stop(Game game) {

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

        final SelectBox<Object> sb = new SelectBox<Object>(skin);
        sb.setItems(blob);

        //add change listener
        sb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(((Label) sb.getSelected()).getText());
            }
        });

        sb.setWidth(200);

        //for easier handling of Widgets
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(sb);
        stage.addActor(table);
    }

    @Override
    protected void pause(Game game) {

    }

    @Override
    protected void resize(int width, int height) {

    }

}
