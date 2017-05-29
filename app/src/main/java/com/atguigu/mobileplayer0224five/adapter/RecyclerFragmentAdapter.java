package com.atguigu.mobileplayer0224five.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.mobileplayer0224five.R;
import com.atguigu.mobileplayer0224five.activity.PicassoSampleActivity;
import com.atguigu.mobileplayer0224five.bean.NetAudioBean;
import com.atguigu.mobileplayer0224five.utils.Constant;
import com.atguigu.mobileplayer0224five.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.xutils.x;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 作者：田学伟 on 2017/5/29 14:21
 * QQ：93226539
 * 作用：
 */

public class RecyclerFragmentAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final List<NetAudioBean.ListBean> datas;
    /**
     * 视频
     */
    private static final int TYPE_VIDEO = 0;

    /**
     * 图片
     */
    private static final int TYPE_IMAGE = 1;

    /**
     * 文字
     */
    private static final int TYPE_TEXT = 2;

    /**
     * GIF图片
     */
    private static final int TYPE_GIF = 3;


    /**
     * 软件推广
     */
    private static final int TYPE_AD = 4;

    public RecyclerFragmentAdapter(Context mContext, List<NetAudioBean.ListBean> listDatas) {
        this.mContext = mContext;
        this.datas = listDatas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 得到类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        String type = datas.get(position).getType();
        int itemViewType = TYPE_VIDEO;
        if ("video".equals(type)) {
            itemViewType = TYPE_VIDEO;
        } else if ("image".equals(type)) {
            itemViewType = TYPE_IMAGE;
        } else if ("text".equals(type)) {
            itemViewType = TYPE_TEXT;
        } else if ("gif".equals(type)) {
            itemViewType = TYPE_GIF;
        } else {
            itemViewType = TYPE_AD;//广播
        }
        return itemViewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_VIDEO://视频
                viewHolder = new VideoHoder(View.inflate(mContext, R.layout.all_video_item, null));
                break;
            case TYPE_IMAGE://图片
                viewHolder = new ImageHolder(View.inflate(mContext, R.layout.all_image_item, null));
                break;
            case TYPE_TEXT://文字
                viewHolder = new TextHolder(View.inflate(mContext, R.layout.all_text_item, null));

                break;
            case TYPE_GIF://gif
                viewHolder = new GifHolder(View.inflate(mContext, R.layout.all_gif_item, null));

                break;
            case TYPE_AD://软件广告
                viewHolder = new ADHolder(View.inflate(mContext, R.layout.all_ad_item, null));
                break;
            default:
                viewHolder = new ADHolder(View.inflate(mContext, R.layout.all_ad_item, null));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_VIDEO) {
            VideoHoder videoHoder = (VideoHoder) holder;
            videoHoder.setData(datas.get(position));
        } else if (itemViewType == TYPE_IMAGE) {
            ImageHolder imageHolder = (ImageHolder) holder;
            imageHolder.setData(datas.get(position));
        }else if(itemViewType == TYPE_TEXT) {
            TextHolder textHolder = (TextHolder) holder;
            textHolder.setData(datas.get(position));
        }
    }

    class VideoHoder extends BaseViewHolder {
        Utils utils;
        TextView tvContext;
        JCVideoPlayerStandard jcvVideoplayer;
        TextView tvPlayNums;
        TextView tvVideoDuration;
        ImageView ivCommant;
        TextView tvCommantContext;

        public VideoHoder(View convertView) {
            super(convertView);
            //中间公共部分 -所有的都有
            tvContext = (TextView) convertView.findViewById(R.id.tv_context);
            utils = new Utils();
            tvPlayNums = (TextView) convertView.findViewById(R.id.tv_play_nums);
            tvVideoDuration = (TextView) convertView.findViewById(R.id.tv_video_duration);
            ivCommant = (ImageView) convertView.findViewById(R.id.iv_commant);
            tvCommantContext = (TextView) convertView.findViewById(R.id.tv_commant_context);
            jcvVideoplayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.jcv_videoplayer);
        }

        @Override
        public void setData(NetAudioBean.ListBean mediaItem) {
            super.setData(mediaItem);
            //设置文本-所有的都有,只有广告没有哦
            tvContext.setText(mediaItem.getText() + "_" + mediaItem.getType());

            //视频特有的------------------------
            //第一个参数是视频播放地址，第二个参数是显示封面的地址，第三参数是标题
            boolean setUp = jcvVideoplayer.setUp(Constant.BASE_URL + mediaItem.getVideo().getVideo().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    "");
            //加载图片
            if (setUp) {
                //设置默认封面
                Glide.with(mContext).load(Constant.BASE_URL + mediaItem.getVideo().getThumbnail().get(0)).into(jcvVideoplayer.thumbImageView);
            }
            tvPlayNums.setText(mediaItem.getVideo().getPlaycount() + "次播放");
            tvVideoDuration.setText(utils.stringForTime(mediaItem.getVideo().getDuration() * 1000) + "");

        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHeadpic;
        TextView tvName;
        TextView tvTimeRefresh;
        ImageView ivRightMore;
        ImageView ivVideoKind;
        TextView tvVideoKindText;
        TextView tvShenheDingNumber;
        TextView tvShenheCaiNumber;
        TextView tvPostsNumber;
        LinearLayout llDownload;

        public BaseViewHolder(View convertView) {
            super(convertView);
            //公共的
            ivHeadpic = (ImageView) convertView.findViewById(R.id.iv_headpic);
            tvName = (TextView) convertView.findViewById(R.id.tv_name);
            tvTimeRefresh = (TextView) convertView.findViewById(R.id.tv_time_refresh);
            ivRightMore = (ImageView) convertView.findViewById(R.id.iv_right_more);
            //bottom
            ivVideoKind = (ImageView) convertView.findViewById(R.id.iv_video_kind);
            tvVideoKindText = (TextView) convertView.findViewById(R.id.tv_video_kind_text);
            tvShenheDingNumber = (TextView) convertView.findViewById(R.id.tv_shenhe_ding_number);
            tvShenheCaiNumber = (TextView) convertView.findViewById(R.id.tv_shenhe_cai_number);
            tvPostsNumber = (TextView) convertView.findViewById(R.id.tv_posts_number);
            llDownload = (LinearLayout) convertView.findViewById(R.id.ll_download);
//设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetAudioBean.ListBean listEntity = datas.get(getLayoutPosition());
                    if (listEntity != null) {
                        //3.传递视频列表
                        Intent intent = new Intent(mContext, PicassoSampleActivity.class);
                        if (listEntity.getType().equals("gif")) {
                            String url = Constant.BASE_URL + listEntity.getGif().getImages().get(0);
                            intent.putExtra("url", url);
                            mContext.startActivity(intent);
                        } else if (listEntity.getType().equals("image")) {
                            String url = Constant.BASE_URL + listEntity.getImage().getThumbnail_small().get(0);
                            intent.putExtra("url", url);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
        }

        /**
         * 设置公共部分的数据
         *
         * @param mediaItem
         */
        public void setData(NetAudioBean.ListBean mediaItem) {
            //设置头像
            if (mediaItem.getU() != null && mediaItem.getU().getHeader() != null && mediaItem.getU().getHeader().get(0) != null) {
                x.image().bind(ivHeadpic, Constant.BASE_URL + mediaItem.getU().getHeader().get(0));
            }
            //设置文本
            if (mediaItem.getU() != null && mediaItem.getU().getName() != null) {
                tvName.setText(mediaItem.getU().getName() + "");
            }

            tvTimeRefresh.setText(mediaItem.getPasstime());

            //设置标签
            List<NetAudioBean.ListBean.TagsBean> tagsEntities = mediaItem.getTags();
            if (tagsEntities != null && tagsEntities.size() > 0) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < tagsEntities.size(); i++) {
                    buffer.append(tagsEntities.get(i).getName() + " ");
                }
                tvVideoKindText.setText(buffer.toString());
            }

            //设置点赞，踩,转发

            tvShenheDingNumber.setText(mediaItem.getUp());
            tvShenheCaiNumber.setText(mediaItem.getDown() + "");
            tvPostsNumber.setText(mediaItem.getForward() + "");

        }
    }

    class ImageHolder extends BaseViewHolder {
        TextView tvContext;
        ImageView ivImageIcon;

        public ImageHolder(View convertView) {
            super(convertView);
//中间公共部分 -所有的都有
            tvContext = (TextView) convertView.findViewById(R.id.tv_context);
            ivImageIcon = (ImageView) convertView.findViewById(R.id.iv_image_icon);
        }

        @Override
        public void setData(NetAudioBean.ListBean mediaItem) {
            super.setData(mediaItem);
            //设置文本-所有的都有
            tvContext.setText(mediaItem.getText() + "_" + mediaItem.getType());
            //图片特有的

            ivImageIcon.setImageResource(R.drawable.bg_item);
            if (mediaItem.getImage() != null && mediaItem.getImage() != null && mediaItem.getImage().getThumbnail_small() != null) {
                Glide.with(mContext).load(Constant.BASE_URL + mediaItem.getImage().getThumbnail_small().get(0)).
                        placeholder(R.drawable.bg_item).
                        error(R.drawable.bg_item).
                        diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(ivImageIcon);
            }
        }
    }

    class TextHolder extends BaseViewHolder {
        TextView tvContext;
        public TextHolder(View convertView) {
            super(convertView);
            tvContext = (TextView) convertView.findViewById(R.id.tv_context);
        }

        @Override
        public void setData(NetAudioBean.ListBean mediaItem) {
            super.setData(mediaItem);
            tvContext.setText(mediaItem.getText() + "_" + mediaItem.getType());
        }
    }

    class GifHolder extends BaseViewHolder {

        public GifHolder(View convertView) {
            super(convertView);
        }
    }

    class ADHolder extends BaseViewHolder {

        public ADHolder(View convertView) {
            super(convertView);
        }
    }
}

