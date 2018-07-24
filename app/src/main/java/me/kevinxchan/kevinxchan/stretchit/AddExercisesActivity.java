package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import me.kevinxchan.kevinxchan.stretchit.adapters.ExerciseAdapter;
import me.kevinxchan.kevinxchan.stretchit.model.Category;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class AddExercisesActivity extends AppCompatActivity {
    private EditText routineNameEditText;
    private RecyclerView recyclerViewExercises;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton floatingActionButtonAddExercise;

    private List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_add_exercises_toolbar);
        setSupportActionBar(toolbar);

        exercises = new ArrayList<Exercise>();
        recyclerViewExercises = initRecyclerView();

        for (int i = 0; i < 10; i++) {
            Exercise exercise = new Exercise(Category.COUNTDOWN, "foo");
            exercises.add(exercise);
        }

        routineNameEditText = (EditText) findViewById(R.id.routineNameEditText);
        floatingActionButtonAddExercise = (FloatingActionButton) findViewById(R.id.floatingActionButtonAddExercise);

        floatingActionButtonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(exerciseIntent);
            }
        });
        recyclerViewExercises.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });
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

    private RecyclerView initRecyclerView() {
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(null));
        adapter = new ExerciseAdapter(exercises);
        recyclerViewExercises.setAdapter(adapter);
        return recyclerViewExercises;
    }
}
