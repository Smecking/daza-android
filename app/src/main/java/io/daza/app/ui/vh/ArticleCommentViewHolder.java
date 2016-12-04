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

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.blankapp.annotation.ViewById;

import io.daza.app.R;
import io.daza.app.model.ArticleComment;
import io.daza.app.model.User;
import io.daza.app.util.DateUtils;
import io.daza.app.util.Thumbnail;

public class ArticleCommentViewHolder extends BaseViewHolder {

    @ViewById(R.id.iv_avatar)
    private ImageView mIvAvatar;
    @ViewById(R.id.tv_name)
    private TextView mTvName;
    @ViewById(R.id.tv_content)
    private TextView mTvContent;
    @ViewById(R.id.tv_created_at)
    private TextView mTvCreatedAt;

    private OnClickListener mListener;

    public ArticleCommentViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(ArticleComment data) {
        final User fromUser = data.getUser();
        Glide
                .with(itemView.getContext())
                .load(new Thumbnail(fromUser.getAvatar_url()).small())
                .centerCrop()
                .placeholder(R.mipmap.placeholder_image)
                .crossFade()
                .into(mIvAvatar);
        mTvName.setText(fromUser.getName());
        mTvContent.setText(data.getContent());
        mTvCreatedAt.setText(DateUtils.toTimeAgo(itemView.getContext(), data.getCreated_at()));

        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClickUser(fromUser);
                }
            }
        });
        mTvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClickUser(fromUser);
                }
            }
        });
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public static interface OnClickListener {
        void onClickUser(User user);
    }

}
