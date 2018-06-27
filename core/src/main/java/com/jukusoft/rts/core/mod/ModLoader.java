package com.jukusoft.rts.core.mod;

import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.utils.FileUtils;
import com.teamunify.i18n.I;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ModLoader {

    protected static final String MOD_JSON_NAME = "mod.json";

    protected ModLoader () {
        //
    }

    public static Mod loadMod (String modPath) throws IOException {
        if (!modPath.endsWith("/")) {
            modPath = modPath + "/";
        }

        //first, check if mod directory exists
        if (!new File(modPath).exists()) {
            throw new FileNotFoundException("mod directory doesnt exists: " + new File(modPath).getAbsolutePath());
        }

        if (!new File(modPath + MOD_JSON_NAME).exists()) {
            throw new FileNotFoundException("Couldnt found mod.json! path: " + modPath + MOD_JSON_NAME);
        }

        LocalLogger.print(I.trf("load mod.json: {0}", modPath + MOD_JSON_NAME));

        String content = FileUtils.readFile(modPath + MOD_JSON_NAME, StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(content);

        String name = json.getString("name");
        String title = json.getString("title");
        String version = json.getString("version");

        return new Mod(name, title, version);
    }

}
