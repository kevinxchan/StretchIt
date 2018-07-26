package me.kevinxchan.kevinxchan.stretchit.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import me.kevinxchan.kevinxchan.stretchit.model.Converter;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;

@Database(entities = { Routine.class, Exercise.class}, version = 1)
@TypeConverters({ Converter.class })
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static final String dbName = "routines.db";

    public abstract RoutineDao routineDao();
    public abstract ExerciseDao exerciseDao();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, dbName)
                    .allowMainThreadQueries().build(); // TODO: change in production
        }
        return instance;
    }


}
