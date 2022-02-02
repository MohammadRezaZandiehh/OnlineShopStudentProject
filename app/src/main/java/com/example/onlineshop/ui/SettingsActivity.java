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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ShopAdapter;
import com.example.onlineshop.listener.ListenerInterface;
import com.example.onlineshop.manager.Manager;
import com.example.onlineshop.model.Example;
import com.example.onlineshop.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {
    private Manager manager;
    private String gender = "";
    ListenerInterface listenerInterface;
    ShopAdapter shopAdapter;
    Button buttonSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settintgs);

        RecyclerView recyclerViewSettings = findViewById(R.id.recyclerViewSettings);
        manager = new Manager(this);
        RadioGroup radioGroup = findViewById(R.id.radioGroup_check);
        RadioButton radioButton = findViewById(R.id.radioBtn_Description);

        gender = manager.getGender();
        if (gender.equalsIgnoreCase("description")) {
            radioGroup.check(R.id.radioBtn_Description);
        } else if (gender.equalsIgnoreCase("title")) {
            radioGroup.check(R.id.radioBtn_title);
        } else if (gender.equalsIgnoreCase("category")) {
            radioGroup.check(R.id.textVieCategory);
        } else if (gender.equalsIgnoreCase("all")) {
            radioGroup.check(R.id.radioBtn_all);
        }
        //allStates


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

                shopAdapter = new ShopAdapter(exampleList, SettingsActivity.this);
                recyclerViewSettings.setAdapter(shopAdapter);
                recyclerViewSettings.setLayoutManager(new LinearLayoutManager(SettingsActivity.this));

            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                Toast.makeText(SettingsActivity.this, "خطایی وجود دارد", Toast.LENGTH_SHORT).show();

            }
        });
        onRadioButtonClicked(radioButton);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.actionSettings:
                intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.actionFavourite:
                intent = new Intent(SettingsActivity.this, FavouriteActivity.class);
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


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioBtn_Description:
                if (checked) {
                    listenerInterface.onListenerDescription();
                    manager.saveUserInformation(gender);
                }
                break;
            case R.id.radioBtn_title:
                if (checked)
                    listenerInterface.onListenerTitle();
                manager.saveUserInformation(gender);
                break;
            case R.id.radioBtn_category:
                if (checked)
                    listenerInterface.onListenerCategory();
                manager.saveUserInformation(gender);
                break;
        }
    }
}