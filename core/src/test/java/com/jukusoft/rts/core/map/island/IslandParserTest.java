package com.jukusoft.rts.core.map.island;

import com.carrotsearch.hppc.ObjectArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IslandParserTest {

    @Test
    public void testConstructor () {
        new IslandParser();
    }

    @Test
    public void testParseIslands () {
        //create an example json object
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        for (int i = 0; i < 10; i++) {
            JSONObject island = new JSONObject();
            island.put("island_id", i);
            island.put("title", "island_" + i);

            island.put("x", 10 + i);
            island.put("y", 20 + i);

            island.put("width", 30 + i);
            island.put("height", 40 + i);

            island.put("tmx_file", "island_" + i + ".tmx");

            //add json object to array
            array.put(island);
        }

        json.put("islands", array);

        IslandParser parser = new IslandParser();
        ObjectArrayList<Island> list = parser.parseIslands(json);

        assertEquals(10, list.size());

        for (int i = 0; i < 10; i++) {
            Island island = list.get(i);
            assertNotNull(island);

            assertEquals(i, island.getId());
            assertEquals("island_" + i, island.getTitle());
            assertEquals(10 + i, island.getX(), 0.0001f);
            assertEquals(20 + i, island.getY(), 0.0001f);
            assertEquals(30 + i, island.getWidth());
            assertEquals(40 + i, island.getHeight());
            assertEquals("island_" + i + ".tmx", island.getTmxPath());
        }
    }

}
