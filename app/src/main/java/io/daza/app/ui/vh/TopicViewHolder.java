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

import io.daza.app.R;
import io.daza.app.model.Topic;
import io.daza.app.util.Thumbnail;

public class TopicViewHolder extends BaseViewHolder {

    private ImageView mIvImage;
    private TextView mTvName;
    private TextView mTvDescription;

    public TopicViewHolder(View itemView) {
        super(itemView);
        mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        mTvDescription = (TextView) itemView.findViewById(R.id.tv_description);
    }

    public void bind(Topic data) {
        Glide
                .with(itemView.getContext())
                .load(new Thumbnail(data.getImage_url()).small())
                .centerCrop()
                .placeholder(R.mipmap.placeholder_image)
                .crossFade()
                .into(mIvImage);
        mTvName.setText(data.getName());
        mTvDescription.setText(data.getDescription());
    }
}
