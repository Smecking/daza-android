package io.daza.app.ui.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.daza.app.R;
import io.daza.app.model.ArticleComment;
import io.daza.app.model.User;
import io.daza.app.util.Thumbnail;

public class ArticleCommentViewHolder extends RecyclerView.ViewHolder {

    private ImageView mIvAvatar;
    private TextView mTvContent;

    public ArticleCommentViewHolder(View itemView) {
        super(itemView);
        mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    public void bind(ArticleComment data) {
        User fromUser = data.getUser();
        Glide
                .with(itemView.getContext())
                .load(new Thumbnail(fromUser.getAvatar_url()).small())
                .centerCrop()
                .placeholder(R.mipmap.placeholder_image)
                .crossFade()
                .into(mIvAvatar);

    }
}
