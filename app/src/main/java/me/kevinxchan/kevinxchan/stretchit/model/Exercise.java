package me.kevinxchan.kevinxchan.stretchit.model;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

@Entity(tableName = "exercise",
        foreignKeys = @ForeignKey(entity = Routine.class,
                parentColumns = "rid",
                childColumns = "routineId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "name", unique = true)})
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "eid")
    private int exerciseID;

    @ColumnInfo(name = "category")
    private Category category;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    public Exercise(Category category, @NonNull String name) {
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
