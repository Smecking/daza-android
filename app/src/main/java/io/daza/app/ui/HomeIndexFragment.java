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

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.blankapp.annotation.ViewById;

import java.util.ArrayList;
import java.util.List;

import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.model.Category;
import io.daza.app.model.Notification;
import io.daza.app.model.Result;
import io.daza.app.ui.adapters.HomeIndexAdapter;
import io.daza.app.ui.base.BaseFragment;
import io.daza.app.ui.base.BaseListFragment;
import io.daza.app.ui.vh.ArticleViewHolder;
import io.daza.app.ui.vh.TopicViewHolder;

public class HomeIndexFragment extends BaseFragment {

    @ViewById(R.id.tablayout)
    private TabLayout mTabLayout;
    @ViewById(R.id.viewpager)
    private ViewPager mViewPager;

    private List<ArticlesFragment> mFragments;
    private HomeIndexAdapter mHomeIndexAdapter;

    public HomeIndexFragment() {
        // Required empty public constructor
    }

    public static HomeIndexFragment newInstance() {
        HomeIndexFragment fragment = new HomeIndexFragment();
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
        return inflater.inflate(R.layout.fragment_home_index, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragments = new ArrayList<>();
        mFragments.add(ArticlesFragment.newInstance(Category.latest()));
        mFragments.add(ArticlesFragment.newInstance(Category.popular()));

        mHomeIndexAdapter = new HomeIndexAdapter(getChildFragmentManager(), mFragments);

        mViewPager.setAdapter(mHomeIndexAdapter);
        mViewPager.setOffscreenPageLimit(4);

        mTabLayout.setupWithViewPager(mViewPager);
    }

}
