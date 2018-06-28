package com.jukusoft.rts.core.map;

import com.jukusoft.rts.core.utils.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MapMeta {

    protected String dir = "";
    protected String name = "";

    public MapMeta() {
        //
    }

    public void load (String mapDir, String name) throws IOException {
        if (mapDir == null) {
            throw new NullPointerException("path cannot be null.");
        }

        if (mapDir.isEmpty()) {
            throw new IllegalArgumentException("path cannot be empty.");
        }

        if (!mapDir.endsWith("/")) {
            throw new IllegalArgumentException("map directory has to end with / .");
        }

        final String mapPath =  mapDir + "map.json";

        if (!new File(mapPath).exists()) {
            throw new FileNotFoundException("map path doesnt exists: " + mapPath);
        }

        //read content from file and convert to json
        String content = FileUtils.readFile(mapPath, StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(content);

        //store map directory path
        this.dir = mapDir;

        this.name = name;
    }

    public String getName() {
        return name;
    }

}
