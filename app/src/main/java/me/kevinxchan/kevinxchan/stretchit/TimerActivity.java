package me.kevinxchan.kevinxchan.stretchit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import me.kevinxchan.kevinxchan.stretchit.model.exercise.Exercise;

import java.util.List;

public class TimerActivity extends AppCompatActivity {
    private static final String TAG = "TimerActivity";
    public enum TimerState {
        Stopped, Paused, Running
    }

    private long timerLengthSeconds;
    private long secondsRemaining;
    private long timerState;
    private List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            exercises = extras.getParcelableArrayList("EXERCISES");
            Log.d(TAG, "received exercise list of length " + exercises.size());
        }
        setToolbar(getString(R.string.app_name));

        initTimerValues();
    }

    private void initTimerValues() {

    }

    private void setToolbar(@NonNull String string) {
        Toolbar toolbar = findViewById(R.id.activity_timer_toolbar);
        toolbar.setTitle(string);
    }
}
