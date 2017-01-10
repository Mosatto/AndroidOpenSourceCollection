package com.glumes.opensource.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glumes.opensource.R;
import com.glumes.opensource.net.entity.BaseResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by zhaoying on 2017/1/9.
 */

public class InfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_HEADER = 1;
    private static final int ITEM_BASE = 2;
    private static final int ITEM_FOOTER = 3;

    private List<BaseResult> mData;
    private Context mContext;


    public InfoListAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public InfoListAdapter(List<BaseResult> results, Context context) {
        mData = results;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_FOOTER){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_refresh_footer, parent, false);
            return new FooterViewHolder(view);
        }


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position + 1 != getItemCount()){
            ((ItemViewHolder)holder).mTvItemTitle.setText(mData.get(position).getDesc() == null ? "error" : mData.get
                    (position).getDesc());
            Timber.d(mData.get(position).getDesc());
            ((ItemViewHolder)holder).mTvItemPublisher.setText(mData.get(position).getWho() == null ? "error" : mData
                    .get(position).getWho());
            ((ItemViewHolder)holder).mTvItemTime.setText(mData.get(position).getPublishedAt() == null ? "error" :
                    mData.get(position).getPublishedAt());
            if (mData.get(position).getImages() != null  ){
                Timber.d(mData.get(position).getImages().get(0) +  "?imageView2/0/w/200");
                Glide.with(mContext).load(mData.get(position).getImages().get(0) + "?imageView2/0/w/200").into((
                        (ItemViewHolder)holder).mIvItemImg);
            }else {
                Glide.with(mContext).load(R.mipmap.image_default).into(((ItemViewHolder)holder)
                        .mIvItemImg);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size() + 1 ;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return ITEM_FOOTER ;
        }
        return ITEM_BASE ;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item_img)
        ImageView mIvItemImg;
        @BindView(R.id.tv_item_title)
        AppCompatTextView mTvItemTitle;
        @BindView(R.id.tv_item_publisher)
        AppCompatTextView mTvItemPublisher;
        @BindView(R.id.tv_item_time)
        AppCompatTextView mTvItemTime;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_load);
        }
    }

    public List<BaseResult> getData() {
        return mData;
    }

    public void setData(List<BaseResult> data) {
        mData.addAll(data);
    }

}
