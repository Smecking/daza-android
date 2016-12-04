package io.daza.app.ui.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.blankapp.util.ViewUtils;

public class BaseViewHolder extends RecyclerView.ViewHolder  {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ViewUtils.inject(this, itemView);
    }

}
