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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StringLoader;

import org.blankapp.annotation.ViewById;

import java.util.Locale;

import io.daza.app.R;
import io.daza.app.model.Topic;
import io.daza.app.model.User;
import io.daza.app.util.Thumbnail;

public class TopicDetailHeadViewHolder extends BaseViewHolder {

    private ImageView mIvImage;
    private TextView mTvName;
    private TextView mTvDescription;
    @ViewById(R.id.btn_subscribe)
    private Button mBtnSubscribe;
    @ViewById(R.id.tv_creater)
    private TextView mTvCreater;


    private OnClickListener mListener;

    public TopicDetailHeadViewHolder(View itemView) {
        super(itemView);
        mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        mTvDescription = (TextView) itemView.findViewById(R.id.tv_description);
    }

    public void bind(final Topic data) {
        if (data == null) {
            mIvImage.setImageResource(R.mipmap.placeholder_image);
            mTvName.setText("");
            mTvCreater.setText("");
            mTvDescription.setText("");
            return;
        }
        Glide
                .with(itemView.getContext())
                .load(new Thumbnail(data.getImage_url()).small())
                .centerCrop()
                .placeholder(R.mipmap.placeholder_image)
                .crossFade()
                .into(mIvImage);
        mTvName.setText(data.getName());
        mTvDescription.setText(data.getDescription());
        mBtnSubscribe.setText(String.format(Locale.US, (data.isSubscribed() ? "已" : "") + "订阅 (%d)", data.getSubscriber_count()));
        if (data.getUser() != null) {
            mTvCreater.setText(String.format(Locale.US, "由 %s 维护", data.getUser().getName()));
            mTvCreater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onClickUser(data.getUser());
                    }
                }
            });
        } else {
            mTvCreater.setText("");
        }
        mBtnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClickSubscribe(data);
                }
            }
        });
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnClickListener {
        void onClickUser(User user);
        void onClickSubscribe(Topic topic);
    }
}
