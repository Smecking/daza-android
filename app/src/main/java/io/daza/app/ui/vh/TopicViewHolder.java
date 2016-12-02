package io.daza.app.ui.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.daza.app.R;
import io.daza.app.model.Topic;

public class TopicViewHolder extends RecyclerView.ViewHolder {

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
                .load(data.getImage_url())
                .centerCrop()
                .placeholder(R.mipmap.placeholder_image)
                .crossFade()
                .into(mIvImage);
        mTvName.setText(data.getName());
        mTvDescription.setText(data.getDescription());
    }
}
