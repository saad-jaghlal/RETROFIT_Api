package com.example.with_retrofit;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.with_retrofit.Data.DataMangemant;
import com.example.with_retrofit.models.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class AjouteActivity extends AppCompatActivity {
    EditText edID, edTitle, edUserId, edBody;

    Button add;
    private static String URL = "https://jsonplaceholder.typicode.com/";
    ApiService apiService;
    Post post;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoute);
        edID = findViewById(R.id.editTextId);
        edTitle = findViewById(R.id.titleEditText);
        edUserId = findViewById(R.id.userIdEditText);
        edBody = findViewById(R.id.bodyEditText);
        add = findViewById(R.id.addButton);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edID.getText().toString()) && !TextUtils.isEmpty(edTitle.getText().toString()) && !TextUtils.isEmpty(edBody.getText().toString())
                        && !TextUtils.isEmpty(edUserId.getText().toString())) {
                    post = new Post(Integer.parseInt(edUserId.getText().toString()),
                            edTitle.getText().toString(),
                            edBody.getText().toString());
//                    createPost();
                    PostData(post);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "fill all", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void PostData(Post post) {
        Call<Post> call = apiService.postData(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Log.d("API", "Post successful");
                    Post postResponse = response.body();
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "UserId: " + postResponse.getUserId() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getBody() + "\n\n";
                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                    DataMangemant.posts.add(postResponse);
                } else {
                    Log.e("API", "Post failed. Code: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("API", "Post failed. Error: " + t.getMessage());
            }
        });
    }
    /*private void createPost() {
        Post post = new Post(23, "New Title", "New Text");
        Call<Post> call = apiService.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getBody() + "\n\n";

                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
}