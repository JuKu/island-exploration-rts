package com.jukusoft.rts.gui.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.gui.camera.CameraHelper;

/**
 * Created by Justin on 14.09.2017.
 */
public interface IRenderer {

    /**
     * update renderer
     */
    public void update(Game game, GameTime time);

    /**
     * draw renderer
     */
    public void draw(Game game, GameTime time, CameraHelper camera, SpriteBatch batch);

}
