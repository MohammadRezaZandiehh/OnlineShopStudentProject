package com.example.onlineshop.convertor;

import androidx.room.TypeConverter;

import com.example.onlineshop.model.Rating;

import org.json.JSONObject;

public class RateTypeConverter {
    @TypeConverter
    public static String ratingToString(Rating rating) {
        if (rating == null) {
            return null;
        } else {
            return rating.getRate().toString();
        }
    }

    @TypeConverter
    public Rating fromTimestamp(String string) {
        return string == null ? null : new Rating();
    }
}
