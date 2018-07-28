package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;
import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        setToolbar(getString(R.string.activity_exercises_title));

        initFormData();

        Button doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: send form data
                // TODO: check form properly filled out
                Intent doneIntent = new Intent(getApplicationContext(), AddExercisesActivity.class);
                startActivity(doneIntent);
            }
        });
    }

    private void initFormData() {
        WheelPicker exerciseCategoryPicker = (WheelPicker) findViewById(R.id.exerciseCategoryWheelPicker);
        WheelPicker hoursWheelPicker = (WheelPicker) findViewById(R.id.hoursWheelPicker);
        WheelPicker minutesWheelPicker = (WheelPicker) findViewById(R.id.minutesWheelPicker);
        WheelPicker secondsWheelPicker = (WheelPicker) findViewById(R.id.secondsWheelPicker);

        initializeExerciseCategoryPicker(exerciseCategoryPicker);
        List<String> hoursScroll = initializeTimeArray(0, 99);
        List<String> minutesScroll = initializeTimeArray(0, 59);
        List<String> secondsScroll = initializeTimeArray(0, 59);

        hoursWheelPicker.setData(hoursScroll);
        minutesWheelPicker.setData(minutesScroll);
        secondsWheelPicker.setData(secondsScroll);
    }

    private void setToolbar(String string) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(string);
    }

    private List<String> initializeTimeArray(int start, int end) {
        List<String> arr = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            String time = Integer.toString(i);
            if (i < 10) {
                time = "0" + time;
            }
            arr.add(time);
        }
        return arr;
    }

    private void initializeExerciseCategoryPicker(WheelPicker exerciseCategoryPicker) {
        List<String> exerciseCategories = new ArrayList<>();
        exerciseCategories.add("Countdown");
        exerciseCategories.add("Exercise");
        exerciseCategories.add("Rest");
        exerciseCategoryPicker.setData(exerciseCategories);
    }

}
