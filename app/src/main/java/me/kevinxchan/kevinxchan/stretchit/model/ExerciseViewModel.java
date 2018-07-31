package me.kevinxchan.kevinxchan.stretchit.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import me.kevinxchan.kevinxchan.stretchit.db.AppDatabase;
import me.kevinxchan.kevinxchan.stretchit.db.ExerciseDao;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> exerciseLiveData;
    private AppDatabase appDatabase;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application, false);
        exerciseDao = appDatabase.exerciseDao();
        exerciseLiveData = exerciseDao.getAllExercises();
    }

    public LiveData<List<Exercise>> getExerciseList() {
        return exerciseLiveData;
    }

    public LiveData<List<Exercise>> getExerciseListByRId(int rid) {
        return exerciseDao.getExercisesByRid(rid);
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

    public void addExercise(final Exercise exercise) {
        new addAsyncTask(appDatabase).execute(exercise);
    }

    private static class addAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Exercise... exercises) {
            ExerciseDao exerciseDao = db.exerciseDao();
            exerciseDao.insert(exercises[0]);
            return null;
        }
    }
}
