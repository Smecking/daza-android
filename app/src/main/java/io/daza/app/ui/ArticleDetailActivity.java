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
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.blankapp.annotation.ViewById;
import org.blankapp.util.ViewUtils;

import java.util.Locale;

import io.daza.app.BuildConfig;
import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.model.Model;
import io.daza.app.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class ArticleDetailActivity extends InAppBrowserActivity {
    private final String TAG = ArticleDetailActivity.class.getSimpleName();

    private int mArticleId;
    private Article mArticle;

    @ViewById(R.id.btn_create_comment)
    private Button mBtnCreateComment;
    @ViewById(R.id.btn_comment)
    private ImageButton mBtnComment;
    @ViewById(R.id.tv_comment_count)
    private TextView mTvCommentCount;
    @ViewById(R.id.btn_upvote)
    private ImageButton mBtnUpvote;
    @ViewById(R.id.btn_share)
    private ImageButton mBtnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        mArticleId = getIntent().getIntExtra("extra_article_id", 0);
        mArticle = Model.parseObject(getIntent().getStringExtra("extra_article"), Article.class);

        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            Uri data = getIntent().getData();
            mArticleId = Integer.parseInt(data.getPathSegments().get(0));

            // 如果DeepLink 为 daza://articles/{id}/comments，即跳转到评论列表
            if (data.getPathSegments().size() >= 2 &&
                data.getPathSegments().get(1).equals("comments")) {

                Intent intent = new Intent(ArticleDetailActivity.this, ArticleCommentsActivity.class);
                intent.putExtra("extra_article_id", mArticleId);
                startActivity(intent);
                this.finish();
                return;
            }
        }

        loadUrl(BuildConfig.WEB_BASE_URL + "/in-app/articles/" + mArticleId);

        mBtnCreateComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleDetailActivity.this, ArticleCommentCreateActivity.class);
                intent.putExtra("extra_article_id", mArticleId);
                if (mArticle != null) {
                    intent.putExtra("extra_article", mArticle.toJSONString());
                }
                startActivity(intent);
            }
        });

        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleDetailActivity.this, ArticleCommentsActivity.class);
                intent.putExtra("extra_article_id", mArticleId);
                if (mArticle != null) {
                    intent.putExtra("extra_article", mArticle.toJSONString());
                }
                startActivity(intent);
            }
        });

        mBtnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArticle == null) {
                    return;
                }
                API.voteArticle(mArticleId, "up").enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            mArticle.setUpvoted(true);
                            mArticle.setUpvote_count(mArticle.getUpvote_count() + 1);
                            mBtnUpvote.setImageResource(R.mipmap.ic_upvote_checked);
                            Snackbar.make(mWebView, "已赞", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }
        });

        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = BuildConfig.WEB_BASE_URL + "/articles/" + mArticleId;
                String content = "来自「daza.io」的文章《" + mArticle.getTitle() + "》";

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content + "\n" + url);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        this.initView();

        if (mArticle == null) {
            API.getArticle(mArticleId).enqueue(new Callback<Result<Article>>() {
                @Override
                public void onResponse(Call<Result<Article>> call, Response<Result<Article>> response) {
                    if (response.isSuccessful()) {
                        mArticle = response.body().getData();
                        initView();
                    }
                }

                @Override
                public void onFailure(Call<Result<Article>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
    }

    private void initView() {
        if (mArticle != null) {
            mTvCommentCount.setText(String.format(Locale.US, "%d", mArticle.getComment_count()));
            ViewUtils.setGone(mTvCommentCount, mArticle.getComment_count() <= 0);
            if (mArticle.isUpvoted()) {
                mBtnUpvote.setImageResource(R.mipmap.ic_upvote_checked);
            }
        }
    }
}
