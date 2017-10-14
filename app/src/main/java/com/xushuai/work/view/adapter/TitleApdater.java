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
import com.xushuai.work.utils.FileUtils;

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
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    public TitleApdater(Context context, List<OnlineMusic> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }

        return TYPE_NORMAL;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new BaseViewHolder(mHeaderView, mItemClickListener);
        }
        BaseViewHolder baseViewHolder = new BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.recy_item, parent, false), mItemClickListener);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;
        final int pos = getRealPosition(holder);
        final  OnlineMusic line = list.get(pos);
        holder.title.setText(line.getTitle());
        String attr = FileUtils.getArtistAndAlbum(line.getArtist_name(),line.getAlbum_title());
        holder.name.setText(attr);
        Glide.with(context).load(line.getPic_small()).into(holder.imageView);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? list.size() : list.size() + 1;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, name;
        ImageView imageView;
        private MyItemClickListener itemClickListener;

        public BaseViewHolder(View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            if (itemView == mHeaderView)
                return;
            title = (TextView) itemView.findViewById(R.id.tv_second);
            name = (TextView) itemView.findViewById(R.id.tv_thred);
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