package com.xushuai.work.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xushuai.work.R;
import com.xushuai.work.bean.JavaBean;

import java.util.List;

/**
 * date:2017/10/10
 * author:徐帅(acer)
 * funcation:
 */

public class WebListAdapter extends RecyclerView.Adapter<WebListAdapter.MyViewHolder> {

    private List<JavaBean.SongListBean> list;
    private Context context;

    public WebListAdapter(List<JavaBean.SongListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.web_list_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getPic_small()).into(holder.pic_small);
        holder.artist_name.setText(list.get(position).getArtist_name());
        holder.album_title.setText(list.get(position).getAlbum_title());
        holder.album_title2.setText(list.get(position).getAlbum_title());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView pic_small;
        TextView album_title, artist_name, album_title2;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic_small = (ImageView) itemView.findViewById(R.id.pic_small);
            album_title = (TextView) itemView.findViewById(R.id.album_title);
            album_title2 = (TextView) itemView.findViewById(R.id.album_title2);
            artist_name = (TextView) itemView.findViewById(R.id.artist_name);
        }
    }
}