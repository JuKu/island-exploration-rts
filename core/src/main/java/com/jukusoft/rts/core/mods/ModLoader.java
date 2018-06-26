package com.jukusoft.rts.core.mods;

import com.jukusoft.rts.core.utils.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ModLoader {


    public static Mod loadMod (String modPath) throws IOException {
        //first, check if mod directory exists
        if (!new File(modPath).exists()) {
            throw new FileNotFoundException("mod directory doesnt exists: " + new File(modPath).getAbsolutePath());
        }

        if (!new File(modPath + "mod.json").exists()) {
            throw new FileNotFoundException("Couldnt found mod.json! path: " + modPath + "mod.json");
        }

        String content = FileUtils.readFile(modPath + "mod.json", StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(content);

        String name = json.getString("name");
        String title = json.getString("title");
        String version = json.getString("version");

        return new Mod(name, title, version);
    }

}
