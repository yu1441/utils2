package com.topkl.libapp;

import java.util.List;
import java.util.Map;

public class TestUser {
    public String 名字;
    public String 其他;
    public Map<String, Object> map;
    public List<?> list;
    public TestUser 朋友;

    @Override
    public String toString() {
        return "TestUser{" +
                "名字='" + 名字 + '\'' +
                ", 其他='" + 其他 + '\'' +
                ", map=" + map +
                ", list=" + list +
                ", 朋友=" + 朋友 +
                '}';
    }
}
