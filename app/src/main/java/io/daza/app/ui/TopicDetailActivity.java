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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.model.Model;
import io.daza.app.model.Result;
import io.daza.app.model.Topic;
import io.daza.app.ui.base.BaseListActivity;
import io.daza.app.ui.vh.ArticleViewHolder;
import io.daza.app.ui.vh.BaseViewHolder;
import io.daza.app.ui.vh.TopicDetailHeadViewHolder;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class TopicDetailActivity extends BaseListActivity<BaseViewHolder, Article, Result<List<Article>>> {

    private final int VIEWTYPE_HEAD = 0;
    private final int VIEWTYPE_ITEM = 1;

    private int mTopicId;
    private Topic mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        mTopicId = getIntent().getIntExtra("extra_topic_id", 0);
        mTopic = Model.parseObject(getIntent().getStringExtra("extra_topic"), Topic.class);

        getItemsSource().add(new Article());
        getAdapter().notifyDataSetChanged();

        this.initLoader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topic_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_HEAD) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_topic_detail_list_head, parent, false);
            return new TopicDetailHeadViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_topic_detail_list_item, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == VIEWTYPE_HEAD) {
            TopicDetailHeadViewHolder viewHolder = (TopicDetailHeadViewHolder) holder;
            viewHolder.bind(mTopic);
        } else {
            ArticleViewHolder viewHolder = (ArticleViewHolder) holder;
            Article data = getItemsSource().get(position);
            viewHolder.bind(data);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEWTYPE_HEAD;
        }
        return VIEWTYPE_ITEM;
    }

    @Override
    public Result<List<Article>> onLoadInBackground() throws Exception {
        Response<Result<List<Article>>> response = API.getTopicArticles(mTopicId, getNextPage()).execute();
        return response.body();
    }

    @Override
    public void onLoadComplete(Result<List<Article>> data) {
        if (data.isSuccessful()) {
            setPagination(data.getPagination());
            if (data.getPagination().getCurrent_page() == 1) {
                getItemsSource().clear();
                getItemsSource().add(new Article());
            }
            getItemsSource().addAll(data.getData());
        }
        super.onRefreshComplete();
    }

    @Override
    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
        if (getItemViewType(position) == VIEWTYPE_HEAD) {
            return;
        }
        Article data = getItemsSource().get(position);
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("extra_article_id", data.getId());
        intent.putExtra("extra_article", data.toJSONString());
        startActivity(intent);
    }
}
