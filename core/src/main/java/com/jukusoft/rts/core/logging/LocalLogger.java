package com.jukusoft.rts.core.logging;

public class LocalLogger {

    protected LocalLogger() {
        //
    }

    public static void print (String message, boolean printEnabled) {
        if (printEnabled) {
            LocalLogger.print(message);
        }
    }

    public static void print (String message) {
        System.out.println(message);
    }

    public static void warn (String message) {
        System.err.println(message);
    }

    public static void printStacktrace (Throwable e) {
        e.printStackTrace();
    }

}
