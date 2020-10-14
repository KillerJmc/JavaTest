package com.jmc.lang;
/**
 * 作者：Jmc
 * 时间：2020.8.18
 * 功能：boolean拓展
 *
 */


public class Bools
{
    public static boolean and(boolean... bools) {
        for (boolean b : bools) if (!b) return false;
        return true;
    }
    
    public static boolean or(boolean... bools) {
        for (boolean b : bools) if (b) return true;
        return false;
    }
    
    public static boolean in(double t, double former, double latter) {
        return t >= former && t <= latter;
    }
}
