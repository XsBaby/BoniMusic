package com.xushuai.work.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xushuai.work.R;
import com.xushuai.work.bean.OnlineMusic;
import com.xushuai.work.utils.FileUtils;
import com.xushuai.work.utils.OnMoreClickListener;
import com.xushuai.work.utils.binding.Bind;
import com.xushuai.work.utils.binding.ViewBinder;

import java.util.List;

/**
 * date:2017/10/10
 * author:徐帅(acer)
 * funcation:
 */

public class WebListAdapter extends RecyclerView.Adapter<WebListAdapter.MyViewHolder> {

    private List<OnlineMusic> list;
    private OnMoreClickListener mListener;
    private Context context;

    public WebListAdapter(List<OnlineMusic> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.view_holder_music, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        OnlineMusic onlineMusic = list.get(position);
        Glide.with(context)
                .load(onlineMusic.getPic_small())
                .placeholder(R.drawable.default_cover)
                .error(R.drawable.default_cover)
                .into(holder.ivCover);
        holder.tvTitle.setText(onlineMusic.getTitle());
        String artist = FileUtils.getArtistAndAlbum(onlineMusic.getArtist_name(), onlineMusic.getAlbum_title());
        holder.tvArtist.setText(artist);
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMoreClick(position);
            }
        });
        holder.vDivider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_cover)
        private ImageView ivCover;
        @Bind(R.id.tv_title)
        private TextView tvTitle;
        @Bind(R.id.tv_artist)
        private TextView tvArtist;
        @Bind(R.id.iv_more)
        private ImageView ivMore;
        @Bind(R.id.v_divider)
        private View vDivider;

        public MyViewHolder(View itemView) {
            super(itemView);
            ViewBinder.bind(this, itemView);
        }
    }

    private boolean isShowDivider(int position) {
        return position != list.size() - 1;
    }

    public void setOnMoreClickListener(OnMoreClickListener listener) {
        mListener = listener;
    }
}