package com.test.ORM.SORM.utils;

/**
 * Invoke close method of the closeable items.
 * Copied from com.jmc.chatserver.CloseUtils.
 * @author Jmc
 */
public class CloseUtils {
    /**
     * Invoke close method to close items.
     * @param items the items would be closed
     */
    public static void closeAll(AutoCloseable... items) {
        for (AutoCloseable item : items) {
            try {
                if (item != null) item.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
