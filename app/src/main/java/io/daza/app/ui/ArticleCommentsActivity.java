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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.model.ArticleComment;
import io.daza.app.model.Model;
import io.daza.app.model.Result;
import io.daza.app.ui.base.BaseListActivity;
import io.daza.app.ui.vh.ArticleCommentViewHolder;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class ArticleCommentsActivity extends BaseListActivity<ArticleCommentViewHolder, ArticleComment, Result<List<ArticleComment>>> {

    private int mArticleId;
    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticle_comments);

        mArticleId = getIntent().getIntExtra("extra_article_id", 0);
        mArticle = Model.parseObject(getIntent().getStringExtra("extra_article"), Article.class);

        this.initLoader();
    }

    @Override
    public ArticleCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_acticle_comments_list_item, parent, false);
        return new ArticleCommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleCommentViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(getItemsSource().get(position));
    }

    @Override
    public Result<List<ArticleComment>> onLoadInBackground() throws Exception {
        Response<Result<List<ArticleComment>>> response = API.getArticleComments(mArticleId, getNextPage()).execute();
        return response.body();
    }

    @Override
    public void onLoadComplete(Result<List<ArticleComment>> data) {
        if (data.isSuccessful()) {
            setPagination(data.getPagination());
            if (data.getPagination().getCurrent_page() == 1) {
                getItemsSource().clear();
            }
            getItemsSource().addAll(data.getData());
        }
        super.onRefreshComplete();
    }
}
