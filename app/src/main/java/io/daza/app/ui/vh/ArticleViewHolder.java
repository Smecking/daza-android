package io.daza.app.ui.vh;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.blankapp.util.ViewUtils;

import io.daza.app.R;
import io.daza.app.model.Article;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvTopicName;
    private TextView mTvCommentCount;
    private TextView mTvViewCount;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mTvTopicName = (TextView) itemView.findViewById(R.id.tv_topic_name);
        mTvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        mTvViewCount = (TextView) itemView.findViewById(R.id.tv_view_count);
    }

    public void bind(Article data) {
        ViewUtils.setGone(mIvImage, TextUtils.isEmpty(data.getImage_url()));
        mTvTitle.setText(data.getTitle());
        mTvTopicName.setText(data.getTopic().getName());
        mTvCommentCount.setText(data.getComment_count() + "评论");
        mTvViewCount.setText(data.getView_count() + "阅读");
    }

}