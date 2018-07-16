package com.jukusoft.rts.gui.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class InputManager {

    //singleton instance
    protected static final InputManager instance = new InputManager();

    protected InputMultiplexer inputMultiplexer = null;

    public InputManager () {
        this.inputMultiplexer = new InputMultiplexer();

        this.setGdxInputProcessor();
    }

    public void setGdxInputProcessor () {
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    public static InputManager getInstance () {
        return instance;
    }

}
