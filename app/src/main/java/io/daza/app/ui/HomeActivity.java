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

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.blankapp.annotation.ViewById;

import io.daza.app.R;
import io.daza.app.ui.adapters.HomeBottomNavigationAdapter;
import io.daza.app.ui.base.BaseActivity;

import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_INDEX;
import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_EXPLORE;
import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_INBOX;
import static io.daza.app.ui.adapters.HomeBottomNavigationAdapter.HOME_TAB_MINE;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeIndexFragment homeIndexFragment;
    private HomeExploreFragment homeExploreFragment;
    private HomeInboxFragment homeInboxFragment;
    private HomeMineFragment homeMineFragment;

    @ViewById(R.id.viewpager)
    private ViewPager mViewPager;
    @ViewById(R.id.bottom_navigation)
    private BottomNavigationView mBottomNavigationView;

    private HomeBottomNavigationAdapter mHomeBottomNavigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}
