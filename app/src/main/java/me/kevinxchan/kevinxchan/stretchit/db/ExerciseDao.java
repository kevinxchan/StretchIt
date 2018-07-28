package me.kevinxchan.kevinxchan.stretchit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import me.kevinxchan.kevinxchan.stretchit.model.Category;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    List<Exercise> getListOfExercises();

    @Query("SELECT routineId FROM exercise WHERE eid = :eid")
    int getRoutineIdFromExerciseId(int eid);

    @Query("SELECT category FROM exercise WHERE eid = :eid")
    Category getCategoryFromId(int eid);

    @Query("SELECT * FROM exercise ORDER BY name ASC")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercise WHERE routineId = :rid ORDER BY name ASC")
    LiveData<List<Exercise>> getExercisesByRid(int rid);

    @Query("SELECT * FROM exercise WHERE eid = :eid")
    Exercise getExerciseById(int eid);

    @Insert
    void insert(Exercise exercise);

    @Query("UPDATE exercise SET category = :newCategory WHERE eid = :eid")
    void setExerciseCategory(Category newCategory, int eid);

    @Query("UPDATE exercise SET name = :newName WHERE eid = :eid")
    void setExerciseNameById(String newName, int eid);

    @Query("DELETE FROM exercise WHERE eid = :eid")
    void deleteByName(int eid);

    @Delete
    void delete(Exercise exercise);
}
