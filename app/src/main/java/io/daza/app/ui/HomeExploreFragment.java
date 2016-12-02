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

import java.util.ArrayList;

import io.daza.app.R;
import io.daza.app.api.ApiClient;
import io.daza.app.model.Result;
import io.daza.app.model.Topic;
import io.daza.app.ui.base.BaseListFragment;
import io.daza.app.ui.vh.TopicViewHolder;
import retrofit2.Response;

public class HomeExploreFragment extends BaseListFragment<TopicViewHolder, Topic, Result<ArrayList<Topic>>> {

    public HomeExploreFragment() {
        // Required empty public constructor
    }

    public static HomeExploreFragment newInstance() {
        HomeExploreFragment fragment = new HomeExploreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_home_explore, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initLoader();
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_explore_list_item, parent, false);
        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Topic data = getItemsSource().get(position);
        holder.bind(data);
    }

    @Override
    public Result<ArrayList<Topic>> onLoadInBackground() throws Exception {
//        Result<ArrayList<Topic>> result = new Result<>();
//
//        ArrayList<Topic> data = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            data.add(new Topic());
//        }
//        result.setData(data);
//
//        return result;
        ApiClient apiClient = new ApiClient();
        Response<Result<ArrayList<Topic>>> response = apiClient.api.getTopics(1).execute();
        return response.body();
    }

    @Override
    public void onLoadComplete(Result<ArrayList<Topic>> data) {
        getItemsSource().addAll(data.getData());
        getAdapter().notifyDataSetChanged();
        super.onRefreshComplete();
    }

    @Override
    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), TopicDetailActivity.class);
        startActivity(intent);
    }
}
