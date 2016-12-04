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
import io.daza.app.model.Model;
import io.daza.app.model.Result;
import io.daza.app.model.Topic;
import io.daza.app.model.User;
import io.daza.app.ui.base.BaseListActivity;
import io.daza.app.ui.vh.TopicViewHolder;
import io.daza.app.util.Auth;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class OwnTopicsActivity extends BaseListActivity<TopicViewHolder, Topic, Result<List<Topic>>> {

    private int mUserId = 0;
    private User mUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_topics);

        mUserId = getIntent().getIntExtra("extra_user_id", 0);
        mUser = Model.parseObject(getIntent().getStringExtra("extra_user"), User.class);

        if (Auth.check() && mUserId == Auth.id()) {
            setTitle("我的主题");
        }
        this.initLoader();
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_own_topics_list_item, parent, false);
        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Topic data = getItemsSource().get(position);
        holder.bind(data);
    }

    @Override
    public Result<List<Topic>> onLoadInBackground() throws Exception {
        Response<Result<List<Topic>>> response = API.getUserTopics(mUserId, getNextPage()).execute();
        return response.body();
    }

    @Override
    public void onLoadComplete(Result<List<Topic>> data) {
        if (data.isSuccessful()) {
            setPagination(data.getPagination());
            if (data.getPagination().getCurrent_page() == 1) {
                getItemsSource().clear();
            }
            getItemsSource().addAll(data.getData());
        }
        getAdapter().notifyDataSetChanged();
        super.onRefreshComplete();
    }

    @Override
    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
        Intent intent = new Intent(this, TopicDetailActivity.class);
        Topic topic = getItemsSource().get(position);
        intent.putExtra("extra_topic", topic.toJSONString());
        intent.putExtra("extra_topic_id", topic.getId());
        startActivity(intent);
    }
}
