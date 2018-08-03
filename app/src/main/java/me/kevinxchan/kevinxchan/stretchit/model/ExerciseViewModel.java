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

    public void updateExercise(final Exercise exercise) {
        new updateAsyncTask(appDatabase).execute(exercise);
    }

    public void updateExerciseName(String newName, int eid) {
        exerciseDao.setExerciseName(newName, eid);
    }

    public void updateExerciseCategory(Category category, int eid) {
        exerciseDao.setExerciseCategory(category, eid);
    }

    public void updateExerciseDuration(String duration, int eid) {
        exerciseDao.setDuration(duration, eid);
    }

    public void deleteExercise(final Exercise exercise) {
        new deleteAsyncTask(appDatabase).execute(exercise);
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

    private static class updateAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private AppDatabase db;

        updateAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            ExerciseDao exerciseDao = db.exerciseDao();
            exerciseDao.updateExercise(exercises[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            ExerciseDao exerciseDao = db.exerciseDao();
            exerciseDao.delete(exercises[0]);
            return null;
        }
    }
}
