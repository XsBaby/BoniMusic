package com.xushuai.work.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xushuai.work.R;
import com.xushuai.work.bean.OnlineMusic;

import java.util.List;

/**
 * 创建时间： 2017/9/29.
 * 创建人： 徐帅
 * 类的作用：
 */

public class TitleApdater extends RecyclerView.Adapter<TitleApdater.BaseViewHolder> {
    private Context context;
    private List<OnlineMusic> list;
    private MyItemClickListener mItemClickListener;

    public TitleApdater(Context context, List<OnlineMusic> list) {
        this.context = context;
        this.list = list;
    }

    @Override

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = new BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.recy_item, parent, false), mItemClickListener);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.name.setText(list.get(position).getArtist_name());
        holder.song.setText(list.get(position).getAlbum_title());
        Glide.with(context).load(list.get(position).getPic_small()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, name, song;
        ImageView imageView;
        private MyItemClickListener itemClickListener;

        public BaseViewHolder(View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_second);
            name = (TextView) itemView.findViewById(R.id.tv_thred);
            song = (TextView) itemView.findViewById(R.id.tv_song);
            imageView = (ImageView) itemView.findViewById(R.id.iv_img);
            this.itemClickListener = mItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //判断点击事件位置
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, getPosition());
            }
        }
    }

    //接口回调
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(MyItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
