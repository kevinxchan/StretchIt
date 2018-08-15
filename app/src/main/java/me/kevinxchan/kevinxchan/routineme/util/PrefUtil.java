package me.kevinxchan.kevinxchan.routineme.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import me.kevinxchan.kevinxchan.routineme.TimerActivity;

public class PrefUtil {
    private static final String PREVIOUS_TIMER_LENGTH_MILLISECONDS_ID = "me.kevinxchan.kevinxchan.previous_timer_length";
    private static final String TIMER_STATE_ID = "me.kevinxchan.kevinxchan.timer_state";
    private static final String MILLISECONDS_REMAINING_ID = "me.kevinxchan.kevinxchan.seconds_remaining";
    private Context context;

    public static Integer getTimerLength(Context context) {
        // TODO: placeholder
        return 1;
    }

    public static Long getTimerLengthMilliseconds(Context context) {
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(context);
        return preferenceManager.getLong(PREVIOUS_TIMER_LENGTH_MILLISECONDS_ID, 0);
    }

    public static void setTimerLengthMilliseconds(Long seconds, Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(TIMER_STATE_ID, seconds);
        editor.apply();
    }

    public static TimerActivity.TimerState getTimerState(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Integer ordinal = preferences.getInt(TIMER_STATE_ID, 0);
        return TimerActivity.TimerState.values()[ordinal];
    }

    public static void setTimerState(TimerActivity.TimerState state, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Integer ordinal = state.ordinal();
        editor.putInt(TIMER_STATE_ID, ordinal);
        editor.apply();
    }

    public static Long getPreviousTimerLengthMilliseconds(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(PREVIOUS_TIMER_LENGTH_MILLISECONDS_ID, 0);
    }

    public static void setPreviousTimerLengthMilliseconds(Long seconds, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(PREVIOUS_TIMER_LENGTH_MILLISECONDS_ID, seconds);
        editor.apply();
    }

    public static Long getMillisecondsRemaining(Context context) {
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(context);
        return preferenceManager.getLong(MILLISECONDS_REMAINING_ID, 0);
    }

    public static void setMillisecondsRemaining(Long seconds, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(MILLISECONDS_REMAINING_ID, seconds);
        editor.apply();
    }

}
