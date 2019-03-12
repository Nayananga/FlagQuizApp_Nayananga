package com.example.flagquizapp_nayananga;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class GuessTheFlag extends AppCompatActivity {
    private Context mContext;
    private QuizViewModel quizViewModel;
    private Button nextButton;
    private TextView scoreBoardView;
    private ImageButton imageButton0;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private TextView textViewGuessTheFlag;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        quizViewModel.setFileNameList(this.getAssets());

        mContext = getApplicationContext();

        scoreBoardView = findViewById(R.id.textViewScore);
        scoreBoardView.setText("Score " + String.valueOf(quizViewModel.getScore()));

        imageButton0 = findViewById(R.id.imageButton0);
        loadNextFlag(imageButton0);
        imageButton1 = findViewById(R.id.imageButton1);
        loadNextFlag(imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        loadNextFlag(imageButton2);

        textViewGuessTheFlag = findViewById(R.id.textViewGuessTheFlag);
        textViewGuessTheFlag.setText(quizViewModel.getCorrectAnswerList().get(new Random().nextInt(3)));

        nextButton = findViewById(R.id.buttonNext);
        nextButton.setVisibility(View.GONE);
    }

    private void loadNextFlag(ImageButton imageButton) {
        AssetManager assets = this.getAssets();
        try {
            String nextImage = quizViewModel.getNextCountryFlagRemove();
            try (InputStream stream = assets.open("png250px/"+ nextImage)) {
                Drawable flag = Drawable.createFromStream(stream, nextImage);
                imageButton.setImageDrawable(flag);
                imageButton.setTag(nextImage.replace(".png",""));
            } catch (IOException e) {
                Log.e(QuizViewModel.getTag(), "Error Loading " + nextImage, e);
            }

            quizViewModel.setCorrectAnswerList( String.valueOf(quizViewModel.getFileNameList().get(nextImage.replace(".png",""))));

        }catch (NullPointerException e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Game Over!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void home(View view) {
        finish();
    }

    public void ImageButton0(View view) {
        answerCheck(imageButton0, view);
    }

    public void ImageButton1(View view) {
        answerCheck(imageButton1 , view);
    }

    public void ImageButton2(View view) {
        answerCheck(imageButton2 , view);
    }

    public void answerCheck(ImageButton imageButton , View view){
        String tag = (String) imageButton.getTag();
        String textViewText = (String) textViewGuessTheFlag.getText();
        if(quizViewModel.getFileNameList().get(tag).equals(textViewText)){
            quizViewModel.updateScore(1);
            scoreBoardView.setText("Score " + String.valueOf(quizViewModel.getScore()));

            popupWindow = quizViewModel.setPopUpWindowCorrect(view, mContext);
            nextButton.setVisibility(View.VISIBLE);
        }
        else {
            quizViewModel.updateScore(-1);
            scoreBoardView.setText("Score " + String.valueOf(quizViewModel.getScore()));
            popupWindow = quizViewModel.setPopUpWindowWrong(view, mContext);
            nextButton.setVisibility(View.VISIBLE);
        }
        quizViewModel.clearCorrectAnswersList();

    }

    public void next(View view) {
        popupWindow.dismiss();
        loadNextFlag(imageButton0);
        loadNextFlag(imageButton1);
        loadNextFlag(imageButton2);
        textViewGuessTheFlag.setText(quizViewModel.getCorrectAnswerList().get(new Random().nextInt(3)));
        nextButton.setVisibility(View.GONE);
    }
}
