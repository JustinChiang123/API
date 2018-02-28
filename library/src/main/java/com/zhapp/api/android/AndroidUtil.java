package com.zhapp.api.android;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.zhapp.api.BuildConfig;
import com.zhapp.api.android.log.LogUtils;

import java.io.File;
import java.util.List;

/**
 * <pre>
 * Describe: AndroidUtil。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2018-01-19
 * Time: 13:17
 * <pre/>
 */
public class AndroidUtil {

    public static boolean isRunning(@NonNull Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(context.getPackageName()) && info.baseActivity.getPackageName().equals(context.getPackageName())) {
                LogUtils.e("isAppRunning == " + true);
                return true;
            }
        }
        return false;
    }


    /**
     * 判断手机是否存在sdcard
     *
     * @return boolean 返回类型
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static boolean hasSdcard() {
        boolean result = false;
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            result = true;
        }
        return result;
    }

    /**
     * 安装软件
     *
     * @param context
     * @param apkfile 参数
     *
     * @author <a href="mailto: Justin_Chiang@foxmail.com">Justin_Chiang</a>
     */
    public static void installSoftWare(@NonNull Context context, @NonNull File apkfile) {
        if (context == null) {
            return;
        }
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        else {
            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void openQQ(@NonNull Context context, @NonNull String url) {
        try {
            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            getActivity().MToast("正在跳转QQ...");
        } catch (Exception e) {
            e.printStackTrace();
            getActivity().MToast("请检查是否安装最新版本QQ...");
        }
    }


    public static void openQQQun(@NonNull Context context,@NonNull String url) {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(url));
            getActivity().startActivity(intent);
            getActivity().MToast("正在跳转QQ...");
        } catch (Exception e) {
            e.printStackTrace();
            getActivity().MToast("请检查是否安装最新版本QQ...");
        }
    }


    public static void openWechat(@NonNull Context context,@NonNull String pkg, @NonNull String cls) {
        try {
            // ComponentName（组件名称）是用来打开其他应用程序中的Activity或服务的
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName(pkg, cls);// 报名该有activity
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            getActivity().startActivityForResult(intent, 0);
            getActivity().MToast("正在跳转微信...");
        } catch (Exception e) {
            e.printStackTrace();
            getActivity().MToast("请检查是否安装最新版本微信...");
        }
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     * @param requestCode
     */
    public static void openApplicationMarket(@NonNull Context context,@NonNull String packageName, @NonNull int requestCode, @NonNull String storeurl) {
        LogUtils.e("packageName=" + packageName);
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            getActivity().startActivityForResult(localIntent, requestCode);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            getActivity().MToast("打开应用商店失败");
            // 调用系统浏览器进入商城
            openLinkBySystem(storeurl, requestCode);
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    private static void openLinkBySystem(@NonNull Context context,@NonNull String url, @NonNull int requestCode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        getActivity().startActivity(intent);
    }
}