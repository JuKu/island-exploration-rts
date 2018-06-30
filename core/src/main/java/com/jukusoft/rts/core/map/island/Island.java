package com.jukusoft.rts.core.map.island;

import com.jukusoft.rts.core.tiled.Orientation;

public class Island {

    protected final int id;
    protected String title = "";

    //island position on map
    protected float x = 0;
    protected float y = 0;

    //width & height in pixels
    protected int width = 0;
    protected int height = 0;

    protected String tmxPath = "";
    protected Orientation orientation = null;

    public Island(int id, String title, float x, float y, int width, int height, Orientation orientation, String tmxPath) {
        if (id < 0) {
            throw new IllegalArgumentException("id has to be >= 0.");
        }

        if (title == null) {
            throw new NullPointerException("title cannot be null.");
        }

        if (title.isEmpty()) {
            throw new IllegalArgumentException("title cannot be empty.");
        }

        if (x < 0) {
            throw new IllegalArgumentException("x position has to be >= 0.");
        }

        if (y < 0) {
            throw new IllegalArgumentException("y position has to be >= 0.");
        }

        this.id = id;
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.orientation = this.orientation;
        this.tmxPath = tmxPath;
    }

    public String getTitle () {
        return this.title;
    }

    public int getId () {
        return id;
    }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getTmxPath() {
        return tmxPath;
    }

}
