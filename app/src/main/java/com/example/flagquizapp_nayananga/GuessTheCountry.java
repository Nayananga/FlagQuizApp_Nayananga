package com.example.flagquizapp_nayananga;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class GuessTheCountry extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int position;
    private String nextImage;
    private String country [];
    private Button nextButton;
    private Button submitButton;
    private TextView scoreBoardView;
    private ImageView flagImageView;
    private PopupWindow popupWindow;
    private QuizViewModel quizViewModel;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_country);
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        quizViewModel.setFileNameList(this.getAssets());

        mContext = getApplicationContext();

        scoreBoardView = findViewById(R.id.textViewScore);
        scoreBoardView.setText(String.format("Score %s", String.valueOf(quizViewModel.getScore())));
        flagImageView = findViewById(R.id.flagImageView);
        Spinner spin = findViewById(R.id.spinnerGuessTheFlag);
        nextButton = findViewById(R.id.buttonNext);
        nextButton.setVisibility(View.GONE);
        submitButton = findViewById(R.id.buttonSubmit);

        spin.setAdapter(SetSpinAdapter());
        spin.setOnItemSelectedListener(this);
        loadNextFlag();
    }

    private void loadNextFlag() {
        AssetManager assets = this.getAssets();
        nextImage = quizViewModel.getNextCountryFlag();

        try (InputStream stream = assets.open("png250px/"+nextImage)) {
            Drawable flag = Drawable.createFromStream(stream, nextImage);
            flagImageView.setImageDrawable(flag);
        } catch (IOException e) {
            Log.e(QuizViewModel.getTag(), "Error Loading " + nextImage, e);
        }

    }

    private ArrayAdapter SetSpinAdapter() {
        country = quizViewModel.getCountryNameList().toArray(new String[0]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,country);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void home(View view) {
        finish();
    }

    public void submit(View view) {
        quizViewModel.setCorrectAnswer( String.valueOf(quizViewModel.getFileNameList().get(nextImage.replace(".png",""))));

        if(quizViewModel.getCorrectAnswer().equals(country[position])){

            popupWindow = quizViewModel.setPopUpWindowCorrect(view, mContext);

            quizViewModel.updateScore(3);
            scoreBoardView.setText(String.format("Score %s", String.valueOf(quizViewModel.getScore())));
            
            submitButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);

        }

        else{

            quizViewModel.updateScore(-1);
            scoreBoardView.setText(String.format("Score %s", String.valueOf(quizViewModel.getScore())));
            popupWindow = quizViewModel.setPopUpWindowWrong(view, mContext);
            submitButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }

    }

    public void next(View view) {
        popupWindow.dismiss();
        loadNextFlag();
        nextButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);

    }

}

