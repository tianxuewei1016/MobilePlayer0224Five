package com.atguigu.mobileplayer0224five.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mobileplayer0224five.R;
import com.atguigu.mobileplayer0224five.adapter.RecyclerFragmentAdapter;
import com.atguigu.mobileplayer0224five.base.BaseFragment;
import com.atguigu.mobileplayer0224five.bean.NetAudioBean;
import com.atguigu.mobileplayer0224five.utils.Constant;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/5/29 13:37
 * QQ：93226539
 * 作用：recyclerview
 */

public class RecyclerviewFragment extends BaseFragment {

    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.tv_nomedia)
    TextView tvNomedia;
    private List<NetAudioBean.ListBean> listDatas;
    private RecyclerFragmentAdapter myAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_recyclerview, null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    /**
     * 网络请求
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constant.NET_AUDIO);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "网络音乐请求成功" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "网络音乐请求失败" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        NetAudioBean netAudioBean = new Gson().fromJson(json,NetAudioBean.class);
        listDatas = netAudioBean.getList();
        if(listDatas!=null&&listDatas.size()>0) {
            tvNomedia.setVisibility(View.GONE);
            myAdapter = new RecyclerFragmentAdapter(mContext, listDatas);
            recyclerview.setAdapter(myAdapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        }else{
            tvNomedia.setVisibility(View.VISIBLE);
        }
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
