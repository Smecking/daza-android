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

package io.daza.app.api;

import java.util.List;

import io.daza.app.model.Article;
import io.daza.app.model.ArticleComment;
import io.daza.app.model.ArticleVote;
import io.daza.app.model.Category;
import io.daza.app.model.Notification;
import io.daza.app.model.Result;
import io.daza.app.model.Topic;
import io.daza.app.model.TopicSubscriber;
import io.daza.app.model.User;
import io.daza.app.model.UserConfig;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("/account/register")
    Call<Result<User>> register(@Field("username") String username,
                                @Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("/account/login")
    Call<Result<User>> login(@Field("email") String email,
                             @Field("password") String password);

    @POST("/account/logout")
    Call<Result> logout();

    @GET("/account/profile")
    Call<Result<User>> getProfile();

    @FormUrlEncoded
    @PUT("/account/profile")
    Call<Result<User>> updateProfile(@Field("name") String name,
                                     @Field("city") String city,
                                     @Field("website") String website,
                                     @Field("bio") String bio);

    @FormUrlEncoded
    @PUT("/account/configs")
    Call<Result<List<UserConfig>>> updateConfigs(@Field("notification_followed") boolean notification_followed,
                                                 @Field("notification_subscribed") boolean notification_subscribed,
                                                 @Field("notification_upvoted") boolean notification_upvoted,
                                                 @Field("notification_comment") boolean notification_comment,
                                                 @Field("notification_mention") boolean notification_mention);

    @FormUrlEncoded
    @POST("/account/password_modify")
    Call<Result> modifyPassword(@Field("old_password") String old_password,
                                @Field("new_password") String new_password);


    @GET("/users/{user_id}")
    Call<Result<User>> getUser(@Path("user_id") int user_id);

    @GET("/users/{user_id}/topics")
    Call<Result<List<Topic>>> getUserTopics(@Path("user_id") int user_id,
                                            @Query("page") int page);

    @GET("/users/{user_id}/subscribes")
    Call<Result<List<TopicSubscriber>>> getUserSubscribes(@Path("user_id") int user_id,
                                                          @Query("page") int page);


    @GET("/users/{user_id}/upvotes")
    Call<Result<List<ArticleVote>>> getUserUpvoteArticles(@Path("user_id") int user_id,
                                                          @Query("page") int page);

    @GET("/categories")
    Call<Result<List<Category>>> getCategories(@Query("page") int page);

    @GET("/topics")
    Call<Result<List<Topic>>> getTopics(@Query("page") int page);

    @GET("/topics/{topic_id}")
    Call<Result<Topic>> getTopic(@Path("topic_id") int topic_id);

    @GET("/topics/{topic_id}/articles")
    Call<Result<List<Article>>> getTopicArticles(@Path("topic_id") int topic_id,
                                                 @Query("page") int page);

    @POST("/topics/{topic_id}/subscribe")
    Call<Result<Topic>> subscribeTopic(@Path("topic_id") int topic_id);

    @GET("/articles")
    Call<Result<List<Article>>> getArticles(@Query("page") int page,
                                            @Query("category_id") int category_id,
                                            @Query("category_slug") String category_slug);

    @GET("/articles/{article_id}")
    Call<Result<Article>> getArticle(@Path("article_id") int article_id);

    @FormUrlEncoded
    @POST("/articles/{article_id}/votes")
    Call<Result> voteArticle(@Path("article_id") int article_id,
                             @Field("type") String type);

    @GET("/articles/{article_id}/comments")
    Call<Result<List<ArticleComment>>> getArticleComments(@Path("article_id") int article_id,
                                                          @Query("page") int page);

    @FormUrlEncoded
    @POST("/articles/{article_id}/comments")
    Call<Result<ArticleComment>> createArticleComment(@Path("article_id") int article_id,
                                                      @Field("content") String content);

    @GET("/notifications")
    Call<Result<List<Notification>>> getNotifications(@Query("page") int page,
                                                      @Query("reason") String reason);

    @GET("/notifications/count")
    Call<Result<Notification>> getNotificationCount();

    @POST("/notifications/mark_as_read")
    Call<Result> markAsRead();

    @GET("/search/articles")
    Call<Result<List<Article>>> getSearchResult(@Query("page") int page,
                                                @Query("keyword") String keyword);
}
