package com.example.flagquizapp_nayananga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GuessTheFlag extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);
    }

    public void home(View view) {
        finish();
    }
}
