package com.atguigu.mobileplayer0224five.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.atguigu.mobileplayer0224five.bean.NetAudioBean;

import java.util.List;

/**
 * 作者：田学伟 on 2017/5/29 14:21
 * QQ：93226539
 * 作用：
 */

public class RecyclerFragmentAdapter extends RecyclerView.Adapter{

    private final Context mContext;
    private final List<NetAudioBean.ListBean> datas;

    public RecyclerFragmentAdapter(Context mContext, List<NetAudioBean.ListBean> listDatas) {
        this.mContext = mContext;
        this.datas = listDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
