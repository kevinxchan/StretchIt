package me.kevinxchan.kevinxchan.stretchit.model.exercise;

import android.arch.persistence.room.*;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import me.kevinxchan.kevinxchan.stretchit.model.Category;
import me.kevinxchan.kevinxchan.stretchit.model.routine.Routine;

@Entity(tableName = "exercise",
        foreignKeys = @ForeignKey(entity = Routine.class,
                parentColumns = "rid",
                childColumns = "routineId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("eid"), @Index("routineId")})
public class Exercise implements Parcelable {
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

    @ColumnInfo(name = "duration") // string as "HH:MM:SS"
    private String duration;

    public Exercise(Category category, @NonNull String name, @NonNull int routineId, String duration) {
        this.category = category;
        this.name = name;
        this.routineId = routineId;
        this.duration = duration;
    }

    protected Exercise(Parcel in) {
        exerciseID = in.readInt();
        name = in.readString();
        routineId = in.readInt();
        duration = in.readString();
        category = Category.valueOf(in.readString().toUpperCase());
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(exerciseID);
        parcel.writeString(name);
        parcel.writeInt(routineId);
        parcel.writeString(duration);
        parcel.writeString(category == null ? null : category.toString());
    }
}
