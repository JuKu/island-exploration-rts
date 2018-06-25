package com.jukusoft.rts.gui.fps;

import com.jukusoft.rts.core.time.GameTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FPSManagerTest {

    @Test
    public void testConstructor () {
        new FPSManager();
    }

    @Test
    public void testGetInstance () {
        FPSManager instance = FPSManager.getInstance();
        assertNotNull(instance);

        //check, that instances are equals
        FPSManager instance1 = FPSManager.getInstance();
        assertNotNull(instance1);
        assertEquals(instance, instance1);
    }

    @Test
    public void testGetterAndSetter () {
        GameTime.getInstance().setTime(System.currentTimeMillis());

        FPSManager.getInstance().setFPS(0);
        assertEquals(0, FPSManager.getInstance().getFPS());
        assertEquals(false, FPSManager.getInstance().isCriticalFPSValue());
        FPSManager.getInstance().showWarningIfNeccessary();

        FPSManager.getInstance().setFPS(50);
        assertEquals(50, FPSManager.getInstance().getFPS());
        assertEquals(true, FPSManager.getInstance().isCriticalFPSValue());
        FPSManager.getInstance().showWarningIfNeccessary();
        FPSManager.getInstance().showWarningIfNeccessary();

        FPSManager.getInstance().setFPS(60);
        assertEquals(60, FPSManager.getInstance().getFPS());
        assertEquals(false, FPSManager.getInstance().isCriticalFPSValue());
        FPSManager.getInstance().showWarningIfNeccessary();
    }

}
