package com.jukusoft.rts.core.map.island;

import com.carrotsearch.hppc.ObjectArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class IslandParser {

    public IslandParser () {
        //
    }

    public ObjectArrayList<Island> parseIslands (JSONObject json, String mapDir) {
        ObjectArrayList<Island> list = new ObjectArrayList<>();

        if (!mapDir.endsWith("/")) {
            throw new IllegalArgumentException("map directory path has to end with '/'!");
        }

        JSONArray islandsArray = json.getJSONArray("islands");

        for (int i = 0; i < islandsArray.length(); i++) {
            JSONObject islandJson = islandsArray.getJSONObject(i);

            int id = islandJson.getInt("island_id");
            String title = islandJson.getString("title");
            int x = islandJson.getInt("x");
            int y = islandJson.getInt("y");
            int width = islandJson.getInt("width");
            int height = islandJson.getInt("height");
            String tmxFile = islandJson.getString("tmx_file");

            Island island = new Island(id, title, x, y, width, height, mapDir + tmxFile);
            list.add(island);
        }

        return list;
    }

}
