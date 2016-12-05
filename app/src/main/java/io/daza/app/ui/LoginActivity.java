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
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.blankapp.annotation.ViewById;
import org.blankapp.validation.Rule;
import org.blankapp.validation.Validator;
import org.blankapp.validation.handlers.DefaultErrorHandler;
import org.greenrobot.eventbus.EventBus;

import io.daza.app.R;
import io.daza.app.api.ApiClient;
import io.daza.app.event.LoginStatusChangedEvent;
import io.daza.app.model.Result;
import io.daza.app.model.User;
import io.daza.app.ui.base.BaseActivity;
import io.daza.app.util.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class LoginActivity extends BaseActivity {

    @ViewById(R.id.edt_email)
    private EditText mEdtEmail;
    @ViewById(R.id.edt_password)
    private EditText mEdtPassword;
    @ViewById(R.id.btn_submit)
    private Button mBtnSubmit;

    private Validator mValidator = new Validator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtEmail.setText("lijy91@foxmail.com");
        mEdtPassword.setText("7t2U9P8q99jg");

        mValidator.add(Rule.with(mEdtEmail).required().email());
        mValidator.add(Rule.with(mEdtPassword).required().minLength(6).maxLength(32));
        mValidator.setErrorHandler(new DefaultErrorHandler());

        mBtnSubmit.setOnClickListener(mSubmitClickListener);
    }

    private View.OnClickListener mSubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mValidator.validate()) {
                return;
            }

            String email = mEdtEmail.getText().toString();
            String password = mEdtPassword.getText().toString();

            Call<Result<User>> call = API.login(email, password);

            call.enqueue(new Callback<Result<User>>() {
                @Override
                public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                    if (response.isSuccessful()) {
                        Result<User> result = response.body();
                        if (result.isSuccessful()) {
                            User user = result.getData();
                            Auth.jwtToken(user.getJwt_token());
                            Auth.user(user);
                            Auth.userConfigs(user.getConfigs());
                            EventBus.getDefault().post(new LoginStatusChangedEvent());
                            LoginActivity.this.finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result<User>> call, Throwable t) {

                }
            });
        }
    };
}
