package com.example.with_retrofit;

import com.example.with_retrofit.models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);
    @GET("posts")
    Call<List<Post>> getPosts();
    @POST("posts")
    Call<Post> postData(@Body Post post);

    @POST("posts")
    Call<Post> createPost(@Body Post post);
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Post> deletePost(@Path("id") int id);
}
