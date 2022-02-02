package com.example.onlineshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlineshop.convertor.RateTypeConverter;
import com.example.onlineshop.model.Example;

@Database(entities = Example.class, exportSchema = false, version = 1)
@TypeConverters({RateTypeConverter.class})
public abstract class ShopDatabase extends RoomDatabase {
    public static final String DB_NAME = "dataabse";
    static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    ShopDatabase.class,
                    DB_NAME
            ).fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


    public abstract ProductDao productDao();
}
