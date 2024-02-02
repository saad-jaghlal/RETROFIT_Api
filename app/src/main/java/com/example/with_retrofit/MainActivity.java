package com.example.with_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.with_retrofit.Data.DataMangemant;
import com.example.with_retrofit.adapter.PostAdapter;
import com.example.with_retrofit.models.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,PostAdapter.OnItemClickListener{
    List<String> stringList;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        postAdapter = new PostAdapter(DataMangemant.posts,this);
        getPosts();
        findViewById(R.id.fab).setOnClickListener(this);
    }
    @Override
    public void onItemClick(int position) {
        DataMangemant.postsSelected.add(DataMangemant.posts.get(position));
        Toast.makeText(getApplicationContext(),"Clicked on "+position,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),UpdateOrDeleteActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = apiService.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
//                    textViewResult.setText("Code: ☼" + response.code());
                    return;
                }

                DataMangemant.posts.addAll(response.body());
                int count = 0;
                for (Post post : DataMangemant.posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Body: " + post.getBody() + "\n\n";
                    count +=1;
//                    stringList.add(content);
                }
//                Toast.makeText(getApplicationContext(),"nb info"+count,Toast.LENGTH_LONG).show();
//                adapter.addAll(stringList);
//                textViewResult.setAdapter(adapter);
                recyclerView.setAdapter(postAdapter);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"info"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab){
            Intent intent = new Intent(getApplicationContext(),AjouteActivity.class);
            startActivity(intent);
        }
    }


}