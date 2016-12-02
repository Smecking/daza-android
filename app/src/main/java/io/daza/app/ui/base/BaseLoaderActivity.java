package io.daza.app.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import org.blankapp.app.LoaderActivity;

public abstract class BaseLoaderActivity<D> extends LoaderActivity<D> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadError(Exception e) {

    }
}
