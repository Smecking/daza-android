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

import java.util.List;

public class Article extends Model {


    private String short_id;        // ShortId
    private int user_id;            // 用户Id
    private Topic topic;
    private int topic_id;           // 主题Id
    private String guid;            // 文章唯一标识符
    private String type;            // 类型 = ['ad', 'share', 'original']stringEnum:"ad", "share", "original"
    private String link;            // 文章链接
    private String title;           // 标题
    private String summary;         // 摘要
    private String content_format;  // 内容格式
    private String content;         // 内容
    private String image_url;       // 图片链接（原始尺寸）
    private String audio_url;       // 音频链接
    private String video_url;       // 视频链接
    private String author;          // 作者
    private String author_email;    // 作者邮箱
    private String author_link;     // 作者链接
    private String source;          // 来源
    private String source_link;     // 来源链接
    private boolean upvoted;        // 已赞成
    private int upvote_count;       // 赞成票数
    private boolean downvoted;      // 已反对
    private int downvote_count;     // 反对票数
    private int view_count;         // 阅读数
    private int comment_count;      // 评论数
    private String published_at;    // 发布时间
    private List<ArticleTag> tags;  // 文章标签

    public String getShort_id() {
        return short_id;
    }

    public void setShort_id(String short_id) {
        this.short_id = short_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent_format() {
        return content_format;
    }

    public void setContent_format(String content_format) {
        this.content_format = content_format;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthor_link() {
        return author_link;
    }

    public void setAuthor_link(String author_link) {
        this.author_link = author_link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }

    public boolean isUpvoted() {
        return upvoted;
    }

    public void setUpvoted(boolean upvoted) {
        this.upvoted = upvoted;
    }

    public int getUpvote_count() {
        return upvote_count;
    }

    public void setUpvote_count(int upvote_count) {
        this.upvote_count = upvote_count;
    }

    public boolean isDownvoted() {
        return downvoted;
    }

    public void setDownvoted(boolean downvoted) {
        this.downvoted = downvoted;
    }

    public int getDownvote_count() {
        return downvote_count;
    }

    public void setDownvote_count(int downvote_count) {
        this.downvote_count = downvote_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public List<ArticleTag> getTags() {
        return tags;
    }

    public void setTags(List<ArticleTag> tags) {
        this.tags = tags;
    }
}
