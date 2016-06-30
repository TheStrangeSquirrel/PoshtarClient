package net.squirrel.postar.client.utils;

import android.util.Log;

public class LogUtil {
    public final static boolean DISABLE_ALL_LOG = false;

    public final static boolean ERROR_ENABLED = true;
    public final static boolean WARING_ENABLED = true;
    public final static boolean INFO_ENABLED = true;
    public final static boolean DEBUG_ENABLED = true;


    public final static String TAG_SUFFIX = "POSHTAR ";


    private static String getTag() {
        return TAG_SUFFIX + "From class: " + getContextClass();
    }

    private static String getContextClass() {
        return new RuntimeException().getStackTrace()[1].getClassName();
    }

    public static void e(String msg, Throwable tr) {
        if (ERROR_ENABLED && !DISABLE_ALL_LOG) {
            Log.e(getTag(), msg, tr);
        }
    }

    public static void e(String msg) {
        if (ERROR_ENABLED && !DISABLE_ALL_LOG) {
            Log.e(getTag(), msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (WARING_ENABLED && !DISABLE_ALL_LOG) {
            Log.w(getTag(), msg, tr);
        }
    }

    public static void w(String msg) {
        if (ERROR_ENABLED && !DISABLE_ALL_LOG) {
            Log.w(getTag(), msg);
        }
    }

    public static void i(String msg, Throwable tr) {
        if (INFO_ENABLED && !DISABLE_ALL_LOG) {
            Log.i(getTag(), msg, tr);
        }
    }

    public static void i(String msg) {
        if (INFO_ENABLED && !DISABLE_ALL_LOG) {
            Log.i(getTag(), msg);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (DEBUG_ENABLED && !DISABLE_ALL_LOG) {
            Log.d(getTag(), msg, tr);
        }
    }

    public static void d(String msg) {
        if (DEBUG_ENABLED && !DISABLE_ALL_LOG) {
            Log.d(getTag(), msg);
        }
    }
}
