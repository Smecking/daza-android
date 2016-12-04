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
import io.daza.app.util.Thumbnail;

public class TopicDetailHeadViewHolder extends BaseViewHolder {

    private ImageView mIvImage;
    private TextView mTvName;
    private TextView mTvDescription;
    @ViewById(R.id.btn_subscribe)
    private Button mBtnSubscribe;
    @ViewById(R.id.tv_creater)
    private TextView mTvCreater;

    public TopicDetailHeadViewHolder(View itemView) {
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
        mBtnSubscribe.setText(String.format(Locale.US, "订阅 (%d)", data.getSubscriber_count()));
        if (data.getUser() != null) {
            mTvCreater.setText(String.format(Locale.US, "由 %s 维护", data.getUser().getName()));
        }
    }
}
