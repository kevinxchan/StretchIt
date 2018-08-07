package me.kevinxchan.kevinxchan.routineme;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.aigestudio.wheelpicker.WheelPicker;
import me.kevinxchan.kevinxchan.routineme.model.Category;
import me.kevinxchan.kevinxchan.routineme.model.exercise.Exercise;
import me.kevinxchan.kevinxchan.routineme.model.exercise.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements WheelPicker.OnItemSelectedListener {
    private int currRoutineId;
    private EditText exerciseNameEditText;
    private Category exerciseCategory;
    private String exerciseDuration;
    private String hours;
    private String minutes;
    private String seconds;
    private String categoryStr;

    private int currExerciseId;
    private String currExerciseName;
    private Category currExerciseCategory;
    private String currExerciseDuration;

    private ExerciseViewModel exerciseViewModel;

    private static final String TAG = "ExerciseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        setToolbar(getString(R.string.activity_exercises_title));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currRoutineId = extras.getInt("ROUTINE_ID");
            currExerciseId = extras.getInt("EXERCISE_ID");
            currExerciseName = extras.getString("EXERCISE_NAME");
            currExerciseCategory = (Category) extras.get("EXERCISE_CATEGORY");
            currExerciseDuration = extras.getString("EXERCISE_DURATION");
        }

        initFormData();
        initView();
    }

    private void initFormData() {
        WheelPicker exerciseCategoryPicker = (WheelPicker) findViewById(R.id.exerciseCategoryWheelPicker);
        exerciseCategoryPicker.setOnItemSelectedListener(this);
        WheelPicker hoursWheelPicker = (WheelPicker) findViewById(R.id.hoursWheelPicker);
        hoursWheelPicker.setOnItemSelectedListener(this);
        WheelPicker minutesWheelPicker = (WheelPicker) findViewById(R.id.minutesWheelPicker);
        minutesWheelPicker.setOnItemSelectedListener(this);
        WheelPicker secondsWheelPicker = (WheelPicker) findViewById(R.id.secondsWheelPicker);
        secondsWheelPicker.setOnItemSelectedListener(this);

        initializeExerciseCategoryPicker(exerciseCategoryPicker);
        List<String> hoursScroll = initializeTimeArray(0, 99);
        List<String> minutesScroll = initializeTimeArray(0, 59);
        List<String> secondsScroll = initializeTimeArray(0, 59);

        hoursWheelPicker.setData(hoursScroll);
        minutesWheelPicker.setData(minutesScroll);
        secondsWheelPicker.setData(secondsScroll);

        setTimePositions(hoursWheelPicker, minutesWheelPicker, secondsWheelPicker);
        setCategoryPosition(exerciseCategoryPicker);
        // default values: first option in picker
        int currentCategoryPosition = exerciseCategoryPicker.getCurrentItemPosition();
        int currentHoursPosition = hoursWheelPicker.getCurrentItemPosition();
        int currentMinutesPosition = minutesWheelPicker.getCurrentItemPosition();
        int currentSecondsPosition = secondsWheelPicker.getCurrentItemPosition();

        categoryStr = exerciseCategoryPicker.getData().get(currentCategoryPosition).toString();
        hours = hoursWheelPicker.getData().get(currentHoursPosition).toString();
        minutes = minutesWheelPicker.getData().get(currentMinutesPosition).toString();
        seconds = secondsWheelPicker.getData().get(currentSecondsPosition).toString();
    }

    private void setTimePositions(WheelPicker hoursWheelPicker, WheelPicker minutesWheelPicker, WheelPicker secondsWheelPicker) {
        if (currExerciseDuration == null)
            return;
        String[] timeArr = currExerciseDuration.split(":");
        int hours = Integer.parseInt(timeArr[0]);
        int minutes = Integer.parseInt(timeArr[1]);
        int seconds = Integer.parseInt(timeArr[2]);

        hoursWheelPicker.setSelectedItemPosition(hours);
        minutesWheelPicker.setSelectedItemPosition(minutes);
        secondsWheelPicker.setSelectedItemPosition(seconds);
    }

    private void setCategoryPosition(WheelPicker exerciseCategoryPicker) {
        if (currExerciseCategory == null)
            return;
        String category = currExerciseCategory.toString();
        int pos;
        switch (category) {
            case "Countdown":
                pos = 0;
                break;
            case "Exercise":
                pos = 1;
                break;
            case "Rest":
                pos = 2;
                break;
            default:
                pos = 0;
                break;
        }
        exerciseCategoryPicker.setSelectedItemPosition(pos);
    }

    private void initView() {
        exerciseNameEditText = findViewById(R.id.exerciseNameInput);
        exerciseNameEditText.setText(currExerciseName, TextView.BufferType.EDITABLE);
        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        FloatingActionButton doneFab = findViewById(R.id.doneFab);
        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formComplete()) {
                    exerciseCategory = Category.valueOf(categoryStr.toUpperCase());
                    exerciseDuration = hours + ":" + minutes + ":" + seconds;
                    String exerciseNameStr = exerciseNameEditText.getText().toString();
                    if (currExerciseId != 0) {
                        Log.d(TAG, "exercise with id " + currExerciseId + " being updated");
                        Exercise e = new Exercise(exerciseCategory, exerciseNameStr, currRoutineId, exerciseDuration);
                        e.setExerciseID(currExerciseId);
                        exerciseViewModel.updateExercise(e);
                    } else {
                        Log.d(TAG, "new exercise being added");
                        exerciseViewModel.addExercise(new Exercise(
                                exerciseCategory,
                                exerciseNameStr,
                                currRoutineId,
                                exerciseDuration));
                    }
                    finish();
                } else {
                    Toast.makeText(ExerciseActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean formComplete() {
        boolean nameNotEmpty = !TextUtils.isEmpty(exerciseNameEditText.getText().toString());
        boolean durationNotEmpty = hours != null && minutes != null && seconds != null;
        boolean categoryNotEmpty = !TextUtils.isEmpty(categoryStr);
        return nameNotEmpty && categoryNotEmpty && durationNotEmpty;
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
            case R.id.exerciseCategoryWheelPicker:
                categoryStr = data.toString();
                break;
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
