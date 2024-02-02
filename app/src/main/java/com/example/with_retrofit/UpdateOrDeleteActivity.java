package com.example.with_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class UpdateOrDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edID, edTitle, edUserId, edBody;
    Button Delete, Update;
    ApiService apiService;
    private static String URL = "https://jsonplaceholder.typicode.com/";
    Post postUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_delete);
        edID = findViewById(R.id.editTextId);
        edTitle = findViewById(R.id.titleEditText);
        edUserId = findViewById(R.id.userIdEditText);
        edBody = findViewById(R.id.bodyEditText);
        Delete = findViewById(R.id.deleteButton);
        Update = findViewById(R.id.upDateButton);
        int position = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            Post  post = DataMangemant.posts.get(position);
            edID.setText(String.valueOf(post.getId()));
            edTitle.setHint(post.getTitle());
            edUserId.setHint(String.valueOf(post.getUserId()));
            edBody.setHint(post.getBody());
        } else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        Delete.setOnClickListener(this);
//        Update.setOnClickListener(this);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id = Integer.parseInt(edID.getText().toString().trim());
//            int userId = Integer.parseInt(edUserId.getText().toString().trim());
//            String title = edTitle.getText().toString();
//            String body = edBody.getText().toString();
//                postUpdated = new Post(Integer.parseInt(edID.getText().toString().trim())
//                        ,Integer.parseInt(edUserId.getText().toString().trim())
//                        ,edTitle.getText().toString(),edBody.getText().toString());
            postUpdated = new Post(1,1,"saad","ahmed");
                putPost(postUpdated.getId(), postUpdated);
            }
        });
    }

    public void putPost(int id,Post post) {
        Call<Post> call = apiService.putPost(id,post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                    if(response.isSuccessful()){
                        Post postResponse = response.body();
                        String content = "";
                        content += "Code: " + response.code() + "\n";
                        content += "ID: " + postResponse.getId() + "\n";
                        content += "UserId: " + postResponse.getUserId() + "\n";
                        content += "Title: " + postResponse.getTitle() + "\n";
                        content += "Text: " + postResponse.getBody() + "\n\n";
                        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "UnSuccessful", Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void deletePost(int id){
        Call<Post> call = apiService.deletePost(id);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Post postResponse = response.body();
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "UserId: " + postResponse.getUserId() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getBody() + "\n\n";
                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "UnSuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upDateButton) {
//            int id = Integer.parseInt(edID.getText().toString().trim());
//            int userId = Integer.parseInt(edUserId.getText().toString().trim());
//            String title = edTitle.getText().toString();
//            String body = edBody.getText().toString();
//            postUpdated = new Post(Integer.parseInt(edID.getText().toString().trim())
//                    ,Integer.parseInt(edUserId.getText().toString().trim())
//                    ,edTitle.getText().toString(),edBody.getText().toString());
//            postUpdated = new Post(1,1,"saad","ahmed");
            putPost(postUpdated.getId(), postUpdated);
        } else if (v.getId() == R.id.deleteButton) {
            deletePost(1);
        }
    }
}