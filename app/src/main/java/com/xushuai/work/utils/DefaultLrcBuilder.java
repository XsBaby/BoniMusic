package com.xushuai.work.utils;

import android.util.Log;

import com.xushuai.work.bean.LrcBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/10/15.
 * 创建人： Leilhang
 * 类的作用：
 */

public class DefaultLrcBuilder implements ILrcBuilder {
    static final String TAG = "DefaultLrcBuilder";

    @Override
    public List<LrcBean> getLrcRows(String rawLrc) {
        if (rawLrc == null || rawLrc.length() == 0) {
            Log.e(TAG, "getLrcRows rawLrc null or empty");
            return null;
        }
        StringReader reader = new StringReader(rawLrc);
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        List<LrcBean> rows = new ArrayList<LrcBean>();

        try {
            do {
                line = br.readLine();
                if (line != null && line.length() > 0) {
                    List<LrcBean> lrcRows = LrcBean.createLrc(line);
                    if (lrcRows != null && lrcRows.size() > 0) {
                        for (LrcBean row : lrcRows) {
                            rows.add(row);
                        }
                    }
                }
            } while (line != null);
            if (rows.size() > 0) {
                // 根据歌词行的时间排序
                // Collections.sort(rows);
                if (rows != null && rows.size() > 0) {
                    for (LrcBean lrcRow : rows) {
                        Log.d(TAG, "lrcRow:" + lrcRow.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();
        }
        return rows;
    }
}