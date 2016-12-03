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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.daza.app.R;
import io.daza.app.event.LoginStatusChangedEvent;
import io.daza.app.model.Notification;
import io.daza.app.model.Result;
import io.daza.app.ui.base.BaseListFragment;
import io.daza.app.ui.vh.NotificationViewHolder;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class HomeInboxFragment extends
        BaseListFragment<NotificationViewHolder, Notification, Response<Result<List<Notification>>>> {

    public HomeInboxFragment() {
        // Required empty public constructor
    }

    public static HomeInboxFragment newInstance() {
        HomeInboxFragment fragment = new HomeInboxFragment();
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
        return inflater.inflate(R.layout.fragment_home_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.initLoader();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_inbox_list_item, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Notification data = getItemsSource().get(position);
        holder.bind(data);
    }

    @Override
    public Response<Result<List<Notification>>> onLoadInBackground() throws Exception {
        return API.getNotifications(1, null).execute();
    }

    @Override
    public void onLoadComplete(Response<Result<List<Notification>>> response) {
        Result<List<Notification>> result = response.body();
        if (response.isSuccessful() && result.isSuccessful()) {
            getItemsSource().addAll(result.getData());
        }
        getAdapter().notifyDataSetChanged();
        super.onRefreshComplete();
    }

    @Override
    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
        Notification data = getItemsSource().get(position);

        Intent intent = null;

        switch (data.getReason()) {
            case "followed":
                intent = new Intent(getActivity(), UserDetailActivity.class);
                break;
            case "subscribed":
                intent = new Intent(getActivity(), TopicDetailActivity.class);
                break;
            case "upvoted":
                intent = new Intent(getActivity(), ArticleDetailActivity.class);
                break;
            case "comment":
                intent = new Intent(getActivity(), ArticleDetailActivity.class);
                break;
            case "mention":
                intent = new Intent(getActivity(), ArticleDetailActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginStatusChangedEvent event) {
        // Do something
    }
}
