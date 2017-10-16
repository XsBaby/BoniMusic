package com.xushuai.work.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建时间： 2017/10/13.
 * 创建人： Leilhang
 * 类的作用：
 */

public class Lrc {
    private List<String> mWords;
    private List<Integer> mTimes;

    //初始化歌词的时候去初始化  集合
    public Lrc() {
        mWords = new ArrayList<>();
        mTimes = new ArrayList<>();
    }


    //歌词 和 时间的 获取方法
    public List<String> getmWords() {
        return mWords;
    }

    public List<Integer> getmTimes() {
        return mTimes;
    }


    //设置歌词的时间集合
    private void addTimeList(String s) {
        Matcher matcher = Pattern.compile("\\[\\d{1,2}:\\d{1,2}([\\.:]\\d{1,2})?\\]").matcher(s);//正则表达式解析时间
        if (matcher.find()) {//判断能否拿到数据
            String group = matcher.group();//从正则表达式拿到数据分组

            mTimes.add(timeHandler(group.substring(1, group.length() - 1)));//将group里面的数据转换为微秒添加到mTimes里面
        }
    }

    //分离时间
    private int timeHandler(String substring) {
        String replace = substring.replace(".", ":");//将数据里面.转换为：（符号替换用的）
        String[] timeData = replace.split(":");//以：为分割线读取数据添加到数组timeData

        int minute = Integer.parseInt(timeData[0]);//将里面的第一个数据转换为Int类型存到minute
        int second = Integer.parseInt(timeData[1]);
        int millisecond = Integer.parseInt(timeData[2]);


        int currentTime = (minute * 60 + second) * 100 + millisecond;//单位运算

        return currentTime;//将数据返回到currentTime
    }

    //解析歌词的方法
    public void readLrc(InputStream inputStream) {
        try {
            InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");//读取的输入流
            BufferedReader br = new BufferedReader(isr);//将读取流存放在buffreader里面
            String s = "";//申明一个空的字符串
            while ((s = br.readLine()) != null) {//从br里面读取一行数据
                addTimeList(s);//读取时间

                if ((s.indexOf("[ar:") != -1) || (s.indexOf("[ti:") != -1)//如果有[ar：, [ti： , [by： 开头提取数据
                        || (s.indexOf("[by:") != -1)) {
                    s = s.substring(s.indexOf(":") + 1, s.indexOf("]"));//将提取的数据存放到s里面
                } else {//以[开头,]结尾取出字符串
                    String ss = s.substring(s.indexOf("["), s.indexOf("]") + 1);//组成一个新的字符串放入到ss里面
                    s = s.replace(ss, "");//把ss替换为空
                }
                mWords.add(s);//将s添加到mWords里面
            }
            br.close();
            isr.close();//将流关闭


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
