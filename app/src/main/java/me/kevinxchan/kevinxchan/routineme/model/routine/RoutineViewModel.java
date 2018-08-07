package me.kevinxchan.kevinxchan.stretchit.model.routine;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import me.kevinxchan.kevinxchan.stretchit.db.AppDatabase;
import me.kevinxchan.kevinxchan.stretchit.db.RoutineDao;
import me.kevinxchan.kevinxchan.stretchit.model.TaskCompleted;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {
    private RoutineDao routineDao;
    private LiveData<List<Routine>> routineLiveData;
    private AppDatabase appDatabase;

    public RoutineViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application, false);
        routineDao = appDatabase.routineDao();
        routineLiveData = routineDao.getAllRoutines();
    }

    public LiveData<List<Routine>> getRoutinesList() {
        return routineLiveData;
    }

    public void insert(Routine routine, TaskCompleted taskCompleted) {
        new addAsyncTask(appDatabase, taskCompleted).execute(routine);
    }

    public void delete(Routine routine) {
        new deleteAsyncTask(appDatabase).execute(routine);
    }

    public void setRoutineName(String oldName, String newName) {
        routineDao.setRoutineName(oldName, newName);
    }

    public void incrementRoutineTimesUsed(int rid) {
        new updateTimesUsedAsyncTask(appDatabase).execute(rid);
    }

    private static class addAsyncTask extends AsyncTask<Routine, Void, Integer> {
        private AppDatabase db;
        private TaskCompleted taskCompleted;

        addAsyncTask(AppDatabase appDatabase, TaskCompleted taskCompleted) {
            db = appDatabase;
            this.taskCompleted = taskCompleted;
        }

        @Override
        protected Integer doInBackground(Routine... routines) {
            RoutineDao routineDao = db.routineDao();
            Routine routine = routines[0];
            Integer rid = (int) routineDao.insert(routine);
            return rid;
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            taskCompleted.onTaskComplete(i);
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Routine, Void, Void> {
        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Routine... routines) {
            RoutineDao routineDao = db.routineDao();
            routineDao.delete(routines[0]);
            return null;
        }
    }

    private static class updateTimesUsedAsyncTask extends AsyncTask<Integer, Void, Void> {
        private AppDatabase db;

        updateTimesUsedAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            RoutineDao routineDao = db.routineDao();
            routineDao.incrementRoutineTimesUsed(ids[0]);
            return null;
        }
    }

}
