package me.kevinxchan.kevinxchan.stretchit.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;

@Database(entities = { Routine.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoutineDao routineDao();
    // TODO: implement!
}
