package me.kevinxchan.kevinxchan.stretchit.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import me.kevinxchan.kevinxchan.stretchit.db.AppDatabase;
import me.kevinxchan.kevinxchan.stretchit.db.ExerciseDao;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> exerciseLiveData;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        exerciseDao = AppDatabase.getInstance(application, false).exerciseDao();
        exerciseLiveData = exerciseDao.getAllExercises();
    }

    public LiveData<List<Exercise>> getExerciseList() {
        return exerciseLiveData;
    }

    // TODO: figure out what to put here
    public void insert(Exercise exercise) {
        exerciseDao.insert(exercise);
    }

    public void updateExerciseName(String newName, int eid) {
        exerciseDao.setExerciseNameById(newName, eid);
    }

    public void deleteById(int eid) {
        exerciseDao.deleteById(eid);
    }
}
