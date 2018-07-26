package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import me.kevinxchan.kevinxchan.stretchit.db.AppDatabase;
import me.kevinxchan.kevinxchan.stretchit.db.RoutineDao;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;

public class RoutineNameActivity extends AppCompatActivity {
    private EditText routineNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_name);

        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        routineNameEditText = (EditText) findViewById(R.id.routineNameEditText);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String routineName = routineNameEditText.getText().toString();
                if (!hasBeenFilled(routineName))
                    routineNameEditText.setError("Routine name is required!");

                if (hasBeenFilled(routineName) && notSameName(routineName)) {
                    saveRoutine(routineNameEditText);
                    Intent nextIntent = new Intent(getApplicationContext(), AddExercisesActivity.class);
                    startActivity(nextIntent);
                } else {
                    routineNameEditText.setError("This name is already taken! Please choose a different one.");
                }
            }
        });

    }

    private boolean notSameName(String routineName) {
        RoutineDao routineDao = AppDatabase.getInstance(this, false).routineDao();
        Routine r = routineDao.getRoutineByName(routineName);
        return r == null;
    }

    private void saveRoutine(EditText routineNameEditText) {
        Log.d("Save routine", "saving routine with name " + routineNameEditText);
        RoutineDao routineDao = AppDatabase.getInstance(this, false).routineDao();
        routineDao.insert(new Routine(routineNameEditText.getText().toString()));
    }

    private boolean hasBeenFilled(String routineName) {
        return !(TextUtils.isEmpty(routineName));
    }
}
