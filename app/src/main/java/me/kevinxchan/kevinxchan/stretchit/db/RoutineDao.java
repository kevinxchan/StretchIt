package me.kevinxchan.kevinxchan.stretchit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;

import java.util.List;

@Dao
public interface RoutineDao {
    @Query("SELECT * FROM routine")
    List<Routine> getAllRoutines();

    // TODO: implement!
}
