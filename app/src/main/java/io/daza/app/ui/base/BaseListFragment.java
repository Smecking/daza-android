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

package io.daza.app.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.blankapp.app.ListFragment;
import org.blankapp.util.ViewUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.daza.app.R;
import io.daza.app.model.Pagination;

public abstract class BaseListFragment<VH extends RecyclerView.ViewHolder, Item, Result> extends
        ListFragment<VH, Item, Result> {

    private ProgressBar mPbLoading;
    private TextView mTvLoading;
    private TextView mTvEmpty;
    private TextView mTvError;
    private Button mBtnRetry;

    private Pagination mPagination;
    private boolean mIsLoadMore = false;
    protected EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mEndlessRecyclerViewScrollListener == null) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {

                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    BaseListFragment.this.onLoadMore(page, totalItemsCount);
                }
            };
            getRecyclerView().addOnScrollListener(mEndlessRecyclerViewScrollListener);
        }
    }
    @Override
    public void onRefresh() {
        this.mIsLoadMore = false;
        this.forceLoad();
    }

    protected void onLoadMore(int page, int totalItemsCount) {
        this.mIsLoadMore = true;
        this.forceLoad();
    }

    @Override
    public void onLoadStart() {
        if (isEmpty()) {
            showLoading();
        }
        mEndlessRecyclerViewScrollListener.setLoading(true);
    }

    @Override
    public void onLoadError(Exception e) {
        onRefreshComplete();
        if (isEmpty()) {
            String error = "";
            if (e != null) {
                e.printStackTrace();
                //加入404与500
                if (e instanceof UnknownHostException) {
                    //无法解析---网络错误
                    error = getString(R.string.network_not_connected);
                } else if (e instanceof SocketTimeoutException) {
                    error = getString(R.string.http_exception_error);
                } else {
                    error = e.getLocalizedMessage();
                }
            }
            showError(error);
        }
    }

    @Override
    public void onRefreshComplete() {
        super.onRefreshComplete();
        if (isEmpty()) {
            showEmpty();
        } else {
            hideEmptyIndicator();
        }
        mEndlessRecyclerViewScrollListener.setLoading(false);
        getAdapter().notifyDataSetChanged();
    }

    protected void firstRefresh() {
        setPagination(null);
        mEndlessRecyclerViewScrollListener.setLoading(true);
        getItemsSource().clear();
        this.onRefreshComplete();
        this.onRefresh();
    }

    public void setPagination(Pagination pagination) {
        this.mPagination = pagination;
    }

    public int getNextPage() {
        if (this.mPagination != null && mIsLoadMore) {
            return this.mPagination.getCurrent_page() + 1;
        }
        return 1;
    }

    public Item getItem(int position) {
        return getItemsSource().get(position);
    }

    protected void showLoading() {
        hideEmptyIndicator();
        if (mPbLoading != null) ViewUtils.setVisible(mPbLoading, true);
        if (mTvLoading != null) ViewUtils.setVisible(mTvLoading, true);
    }

    protected void showEmpty() {
        hideEmptyIndicator();
        if (mTvEmpty != null) ViewUtils.setVisible(mTvEmpty, true);
    }

    protected void showError(String error) {
        hideEmptyIndicator();
        if (mTvError != null) {
            ViewUtils.setVisible(mTvError, true);
            if (!TextUtils.isEmpty(error)) {
                mTvError.setText(error);
            } else {
                mTvError.setText("an error occurred, please try again later");
            }
        }
        if (mBtnRetry != null) {
            ViewUtils.setVisible(mBtnRetry, true);
            mBtnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRefresh();
                }
            });
        }
    }

    public void ensureEmptyIndicatorView() {
        View view = getView();
        if (view == null) {
            return;
        }
        if (mPbLoading == null) {
            mPbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
        }
        if (mTvLoading == null) {
            mTvLoading = (TextView) view.findViewById(R.id.tv_loading);
        }
        if (mTvEmpty == null) {
            mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        }
        if (mTvError == null) {
            mTvError = (TextView) view.findViewById(R.id.tv_error);
        }
        if (mBtnRetry == null) {
            mBtnRetry = (Button) view.findViewById(R.id.btn_retry);
        }
    }

    private void hideEmptyIndicator() {
        ensureEmptyIndicatorView();
        if (mPbLoading != null) ViewUtils.setGone(mPbLoading, true);
        if (mTvLoading != null) ViewUtils.setGone(mTvLoading, true);
        if (mTvEmpty != null) ViewUtils.setGone(mTvEmpty, true);
        if (mTvError != null) ViewUtils.setGone(mTvError, true);
        if (mBtnRetry != null) ViewUtils.setGone(mBtnRetry, true);
    }
}
