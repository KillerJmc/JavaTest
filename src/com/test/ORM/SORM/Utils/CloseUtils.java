/**
 * Copied from com.jmc.chatserver.CloseUtils
 * @author Jmc
 */
package com.test.ORM.SORM.Utils;

public class CloseUtils {
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
