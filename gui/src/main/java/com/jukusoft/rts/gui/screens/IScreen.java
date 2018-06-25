package com.jukusoft.rts.gui.screens;

import com.jukusoft.rts.core.Game;

/**
 * Screen interface - screens are responsible for drawing, not for updating your game state!
 *
 * Created by Justin on 06.02.2017.
 */
public interface IScreen {

    /**
    * method which should be executed if screen is created
    */
    public void onStart(Game game, ScreenManager<IScreen> screenManager);

    /**
     * method which should be executed if screen has stopped
     */
    public void onStop(Game game);

    /**
     * method is executed, if screen is set to active state now.
     */
    public void onResume(Game game);

    /**
    * method is executed, if screen isn't active anymore
    */
    public void onPause(Game game);

    /**
    * window was resized
     *
     * @param width new window width
     * @param height new window height
    */
    public void onResize(int width, int height);

    /**
    * process input
     *
     * @return true, if input was processed and no other screen has to process input anymore
    */
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager);

    /**
     * update game screen
     */
    public void update(Game game, ScreenManager<IScreen> screenManager);

    /**
     * beforeDraw game screen
     */
    public void draw(Game game);

}
