package me.kevinxchan.kevinxchan.stretchit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import me.kevinxchan.kevinxchan.stretchit.model.exercise.Exercise;
import me.kevinxchan.kevinxchan.stretchit.util.PrefUtil;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import java.util.List;

public class TimerActivity extends AppCompatActivity {
    private static final String TAG = "TimerActivity";
    public enum TimerState {
        Stopped, Paused, Running
    }

    private long timerLengthSeconds;
    private long secondsRemaining;
    private TimerState timerState;
    private CountDownTimer timer;
    private List<Exercise> exercises;

    private FloatingActionButton startFab;
    private FloatingActionButton pauseFab;
    private FloatingActionButton stopFab;
    private ImageView currCategoryImageView;
    private TextView currExerciseName;
    private TextView countdownTextView;
    private MaterialProgressBar materialProgressBar;

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

        initView();
        initFabListeners();
        initTimerValues();
        initTimer();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initTimer();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        if (timerState == TimerState.Running) {
//            timer.cancel();
//        }
//        else if (timerState == TimerState.Paused) {
//        }
//
//        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this);
//        PrefUtil.setSecondsRemaining(secondsRemaining, this);
//        PrefUtil.setTimerState(timerState, this);
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        buildDialog(builder);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void buildDialog(AlertDialog.Builder builder) {
        builder.setMessage(R.string.timer_back_dialog_msg);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.timer_continue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.timer_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }

    private void initTimer(){
        timerState = PrefUtil.getTimerState(this);

        // we don't want to change the length of the timer which is already running
        // if the length was changed in settings while it was backgrounded
        if (timerState == TimerState.Stopped)
            setNewTimerLength();
        else
            setPreviousTimerLength();

        if (timerState == TimerState.Running || timerState == TimerState.Paused) {
            secondsRemaining = PrefUtil.getSecondsRemaining(this);
        } else {
            secondsRemaining = timerLengthSeconds;
        }

        if (timerState == TimerState.Running)
            startTimer();

        updateButtons();
        updateCountdownUI();
    }

    private void initView() {
        startFab = findViewById(R.id.fab_start);
        pauseFab = findViewById(R.id.fab_pause);
        stopFab = findViewById(R.id.fab_stop);
        currCategoryImageView = findViewById(R.id.currentCategoryImageView);
        currExerciseName = findViewById(R.id.currentExerciseTextView);
        countdownTextView = findViewById(R.id.countdownTextView);
        materialProgressBar = findViewById(R.id.timerCountdownProgress);
    }

    private void initFabListeners() {
        startFab.setOnClickListener(view -> {
            startTimer();
            timerState = TimerState.Running;
            updateButtons();
        });
        pauseFab.setOnClickListener(view -> {
            timer.cancel();
            timerState = TimerState.Paused;
            updateButtons();
        });
        stopFab.setOnClickListener(view -> {
            timer.cancel();
            onTimerFinished();
        });
    }

    private void startTimer(){
        timerState = TimerState.Running;

        timer = new CountDownTimer(secondsRemaining * 1000, 1000) {
            @Override
            public void onFinish() {
                onTimerFinished();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                secondsRemaining = millisUntilFinished / 1000;
                updateCountdownUI();
            }
        }.start();
    }

    private void onTimerFinished() {
        timerState = TimerState.Stopped;
        setNewTimerLength();
        materialProgressBar.setProgress(0);
        PrefUtil.setSecondsRemaining(timerLengthSeconds, this);
        secondsRemaining = timerLengthSeconds;
        updateButtons();
        updateCountdownUI();
    }

    private void setNewTimerLength() {
        long lengthInMinutes = PrefUtil.getTimerLength(this);
        timerLengthSeconds = lengthInMinutes * 60;
        materialProgressBar.setMax((int) timerLengthSeconds);
    }

    private void setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);
        materialProgressBar.setMax((int) timerLengthSeconds);
    }

    private void updateCountdownUI() {
        long minutesUntilFinished = secondsRemaining / 60;
        long hoursUntilFinished = minutesUntilFinished / 60;
        long secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60;
        long minutesInHourUntilFinished = minutesUntilFinished - hoursUntilFinished * 60;
        String timeStr = formatNumber(hoursUntilFinished) + ":" + formatNumber(minutesInHourUntilFinished) + ":" + formatNumber(secondsInMinuteUntilFinished);
        countdownTextView.setText(timeStr);
        materialProgressBar.setProgress((int) (timerLengthSeconds - secondsRemaining));
    }

    private String formatNumber(long time) {
        if (time < 10)
            return "0" + time;
        return String.valueOf(time);
    }

    private void updateButtons() {
        switch (timerState) {
            case Running:
                startFab.setEnabled(false);
                pauseFab.setEnabled(true);
                stopFab.setEnabled(true);
                break;
            case Paused:
                startFab.setEnabled(true);
                pauseFab.setEnabled(false);
                stopFab.setEnabled(true);
                break;
            case Stopped:
                startFab.setEnabled(true);
                pauseFab.setEnabled(true);
                stopFab.setEnabled(false);
                break;
        }
    }

    private void initTimerValues() {
        Exercise firstExercise = exercises.get(0);
        timerLengthSeconds = strToSeconds(firstExercise.getDuration());
        Log.d(TAG, "timerLengthSeconds: " + timerLengthSeconds);
        timerState = TimerState.Stopped;
        secondsRemaining = 0;
    }

    private long strToSeconds(String duration) {
        String[] timeArr = duration.split(":");
        long hours = Long.parseLong(timeArr[0]);
        long minutes = Long.parseLong(timeArr[1]);
        long seconds = Long.parseLong(timeArr[2]);
        return hours * 60 * 60 + minutes * 60 + seconds;
    }

    private void setToolbar(@NonNull String string) {
        Toolbar toolbar = findViewById(R.id.activity_timer_toolbar);
        toolbar.setTitle(string);
    }
}
