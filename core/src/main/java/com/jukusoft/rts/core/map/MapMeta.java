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

    //meta data
    protected String title = "";
    protected int width = 0;
    protected int height = 0;
    String minimap = "";

    //camera start position
    protected float cameraStartX = 0;
    protected float cameraStartY = 0;

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

        //parse json
        this.title = json.getString("title");
        this.width = json.getInt("width");
        this.height = json.getInt("height");
        this.minimap = json.getString("minimap");

        //get camera start position
        JSONObject camera = json.getJSONObject("camera");
        this.cameraStartX = camera.getInt("x");
        this.cameraStartY = camera.getInt("y");

        //store map directory path
        this.dir = mapDir;

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDir() {
        return dir;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getMinimap() {
        return minimap;
    }

    public float getCameraStartX() {
        return cameraStartX;
    }

    public float getCameraStartY() {
        return cameraStartY;
    }
    
}
