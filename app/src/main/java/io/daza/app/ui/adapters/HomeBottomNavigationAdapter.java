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

package io.daza.app.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.daza.app.ui.HomeExploreFragment;
import io.daza.app.ui.HomeInboxFragment;
import io.daza.app.ui.HomeIndexFragment;
import io.daza.app.ui.HomeMineFragment;

public class HomeBottomNavigationAdapter extends FragmentStatePagerAdapter {

    public static final int HOME_TAB_INDEX = 0;
    public static final int HOME_TAB_EXPLORE = 1;
    public static final int HOME_TAB_INBOX = 2;
    public static final int HOME_TAB_MINE = 3;

    private HomeIndexFragment homeIndexFragment;
    private HomeExploreFragment homeExploreFragment;
    private HomeInboxFragment homeInboxFragment;
    private HomeMineFragment homeMineFragment;

    public HomeBottomNavigationAdapter(FragmentManager fm,
                                   HomeIndexFragment homeIndexFragment,
                                   HomeExploreFragment homeExploreFragment,
                                   HomeInboxFragment homeInboxFragment,
                                   HomeMineFragment homeMineFragment) {
        super(fm);
        this.homeIndexFragment = homeIndexFragment;
        this.homeExploreFragment = homeExploreFragment;
        this.homeInboxFragment = homeInboxFragment;
        this.homeMineFragment = homeMineFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case HOME_TAB_INDEX:
                fragment = homeIndexFragment;
                break;
            case HOME_TAB_EXPLORE:
                fragment = homeExploreFragment;
                break;
            case HOME_TAB_INBOX:
                fragment = homeInboxFragment;
                break;
            case HOME_TAB_MINE:
                fragment = homeMineFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
