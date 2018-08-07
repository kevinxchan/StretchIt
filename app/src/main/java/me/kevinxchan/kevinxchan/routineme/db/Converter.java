package me.kevinxchan.kevinxchan.stretchit.db;

import android.arch.persistence.room.TypeConverter;
import me.kevinxchan.kevinxchan.stretchit.model.Category;

public class Converter {
    @TypeConverter
    public static String fromCategoryToString(Category category) {
        if (category == null)
            return null;
        return category.toString();
    }

    @TypeConverter
    public static Category fromStringToCategory(String string) {
        if (string == null)
            return null;
        switch (string) {
            case("Countdown"):
                return Category.COUNTDOWN;
            case("Rest"):
                return Category.REST;
            case("Exercise"):
                return Category.EXERCISE;
        }
        throw new IllegalArgumentException("I should never be reached!");
    }
}
