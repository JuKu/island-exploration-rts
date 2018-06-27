package com.jukusoft.rts.core.mod;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModLoaderTest {

    @Test
    public void testConstructor () {
        new ModLoader();
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadNotExistentMod () throws IOException {
        ModLoader.loadMod("not-existent-path");
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadNotExistentModJSON () throws IOException {
        ModLoader.loadMod("../mods/");
    }

    @Test
    public void testLoadMod () throws IOException {
        Mod mod = ModLoader.loadMod("../mods/base/");

        assertNotNull(mod);
        assertEquals(true, !mod.getName().isEmpty());
        assertEquals(true, !mod.getTitle().isEmpty());
        assertEquals(true, !mod.getVersion().isEmpty());
        assertEquals("base", mod.getName());
    }

}
