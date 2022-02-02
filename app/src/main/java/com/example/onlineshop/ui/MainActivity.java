package com.example.onlineshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ShopAdapter;
import com.example.onlineshop.database.ShopDatabase;
import com.example.onlineshop.listener.OnAddProduct;
import com.example.onlineshop.model.Example;
import com.example.onlineshop.service.ApiService;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewMian;
    ImageView imageViewMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMian = findViewById(R.id.recyclerViewMain);
        imageViewMore = findViewById(R.id.imageViewMore);


        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(gsonConverterFactory)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getAllExample().enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                List<Example> exampleList = response.body();
                ShopAdapter shopAdapter = new ShopAdapter(exampleList, MainActivity.this );
                recyclerViewMian.setAdapter(shopAdapter);
                recyclerViewMian.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "خطایی وجود دارد", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.actionSettings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.actionFavourite:
                intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }
}