package com.jukusoft.rts.core.logging;

import org.junit.Test;

public class LocalLoggerTest {

    @Test
    public void testConstructor () {
        new LocalLogger();
    }

    @Test
    public void testPrint () {
        LocalLogger.print("test message", false);
        LocalLogger.print("test message", true);
        LocalLogger.print("test message");
    }

    @Test
    public void testWarn () {
        LocalLogger.warn("test warning");
    }

    @Test
    public void testPrintStacktrace () {
        LocalLogger.printStacktrace(new Throwable("test"));
    }

}
