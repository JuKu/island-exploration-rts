package com.jukusoft.rts.core.cache;

import com.jukusoft.rts.core.utils.FileUtils;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Cache {

    protected static Cache instance = null;

    protected final String CACHE_PATH;

    protected Cache(File cacheConfig) throws IOException {
        if (cacheConfig == null) {
            throw new NullPointerException("cache config file cannot be null.");
        }

        //check, if cache config file exists
        if (!cacheConfig.exists()) {
            throw new FileNotFoundException("cache config file doesnt exists: " + cacheConfig.getAbsolutePath());
        }

        //read config
        Ini ini = new Ini(cacheConfig);
        Profile.Section section = ini.get("Cache");
        String path = section.get("path");

        if (!path.endsWith("/")) {
            path += "/";
        }

        CACHE_PATH = path;

        //create directory, if neccessary
        FileUtils.createWritableDirIfAbsent(CACHE_PATH);
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + "assets/");
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + "security/");
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + "maps/");
    }

    /**
    * initialize cache
    */
    public static void init (File cacheConfig) throws IOException {
        instance = new Cache(cacheConfig);
    }

    public static Cache getInstance () {
        if (instance == null) {
            throw new IllegalStateException("You havent initialized cache with Cache::init() before.");
        }

        return instance;
    }

    public String getPath () {
        return CACHE_PATH;
    }

    public void createDirIfAbsent (String dirName) {
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + dirName + "/");
    }

    public String getCachePath (String dirName) {
        return CACHE_PATH + dirName + "/";
    }

    public void clear () throws IOException {
        FileUtils.recursiveDeleteDirectory(new File(CACHE_PATH));
    }

}
