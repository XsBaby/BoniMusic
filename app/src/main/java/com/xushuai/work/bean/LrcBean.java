package com.xushuai.work.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 创建时间： 2017/10/15.
 * 创建人： Leilhang
 * 类的作用：
 */

public class LrcBean implements Comparator<LrcBean> {
    public final static String TAG = "LrcRow";
    public String strTime;
    public long time;
    public String content;

    public LrcBean(String strTime, long time, String content) {
        this.strTime = strTime;
        this.time = time;
        this.content = content;
    }

    @Override
    public String toString() {
        return
                strTime + '\'' +
                        time +
                        content + '\'';
    }

    public static List<LrcBean> createLrc(String standardLrcLine) {
        if (standardLrcLine.indexOf("[") != 0 || standardLrcLine.indexOf("]") != 9) {
            return null;
        }
        int lastIndexOfRightBracket = standardLrcLine.lastIndexOf("]");

        String content = standardLrcLine.substring(lastIndexOfRightBracket + 1, standardLrcLine.length());

        String times = standardLrcLine.substring(0, lastIndexOfRightBracket + 1).replace("[", "-").replace("]", "-");

        String arrTimes[] = times.split("-");
        List<LrcBean> listTimes = new ArrayList<LrcBean>();
        for (String temp : arrTimes) {
            if (temp.trim().length() == 0) {
                continue;
            }
            LrcBean lrcBean = new LrcBean(temp, timeConvert(temp), content);
            listTimes.add(lrcBean);
        }
        return listTimes;
    }

    private static long timeConvert(String timeString) {
        int valueds = 0;
        int valueMin = 0;
        int valueSS = 0;
        //因为给如的字符串的时间格式为XX:XX.XX,返回的long要求是以毫秒为单位
        //将字符串 XX:XX.XX 转换为 XX:XX:XX
        timeString = timeString.replace('.', ':');
        //将字符串 XX:XX:XX 拆分
        String[] times = timeString.split(":");
        // mm:ss:SS
        try {
            valueds = Integer.valueOf(times[0].toString().trim()) * 60 * 1000;
            valueMin = Integer.valueOf(times[1].toString().trim()) * 1000;
            valueSS = Integer.valueOf(times[2].toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
//                return Integer.valueOf(times[0]) * 60 * 1000 +//分
//                    Integer.valueOf(times[1]) * 1000 +//秒
//                    Integer.valueOf(times[2]) ;//毫秒
        return valueds + valueMin + valueSS;
    }

    @Override
    public int compare(LrcBean lrcBean, LrcBean t1) {
        return 0;
    }
}