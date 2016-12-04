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