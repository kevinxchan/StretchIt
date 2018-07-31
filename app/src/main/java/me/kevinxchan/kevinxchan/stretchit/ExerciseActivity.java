package me.kevinxchan.kevinxchan.stretchit;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.aigestudio.wheelpicker.WheelPicker;
import me.kevinxchan.kevinxchan.stretchit.model.Category;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;
import me.kevinxchan.kevinxchan.stretchit.model.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements WheelPicker.OnItemSelectedListener {
    private int currRoutineId;
    private EditText exerciseName;
    private Category exerciseCategory;
    private String exerciseDuration;
    private String hours;
    private String minutes;
    private String seconds;

    private ExerciseViewModel exerciseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        setToolbar(getString(R.string.activity_exercises_title));
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            currRoutineId = extras.getInt("ROUTINE_ID");

        initFormData();
        initView();
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

    private void initView() {
        exerciseName = findViewById(R.id.exerciseNameInput);
        TextView category = findViewById(R.id.exerciseCategory);
        if (category != null) {
            String categoryStr = category.getText().toString();
            exerciseCategory = Category.valueOf(categoryStr.toUpperCase());
        }
        exerciseDuration = hours + ":" + minutes + ":" + seconds;

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        FloatingActionButton doneFab = findViewById(R.id.doneFab);
        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formComplete()) {
                    exerciseViewModel.addExercise(new Exercise(
                            exerciseCategory,
                            exerciseName.getText().toString(),
                            currRoutineId,
                            exerciseDuration));
                    finish();
                } else {
                    Toast.makeText(ExerciseActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean formComplete() {
        return exerciseName != null && exerciseCategory != null && exerciseDuration != null;
    }

    private void setToolbar(@NonNull String string) {
        Toolbar toolbar = findViewById(R.id.activity_exercise_toolbar);
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

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        switch (picker.getId()) {
            case R.id.hoursWheelPicker:
                hours = data.toString();
                break;
            case R.id.minutesWheelPicker:
                minutes = data.toString();
                break;
            case R.id.secondsWheelPicker:
                seconds = data.toString();
                break;
        }
    }
}
