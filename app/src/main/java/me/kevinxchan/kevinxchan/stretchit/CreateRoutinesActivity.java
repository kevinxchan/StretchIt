package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRoutinesActivity extends AppCompatActivity {
    EditText routineNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routines);

        routineNameEditText = (EditText) findViewById(R.id.routineNameEditText);
        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNotEmpty(routineNameEditText)) {
                    Log.d("foo", "I AM NOT EMPTY");
                    Intent nextActivity = new Intent(getApplicationContext(), AddExercisesActivity.class);
                    startActivity(nextActivity);
                } else {
                    routineNameEditText.setError("Routine name is required!");
                }
            }
        });
    }

    private boolean editTextNotEmpty(EditText editText) {
        return !(TextUtils.isEmpty(editText.getText()));
    }
}
