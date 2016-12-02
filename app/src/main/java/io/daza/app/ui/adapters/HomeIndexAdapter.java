package io.daza.app.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import io.daza.app.ui.ArticlesFragment;

public class HomeIndexAdapter extends FragmentStatePagerAdapter {

    private List<ArticlesFragment> mFragments;

    public HomeIndexAdapter(FragmentManager fm, List<ArticlesFragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ArticlesFragment fragment = mFragments.get(position);
        return fragment.getCategoryName();
    }

}
