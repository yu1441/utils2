package com.yujing.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import com.yujing.net.dowload.YDownload;
import com.yujing.net.dowload.YDownloadAndroid;

import java.io.File;


/**
 * 更新APP
 *
 * @author yujing 2018年11月30日12:11:26
 */
@SuppressWarnings("ALL")
public class YVersionUpdate {
    private Activity activity;
    private int serverCode;//服务器版本
    private boolean forceUpdate;//是否强制更新
    private String DownUrl;//下载路径
    private YNoticeDownload yNoticeDownload;
    private YNoticeDownload.DownLoadComplete downLoadCompleteListener;//下载成功回调
    private boolean useNotificationDownload = true;//是否使用通知栏下载

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getServerCode() {
        return serverCode;
    }

    public void setServerCode(int serverCode) {
        this.serverCode = serverCode;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDownUrl() {
        return DownUrl;
    }

    public void setDownUrl(String downUrl) {
        DownUrl = downUrl;
    }

    public boolean isUseNotificationDownload() {
        return useNotificationDownload;
    }

    public void setUseNotificationDownload(boolean useNotificationDownload) {
        this.useNotificationDownload = useNotificationDownload;
    }

    public void setDownLoadCompleteListener(YNoticeDownload.DownLoadComplete downLoadCompleteListener) {
        this.downLoadCompleteListener = downLoadCompleteListener;
    }


    public YVersionUpdate() {

    }


    public YVersionUpdate(Activity activity, int serverCode, boolean forceUpdate, String DownUrl) {
        this.activity = activity;
        this.serverCode = serverCode;
        this.forceUpdate = forceUpdate;
        this.DownUrl = DownUrl;
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {
        if (serverCode > YUtils.getVersionCode(activity, activity.getPackageName())) {
            needUpdate();
        } else {
            noNeedUpdate();
        }
    }

    /**
     * 只提示更新
     */
    public void promptUpdate() {
        if (serverCode > YUtils.getVersionCode(activity, activity.getPackageName())) {
            needUpdate();
        }
    }

    /**
     * 不用更新
     */
    private void noNeedUpdate() {
        int verCode = YUtils.getVersionCode(activity, activity.getPackageName()); // 获取当前APK的版本值
        String verName = YUtils.getVersionName(activity, activity.getPackageName());// 获取当前APK的版本名称
        String sb = "当前版本名:" + verName +
                "\n当前版本号:" + verCode +
                "\n服务器版本号:" + serverCode +
                "\n已是最新版,无需更新!";
        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("软件更新").setMessage(sb)// 设置内容
                .setPositiveButton("确定", null).create();// 创建
        // 显示对话框
        if (Build.VERSION.SDK_INT >= 19 && (activity.isDestroyed() || activity.isFinishing())) {
            return;
        }
        dialog.show();
    }

    /**
     * 需要更新
     */
    private void needUpdate() {
        int verCode = YUtils.getVersionCode(activity, activity.getPackageName());
        String verName = YUtils.getVersionName(activity, activity.getPackageName());
        String sb = "当前版本名:" + verName +
                "\n当前版本号:" + verCode +
                "\n服务器版本号:" + serverCode +
                "\n发现新版本，是否更新?";
        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("软件更新").setCancelable(!forceUpdate).setMessage(sb)
                // 设置内容
                .setPositiveButton("更新", // 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (useNotificationDownload) {
                                    notifiDownApkFile();
                                } else {
                                    myDownApkFile();
                                }
                            }
                        })
                .setNegativeButton(forceUpdate ? "退出" : "暂不更新", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 判断强制更新
                        if (forceUpdate) {
                            // 点击"取消"按钮之后退出程序
                            dialog.dismiss();
                            activity.finish();
                            System.exit(0);
                        } else {
                            // 点击"取消"按钮之后退出程序
                            dialog.dismiss();
                        }
                    }
                }).create();// 创建
        // 显示对话框
        if (Build.VERSION.SDK_INT >= 19 && (activity.isDestroyed() || activity.isFinishing())) {
            return;
        }
        dialog.show();
    }

    /**
     * 自己下载器
     */
    private void myDownApkFile() {
        YShow.show(activity, "正在下载");
        YShow.setMessageOther("请稍候...");
        YShow.setCancel(forceUpdate);
        String saveApkName = DownUrl.substring(DownUrl.lastIndexOf("/") + 1);
        final String path = Environment.getExternalStorageDirectory() + "/download/";
        YDownloadAndroid.Download(DownUrl, path, saveApkName, new YDownload.YDownloadListener() {
            @Override
            public void response(int downloadedSize, int fileSize, double progress, boolean isfail, File file) {
                if (activity.isFinishing()) {
                    YShow.finish();
                    return;
                }
                if (isfail) {
                    YShow.setMessage("下载失败");
                    YShow.setMessageOther(null);
                    YShow.setCancel(true);
                    return;
                }
                String success;
                if (downloadedSize > 1048576) {
                    success = "已下载:" + Ynumber.showNumber(downloadedSize / 1048576d, 1) + "MB";
                } else {
                    success = "已下载:" + downloadedSize / 1024 + "KB";
                }
                YShow.setMessage("下载进度" + progress + "%");
                YShow.setMessageOther(success);
                if (downloadedSize == fileSize) {//下载回调
                    if (!activity.isFinishing())
                        YShow.finish();
                    YShow.setMessageOther("下载完成");
                    if (downLoadCompleteListener != null) {
                        downLoadCompleteListener.complete(null, file);
                    } else {
                        YUtils.installApk(activity, file);
                    }
                }
            }
        });
    }

    /**
     * 下载APK
     */
    private void notifiDownApkFile() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            YToast.show(activity, "请打开文件写入权限！");
            return;
        }
        YShow.show(activity, "正在下载");
        YShow.setMessageOther("请稍候...");
        //通知栏下载
        if (yNoticeDownload == null)
            yNoticeDownload = new YNoticeDownload(activity);
        yNoticeDownload.setUrl(DownUrl);
        yNoticeDownload.setAPK(true);
        yNoticeDownload.setDownLoadComplete(new YNoticeDownload.DownLoadComplete() {
            @Override
            public void complete(final Uri uri, File file) {
                if (!activity.isFinishing())
                    YShow.finish();
                if (downLoadCompleteListener != null) {
                    downLoadCompleteListener.complete(uri, file);
                } else {
                    YUtils.installApk(activity, uri);
                }
            }
        });
        yNoticeDownload.setDownLoadFail(new YNoticeDownload.DownLoadFail() {
            @Override
            public void fail() {
                if (!activity.isFinishing()) {
                    YShow.setMessage("下载失败");
                    YShow.setMessageOther(null);
                }
            }
        });
        yNoticeDownload.setDownLoadProgress(new YNoticeDownload.DownLoadProgress() {
            @Override
            public void progress(final long downloaded, final long total, final double progress) {
                if (progress != 100) {
                    String success;
                    if (downloaded > 1048576) {
                        success = "已下载:" + Ynumber.showNumber(downloaded / 1048576d, 1) + "MB";
                    } else {
                        success = "已下载:" + downloaded / 1024 + "KB";
                    }
                    if (!activity.isFinishing()) {
                        YShow.setMessage("下载进度" + progress + "%");
                        YShow.setMessageOther(success);
                    }
                } else {
                    if (!activity.isFinishing()) {
                        YShow.setMessage("下载进度" + progress + "%");
                        YShow.setMessageOther("下载完成");
                    }
                }
            }
        });
        yNoticeDownload.start();
    }

    public void onResume() {
        if (yNoticeDownload != null)
            yNoticeDownload.onResume();
    }

    public void onDestroy() {
        //注销广播
        if (yNoticeDownload != null)
            yNoticeDownload.onDestroy();
    }
}
