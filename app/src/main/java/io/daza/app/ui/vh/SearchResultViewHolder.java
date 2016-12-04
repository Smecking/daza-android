package io.daza.app.ui.vh;

import android.view.View;
import android.widget.TextView;

import org.blankapp.annotation.ViewById;

import io.daza.app.R;
import io.daza.app.model.Article;

/**
 * Created by Lijy91 on 2016/12/4.
 */

public class SearchResultViewHolder extends BaseViewHolder {

    @ViewById(R.id.tv_title)
    private TextView mTvTitle;

    public SearchResultViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Article data) {
        mTvTitle.setText(data.getTitle());
    }
}
