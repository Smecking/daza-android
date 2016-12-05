/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.daza.app.ui.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.blankapp.util.ViewUtils;

import java.util.Locale;

import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.util.DateUtils;
import io.daza.app.util.Thumbnail;

public class ArticleViewHolder extends BaseViewHolder {

    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvTopicName;
    private TextView mTvTopicNameRightDot;
    private TextView mTvPublishedAt;
    private TextView mTvCommentCount;
    private TextView mTvViewCount;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mTvTopicName = (TextView) itemView.findViewById(R.id.tv_topic_name);
        mTvTopicNameRightDot = (TextView) itemView.findViewById(R.id.tv_topic_name_right_dot);
        mTvPublishedAt = (TextView) itemView.findViewById(R.id.tv_published_at);
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
        } else {
            mTvTopicName.setText("");
        }
        ViewUtils.setGone(mTvTopicName, data.getTopic() == null);
        ViewUtils.setGone(mTvTopicNameRightDot, data.getTopic() == null);

        mTvPublishedAt.setText(DateUtils.toTimeAgo(itemView.getContext(), data.getPublished_at()));
        mTvCommentCount.setText(String.format(Locale.US, "%d评论", data.getComment_count()));
        mTvViewCount.setText(String.format(Locale.US, "%d阅读", data.getView_count()));
    }

}