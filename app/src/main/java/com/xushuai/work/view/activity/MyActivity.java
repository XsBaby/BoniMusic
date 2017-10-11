package com.xushuai.work.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.slidingmenu.SlidingMenu;
import com.xushuai.work.R;
import com.xushuai.work.view.adapter.MyAdapter;

import java.util.ArrayList;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager my_vp;
    private ArrayList<TextView> textlist;
    private TextView top_my;
    private TextView top_zx;
    private ImageView top_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //查找控件
        initView();

        //添加数据
        initData();

        //侧滑菜单的方法
        getSlidingMenu();
    }

    private void initView() {
        my_vp = (ViewPager) findViewById(R.id.my_vp);
        top_my = (TextView) findViewById(R.id.top_my);
        top_zx = (TextView) findViewById(R.id.top_zx);
        top_menu = (ImageView) findViewById(R.id.top_menu);
    }

    private void initData() {
        textlist = new ArrayList<>();
        textlist.add(top_my);
        textlist.add(top_zx);

        //给两个TextView添加监听事件
        top_my.setOnClickListener(this);
        top_zx.setOnClickListener(this);
        top_menu.setOnClickListener(this);

        //当ViewPager滑动改变时
        my_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //改变字体颜色	//先获取对应的id
                int id = textlist.get(position).getId();
                changeColor(id);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 设置数据适配器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        my_vp.setAdapter(adapter);
    }

    //改变颜色的方法
    public void changeColor(int id) {
        for (TextView tv : textlist) {
            if (tv.getId() == id) {
                tv.setTextColor(Color.WHITE);
            } else {
                tv.setTextColor(this.getResources().getColor(R.color.textcolor));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_my:
                my_vp.setCurrentItem(0);
                break;
            case R.id.top_zx:
                my_vp.setCurrentItem(1);
                break;
            case R.id.top_menu:
                //侧滑菜单的方法
//                getSlidingMenu();
                break;
        }
    }

    public void getSlidingMenu() {
        // 实例化滑动菜单对象
        SlidingMenu menu = new SlidingMenu(this);
        // 设置为左滑菜单
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置滑动阴影的宽度
        menu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动阴影的图像资源
        menu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单划出时主页面显示的剩余宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        // 附加在Activity上
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // 设置滑动菜单的布局
        menu.setMenu(R.layout.left_menu);
    }
}