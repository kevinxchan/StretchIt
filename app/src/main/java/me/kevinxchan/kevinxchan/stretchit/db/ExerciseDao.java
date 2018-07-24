package me.kevinxchan.kevinxchan.stretchit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import me.kevinxchan.kevinxchan.stretchit.model.Category;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise ORDER BY name ASC")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercise WHERE name = :name")
    Exercise getExerciseByName();

    @Insert
    void insert(Exercise exercise);

    @Query("UPDATE exercise SET category = newCategory WHERE name = :name")
    void updateExerciseCategory(Category newCategory, String name);

    @Query("UPDATE exercise SET name = :newName WHERE name = :oldName")
    void updateExerciseName(String newName, String oldName);

    @Query("DELETE FROM exercise WHERE name = :name")
    void deleteByName(String name);
}
