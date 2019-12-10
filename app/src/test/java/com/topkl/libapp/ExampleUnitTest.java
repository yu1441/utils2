package com.topkl.libapp;

import com.yujing.utils.YConvert;

import org.junit.Test;
import org.nustaq.serialization.FSTConfiguration;

import java.io.Serializable;
import java.util.Arrays;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();

    public static <Object extends Serializable>byte[] serialize(Object obj) {
//        return configuration.asByteArray(obj);
        return YConvert.object2Bytes(obj);
    }

    public static Object deserialize(byte[] sec) {
//        return configuration.asObject(sec);
        return YConvert.bytes2Object(sec);
    }

    @Test
    public void addition_isCorrect() {
        byte[] b1=serialize("当前进度：哈哈");
        byte[] b2=serialize(2);
        byte[] b3=serialize(3d);
        byte[] b4= serialize(new byte[]{66, 67});
        byte[] b5= serialize(true);
        byte[] b6= serialize(new User());

        System.out.println("B1="+new String(b1));
        System.out.println("B2="+new String(b2));
        System.out.println("B3="+new String(b3));
        System.out.println("B4="+new String(b4));
        System.out.println("B5="+new String(b5));
        System.out.println("B6="+new String(b6));
        System.out.println("B6="+Arrays.toString(b6));

        System.out.println("B1="+deserialize(b1));
        System.out.println("B2="+deserialize(b2));
        System.out.println("B3="+deserialize(b3));
        System.out.println("B4="+ Arrays.toString((byte[])deserialize(b4)));
        System.out.println("B5="+deserialize(b5));
        System.out.println("B6="+((User)deserialize(b6)).toString());

    }


}