package com.jukusoft.rts.gui.renderer.island;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.island.Island;
import com.jukusoft.rts.core.tiled.TiledMapParser;
import com.jukusoft.rts.core.tiled.tileset.TextureTileset;
import com.jukusoft.rts.core.tiled.tileset.Tileset;
import com.jukusoft.rts.core.tiled.tileset.TsxTileset;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.renderer.IRenderer;

public class IslandRenderer implements IRenderer {

    //asset manager
    protected GameAssetManager assetManager = GameAssetManager.getInstance();

    //island
    protected final Island island;
    protected final TiledMapParser parser;

    public IslandRenderer (Island island, TiledMapParser parser) {
        this.island = island;
        this.parser = parser;
    }

    @Override
    public void update(Game game, GameTime time) {
        //
    }

    @Override
    public void draw(Game game, GameTime time, CameraHelper camera, SpriteBatch batch) {
        //
    }

    public void loadSync () {
        //check, required assets
        parser.listTilesets().iterator().forEachRemaining(tileset -> {
            if (tileset instanceof TextureTileset) {
                //check, if images are loaded
                ((TextureTileset) tileset).listTextures().iterator().forEachRemaining(texture -> {
                    LocalLogger.print("finish loading of asset: " + texture.value.source);
                    assetManager.finishLoading(texture.value.source);
                });
            } else if (tileset instanceof TsxTileset) {
                //
            } else {
                throw new UnsupportedOperationException("tileset type '" + tileset.getClass().getSimpleName() + "' is not supported yet.");
            }
        });
    }

    @Override
    public void dispose() {
        //
    }

    protected boolean isInBounds (CameraHelper camera) {
        return true;
    }

}
