package com.jukusoft.rts.core.utils;

import com.carrotsearch.hppc.ObjectStack;

public class Platform {

    //tasks which should be executed in OpenGL context thread
    protected static ObjectStack<Runnable> uiQueue = new ObjectStack<>(30);

    /**
    * private constructor
    */
    protected Platform() {
        //
    }

    public static void runOnUIThread (Runnable runnable) {
        uiQueue.push(runnable);
    }

    public static void executeQueue () {
        while (uiQueue.size() > 0) {
            //execute tasks, which should be executed in OpenGL context thread
            Runnable runnable = uiQueue.pop();

            runnable.run();
        }
    }

}
