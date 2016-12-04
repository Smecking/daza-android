package io.daza.app.ui.vh;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.blankapp.util.ViewUtils;

import java.util.Locale;

import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.util.Thumbnail;

public class ArticleViewHolder extends BaseViewHolder {

    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvTopicName;
    private TextView mtvPublishedAt;
    private TextView mTvCommentCount;
    private TextView mTvViewCount;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mTvTopicName = (TextView) itemView.findViewById(R.id.tv_topic_name);
        mtvPublishedAt = (TextView) itemView.findViewById(R.id.tv_published_at);
        mTvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        mTvViewCount = (TextView) itemView.findViewById(R.id.tv_view_count);
    }

    public void bind(Article data) {
        Glide
                .with(itemView.getContext())
                .load(new Thumbnail(data.getImage_url()).small())
                .centerCrop()
                .placeholder(R.mipmap.placeholder_image)
                .crossFade()
                .into(mIvImage);
        ViewUtils.setGone(mIvImage, TextUtils.isEmpty(data.getImage_url()));
        mTvTitle.setText(data.getTitle());
        if (data.getTopic() != null) {
            mTvTopicName.setText(data.getTopic().getName());
        }
        mtvPublishedAt.setText(data.getPublished_at());
        mTvCommentCount.setText(String.format(Locale.US, "%d评论", data.getComment_count()));
        mTvViewCount.setText(String.format(Locale.US, "%d阅读", data.getView_count()));
    }

}