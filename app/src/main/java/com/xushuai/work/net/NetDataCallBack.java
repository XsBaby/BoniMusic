package com.xushuai.work.net;

/**
 * 创建时间： 2017/9/22.
 * 创建人： 徐帅
 * 类的作用：
 */

public interface NetDataCallBack<T> {
    void success(T t);
    void faild(int positon, String str);
}