package com.yujing.utils;

import android.app.Activity;
import android.os.Handler;

/**
 * 队列运行，等待指定时间后运行下一个。
 *
 * @author yujing 2019年2月15日17:23:15
 */
public class YQueueAndroid extends YQueue {
    /**
     * 运行
     * @param time 时间毫秒
     * @param qRun 回调
     */
    @Override
    public void run(final int time, final QRun qRun) {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            qRun.queueRun();
                        }
                    });
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                } finally {
                    shutdown();
                }

            }
        });
        add(thread);
    }

    /**
     * 运行
     * @param activity activity
     * @param time 时间毫秒
     * @param qRun 回调
     */
    public void run(final Activity activity, final int time, final QRun qRun) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (activity.isDestroyed()) {
                                return;
                            }
                            qRun.queueRun();
                        }
                    });
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                } finally {
                    shutdown();
                }
            }
        });
        add(thread);
    }
}
