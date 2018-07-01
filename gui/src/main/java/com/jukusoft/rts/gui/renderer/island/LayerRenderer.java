package com.jukusoft.rts.gui.renderer.island;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.renderer.IRenderer;

public class LayerRenderer implements IRenderer {

    protected TextureRegion[][] cells;
    protected boolean visible = true;

    //position
    protected float xPos = 0;
    protected float yPos = 0;

    //width & height in tiles
    protected final int width;
    protected final int height;

    //width & height of tiles in pixel
    protected final int tileWidth;
    protected final int tileHeight;

    public LayerRenderer (int widthInTiles, int heightInTiles, float x, float y, int width, int height, int tileWidth, int tileHeight) {
        this.cells = new TextureRegion[widthInTiles][heightInTiles];

        this.xPos = x;
        this.yPos = y;

        this.width = width;
        this.height = height;

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    @Override
    public void update(Game game, GameTime time) {
        //we dont need this method here
    }

    @Override
    public void draw(Game game, GameTime time, CameraHelper camera, SpriteBatch batch) {
        //we dont need to draw invisible layers
        if (!this.visible) {
            return;
        }

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                //get cell
                TextureRegion region = getCell(x, y);

                if (region == null) {
                    //transparent cell without tile
                    continue;
                }

                float tileXPos = x * this.tileWidth + this.xPos;
                float tileYPos = (this.height - 1 - y) * this.tileHeight + this.yPos;

                //render tile
                batch.draw(region, tileXPos, tileYPos, this.tileWidth, this.tileHeight);
            }
        }
    }

    @Override
    public void dispose() {
        this.cells = null;
    }

    public void setCell (int x, int y, TextureRegion region) {
        this.cells[x][y] = region;
    }

    public TextureRegion getCell (int x, int y) {
        return this.cells[x][y];
    }

    public boolean isVisible () {
        return visible;
    }

    public void setVisible (boolean visible) {
        this.visible = visible;
    }

}
