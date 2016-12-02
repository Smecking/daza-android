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

import io.daza.app.BuildConfig;
import io.daza.app.R;
import io.daza.app.model.Article;
import io.daza.app.model.Model;

public class ArticleDetailActivity extends InAppBrowserActivity {

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticle_detail);

        mArticle = Model.parseObject(getIntent().getStringExtra("extra_article"), Article.class);

        loadUrl(BuildConfig.WEB_BASE_URL + "/in-app/articles/" + mArticle.getId());
    }
}
