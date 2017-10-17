package com.xushuai.work.utils;

import com.xushuai.work.bean.LrcBean;

import java.util.List;

/**
 * 创建时间： 2017/10/15.
 * 创建人： 徐帅
 * 类的作用：
 */

public interface ILrcBuilder {
    List<LrcBean> getLrcRows(String rawLrc);
}
