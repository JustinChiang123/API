package com.zhapp.api.android.network.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * <pre>
 * Describe: 聊天信息。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2018-02-02
 * Time: 03:35
 * <pre/>
 */
public class ChatResponse<T> implements Serializable, MultiItemEntity {

    private static final long serialVersionUID = -1477609349342266116L;

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int ERROR = 3;
    public static final int LOAD = 4;

    private int itemType;
    public T data;

    public ChatResponse(int itemType, T data) {
        this.itemType = itemType;
        this.data = data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
