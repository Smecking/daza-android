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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.blankapp.app.ListFragment;

import io.daza.app.model.Pagination;

public abstract class BaseListFragment<VH extends RecyclerView.ViewHolder, Item, Result> extends
        ListFragment<VH, Item, Result> {

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
                    Log.d("BaseListFragment", "" + page);
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
            // Do something
        }
        mEndlessRecyclerViewScrollListener.setLoading(true);
    }

    @Override
    public void onLoadError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onRefreshComplete() {
        super.onRefreshComplete();
        if (isEmpty()) {
            // Do something
        } else {
            // Do something
        }
        mEndlessRecyclerViewScrollListener.setLoading(false);
        getAdapter().notifyDataSetChanged();
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
}
