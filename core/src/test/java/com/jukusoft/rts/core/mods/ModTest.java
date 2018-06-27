package com.jukusoft.rts.core.mods;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModTest {

    @Test
    public void testConstructor () {
        Mod mod = new Mod("name", "title", "1.0.0");

        assertEquals("name", mod.getName());
        assertEquals("title", mod.getTitle());
        assertEquals("1.0.0", mod.getVersion());
    }

    @Test
    public void testCreate () {
        //create json object and fill with test data
        JSONObject json = new JSONObject();
        json.put("name", "test-mode");
        json.put("title", "test-title");
        json.put("version", "2.0.0");

        Mod mod = Mod.create(json);
        assertEquals("test-mode", mod.getName());
        assertEquals("test-title", mod.getTitle());
        assertEquals("2.0.0", mod.getVersion());
    }

    @Test (expected = JSONException.class)
    public void testCreateMissingName () {
        //create json object and fill with test data
        JSONObject json = new JSONObject();
        json.put("title", "test-title");
        json.put("version", "2.0.0");

        Mod mod = Mod.create(json);
    }

    @Test (expected = JSONException.class)
    public void testCreateMissingTitle () {
        //create json object and fill with test data
        JSONObject json = new JSONObject();
        json.put("name", "test-mode");
        json.put("version", "2.0.0");

        Mod mod = Mod.create(json);
    }

    @Test (expected = JSONException.class)
    public void testCreateMissingVersion () {
        //create json object and fill with test data
        JSONObject json = new JSONObject();
        json.put("name", "test-mode");
        json.put("title", "test-title");

        Mod mod = Mod.create(json);
    }

}
