package com.zhapp.api.android.img;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.zhapp.api.R;

/**
 * <pre>
 * Describe: PicassoUtil。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2017-08-14
 * Time: 11:20
 * <pre/>
 */
public class PicassoUtil {

    public static RequestCreator load(Context context, String path, int errorResId, int placeholderResId) {
        if (errorResId == 0) {
            errorResId = R.mipmap.picasso_error;
        }
        if (placeholderResId == 0) {
            placeholderResId = R.mipmap.picasso_placeholder;
        }
        Picasso picasso = Picasso.with(context);
        //绿色表示从内存加载、蓝色表示从磁盘加载、红色表示从网络加载
//        picasso.setIndicatorsEnabled(OkGoUtils.DEBUG);
        RequestCreator requestCreator;
        if (path == null || path.trim().length() == 0) {
            requestCreator = picasso.load(errorResId);
        }
        else {
            requestCreator = picasso.load(path);
        }
        requestCreator.placeholder(placeholderResId).error(errorResId).fit();
        return requestCreator;
    }

    public static RequestCreator load(Context context, String path, int errorResId) {
        return load(context, path, 0, 0);
    }

    public static RequestCreator load(Context context, String path) {
        return load(context, path, 0);
    }
}
