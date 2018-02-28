package com.zhapp.api.android.network;

import com.lzy.okgo.OkGo;

/**
 * <pre>
 * Describe: NetAction。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2018-02-28
 * Time: 15:16
 * <pre/>
 */
public class NetAction {

    private static final String TAG = "NetAction";
    //    private static int pagesize = 5;
    public static int project = 0;

    public static String push_id;

    public static void setProject(int project) {
        NetAction.project = project;
    }

    /**
     * <pre>
     *     根据Tag取消请求
     * </pre>
     *
     * @param tag
     */
    public static void cancel(String tag) {
        OkGo.getInstance().cancelTag(tag);
    }
}
