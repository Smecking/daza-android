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

package io.daza.app.model;

public class Topic extends Model {

    private String short_id;        // ShortId
    private User user;
    private int user_id;            // 用户Id
    private int category_id;        // 分类Id
    private String name;            // 名称
    private String slug;            // Slug
    private String type;            // 类型 = ['feed', 'original', 'official'
    private String website;         // 主页
    private String image_url;       // 图片链接（原始尺寸）
    private String description;     // 描述
    private String source_format;   // 文章来源格式 = ['daza+json', 'atom+xml', 'rss+xml']
    private String source_link;     // 文章来源链接
    private int article_count;      // 文章数
    private int subscriber_count;   // 订阅数

    public String getShort_id() {
        return short_id;
    }

    public void setShort_id(String short_id) {
        this.short_id = short_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource_format() {
        return source_format;
    }

    public void setSource_format(String source_format) {
        this.source_format = source_format;
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }

    public int getArticle_count() {
        return article_count;
    }

    public void setArticle_count(int article_count) {
        this.article_count = article_count;
    }

    public int getSubscriber_count() {
        return subscriber_count;
    }

    public void setSubscriber_count(int subscriber_count) {
        this.subscriber_count = subscriber_count;
    }
}
