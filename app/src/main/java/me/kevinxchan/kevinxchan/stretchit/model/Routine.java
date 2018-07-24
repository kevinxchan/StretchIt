package me.kevinxchan.kevinxchan.stretchit.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Routine {
    @PrimaryKey(autoGenerate = true)
    private int routineID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "num_times_used")
    private int numTimesUsed;

    public Routine(String name) {
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
