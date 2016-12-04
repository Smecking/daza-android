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

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.model.ArticleComment;
import io.daza.app.model.Notification;
import io.daza.app.model.Topic;
import io.daza.app.model.User;
import io.daza.app.util.Thumbnail;

public class NotificationViewHolder extends BaseViewHolder {

    private ImageView mIvAvatar;
    private TextView mTvContent;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    public void bind(Notification data) {
        User fromUser = data.getFrom_user();
        Topic topic = data.getTopic();
        Article article = data.getArticle();
        ArticleComment articleComment = data.getArticle_comment();
        String content = "未知类型";
        Glide
                .with(itemView.getContext())
                .load(new Thumbnail(fromUser.getAvatar_url()).small())
                .centerCrop()
                .placeholder(R.mipmap.placeholder_image)
                .crossFade()
                .into(mIvAvatar);

        switch (data.getReason()) {
            case "followed":
                content = fromUser.getName() + " 关注了你";
                break;
            case "subscribed":
                content = fromUser.getName() + " 订阅了主题《" + topic.getName() + "》";
                break;
            case "upvoted":
                content = fromUser.getName() + " 赞了文章《" + article.getTitle() + "》";
                break;
            case "comment":
                content = fromUser.getName() + " 评论了文章《" + article.getTitle() + "》";
                break;
//            case "mention":
//                content = fromUser.getName() + "在 ";
//                break;
            default:
                if (TextUtils.isEmpty(data.getContent())) {
                    content = data.getContent();
                }
                break;
        }
        mTvContent.setText(content);
        mTvContent.setTextColor(data.isUnread() ? Color.parseColor("#000000") : Color.parseColor("#aaaaaa"));
    }
}