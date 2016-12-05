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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.blankapp.annotation.ViewById;
import org.blankapp.util.ViewUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.daza.app.R;
import io.daza.app.event.LoginStatusChangedEvent;
import io.daza.app.model.User;
import io.daza.app.ui.base.BaseFragment;
import io.daza.app.util.Auth;
import io.daza.app.util.Thumbnail;

public class HomeMineFragment extends BaseFragment {

    @ViewById(R.id.btn_profile)
    private LinearLayout mBtnProfile;
    @ViewById(R.id.iv_avatar)
    private ImageView mIvAvatar;
    @ViewById(R.id.tv_name)
    private TextView mTvName;
    @ViewById(R.id.tv_bio)
    private TextView mTvBio;
    @ViewById(R.id.btn_own_topics)
    private Button mBtnOwnTopics;
    @ViewById(R.id.btn_own_subscribes)
    private Button mBtnOwnSubscribes;
    @ViewById(R.id.btn_own_upvotes)
    private Button mBtnOwnUpvotes;
    @ViewById(R.id.btn_settings)
    private Button mBtnSettings;

    public HomeMineFragment() {
        // Required empty public constructor
    }

    public static HomeMineFragment newInstance() {
        HomeMineFragment fragment = new HomeMineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_mine, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Auth.check()) {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), ModifyProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        mBtnOwnTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Auth.check()) {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), OwnTopicsActivity.class);
                    intent.putExtra("extra_user", Auth.user().toJSONString());
                    intent.putExtra("extra_user_id", Auth.id());
                    startActivity(intent);
                }
            }
        });

        mBtnOwnSubscribes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Auth.check()) {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), OwnSubscribesActivity.class);
                    intent.putExtra("extra_user", Auth.user().toJSONString());
                    intent.putExtra("extra_user_id", Auth.id());
                    startActivity(intent);
                }
            }
        });

        mBtnOwnUpvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Auth.check()) {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeMineFragment.this.getActivity(), OwnUpvoteArticlesActivity.class);
                    intent.putExtra("extra_user", Auth.user().toJSONString());
                    intent.putExtra("extra_user_id", Auth.id());
                    startActivity(intent);
                }
            }
        });

        mBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeMineFragment.this.getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        initView();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        if (Auth.check()) {
            User user = Auth.user();
            Glide
                    .with(getActivity())
                    .load(new Thumbnail(user.getAvatar_url()).small())
                    .centerCrop()
                    .placeholder(R.mipmap.placeholder_image)
                    .crossFade()
                    .into(mIvAvatar);
            mTvName.setText(user.getName());
            mTvBio.setText(user.getBio());
            ViewUtils.setGone(mTvBio, false);
        } else {
            mIvAvatar.setImageResource(R.mipmap.placeholder_image);
            mTvName.setText("未登录");
            mTvBio.setText("");
            ViewUtils.setGone(mTvBio, true);
        }
    }

    @Subscribe
    public void onEvent(LoginStatusChangedEvent event) {
        this.initView();
    }
}
