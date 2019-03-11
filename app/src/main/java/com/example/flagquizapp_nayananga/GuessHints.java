package com.example.flagquizapp_nayananga;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class GuessHints extends AppCompatActivity {
    private String nextImage;
    private String correctAnswer;
    private StringBuilder temAnswer = new StringBuilder();
    private Button nextButton;
    private Button submitButton;
    private QuizViewModel quizViewModel;
    private Context mContext;
    private TextView scoreBoardView;
    private TextView dashToTextView;
    private EditText editTextGuessCountry;
    private ImageView flagImageView;
    private PopupWindow popupWindow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_hints);
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        quizViewModel.setFileNameList(this.getAssets());

        mContext = getApplicationContext();

        scoreBoardView = findViewById(R.id.textViewScore);
        scoreBoardView.setText("Score " + String.valueOf(quizViewModel.getScore()));
        flagImageView = findViewById(R.id.flagImageView);
        dashToTextView = findViewById(R.id.textViewDashtoLetters);
        editTextGuessCountry = findViewById(R.id.editTextGuessCountry);
        nextButton = findViewById(R.id.buttonNext);
        nextButton.setVisibility(View.GONE);
        submitButton = findViewById(R.id.buttonSubmit);
        loadNextFlag();
        dashToTextView.setText(answerAppend());
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
        quizViewModel.setCorrectAnswer( String.valueOf(quizViewModel.getFileNameList().get(nextImage.replace(".png",""))));
        correctAnswer = quizViewModel.getCorrectAnswer();
    }

    private String answerAppend(){

        for(int i= 0; i<correctAnswer.length();i++){
            temAnswer.append('_');
        }
        return String.valueOf(temAnswer);
    }

    public void home(View view) {
        finish();
    }

    public void submit(View view) {

        if(quizViewModel.getTotalGuesses() < 3){

            String guessAnswer = String.valueOf(editTextGuessCountry.getText());

            if(guessAnswer.length()>0){

                char g = guessAnswer.toLowerCase().charAt(0);
                int index = correctAnswer.toLowerCase().indexOf(g);

                if(index != -1){
                    char c = correctAnswer.charAt(index);
                    temAnswer.setCharAt(index,c);
                    dashToTextView.setText(temAnswer.toString());

                    if(correctAnswer.toLowerCase().equals(temAnswer.toString().toLowerCase())){

                        quizViewModel.updateScore(3);
                        scoreBoardView.setText("Score " + String.valueOf(quizViewModel.getScore()));

                        popupWindow = quizViewModel.setPopUpWindowCorrect(view, mContext);

                        temAnswer.setLength(0);

                        submitButton.setVisibility(View.GONE);
                        nextButton.setVisibility(View.VISIBLE);

                    }

                }
                else {
                    quizViewModel.setTotalGuesses(1);

                }
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please Enter a Letter",
                        Toast.LENGTH_SHORT);
                toast.show();
            }


        }
        else{
            quizViewModel.updateScore(-1);
            scoreBoardView.setText("Score " + String.valueOf(quizViewModel.getScore()));
            popupWindow = quizViewModel.setPopUpWindowWrong(view, mContext);
            temAnswer.setLength(0);
            submitButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);

        }
        editTextGuessCountry.setText("");

    }

    public void next(View view) {
        popupWindow.dismiss();
        quizViewModel.resetTotalGuesses();
        loadNextFlag();
        dashToTextView.setText(answerAppend());
        nextButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);

    }

}
