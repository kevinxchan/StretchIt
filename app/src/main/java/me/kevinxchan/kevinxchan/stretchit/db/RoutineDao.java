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
    List<Routine> getListOfRoutines();

    @Query("SELECT * FROM routine")
    LiveData<List<Routine>> getAllRoutines();

    @Query("SELECT * FROM routine WHERE name = :name ORDER BY name ASC")
    Routine getRoutineByName(String name);

    @Query("SELECT * FROM routine WHERE rid = :rid")
    Routine getRoutineById(int rid);

    @Query("SELECT num_times_used FROM routine WHERE name = :name")
    int getNumTimesUsedByName(String name);

    @Query("SELECT num_times_used FROM routine WHERE rid = :rid")
    int getNumTimesUsedById(int rid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Routine routine);

    @Query("UPDATE routine SET name = :setToName WHERE name = :originalName")
    void setRoutineName(String setToName, String originalName);

    @Query("UPDATE routine SET num_times_used = :newNumTimesUsed WHERE name = :name")
    void setRoutineTimesUsed(int newNumTimesUsed, String name);

    @Query("DELETE FROM routine WHERE name = :name")
    void deleteByName(String name);
}
