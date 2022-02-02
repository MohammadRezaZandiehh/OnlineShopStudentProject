package com.example.onlineshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.FavouriteAdapter;
import com.example.onlineshop.database.ShopDatabase;
import com.example.onlineshop.listener.OnAdapterUpdate;
import com.example.onlineshop.model.Example;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavouriteActivity extends AppCompatActivity {

    Executor executor;
    ShopDatabase shopDatabase;
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerViewFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        shopDatabase = ShopDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();

        recyclerViewFavourite = findViewById(R.id.recyclerViewFavourite);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Example> exampleList = shopDatabase.productDao().getAllProduct();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        favouriteAdapter = new FavouriteAdapter(exampleList, getApplicationContext());
                        favouriteAdapter.setonAdapterUpdate(new OnAdapterUpdate() {
                            @Override
                            public void onAdapterUpdate() {
                                favouriteAdapter.notifyDataSetChanged();
                            }
                        });
                        recyclerViewFavourite.setAdapter(favouriteAdapter);
                        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                });
            }
        });



        EditText searchEt = findViewById(R.id.et_main);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            List<Example> exampleList = shopDatabase.productDao().search(s.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    favouriteAdapter = new FavouriteAdapter(exampleList, getApplicationContext());
                                    recyclerViewFavourite.setAdapter(favouriteAdapter);
                                    recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}