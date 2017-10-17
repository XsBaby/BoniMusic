package com.xushuai.work.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xushuai.work.R;

/**
 * 创建时间： 2017/10/16.
 * 创建人： Leilhang
 * 类的作用：
 */

public class LrcFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_lrc,container,false);
        Bundle bundle = getActivity().getIntent().getExtras();
       // String id = bundle.
        return view;
    }
}
