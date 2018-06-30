package com.jukusoft.rts.gui.renderer.island;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.utils.IntMap;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.island.Island;
import com.jukusoft.rts.core.tiled.TiledMapParser;
import com.jukusoft.rts.core.tiled.tileset.TextureTileset;
import com.jukusoft.rts.core.tiled.tileset.TsxTileset;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.renderer.IRenderer;

public class IslandRenderer implements IRenderer {

    //tmx format uses some flags for tileIDs to check flipping of tiles
    protected static final int FLAG_FLIP_HORIZONTALLY = 0x80000000;
    protected static final int FLAG_FLIP_VERTICALLY = 0x40000000;
    protected static final int FLAG_FLIP_DIAGONALLY = 0x20000000;
    protected static final int MASK_CLEAR = 0xE0000000;

    //asset manager
    protected GameAssetManager assetManager = GameAssetManager.getInstance();

    //island
    protected final Island island;
    protected final TiledMapParser parser;

    protected IntMap<TextureRegion> tiles = new IntMap<>();

    protected BatchTiledMapRenderer renderer = null;

    //https://gamedevelopment.tutsplus.com/tutorials/parsing-and-rendering-tiled-tmx-format-maps-in-your-own-game-engine--gamedev-3104

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
        /*this.renderer = new BatchTiledMapRenderer() {
            @Override
            public void renderTileLayer(TiledMapTileLayer layer) {
                layer.getCell(0, 0).getTile().getTextureRegion()
            }
        }*/

        //check, required assets
        parser.listTilesets().iterator().forEachRemaining(tileset -> {
            if (tileset instanceof TextureTileset) {
                TextureTileset tileset1 = (TextureTileset) tileset;

                //check, if images are loaded
                tileset1.listTextures().iterator().forEachRemaining(texture -> {
                    LocalLogger.print("finish loading of asset: " + texture.value.source);
                    assetManager.finishLoading(texture.value.source);

                    int firstTileID = texture.value.firstTileID;

                    //get texture
                    Texture tex = assetManager.get(texture.value.source, Texture.class);

                    int cols = texture.value.width / texture.value.tilesetTileWidth;

                    for (int y = 0; y < texture.value.height / texture.value.tilesetTileHeight; y++) {
                        for (int x = 0; x < cols; x++) {
                            //calculate tileID
                            int tileID = y * cols + x + firstTileID;

                            //clear flip mask
                            tileID = tileID & ~MASK_CLEAR;

                            int tileX = x * texture.value.tilesetTileWidth;
                            int tileY = y * texture.value.tilesetTileHeight;

                            //create texture region
                            TextureRegion tileRegion = new TextureRegion(tex, x, y, tileX, tileY);

                            //put id to map
                            this.tiles.put(tileID, tileRegion);

                            LocalLogger.print("cell: (" + x + ", " + y + "): " + tileID + " xPos: " + tileX + ", yPos: " + tileY);
                        }
                    }

                    //TODO: calculate tileIDs and fill tiles map
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
