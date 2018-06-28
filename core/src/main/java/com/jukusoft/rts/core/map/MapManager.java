package com.jukusoft.rts.core.map;

import com.jukusoft.rts.core.logging.LocalLogger;
import com.teamunify.i18n.I;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapManager {

    protected List<MapMeta> maps = new ArrayList<>();

    protected static MapManager instance = null;

    protected MapManager (String mapsDir) throws IOException {
        File dir = new File(mapsDir);

        if (!dir.exists()) {
            throw new FileNotFoundException("maps directory doesnt exists: " + mapsDir);
        }

        //list all directories in directory maps/
        String[] directories = dir.list((current, name) -> new File(current, name).isDirectory());

        //load all available maps
        for (String directory : directories) {
            LocalLogger.print(I.trf("map found: {0}", directory));

            //try to load map
            MapMeta map = new MapMeta();
            map.load(mapsDir + directory + "/", directory);

            this.maps.add(map);
        }
    }

    public List<MapMeta> listFreeMaps () {
        return this.maps;
    }

    public static MapManager getInstance () {
        return instance;
    }

    public static void init (String mapsDir) throws IOException {
        instance = new MapManager(mapsDir);
    }

}
