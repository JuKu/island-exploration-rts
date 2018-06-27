package com.jukusoft.rts.core;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Game {

    public void createNewGame (String mapName) throws FileNotFoundException, IOException;

}
