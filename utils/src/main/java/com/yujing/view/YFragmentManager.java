package com.yujing.view;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * fragment管理器
 * @author yujing 2019年11月15日11:07:23
 */
public class YFragmentManager {
    private Fragment currentFragment;//当前fragment
    private int layout; //布局
    private FragmentManager fragmentManager; //管理器
    /**
     * 构造函数
     *
     * @param layout          布局
     * @param fragmentManager 管理器
     */
    public YFragmentManager(@IdRes int layout, FragmentManager fragmentManager) {
        this.layout = layout;
        this.fragmentManager = fragmentManager;
    }

    /**
     * 构造函数
     *
     * @param layout           布局
     * @param fragmentActivity fragmentActivity
     */
    public YFragmentManager(@IdRes int layout, FragmentActivity fragmentActivity) {
        this.layout = layout;
        this.fragmentManager = fragmentActivity.getSupportFragmentManager();
    }

    /**
     * 显示fragment
     *
     * @param targetFragment fragment
     */
    public void showFragment(Fragment targetFragment) {
        FragmentTransaction transaction = fragmentManager
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentFragment != null) transaction.hide(currentFragment);
            transaction.add(layout, targetFragment).commit();
        } else {
            if (currentFragment != null && !currentFragment.equals(targetFragment)) {
                transaction.hide(currentFragment);
            }
            transaction.show(targetFragment).commit();
        }
        currentFragment = targetFragment;//记录当前fragment
    }

    /**
     * 隐藏fragment
     *
     * @param targetFragment targetFragment
     */
    public void hideFragment(Fragment targetFragment) {
        if (targetFragment.isAdded())
            fragmentManager.beginTransaction().hide(targetFragment).commit();
    }

    /**
     * 隐藏当前fragment
     */
    public void hideCurrent() {
        if (currentFragment != null && currentFragment.isAdded())
            fragmentManager.beginTransaction().hide(currentFragment).commit();
    }

    /**
     * 显示当前fragment
     */
    public void showCurrent() {
        if (currentFragment != null && currentFragment.isAdded())
            fragmentManager.beginTransaction().show(currentFragment).commit();
    }
}
