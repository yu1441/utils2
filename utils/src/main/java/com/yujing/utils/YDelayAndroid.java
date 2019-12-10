package com.yujing.utils;


import android.app.Activity;
import android.os.Handler;

/**
 * 延迟类
 * @author yujing 2019年2月15日17:21:46
 */
public class YDelayAndroid extends YDelay{
    /**
     * 延时运行
     * @param time 时间毫秒
     * @param dRun 回调
     */
    public static void run(final int time, final DRun dRun) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dRun.delayedRun();
                    }
                });
            }
        }).start();
    }

    /**
     * 延时运行
     * @param activity activity
     * @param time 时间毫秒
     * @param dRun 回调
     */
    public static void run(final Activity activity, final int time, final DRun dRun) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (activity.isDestroyed()) {
                            return;
                        }
                        dRun.delayedRun();
                    }
                });
            }
        }).start();
    }
}

