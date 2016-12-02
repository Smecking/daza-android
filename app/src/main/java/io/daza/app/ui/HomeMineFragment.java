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
import android.widget.Toast;

import io.daza.app.R;
import io.daza.app.api.ApiClient;
import io.daza.app.model.Result;
import io.daza.app.model.User;
import io.daza.app.ui.base.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMineFragment extends BaseFragment {

    private Button mLogin;
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

        mLogin = (Button) view.findViewById(R.id.btn_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(HomeMineFragment.this.getActivity(), LoginActivity.class);
//                startActivity(intent);
                ApiClient apiClient = new ApiClient();
                apiClient.api.login("1","2").enqueue(new Callback<Result<User>>() {
                    @Override
                    public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
//                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Result<User>> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mBtnSettings = (Button) view.findViewById(R.id.btn_settings);
        mBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeMineFragment.this.getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
