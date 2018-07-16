package com.jukusoft.rts.gui.input.impl;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.jukusoft.rts.gui.camera.CameraHelper;

public class ZoomInputProcessor extends InputAdapter {

    protected final CameraHelper camera;

    protected float scrollSpeed = 0.1f;
    protected static final float MIN_ZOOM_VALUE= 0.2f;

    public ZoomInputProcessor (CameraHelper camera) {
        this.camera = camera;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.setZoom(camera.getZoom() + amount * this.scrollSpeed);

        if (camera.getZoom() < MIN_ZOOM_VALUE) {
            camera.setZoom(MIN_ZOOM_VALUE);
        }

        return true;
    }

}
