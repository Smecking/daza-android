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

package io.daza.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.blankapp.annotation.ViewById;

import java.util.List;

import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.model.Result;
import io.daza.app.ui.base.BaseListActivity;
import io.daza.app.ui.vh.SearchResultViewHolder;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class SearchActivity extends BaseListActivity<SearchResultViewHolder, Article, Result<List<Article>>> implements SearchView.OnQueryTextListener {

    @ViewById(R.id.toolbar)
    private Toolbar mToolbar;
    @ViewById(R.id.sv_keyword)
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle(null);
        setSupportActionBar(mToolbar);

        mSearchView.setQueryHint("搜索你感兴趣的内容");
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(this);

        this.initLoader();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // 收起键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        // 清空原有数据
        getItemsSource().clear();
        getAdapter().notifyDataSetChanged();
        // 刷新数据
        this.onRefresh();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (getItemCount() > 0) {
            getItemsSource().clear();
            getAdapter().notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_list_item, parent, false);
        return new SearchResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Article data = getItemsSource().get(position);
        holder.bind(data);
    }

    @Override
    public Result<List<Article>> onLoadInBackground() throws Exception {
        String keyword = mSearchView.getQuery().toString();
        Response<Result<List<Article>>> response = API.getSearchResult(getNextPage(), keyword).execute();
        return response.body();
    }

    @Override
    public void onLoadComplete(Result<List<Article>> data) {
        if (data != null && data.isSuccessful()) {
            setPagination(data.getPagination());
            if (data.getPagination().getCurrent_page() == 1) {
                getItemsSource().clear();
            }
            getItemsSource().addAll(data.getData());
        }
        super.onRefreshComplete();
    }

    @Override
    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
        Article data = getItemsSource().get(position);
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("extra_article_id", data.getId());
        intent.putExtra("extra_article", data.toJSONString());
        startActivity(intent);
    }

}
