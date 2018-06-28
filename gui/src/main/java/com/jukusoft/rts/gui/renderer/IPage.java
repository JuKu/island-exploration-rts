package com.jukusoft.rts.gui.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.time.GameTime;

/**
 * Created by Justin on 15.09.2017.
 */
public interface IPage {

    public int getWidth();

    public int getHeight();

    public void draw (Game game, GameTime time, SpriteBatch batch, float x, float y);

    public void dispose();

}
