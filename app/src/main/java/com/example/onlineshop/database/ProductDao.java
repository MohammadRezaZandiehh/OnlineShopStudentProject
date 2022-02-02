package com.example.onlineshop.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.onlineshop.model.Example;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProduct(Example example);

    @Delete
    void deleteProduct (Example example);

    @Update
    void updateProduct (Example example);

    @Query("select * from example")
    List<Example> getAllProduct();

    @Query("SELECT * FROM example WHERE title LIKE '%' || :query || '%'")
    List<Example> search(String query);
}
