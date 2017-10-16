package com.xushuai.work.view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xushuai.work.R;
import com.xushuai.work.bean.DownloadInfo;
import com.xushuai.work.bean.Lrc;
import com.xushuai.work.bean.MusicLrc;
import com.xushuai.work.net.HttpCallback;
import com.xushuai.work.net.HttpClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SongActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private ObjectAnimator discAnimation, needleAnimation;
    private boolean isPlaying = true;
    private ImageView disc, needle;
    private TextView textView, nameText, sekText;
    private String name, song_id, song_name;
    private String findk;
    private MediaPlayer mediaPlayer;
    private SeekBar smk;
    private Handler handler = new Handler();
    private Timer timer;
    private String lrcContent;
    private Lrc lrcHandler;
    private List<String> words = new ArrayList<>();//歌词集合
    private List<Integer> times = new ArrayList<>();//时间集合
    private Timer timerLrc;
    private TimerTask taskLrc;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        mediaPlayer = new MediaPlayer();
        timer = new Timer();
        initVIew();
        setAnimations();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                smk.setProgress(0);
            }
        });
    }

    private void lodaData() {
        HttpClient.getMusicDownloadInfo(song_id, new HttpCallback<DownloadInfo>() {

            @Override
            public void onSuccess(DownloadInfo downloadInfo) {
                findk = downloadInfo.getBitrate().getFile_link();
                playing();
                isStart();
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    /**
     * 请求歌词
     */
    private void loadLrcData() {
        HttpClient.getLrc(song_id, new HttpCallback<MusicLrc>() {
            @Override
            public void onSuccess(MusicLrc lrc) {
                lrcContent = lrc.getLrcContent();
                words.add(lrcContent);
                setTextLrc();
                getLrc(lrcContent);

            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    private void initVIew() {
        smk = (SeekBar) findViewById(R.id.sk);
        disc = (ImageView) findViewById(R.id.disc);
        needle = (ImageView) findViewById(R.id.needle);
        img = (ImageView) findViewById(R.id.iv_play);
        textView = (TextView) findViewById(R.id.tv_music_text);
        nameText = (TextView) findViewById(R.id.name_text);
        sekText = (TextView) findViewById(R.id.music);
        tv = (TextView) findViewById(R.id.lrc_text);

        setProessListen();
        img.setOnClickListener(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("title");
        song_name = intent.getStringExtra("songer_name");
        song_id = intent.getStringExtra("song_id");
        lodaData();
        loadLrcData();
        textView.setText(name);
        nameText.setText(song_name);
        tv.setSelected(true);
    }

    public void setProessListen() {
        timer.schedule(new Take(), 0, 1000);
        smk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //动画设置
    private void setAnimations() {
        discAnimation = ObjectAnimator.ofFloat(disc, "rotation", 0, 360);
        discAnimation.setDuration(20000);
        discAnimation.setInterpolator(new LinearInterpolator());
        discAnimation.setRepeatCount(ValueAnimator.INFINITE);

        needleAnimation = ObjectAnimator.ofFloat(needle, "rotation", 0, 25);
        needle.setPivotX(0);
        needle.setPivotY(0);
        needleAnimation.setDuration(800);
        needleAnimation.setInterpolator(new LinearInterpolator());
    }

    //播放时动画设置和图片切换
    private void playing() {
        needleAnimation.start();
        discAnimation.start();
        img.setImageResource(R.drawable.ic_play_btn_pause);
        isPlaying = false;
    }

    @Override
    public void onClick(View view) {
        if (isPlaying) {
            playing();
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            smk.setProgress(0);

        } else {
            if (needleAnimation != null) {
                needleAnimation.reverse();
                needleAnimation.end();
            }
            if (discAnimation != null && discAnimation.isRunning()) {
                discAnimation.cancel();
                float valueAvatar = (float) discAnimation.getAnimatedValue();
                discAnimation.setFloatValues(valueAvatar, 360f + valueAvatar);
            }
            img.setImageResource(R.drawable.ic_play_btn_play);
            isPlaying = true;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
    }

    /**
     * 歌曲播放
     */
    public void isStart() {
        try {
            mediaPlayer.setDataSource(findk);
            mediaPlayer.prepare();
            smk.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            setTimer();
        }
    }

    class Take extends TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (mediaPlayer.isPlaying()) {
                final int progress = mediaPlayer.getCurrentPosition();
                smk.setProgress(progress);
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                        String f1 = format.format(progress);
                        String f2 = format.format(mediaPlayer.getDuration());
                        sekText.setText(f1 + "/" + f2);
                    }
                });
            }
        }
    }

    private void getLrc(String file) {
        words.add(file);
        //创建一个新的歌词对象
//    lrcHandler = new Lrc();
//    words = lrcHandler.getmWords();//拿出getmWords里面的数据放入words
//    times = lrcHandler.getmTimes();
        Log.e("getLrc===", "getLrc: " + words);
        //给歌词设置开头
        if (words.size() > 0) {
            tv.setText(words.get(0));//将歌词集合words里面的输出拿出来传到sv里面显示

        } else {
            tv.setText("歌曲没有歌词，快下载最新歌词吧 。。。");
        }
    }

    int time = 0;//记录当前时间的参数

    public void setTimer() {
        //创建一个新的计时器对象
        timerLrc = new Timer();
        //开启一个新的计时任务
        //设置歌词
        taskLrc = new TimerTask() {
            @Override
            public void run() {
                setTextLrc();//设置歌词
            }
        };
        timer.schedule(taskLrc, 0, 9);//每9微秒开始计时一次
        //当结束的时候清空
        if (time == mediaPlayer.getDuration()) {
            mediaPlayer.stop();
            time = 0;
            taskLrc.cancel();//停止计时器
            mediaPlayer = null;
        }
    }

    private void setTextLrc() {
        time++;//time每9毫秒开始加一次
        for (int j = 0; j < times.size(); j++) {
            if (time == times.get(j)) {//查看集合times的数据是否与time相同
                final int f = j;//调用内部类的方法
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        tv.setText(words.get(f));//从words里面取出第F个元素显示到tv
                    }
                });
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();//停止播放
        time = 0;//初始化计时器
        taskLrc.cancel();//停止执行任务
        mediaPlayer = null;//将播放器置为空
    }
}