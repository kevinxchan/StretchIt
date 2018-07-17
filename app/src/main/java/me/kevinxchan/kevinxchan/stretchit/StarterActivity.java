package me.kevinxchan.kevinxchan.stretchit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_starter);
    }
}
