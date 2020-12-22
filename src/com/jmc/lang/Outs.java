package com.jmc.lang;

import static com.jmc.lang.Tries.*;

public class Outs {
    public static void newLine() {
        System.out.println("--------------------------------------------------------------------------");
    }

    public static void newLine(RunnableThrowsException r) {
        newLine();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        newLine();
    }
}
