package com.jukusoft.rts.core.mods;

import com.jukusoft.rts.core.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ModManager {

    protected List<Mod> modList = new ArrayList<>();

    public ModManager () {
        //
    }

    public void load (File configFile) throws IOException {
        if (!configFile.exists()) {
            throw new FileNotFoundException("mods.json file doesnt exists! path: " + configFile.getAbsolutePath());
        }

        String content = FileUtils.readFile(configFile.getAbsolutePath(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(content);
        JSONArray array = json.getJSONArray("mods");

        //load all mods
        for (int i = 0; i < array.length(); i++) {
            this.loadMod("mods/" + array.getString(i) + "/");
        }
    }

    protected void loadMod (String modPath) {
        //
    }

    public List<Mod> listActivatedMods () {
        return this.modList;
    }

}
