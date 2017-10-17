package com.xushuai.work.view.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xushuai.work.bean.LrcBean;

import java.util.List;

/**
 * 创建时间： 2017/10/15.
 * 创建人： 徐帅
 * 类的作用：
 */

public class FirstLrcView extends View {
    public final static String TAG = "LrcView";
    public final static int DISPLAY_MODE_NORMAL = 0;

    private int mDisplayMode = DISPLAY_MODE_NORMAL;
    /**
     * 歌词集合，包含所有行的歌词
     */
    private List<LrcBean> mLrcRows;
    /**
     * 当前高亮歌词的行数
     */
    private int mHignlightRow = 0;
    /**
     * 当前高亮歌词的字体颜色为黄色
     */
    private int mHignlightRowColor = Color.YELLOW;
    /**
     * 不高亮歌词的字体颜色为白色
     */
    private int mNormalRowColor = Color.WHITE;
    /**
     * 歌词字体大小默认值
     **/
    private int mLrcFontSize = 23;    // font size of lrc
    /**
     * 歌词字体大小最小值
     **/
    private int mMinLrcFontSize = 15;
    /**
     * 歌词字体大小最大值
     **/
    private int mMaxLrcFontSize = 35;

    /**
     * 两行歌词之间的间距
     **/
    private int mPaddingY = 5;
    /**
     * 当没有歌词的时候展示的内容
     **/
    private String mLoadingLrcTip = "Downloading lrc...";

    private Paint mPaint;

    public FirstLrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mLrcFontSize);
    }

    /**
     * 实现onDrow方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight(); // height of this view
        final int width = getWidth(); // width of this view
        //当没有歌词的时候
        if (mLrcRows == null || mLrcRows.size() == 0) {
            if (mLoadingLrcTip != null) {
                // draw tip when no lrc.
                mPaint.setColor(mHignlightRowColor);
                mPaint.setTextSize(mLrcFontSize);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mLoadingLrcTip, width / 2, height / 2 - mLrcFontSize, mPaint);
            }
            return;
        }
        int rowY = 0; // vertical point of each row.
        final int rowX = width / 2;
        int rowNum = 0;

        String highlightText = mLrcRows.get(mHignlightRow).content;
        int highlightRowY = height / 2 - mLrcFontSize;
        mPaint.setColor(mHignlightRowColor);
        mPaint.setTextSize(mLrcFontSize);
        mPaint.setTextAlign(Align.CENTER);
        canvas.drawText(highlightText, rowX, highlightRowY, mPaint);

        // 2、画出正在播放的那句歌词的上面可以展示出来的歌词
        mPaint.setColor(mNormalRowColor);
        mPaint.setTextSize(mLrcFontSize);
        mPaint.setTextAlign(Align.CENTER);
        rowNum = mHignlightRow - 1;
        rowY = highlightRowY - mPaddingY - mLrcFontSize;
        //只画出正在播放的那句歌词的上一句歌词
        if (rowY > -mLrcFontSize && rowNum >= 0) {
            String text = mLrcRows.get(rowNum).content;
            canvas.drawText(text, rowX, rowY, mPaint);
        }

    }

    /**
     * 设置歌词行集合
     *
     * @param lrcRows
     */
    public void setLrc(List<LrcBean> lrcRows) {
        mLrcRows = lrcRows;
        invalidate();
    }


    /**
     * 设置要高亮的歌词为第几行歌词
     *
     * @param position 要高亮的歌词行数
     * @param cb       是否是手指拖动后要高亮的歌词
     */
    public void seekLrc(int position, boolean cb) {
        if (mLrcRows == null || position < 0 || position > mLrcRows.size()) {
            return;
        }
        LrcBean lrcRow = mLrcRows.get(position);
        mHignlightRow = position;
        invalidate();
    }


    /**
     * 播放的时候调用该方法滚动歌词，高亮正在播放的那句歌词
     *
     * @param time
     */
    public void seekLrcToTime(long time) {
        if (mLrcRows == null || mLrcRows.size() == 0) {
            return;
        }
        if (mDisplayMode != DISPLAY_MODE_NORMAL) {
            return;
        }
        Log.d(TAG, "seekLrcToTime:" + time);

        for (int i = 0; i < mLrcRows.size(); i++) {
            LrcBean current = mLrcRows.get(i);
            LrcBean next = i + 1 == mLrcRows.size() ? null : mLrcRows.get(i + 1);
            /**
             *  正在播放的时间大于current行的歌词的时间而小于next行歌词的时间， 设置要高亮的行为current行
             *  正在播放的时间大于current行的歌词，而current行为最后一句歌词时，设置要高亮的行为current行
             */
            if ((time >= current.time && next != null && time < next.time)
                    || (time > current.time && next == null)) {
                seekLrc(i, false);
                return;
            }
        }
    }
}