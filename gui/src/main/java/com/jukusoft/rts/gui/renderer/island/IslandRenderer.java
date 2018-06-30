package com.jukusoft.rts.gui.renderer.island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.island.Island;
import com.jukusoft.rts.core.tiled.Orientation;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.renderer.IRenderer;

public class IslandRenderer implements IRenderer {

    protected final TiledMap tiledMap;
    protected final TiledMapRenderer tiledMapRenderer;

    public IslandRenderer (Island island) {
        //get asset manager
        GameAssetManager assetManager = GameAssetManager.getInstance();

        //get assets first
        assetManager.finishLoading(island.getTmxPath());

        this.tiledMap = assetManager.get(island.getTmxPath(), TiledMap.class);

        if (island.getOrientation() == Orientation.ISOMETRIC) {
            this.tiledMapRenderer = new IsometricTiledMapRenderer(this.tiledMap);
        } else if (island.getOrientation() == Orientation.ORTHOGONAL) {
            this.tiledMapRenderer = new OrthoCachedTiledMapRenderer(this.tiledMap);
        } else {
            throw new UnsupportedOperationException("Orientation '" + island.getOrientation().name() + "' isnt supported yet.");
        }

        LocalLogger.print("IslandRenderer: tiled map was loaded successfully!");
    }

    @Override
    public void update(Game game, GameTime time) {

    }

    @Override
    public void draw(Game game, GameTime time, CameraHelper camera, SpriteBatch batch) {
        tiledMapRenderer.setView(camera.getOriginalCamera());
        tiledMapRenderer.render();
    }

    @Override
    public void dispose() {
        //
    }

    protected boolean isInBounds (CameraHelper camera) {
        return true;
    }

}
