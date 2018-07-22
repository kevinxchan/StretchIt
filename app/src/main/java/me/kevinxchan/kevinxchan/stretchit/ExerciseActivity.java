package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Button doneBtn = (Button) findViewById(R.id.doneBtn);



        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: send form data
                Intent doneIntent = new Intent(getApplicationContext(), AddExercisesActivity.class);
                startActivity(doneIntent);
            }
        });
    }
}
