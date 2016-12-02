package io.daza.app.api;

import java.util.ArrayList;

import io.daza.app.model.Article;
import io.daza.app.model.Category;
import io.daza.app.model.Notification;
import io.daza.app.model.Result;
import io.daza.app.model.Topic;
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
    @POST("/account/login")
    Call<Result<User>> login(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
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
    Call<Result<ArrayList<UserConfig>>> updateConfigs(@Field("notification_followed") boolean notification_followed,
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
    Call<Result<ArrayList<Topic>>> getUserTopics(@Path("user_id") int user_id,
                                                 @Query("page") int page);

    @GET("/users/{user_id}/subscribes")
    Call<Result<ArrayList<Topic>>> getUserSubscribes(@Path("user_id") int user_id,
                                                     @Query("page") int page);


    @GET("/users/{user_id}/upvotes")
    Call<Result<ArrayList<Article>>> getUserUpvoteArticles(@Path("user_id") int user_id,
                                                           @Query("page") int page);

    @GET("/categories")
    Call<Result<ArrayList<Category>>> getCategories(@Query("page") int page);

    @GET("/topics")
    Call<Result<ArrayList<Topic>>> getTopics(@Query("page") int page);

    @GET("/topics/{topic_id}")
    Call<Result<Topic>> getTopic(@Path("topic_id") int topic_id);

    @GET("/topics/{topic_id}/articles")
    Call<Result<ArrayList<Article>>> getTopicArticles(@Path("topic_id") int topic_id,
                                                      @Query("page") int page);

    @FormUrlEncoded
    @POST("/topics/{topic_id}/subscribe")
    Call<Result<Topic>> subscribeTopic(@Path("topic_id") int topic_id);

    @GET("/articles")
    Call<Result<ArrayList<Article>>> getArticles(@Query("page") int page,
                                                 @Query("category_id") int category_id,
                                                 @Query("category_slug") String category_slug);

    @GET("/articles/{article_id}")
    Call<Result<Article>> getArticle(@Path("article_id") int article_id);

    @GET("/articles/{article_id}/comments")
    Call<Result<ArrayList<Article>>> getArticleComments(@Path("article_id") int article_id,
                                                        @Query("page") int page);

    @FormUrlEncoded
    @POST("/articles/{article_id}/comments")
    Call<Result<ArrayList<Article>>> createArticleComment(@Path("article_id") int article_id,
                                                          @Field("content") String content);

    @GET("/notifications")
    Call<Result<ArrayList<Notification>>> getNotifications(@Query("page") int page,
                                                           @Query("reason") String reason);

    @GET("/notifications/count")
    Call<Result<Notification>> getNotificationCount();

    @FormUrlEncoded
    @POST("/notifications/mark_as_read")
    Call<Result> markAsRead();

    @GET("/search/articles")
    Call<Result<ArrayList<Article>>> getSearchResult(@Query("page") int page,
                                                     @Query("keyword") String keyword);
}
