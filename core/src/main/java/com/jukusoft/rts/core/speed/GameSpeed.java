package com.jukusoft.rts.core.speed;

import com.jukusoft.rts.core.utils.Platform;

public class GameSpeed {

    protected float speed = 1f;

    protected static final GameSpeed instance = new GameSpeed();

    protected GameSpeed () {
        //
    }

    /**
    * get game speed
     *
     * 1 means normal speed, 2 means twice as fast
     *
     * @return game speed as float
    */
    public float getSpeed() {
        return speed;
    }

    /**
    * set game speed
     *
     * this method is thread safe, because updating will be executed in main thread
     *
     * @param speed game speed, 1 is normal, 2 means twice as fast
    */
    public void setSpeed (final float speed) {
        Platform.runOnUIThread(() -> this.speed = speed);
    }

    /**
    * get singleton instance of class
     *
     * @return singleton instance of class
    */
    public static GameSpeed getInstance() {
        return instance;
    }
}
