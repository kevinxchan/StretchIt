package me.kevinxchan.kevinxchan.routineme;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import me.kevinxchan.kevinxchan.routineme.model.routine.Routine;
import me.kevinxchan.kevinxchan.routineme.model.routine.RoutineViewModel;
import me.kevinxchan.kevinxchan.routineme.model.TaskCompleted;

import java.util.List;

public class RoutineNameActivity extends AppCompatActivity implements TaskCompleted {
    private EditText routineNameEditText;
    private RoutineViewModel routineViewModel;

    private static final String TAG = "RoutineNameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_name);

        initView();
    }

    private void initView() {
        routineViewModel = ViewModelProviders.of(this).get(RoutineViewModel.class);
        Button nextBtn = findViewById(R.id.nextBtn);
        routineNameEditText = findViewById(R.id.routineNameEditText);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String routineName = routineNameEditText.getText().toString();
                if (!hasBeenFilled(routineName)) {
                    routineNameEditText.setError("Routine name is required!");
                } else if (hasBeenFilled(routineName) && notSameName(routineName)) {
                    saveRoutine(routineNameEditText);
                } else {
                    routineNameEditText.setError("This name is already taken! Please choose a different one.");
                }
            }
        });

    }

    private boolean notSameName(String routineName) {
        List<Routine> routines = routineViewModel.getRoutinesList().getValue();
        if (routines == null)
            return true;
        for (Routine routine : routines) {
            Log.d(TAG, routine.getName());
            if (routine.getName().equals(routineName))
                return false;
        }
        return true;
    }

    private void saveRoutine(EditText routineNameEditText) {
        String name = routineNameEditText.getText().toString();
        Log.d("Save routine", "saving routine with name " + name);
        Routine routine = new Routine(name);
        routineViewModel.insert(routine, this);
    }

    private boolean hasBeenFilled(String routineName) {
        return !(TextUtils.isEmpty(routineName));
    }

    @Override
    public void onTaskComplete(Integer result) {
        Intent nextIntent = new Intent(getApplicationContext(), AddExercisesActivity.class);
        nextIntent.putExtra("ROUTINE_ID", result);
        startActivity(nextIntent);
    }
}
