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
        exerciseDao = AppDatabase.getInstance(application).exerciseDao();
        exerciseLiveData = exerciseDao.getAllExercises();
    }

    public LiveData<List<Exercise>> getExerciseList() {
        return exerciseLiveData;
    }

    public void insert(Exercise exercise) {
        exerciseDao.insert(exercise);
    }

    public void updateExerciseName(String newName, String oldName) {
        exerciseDao.updateExerciseName(newName, oldName);
    }

    public void deleteByName(String name) {
        exerciseDao.deleteByName(name);
    }
}
