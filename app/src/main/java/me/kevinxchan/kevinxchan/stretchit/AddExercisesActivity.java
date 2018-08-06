package me.kevinxchan.kevinxchan.stretchit;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import me.kevinxchan.kevinxchan.stretchit.model.exercise.ExerciseAdapter;
import me.kevinxchan.kevinxchan.stretchit.model.exercise.Exercise;
import me.kevinxchan.kevinxchan.stretchit.model.ItemTouchCallback;
import me.kevinxchan.kevinxchan.stretchit.model.exercise.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddExercisesActivity extends AppCompatActivity implements View.OnClickListener {
    private ExerciseViewModel viewModel;
    private ExerciseAdapter exerciseAdapter;
    private FloatingActionButton floatingActionButtonAddExercise;
    private RecyclerView.OnScrollListener onScrollListener;
    private int currRoutineId;

    private static final String TAG = "AddExercisesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);
        setToolbar(getString(R.string.activity_add_exercises_title));
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            currRoutineId = extras.getInt("ROUTINE_ID");
        initView();
    }

    private void initView() {
        floatingActionButtonAddExercise = findViewById(R.id.floatingActionButtonAddExercise);
        floatingActionButtonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
                exerciseIntent.putExtra("ROUTINE_ID", currRoutineId);
                startActivity(exerciseIntent);
            }
        });

        final RecyclerView recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        onScrollListener = initOnScrollListener();
        recyclerViewExercises.addOnScrollListener(onScrollListener);

        exerciseAdapter = new ExerciseAdapter(new ArrayList<Exercise>(), this);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExercises.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewExercises.setAdapter(exerciseAdapter);

        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        viewModel.getExerciseListByRId(currRoutineId).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                exerciseAdapter.setExerciseList(exercises);
            }
        });

        ItemTouchCallback itemTouchCallback = new ItemTouchCallback(this) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                // TODO: implement this for drag and drop behavior
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                Exercise exercise = exerciseAdapter.getExerciseAtPosition(position);
                Log.d(TAG, "deleting exercise: " + exercise.getName());
                viewModel.deleteExercise(exercise);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewExercises);
    }

    private RecyclerView.OnScrollListener initOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    floatingActionButtonAddExercise.show();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && floatingActionButtonAddExercise.isShown())
                    floatingActionButtonAddExercise.hide();
            }
        };
    }

    private void setToolbar(@NonNull String string) {
        Toolbar toolbar = findViewById(R.id.activity_add_exercises_toolbar);
        toolbar.setTitle(string);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_exercises, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_start_timer:
                Log.d(TAG, "starting timer activity");
                List<Exercise> exercises = exerciseAdapter.getAllExercises();
                if (exercises.size() == 0)
                    Toast.makeText(this, "You need at least one exercise to start the timer.", Toast.LENGTH_LONG).show();
                else {
                    Intent timerIntent = new Intent(AddExercisesActivity.this, TimerActivity.class);
                    timerIntent.putParcelableArrayListExtra("EXERCISES", (ArrayList<Exercise>) exercises);
                    startActivity(timerIntent);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Exercise exercise = (Exercise) view.getTag();
        Log.d(TAG, "exercise " + exercise.getName() + " clicked");
        Intent editExerciseIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
        editExerciseIntent.putExtra("ROUTINE_ID", currRoutineId);
        editExerciseIntent.putExtra("EXERCISE_ID", exercise.getExerciseID());
        editExerciseIntent.putExtra("EXERCISE_NAME", exercise.getName());
        editExerciseIntent.putExtra("EXERCISE_CATEGORY", exercise.getCategory());
        editExerciseIntent.putExtra("EXERCISE_DURATION", exercise.getDuration());
        startActivity(editExerciseIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backToMainIntent = new Intent(AddExercisesActivity.this, MainActivity.class);
        startActivity(backToMainIntent);
        finish();
    }
}
