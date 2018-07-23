package me.kevinxchan.kevinxchan.stretchit.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int exerciseID;
    private Category category;
    private String name;

    public Exercise(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
