package me.kevinxchan.kevinxchan.stretchit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;

import java.util.List;

@Dao
public interface RoutineDao {
    @Query("SELECT * FROM routine")
    LiveData<List<Routine>> getAllRoutines();

    @Query("SELECT * FROM routine WHERE name = :name ORDER BY name ASC")
    Routine findRoutineByName(String name);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Routine routine);

    @Query("UPDATE routine SET name = :setToName WHERE name = :originalName")
    void updateRoutineName(String setToName, String originalName);

    @Query("UPDATE routine SET num_times_used = :newNumTimesUsed WHERE name = :name")
    void updateRoutineTimesUsed(int newNumTimesUsed, String name);
}
