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

import java.util.List;

import io.daza.app.R;
import io.daza.app.handler.ErrorHandler;
import io.daza.app.model.Article;
import io.daza.app.model.ArticleComment;
import io.daza.app.model.Model;
import io.daza.app.model.Result;
import io.daza.app.ui.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class ArticleCommentCreateActivity extends BaseActivity {

    private int mArticleId;
    private Article mArticle;

    @ViewById(R.id.edt_content)
    private EditText mEdtContent;

    private Validator mValidator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_comment_create);

        mArticleId = getIntent().getIntExtra("extra_article_id", 0);
        mArticle = Model.parseObject(getIntent().getStringExtra("extra_article"), Article.class);

        mValidator.add(Rule.with(mEdtContent).required().minLength(2));

        mValidator.setErrorHandler(new DefaultErrorHandler());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_comment_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            if (!mValidator.validate()) {
                return false;
            }
            String content = mEdtContent.getText().toString();
            API.createArticleComment(mArticleId, content).enqueue(new Callback<Result<ArticleComment>>() {
                @Override
                public void onResponse(Call<Result<ArticleComment>> call, Response<Result<ArticleComment>> response) {
                    if (new ErrorHandler(ArticleCommentCreateActivity.this).handleErrorIfNeed(response.body())) {
                        return;
                    }
                    if (response.isSuccessful()) {
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Result<ArticleComment>> call, Throwable t) {
                    new ErrorHandler(ArticleCommentCreateActivity.this).handleError(t);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
