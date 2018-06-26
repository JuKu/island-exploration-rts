package com.jukusoft.rts.core.mods;

import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.utils.FileUtils;
import com.teamunify.i18n.I;
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

    //singleton instance
    protected static final ModManager instance = new ModManager();

    protected ModManager () {
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
            final String modName = array.getString(i);

            LocalLogger.print(I.trf("mod found: {0}", modName));

            Mod mod = ModLoader.loadMod("mods/" + modName + "/");

            //add mod to list
            this.modList.add(mod);
        }
    }

    public List<Mod> listActivatedMods () {
        return this.modList;
    }

    public static ModManager getInstance () {
        return instance;
    }

}
