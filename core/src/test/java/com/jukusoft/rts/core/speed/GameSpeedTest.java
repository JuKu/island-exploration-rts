package com.jukusoft.rts.core.speed;

import com.jukusoft.rts.core.utils.Platform;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameSpeedTest {

    @Test
    public void testConstructor () {
        new GameSpeed();
    }

    @Test
    public void testGetInstance () {
        GameSpeed instance = GameSpeed.getInstance();
        GameSpeed instance1 = GameSpeed.getInstance();

        assertNotNull(instance);
        assertNotNull(instance1);

        //check, if instances are equals
        assertEquals(instance, instance1);
    }

    @Test
    public void testGetterAndSetter () {
        GameSpeed gameSpeed = new GameSpeed();

        assertEquals(1f, gameSpeed.getSpeed(), 0.0001f);

        //update game speed
        gameSpeed.setSpeed(2f);

        //its already 1, because update will be executed on main thread later
        assertEquals(1f, gameSpeed.getSpeed(), 0.0001f);

        //execute queue in main thread, so speed is updated
        Platform.executeQueue();

        //now game speed was updated, because executeQueue() was exeucted from gameloop
        assertEquals(2f, gameSpeed.getSpeed(), 0.0001f);
    }

}
