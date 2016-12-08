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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.daza.app.R;
import io.daza.app.api.ApiClient;
import io.daza.app.model.Article;
import io.daza.app.model.Category;
import io.daza.app.model.Result;
import io.daza.app.ui.base.BaseListFragment;
import io.daza.app.ui.vh.ArticleViewHolder;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class ArticlesFragment extends BaseListFragment<ArticleViewHolder, Article, Result<List<Article>>> {

    private static final int VIEW_TYPE_AD = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private Category mCategory;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    public static ArticlesFragment newInstance(Category category) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.mCategory = category;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initLoader();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = viewType == VIEW_TYPE_AD ? R.layout.fragment_articles_list_ad_item : R.layout.fragment_articles_list_item;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Article data = getItemsSource().get(position);
        holder.bind(data);
    }

    @Override
    public int getItemViewType(int position) {
        if ("ad".equals(getItem(position).getType())) {
            return VIEW_TYPE_AD;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public Result<List<Article>> onLoadInBackground() throws Exception {
        Response<Result<List<Article>>> response = API.getArticles(getNextPage(), mCategory.getId(), mCategory.getSlug()).execute();
        return response.body();
    }

    @Override
    public void onLoadComplete(Result<List<Article>> data) {
        if (data.isSuccessful()) {
            setPagination(data.getPagination());
            if (data.getPagination().getCurrent_page() == 1) {
                getItemsSource().clear();
            }
            getItemsSource().addAll(data.getData());
            // 两页出现一次广告
            if (data.getPagination().getCurrent_page() % 2 == 0) {
                getItemsSource().add(new Article() {{
                    setType("ad");
                }});
            }
        }
        getAdapter().notifyDataSetChanged();
        super.onRefreshComplete();
    }

    @Override
    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
        Article data = getItemsSource().get(position);
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("extra_article_id", data.getId());
        intent.putExtra("extra_article", data.toJSONString());
        startActivity(intent);
    }

    public String getCategoryName() {
        if (mCategory != null) {
            return mCategory.getName();
        }
        return null;
    }
}
