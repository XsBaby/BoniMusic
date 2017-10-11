package com.xushuai.work.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xushuai.work.R;
import com.xushuai.work.bean.JavaBean;
import com.xushuai.work.bean.OnlineMusic;
import com.xushuai.work.bean.OnlineMusicList;
import com.xushuai.work.bean.SongListInfo;
import com.xushuai.work.utils.binding.Bind;
import com.xushuai.work.view.adapter.WebListAdapter;

import java.util.ArrayList;
import java.util.List;

public class WebActivity extends AppCompatActivity {

    private RecyclerView web_list;
    private TextView web_title;
    private ImageView iv_cover;
    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_comment;
    private List<JavaBean.SongListBean> songList = new ArrayList<>();

    private static final int MUSIC_LIST_SIZE = 20;
    private SongListInfo mListInfo;
    private OnlineMusicList mOnlineMusicList;
    private List<OnlineMusic> mMusicList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private int mOffset = 0;
    @Bind(R.id.ll_loading)
    private LinearLayout llLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();

        initData();
    }

    private void initView() {
        web_title = (TextView) findViewById(R.id.web_title);
        iv_cover = (ImageView) findViewById(R.id.iv_cover);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_date = (TextView) findViewById(R.id.tv_update_date);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        web_list = (RecyclerView) findViewById(R.id.web_list);
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        web_list.setLayoutManager(manager);

        getData();

        WebListAdapter webListAdapter = new WebListAdapter(songList, this);
        web_list.setAdapter(webListAdapter);
        webListAdapter.notifyDataSetChanged();
    }

    public void getData() {
//        HttpUtil.sendOkHttpRequest(Api.SONG_MESS, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Gson gson = new Gson();
//                JavaBean bean = gson.fromJson(response.body().string(), JavaBean.class);
//                songList.addAll(bean.getSong_list());
//                System.out.println("bean = " + bean);
//            }
//        });
//        HttpClient.getSongListInfo();
    }
}