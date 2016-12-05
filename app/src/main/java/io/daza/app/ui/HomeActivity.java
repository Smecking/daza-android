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

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.blankapp.annotation.ViewById;
import org.blankapp.util.ViewUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.daza.app.R;
import io.daza.app.event.LoginStatusChangedEvent;
import io.daza.app.event.NotificationEvent;
import io.daza.app.model.Notification;
import io.daza.app.ui.adapters.HomeBottomNavigationAdapter;
import io.daza.app.ui.base.BaseActivity;
import io.daza.app.util.Auth;
import io.yunba.android.manager.YunBaManager;

import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_INDEX;
import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_EXPLORE;
import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_INBOX;
import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_MINE;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeIndexFragment homeIndexFragment;
    private HomeExploreFragment homeExploreFragment;
    private HomeInboxFragment homeInboxFragment;
    private HomeMineFragment homeMineFragment;

    @ViewById(R.id.toolbar)
    private Toolbar mToolbar;
    @ViewById(R.id.iv_logo)
    private ImageView mIvLogo;
    @ViewById(R.id.tv_title)
    private TextView mTvTitle;
    @ViewById(R.id.btn_home_search)
    private Button mBtnHomeSearch;
    @ViewById(R.id.viewpager)
    private ViewPager mViewPager;
    @ViewById(R.id.bottom_navigation)
    private BottomNavigationView mBottomNavigationView;

    private HomeBottomNavigationAdapter mHomeBottomNavigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(mToolbar);

        this.homeIndexFragment = HomeIndexFragment.newInstance();
        this.homeExploreFragment = HomeExploreFragment.newInstance();
        this.homeInboxFragment = HomeInboxFragment.newInstance();
        this.homeMineFragment = HomeMineFragment.newInstance();

        this.mHomeBottomNavigationAdapter = new HomeBottomNavigationAdapter(getSupportFragmentManager(),
                this.homeIndexFragment,
                this.homeExploreFragment,
                this.homeInboxFragment,
                this.homeMineFragment);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        mViewPager.setAdapter(this.mHomeBottomNavigationAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(this);

        mBtnHomeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onEvent(new NotificationEvent(getIntent().getStringExtra("extra_notification")));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onEvent(new NotificationEvent(getIntent().getStringExtra("extra_notification")));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        View view = mBottomNavigationView.getChildAt(0);
        if (view instanceof BottomNavigationMenuView) {
            BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) view;
            bottomNavigationMenuView.getChildAt(position).performClick();
        }

        boolean showLogoEnabled = position == HOME_TAB_INDEX;
        switch (position) {
            case HOME_TAB_INDEX:
                mTvTitle.setText(R.string.action_tab_index);
                break;
            case HOME_TAB_EXPLORE:
                mTvTitle.setText(R.string.action_tab_explore);
                break;
            case HOME_TAB_INBOX:
                mTvTitle.setText(R.string.action_tab_inbox);
                break;
            case HOME_TAB_MINE:
                mTvTitle.setText(R.string.action_tab_mine);
                break;
        }

        ViewUtils.setGone(mIvLogo, !showLogoEnabled);
        ViewUtils.setGone(mTvTitle, showLogoEnabled);
        ViewUtils.setGone(mBtnHomeSearch, !showLogoEnabled);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tab_index:
                mViewPager.setCurrentItem(HOME_TAB_INDEX);
                break;
            case R.id.action_tab_explore:
                mViewPager.setCurrentItem(HOME_TAB_EXPLORE);
                break;
            case R.id.action_tab_inbox:
                mViewPager.setCurrentItem(HOME_TAB_INBOX);
                break;
            case R.id.action_tab_mine:
                mViewPager.setCurrentItem(HOME_TAB_MINE);
                break;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginStatusChangedEvent event) {
        YunBaManager.setAlias(this, Auth.alias(), null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NotificationEvent event) {
        getIntent().removeExtra("extra_notification");
        Notification data = event.getData();
        if (data == null) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("新消息")
                .setMessage(data.getContent())
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }
}
