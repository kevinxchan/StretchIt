package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import me.kevinxchan.kevinxchan.stretchit.adapters.ExerciseAdapter;
import me.kevinxchan.kevinxchan.stretchit.model.Category;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class AddExercisesActivity extends AppCompatActivity {
    EditText routineNameEditText;
    RecyclerView recyclerViewExercises;
    RecyclerView.Adapter adapter;
    FloatingActionButton floatingActionButtonAddExercise;

    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        exercises = new ArrayList<Exercise>();
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);

        for (int i = 0; i < 10; i++) {
            Exercise exercise = new Exercise(Category.COUNTDOWN, "foo");
            exercises.add(exercise);
        }

        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(null));
        adapter = new ExerciseAdapter(exercises);
        recyclerViewExercises.setAdapter(adapter);
        routineNameEditText = (EditText) findViewById(R.id.routineNameEditText);
        Button doneBtn = (Button) findViewById(R.id.doneBtn);
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
//                    floatingActionButtonAddExercise.show(true);
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0 || dy < 0 && floatingActionButtonAddExercise.isShown())
//                    floatingActionButtonAddExercise.hide(true);
            }
        });
    }
}
