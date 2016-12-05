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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.blankapp.annotation.ViewById;
import org.blankapp.validation.Rule;
import org.blankapp.validation.Validator;
import org.blankapp.validation.handlers.DefaultErrorHandler;

import io.daza.app.R;
import io.daza.app.model.ArticleComment;
import io.daza.app.model.Result;
import io.daza.app.model.User;
import io.daza.app.ui.base.BaseActivity;
import io.daza.app.util.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class ModifyProfileActivity extends BaseActivity {

    @ViewById(R.id.edt_name)
    private EditText mEdtName;
    @ViewById(R.id.edt_city)
    private EditText mEdtCity;
    @ViewById(R.id.edt_website)
    private EditText mEdtWebsite;
    @ViewById(R.id.edt_bio)
    private EditText mEdtBio;

    private Validator mValidator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        mValidator.add(Rule.with(mEdtName).required());
        mValidator.add(Rule.with(mEdtCity).required());
        mValidator.add(Rule.with(mEdtBio).required());

        mValidator.setErrorHandler(new DefaultErrorHandler());

        if (Auth.check()) {
            User user = Auth.user();
            mEdtName.setText(user.getName());
            mEdtCity.setText(user.getCity());
            mEdtWebsite.setText(user.getWebsite());
            mEdtBio.setText(user.getBio());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modify_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (!mValidator.validate()) {
                return false;
            }
            String name = mEdtName.getText().toString();
            String city = mEdtCity.getText().toString();
            String website = mEdtWebsite.getText().toString();
            String bio = mEdtBio.getText().toString();
            API.updateProfile(name, city, website, bio).enqueue(new Callback<Result<User>>() {
                @Override
                public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                    if (response.isSuccessful()) {
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Result<User>> call, Throwable t) {

                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
