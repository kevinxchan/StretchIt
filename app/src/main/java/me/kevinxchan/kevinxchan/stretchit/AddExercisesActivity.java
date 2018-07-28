package me.kevinxchan.kevinxchan.stretchit;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import me.kevinxchan.kevinxchan.stretchit.adapters.ExerciseAdapter;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;
import me.kevinxchan.kevinxchan.stretchit.model.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddExercisesActivity extends AppCompatActivity implements View.OnClickListener {
    private ExerciseViewModel viewModel;
    private ExerciseAdapter exerciseAdapter;
    private FloatingActionButton floatingActionButtonAddExercise;
    private RecyclerView.OnScrollListener onScrollListener;
    private int currRoutineId;

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
                startActivity(exerciseIntent);
            }
        });

        final RecyclerView recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        onScrollListener = initOnScrollListener();
        recyclerViewExercises.addOnScrollListener(onScrollListener);

        exerciseAdapter = new ExerciseAdapter(new ArrayList<Exercise>(), this);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExercises.setAdapter(exerciseAdapter);

        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        viewModel.getExerciseListByRId(currRoutineId).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                exerciseAdapter.setExerciseList(exercises);
            }
        });
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
            case R.id.action_confirm_routine_finished:
                // TODO: add all exercises to routine
                Intent backtoMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backtoMainIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Log.d("add exercise click", "hello i'm clicked");
    }
}
