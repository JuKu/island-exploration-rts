package com.jukusoft.rts.core.mods;

import org.json.JSONObject;

public class Mod {

    protected final String name;
    protected final String title;
    protected final String version;

    public Mod (String name, String title, String version) {
        this.name = name;
        this.title = title;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public static Mod create (JSONObject json) {
        String name = json.getString("name");
        String title = json.getString("title");
        String version = json.getString("version");

        return new Mod(name, title, version);
    }

}
