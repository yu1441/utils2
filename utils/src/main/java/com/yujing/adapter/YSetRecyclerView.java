package com.yujing.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 快速设置RecyclerView
 *
 * @author 余静 2018年11月30日12:14:30
 */
@SuppressWarnings("unused")
public class YSetRecyclerView {
    /**
     * 默认纵向布局
     *
     * @param context      context
     * @param recyclerView recyclerView
     */
    public static void init(Context context, RecyclerView recyclerView) {
        init(context, recyclerView, OrientationHelper.VERTICAL);
    }

    /**
     * 自定义横向或纵向布局
     *
     * @param context      context
     * @param recyclerView recyclerView
     * @param Orientation  Orientation，如：OrientationHelper.VERTICAL
     */
    public static void init(Context context, RecyclerView recyclerView, int Orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setOrientation(Orientation);
        recyclerView.setLayoutManager(layoutManager);
    }

    // 自定义多行多列布局
    public static void init(Context context, RecyclerView recyclerView, int Orientation, int items) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, items);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setOrientation(Orientation);
        recyclerView.setLayoutManager(layoutManager);
    }

    // 默认纵向布局
    public static void init(Context context, RecyclerView recyclerView, boolean isScrollView) {
        init(context, recyclerView, OrientationHelper.VERTICAL, isScrollView);
    }

    // 自定义横向或纵向布局
    public static void init(Context context, RecyclerView recyclerView, int Orientation, boolean isScrollView) {
        YFullyLinearLayoutManager layoutManager = new YFullyLinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setOrientation(Orientation);
        recyclerView.setLayoutManager(layoutManager);
    }

    // 自定义多行多列布局
    public static void init(Context context, RecyclerView recyclerView, int Orientation, int items, boolean isScrollView) {
        YFullyGridLayoutManager layoutManager = new YFullyGridLayoutManager(context, items);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setOrientation(Orientation);
        recyclerView.setLayoutManager(layoutManager);
    }

    public static void setOther(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置增加或删除条目的动画
    }

    /**
     * 打开上下拖拽，左右删除
     *
     * @param recyclerView recyclerView
     * @param adapter      adapter
     */
    public static void openItemTouch(RecyclerView recyclerView, YBaseYRecyclerViewAdapter adapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new YSimpleItemTouchCallback(adapter));
        helper.attachToRecyclerView(recyclerView);
    }
}