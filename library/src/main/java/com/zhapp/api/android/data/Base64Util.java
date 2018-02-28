package com.zhapp.api.android.data;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * <pre>
 * Describe: Base64加密。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2017-02-16
 * Time: 23:54
 * <pre/>
 */
public class Base64Util {
    private static final String TAG = "Base64Util";

    // 加密
    public static String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 解密
    public static String getFromBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.decode(str, Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
