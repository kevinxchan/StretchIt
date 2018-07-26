package me.kevinxchan.kevinxchan.stretchit.model;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

@Entity(tableName = "exercise",
        foreignKeys = @ForeignKey(entity = Routine.class,
                parentColumns = "rid",
                childColumns = "routineId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("eid"), @Index("routineId")})
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "eid")
    private int exerciseID;

    @ColumnInfo(name = "category")
    private Category category;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "routineId")
    @NonNull
    private int routineId;

    public Exercise(Category category, @NonNull String name, @NonNull int routineId) {
        this.category = category;
        this.name = name;
        this.routineId = routineId;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
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

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }
}
