package me.kevinxchan.kevinxchan.stretchit.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "routine", indices = { @Index(value = "name", unique = true) })
public class Routine {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rid")
    private int routineID;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "num_times_used")
    private int numTimesUsed;

    public Routine(@NonNull String name) {
        this.name = name;
        this.numTimesUsed = 0;
    }

    public int getRoutineID() {
        return routineID;
    }

    public void setRoutineID(int routineID) {
        this.routineID = routineID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumTimesUsed() {
        return numTimesUsed;
    }

    public void setNumTimesUsed(int numUsed) {
        this.numTimesUsed = numUsed;
    }
}
