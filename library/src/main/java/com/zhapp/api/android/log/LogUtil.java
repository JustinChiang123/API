package com.zhapp.api.android.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * <p/>
 * LOG日志输出管理
 * <p/>
 * Created by Justin_Chiang on 2016/6/13.
 */
public class LogUtil {


    /**
     * 是否是debug模式。
     */
    public static boolean DEBUG = true;
    /**
     * 默认的log TAG。
     */
    public static String customTagPrefix = "HTB_Log";

    /**
     * 根据当前类名获取TAG。
     *
     * @return
     */
    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    /**
     * debug模式下输出日志。
     *
     * @param message
     */
    public static void v(String message) {
        v(generateTag(), message);
    }

    /**
     * 必须输入日志。
     *
     * @param message
     */
    public static void mv(String message) {
        mv(generateTag(), message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param tag
     * @param message
     */
    public static void v(String tag, String message) {
        v(false, tag, message);
    }

    /**
     * 必须输入日志。
     *
     * @param tag
     * @param message
     */
    public static void mv(String tag, String message) {
        v(true, tag, message);
    }

    /**
     * 输入日志。
     *
     * @param isMust  是否必须输入的日志。
     * @param tag     tag。
     * @param message 输出的msg。
     */
    private static void v(boolean isMust, String tag, String message) {
        if (!isMust && !DEBUG) {
            return;
        }
        Log.v(tag, message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param message
     */
    public static void d(String message) {
        d(generateTag(), message);
    }

    /**
     * 必须输入日志。
     *
     * @param message
     */
    public static void md(String message) {
        md(generateTag(), message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        d(false, tag, message);
    }

    /**
     * 必须输入日志。
     *
     * @param tag
     * @param message
     */
    public static void md(String tag, String message) {
        d(true, tag, message);
    }

    /**
     * 输入日志。
     *
     * @param isMust  是否必须输入的日志。
     * @param tag     tag。
     * @param message 输出的msg。
     */
    private static void d(boolean isMust, String tag, String message) {
        if (!isMust && !DEBUG) {
            return;
        }
        Log.d(tag, message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param message
     */
    public static void i(String message) {
        i(generateTag(), message);
    }

    /**
     * 必须输入日志。
     *
     * @param message
     */
    public static void mi(String message) {
        mi(generateTag(), message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        i(false, tag, message);
    }

    /**
     * 必须输入日志。
     *
     * @param tag
     * @param message
     */
    public static void mi(String tag, String message) {
        i(true, tag, message);
    }

    /**
     * 输入日志。
     *
     * @param isMust  是否必须输入的日志。
     * @param tag     tag。
     * @param message 输出的msg。
     */
    private static void i(boolean isMust, String tag, String message) {
        if (!isMust && !DEBUG) {
            return;
        }
        Log.i(tag, message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param message
     */
    public static void w(String message) {
        w(generateTag(), message);
    }

    /**
     * 必须输入日志。
     *
     * @param message
     */
    public static void mw(String message) {
        mw(generateTag(), message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param tag
     * @param message
     */
    public static void w(String tag, String message) {
        w(false, tag, message);
    }

    /**
     * 必须输入日志。
     *
     * @param tag
     * @param message
     */
    public static void mw(String tag, String message) {
        w(true, tag, message);
    }

    /**
     * 输入日志。
     *
     * @param isMust  是否必须输入的日志。
     * @param tag     tag。
     * @param message 输出的msg。
     */
    private static void w(boolean isMust, String tag, String message) {
        if (!isMust && !DEBUG) {
            return;
        }
        Log.w(tag, message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param message
     */
    public static void e(String message) {
        e(generateTag(), message);
    }

    /**
     * 必须输入日志。
     *
     * @param message
     */
    public static void me(String message) {
        me(generateTag(), message);
    }

    /**
     * debug模式下输出日志。
     *
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        e(false, tag, message);
    }

    /**
     * 必须输入日志。
     *
     * @param tag
     * @param message
     */
    public static void me(String tag, String message) {
        e(true, tag, message);
    }

    /**
     * 输入日志。
     *
     * @param isMust  是否必须输入的日志。
     * @param tag     tag。
     * @param message 输出的msg。
     */
    private static void e(boolean isMust, String tag, String message) {
        if (!isMust && !DEBUG) {
            return;
        }
        Log.e(tag, message);
    }
}
