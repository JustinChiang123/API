package com.zhapp.api.android;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Activity管理类
 * <p>
 * Created by Justin_Chiang on 2016/6/13.
 */
public class CollectorUtil {


    private static final List<Activity> list = new ArrayList<>();


    public static void addActivity(Activity activity) {
        list.add(activity);
    }

    public static void removeActivity(Activity activity) {
        list.remove(activity);
        if (activity == null) {
            return;
        }
        if (list.size() == 0) {
            activity.getApplication().onTerminate();
        }
    }

    public static void removeAllActivity() {
        try {
            for (Activity activity : list) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void removeAllActivityExit() {
        try {
            for (Activity activity : list) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
