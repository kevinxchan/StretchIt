package me.kevinxchan.kevinxchan.stretchit.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Routine {
    @PrimaryKey
    private int routineID;
    private String name;
    private int numUsed;

    public Routine(String name) {
        this.name = name;
        this.numUsed = 0;
    }

    public int getRoutineID() {
        return routineID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumUsed() {
        return numUsed;
    }

    public void setNumUsed(int numUsed) {
        this.numUsed = numUsed;
    }
}
