package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.github.clans.fab.FloatingActionButton;

public class AddExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        Button doneBtn = (Button) findViewById(R.id.doneBtn);
        FloatingActionButton floatingActionButtonCountdown = (FloatingActionButton) findViewById(R.id.floatingActionItemCountdown);
        FloatingActionButton floatingActionButtonRest = (FloatingActionButton) findViewById(R.id.floatingActionItemRest);
        FloatingActionButton floatingActionButtonExercise = (FloatingActionButton) findViewById(R.id.floatingActionItemExercise);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doneIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(doneIntent);
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
}
