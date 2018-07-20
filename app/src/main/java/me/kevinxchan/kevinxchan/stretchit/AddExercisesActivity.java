package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.github.clans.fab.FloatingActionButton;
import me.kevinxchan.kevinxchan.stretchit.adapters.ExerciseAdapter;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class AddExercisesActivity extends AppCompatActivity {
    EditText routineNameEditText;
    RecyclerView recyclerViewExercises;
    RecyclerView.Adapter adapter;

    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        exercises = new ArrayList<Exercise>();
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(null));
        adapter = new ExerciseAdapter(exercises);
        recyclerViewExercises.setAdapter(adapter);
        routineNameEditText = (EditText) findViewById(R.id.routineNameEditText);
        Button doneBtn = (Button) findViewById(R.id.doneBtn);
        FloatingActionButton floatingActionButtonCountdown = (FloatingActionButton) findViewById(R.id.floatingActionItemCountdown);
        FloatingActionButton floatingActionButtonRest = (FloatingActionButton) findViewById(R.id.floatingActionItemRest);
        FloatingActionButton floatingActionButtonExercise = (FloatingActionButton) findViewById(R.id.floatingActionItemExercise);


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasBeenFilled(routineNameEditText)) {
                    Intent doneIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(doneIntent);
                } else {
                    routineNameEditText.setError("Routine name is required!");
                }
            }
        });
        floatingActionButtonCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent countDownIntent = new Intent(getApplicationContext(), CountdownActivity.class);
                startActivity(countDownIntent);
            }
        });
        floatingActionButtonRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restIntent = new Intent(getApplicationContext(), RestActivity.class);
                startActivity(restIntent);
            }
        });
        floatingActionButtonExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(exerciseIntent);
            }
        });
    }

    private boolean hasBeenFilled(EditText editText) {
        return !(TextUtils.isEmpty(editText.getText().toString()));
    }
}
