package com.xushuai.work.utils;

/**
 * 创建时间： 2017/9/29.
 * 创建人： 徐帅
 * 类的作用：
 */

public class Api {

    //百度音乐接口
    public static String SONG = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    //获取音乐信息
    public static String SONG_MESS = SONG + "?method=baidu.ting.billboard.billList&type=1&size=10&offset=0";
    //获取改变信息
    public static String SONG_MESS_URL = SONG + SONG_MESS + "&type=1&size=10&offset=0";

}