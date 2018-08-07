package me.kevinxchan.kevinxchan.routineme;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import me.kevinxchan.kevinxchan.routineme.db.AppDatabase;
import me.kevinxchan.kevinxchan.routineme.db.ExerciseDao;
import me.kevinxchan.kevinxchan.routineme.db.RoutineDao;
import me.kevinxchan.kevinxchan.routineme.model.Category;
import me.kevinxchan.kevinxchan.routineme.model.exercise.Exercise;
import me.kevinxchan.kevinxchan.routineme.model.routine.Routine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseModelTest {
    private AppDatabase db;
    private RoutineDao r;
    private ExerciseDao e;

    @Before
    public void runBefore() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        r = db.routineDao();
        e = db.exerciseDao();
    }

    @After
    public void runAfter() {
        db.close();
    }

    @Test
    public void testRoutine() {
        assertEquals(0, r.getListOfRoutines().size());
        Routine routine = new Routine("routine");

        long l = r.insert(routine);
        assertEquals(1, r.getListOfRoutines().size());
        assertEquals(0, r.getRoutineByName("routine").getNumTimesUsed());

        r.setRoutineName("routine2", "routine");
        assertEquals(1, r.getListOfRoutines().size());
        assertEquals(l, r.getRoutineByName("routine2").getRoutineID());

        r.setRoutineTimesUsed(50, "routine2");
        assertEquals(50, r.getNumTimesUsedByName("routine2"));

        r.deleteByName("routine2");
        assertEquals(0, r.getListOfRoutines().size());
    }

    @Test
    public void testExerciseForeignKey() {
        assertEquals(0, e.getListOfExercises().size());

        Routine routine = new Routine("foo");
        int rid = (int) r.insert(routine);

        Exercise exercise = new Exercise(Category.COUNTDOWN, "start", rid, "01:01:01");
        Exercise exercise2 = new Exercise(Category.EXERCISE, "run", rid, "01:01:01");
        Exercise exercise3 = new Exercise(Category.REST, "chill", rid, "01:01:01");

        e.insert(exercise);
        e.insert(exercise2);
        e.insert(exercise3);

        List<Exercise> l = e.getListOfExercises();
        for (int i = 1; i <= l.size(); i++ ) {
            assertEquals(1, e.getRoutineIdFromExerciseId(i));
        }
    }

    @Test
    public void testExerciseChangeCategories() {
        Routine routine = new Routine("foo");
        int rid = (int) r.insert(routine);

        Exercise exercise = new Exercise(Category.COUNTDOWN, "foo", rid, "01:01:01");
        Exercise exercise2 = new Exercise(Category.REST, "bar", rid, "01:01:01");
        e.insert(exercise);
        e.insert(exercise2);

        assertEquals(2, e.getListOfExercises().size());
        assertNotEquals(e.getCategoryFromId(1), e.getCategoryFromId(2));

        e.setExerciseCategory(Category.REST, 1);
        assertEquals(e.getCategoryFromId(1), e.getCategoryFromId(2));
    }

    @Test
    public void testExerciseChangeName() {
        Routine routine = new Routine("foo");
        int rid = (int) r.insert(routine);

        Exercise e1 = new Exercise(Category.REST, "foo", rid, "01:01:01");
        Exercise e2 = new Exercise(Category.REST, "foo", rid, "00:00:00");

        e.insert(e1);
        e.insert(e2);
        assertEquals(e.getExerciseById(1).getName(), e.getExerciseById(2).getName());

        e.setExerciseName("bar", 2);
        assertNotEquals(e.getExerciseById(1).getName(), e.getExerciseById(2).getName());
    }
}
