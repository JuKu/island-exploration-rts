package com.jukusoft.rts.gui.renderer.island;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.island.Island;
import com.jukusoft.rts.core.tiled.TiledLayer;
import com.jukusoft.rts.core.tiled.TiledMapParser;
import com.jukusoft.rts.core.tiled.tileset.TextureTileset;
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

    //map with tileIDs - texture region
    protected IntMap<TextureRegion> tiles = new IntMap<>();

    //list with all layers
    protected ObjectArrayList<LayerRenderer> layerRendererList = new ObjectArrayList<>();
    protected LayerRenderer[] layers = null;//cache array for better performance

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
        //check, if island is visible on map
        if (!this.isInBounds(camera)) {
            return;
        }

        //draw layers
        for (int i = 0; i < this.layers.length; i++) {
            layers[i].draw(game, time, camera, batch);
        }
    }

    public void loadSync () {
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
                    LocalLogger.print("texture: " + texture.value.source + ", width: " + tex.getWidth() + ", height: " + tex.getHeight());

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
                            TextureRegion tileRegion = new TextureRegion(tex, tileX, tileY, texture.value.tilesetTileWidth, texture.value.tilesetTileHeight);

                            //put id to map
                            this.tiles.put(tileID, tileRegion);
                        }
                    }
                });
            } else {
                throw new UnsupportedOperationException("tileset type '" + tileset.getClass().getSimpleName() + "' is not supported yet.");
            }
        });

        this.layers = new LayerRenderer[parser.listLayers().size()];

        int layerID = 0;

        //set cells of layers
        for (TiledLayer layer : parser.listLayers()) {
            LayerRenderer layerRenderer = new LayerRenderer(layer.getWidth(), layer.getHeight(), island.getX(), island.getY(), layer.getWidth(), layer.getHeight(), parser.getTileWidth(), parser.getTileHeight());

            int[] tileIDs = layer.getTileIDs();

            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    //calculate index
                    int index = y * layer.getWidth() + x;

                    //get id of tile to draw
                    int tileID = tileIDs[index];

                    if (tileID == 0) {
                        //we dont have to set transparent cells without tiles
                        continue;
                    }

                    //get texture region of tile
                    TextureRegion region = this.tiles.get(tileID);

                    if (region != null) {
                        layerRenderer.setCell(x, y, new TextureRegion(region.getTexture(), region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight()));
                    } else {
                        LocalLogger.warn("Cannot found tileID on tilesets: " + tileID);
                    }
                }
            }

            //set visible flag
            layerRenderer.setVisible(layer.isVisible());

            this.layerRendererList.add(layerRenderer);
            this.layers[layerID] = layerRenderer;

            layerID++;
        }
    }

    @Override
    public void dispose() {
        this.tiles.clear();
        this.layers = null;

        //dispose layers
        this.layerRendererList.iterator().forEachRemaining(layer -> {
            layer.value.dispose();
        });

        //unload tilesets
        parser.listTilesets().iterator().forEachRemaining(tileset -> {
            if (tileset instanceof TextureTileset) {
                TextureTileset tileset1 = (TextureTileset) tileset;

                //check, if images are loaded
                tileset1.listTextures().iterator().forEachRemaining(texture -> {
                    assetManager.unload(texture.value.source);

                    LocalLogger.print("unload tileset: " + texture.value.source);
                });
            } else {
                throw new UnsupportedOperationException("tileset type '" + tileset.getClass().getSimpleName() + "' is not supported yet.");
            }
        });
    }

    protected boolean isInBounds (CameraHelper camera) {
        //TODO: add code here

        return true;
    }

}
