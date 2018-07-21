package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RoutineNameActivity extends AppCompatActivity {
    EditText routineNameEditText = (EditText) findViewById(R.id.routineNameEditText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_name);
        Button nextBtn = (Button) findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasBeenFilled(routineNameEditText)) {
                    Intent doneIntent = new Intent(getApplicationContext(), AddExercisesActivity.class);
                    startActivity(doneIntent);
                } else {
                    routineNameEditText.setError("Routine name is required!");
                }
            }
        });

    }

    private boolean hasBeenFilled(EditText editText) {
        return !(TextUtils.isEmpty(editText.getText().toString()));
    }
}
