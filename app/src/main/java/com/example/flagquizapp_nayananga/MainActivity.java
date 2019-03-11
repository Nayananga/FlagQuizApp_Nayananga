package com.example.flagquizapp_nayananga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void guessTheCountry(View view) {
        Intent intent = new Intent(this, GuessTheCountry.class);
        startActivity(intent);
    }

    public void guessHints(View view) {
        Intent intent = new Intent(this, GuessHints.class);
        startActivity(intent);
    }

    public void guessTheFlag(View view) {
        Intent intent = new Intent(this, GuessTheFlag.class);
        startActivity(intent);
    }

    public void advanced(View view) {
        Intent intent = new Intent(this, Advance.class);
        startActivity(intent);
    }
}
