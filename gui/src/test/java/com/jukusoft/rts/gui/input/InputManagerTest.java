package com.jukusoft.rts.gui.input;

import com.jukusoft.rts.gui.GameUnitTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InputManagerTest extends GameUnitTest {

    @Test
    public void testConstructor () {
        new InputManager();
    }

    public void testGetInstance () {
        InputManager manager = InputManager.getInstance();
        InputManager manager1 = InputManager.getInstance();

        assertNotNull(manager);
        assertNotNull(manager1);

        assertEquals(manager, manager1);
    }

}
