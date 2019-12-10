package com.topkl.libapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.yujing.utils.ObjectStorageFST;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.topkl.libapp", appContext.getPackageName());
        ObjectStorageFST.put(appContext, "aa1", "当前进度：哈哈");
        ObjectStorageFST.put(appContext, "aa2", 2);
        ObjectStorageFST.put(appContext, "aa3", 3d);
        ObjectStorageFST.put(appContext, "aa4", new byte[]{66, 67});
        ObjectStorageFST.put(appContext, "aa5", true);
        ObjectStorageFST.put(appContext, "aa6", new User());

        Toast.makeText(appContext, "" + ObjectStorageFST.get(appContext, "aa1"), Toast.LENGTH_SHORT).show();
        Toast.makeText(appContext, "" + ObjectStorageFST.get(appContext, "aa2"), Toast.LENGTH_SHORT).show();
        Toast.makeText(appContext, "" + ObjectStorageFST.get(appContext, "aa3"), Toast.LENGTH_SHORT).show();
        Toast.makeText(appContext, "" + (byte[]) ObjectStorageFST.get(appContext, "aa4"), Toast.LENGTH_SHORT).show();
        Toast.makeText(appContext, "" + ObjectStorageFST.get(appContext, "aa5"), Toast.LENGTH_SHORT).show();
        Toast.makeText(appContext, "" + ((User) ObjectStorageFST.get(appContext, "aa6")).toString(), Toast.LENGTH_SHORT).show();

    }
}
