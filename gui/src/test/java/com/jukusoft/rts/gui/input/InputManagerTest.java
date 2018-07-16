package com.jukusoft.rts.gui.input;

import com.badlogic.gdx.InputProcessor;
import com.jukusoft.rts.gui.GameUnitTest;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class InputManagerTest extends GameUnitTest {

    @Test
    public void testConstructor () {
        new InputManager();
    }

    @Test
    public void testGetInstance () {
        InputManager manager = InputManager.getInstance();
        InputManager manager1 = InputManager.getInstance();

        assertNotNull(manager);
        assertNotNull(manager1);

        assertEquals(manager, manager1);
    }

    @Test
    public void testAddAndRemove () {
        InputManager manager = new InputManager();

        InputProcessor processor = Mockito.mock(InputProcessor.class);
        InputProcessor processor1 = Mockito.mock(InputProcessor.class);

        assertNotEquals(processor, processor1);

        assertEquals(0, manager.countProcessors());

        manager.add(processor);
        assertEquals(1, manager.countProcessors());

        manager.add(processor1);
        assertEquals(2, manager.countProcessors());

        //check order
        assertEquals(processor, manager.getProcessors().get(0));
        assertEquals(processor1, manager.getProcessors().get(1));

        manager.remove(processor1);
        assertEquals(1, manager.countProcessors());

        //add first
        manager.addFirst(processor1);

        //check order
        assertEquals(processor1, manager.getProcessors().get(0));
        assertEquals(processor, manager.getProcessors().get(1));
    }

    @Test (expected = IllegalStateException.class)
    public void testAddSameProcessor () {
        InputManager manager = new InputManager();
        InputProcessor processor = Mockito.mock(InputProcessor.class);

        manager.add(processor);
        manager.add(processor);
    }

}
