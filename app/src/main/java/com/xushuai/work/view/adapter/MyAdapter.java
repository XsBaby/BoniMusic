package com.xushuai.work.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xushuai.work.view.fragment.MyFragment;
import com.xushuai.work.view.fragment.ZxFragment;

/**
 * date:2017/9/28
 * author:徐帅(acer)
 * funcation: 主界面ViewPager的适配器类
 */

public class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyFragment();
                break;
            case 1:
                fragment = new ZxFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}