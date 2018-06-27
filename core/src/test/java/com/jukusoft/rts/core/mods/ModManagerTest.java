package com.jukusoft.rts.core.mods;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModManagerTest {

    @Test
    public void testConstructor () {
        ModManager manager = new ModManager();
        assertEquals(true, manager.listActivatedMods().isEmpty());
    }

    @Test
    public void testGetInstance () {
        assertNotNull(ModManager.getInstance());
        assertNotNull(ModManager.getInstance());
        assertNotNull(ModManager.getInstance());
    }

    @Test
    public void testGetSameInstance () {
        ModManager manager = ModManager.getInstance();
        ModManager manager1 = ModManager.getInstance();

        assertEquals(manager, manager1);
    }

    @Test (expected = NullPointerException.class)
    public void testLoadNullFile () throws IOException {
        new ModManager().load(null);
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadNotExistentFile () throws IOException {
        new ModManager().load(new File("not-existent-file.cfg"));
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoad () throws IOException {
        ModManager manager = new ModManager();
        manager.load(new File("../data/mods.json"));

        assertNotNull(manager.listActivatedMods());
        assertEquals(true, !manager.listActivatedMods().isEmpty());
        assertEquals("base", manager.listActivatedMods().get(0).getName());
    }

}
