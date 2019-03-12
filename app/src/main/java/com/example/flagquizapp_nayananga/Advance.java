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
import java.util.Objects;

public class Advance extends AppCompatActivity {
    private Context mContext;
    private QuizViewModel quizViewModel;
    private TextView scoreBoardView;
    private PopupWindow popupWindow;
    private ImageView imageView0;
    private ImageView imageView1;
    private ImageView imageView2;
    private EditText editText0;
    private EditText editText1;
    private EditText editText2;
    private Button nextButton;
    private Button submitButton;
    private Drawable originalDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        quizViewModel.setFileNameList(this.getAssets());

        mContext = getApplicationContext();

        scoreBoardView = findViewById(R.id.textViewScore);
        scoreBoardView.setText(String.format("Score %s", String.valueOf(quizViewModel.getScore())));

        imageView0 = findViewById(R.id.imageViewAdvance0);
        loadNextFlag(imageView0);
        imageView1 = findViewById(R.id.imageViewAdvance1);
        loadNextFlag(imageView1);
        imageView2 = findViewById(R.id.imageViewAdvance2);
        loadNextFlag(imageView2);

        editText0 = findViewById(R.id.editTextViewAdvanceGrid0);
        editText1 = findViewById(R.id.editTextViewAdvanceGrid1);
        editText2 = findViewById(R.id.editTextViewAdvanceGrid2);
        originalDrawable = editText0.getBackground();

        nextButton = findViewById(R.id.buttonNext);
        nextButton.setVisibility(View.GONE);
        submitButton = findViewById(R.id.buttonSubmit);


    }

    private void loadNextFlag(ImageView imageView) {
        AssetManager assets = this.getAssets();
        try {
            String nextImage = quizViewModel.getNextCountryFlagRemove();
            try (InputStream stream = assets.open("png250px/"+ nextImage)) {
                Drawable flag = Drawable.createFromStream(stream, nextImage);
                imageView.setImageDrawable(flag);
                imageView.setTag(nextImage.replace(".png",""));
                quizViewModel.setCorrectAnswerList( String.valueOf(quizViewModel.getFileNameList().get(nextImage.replace(".png",""))));
            } catch (IOException e) {
                Log.e(QuizViewModel.getTag(), "Error Loading " + nextImage, e);
            }

        }catch (NullPointerException e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Game Over!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public boolean answerCheck(ImageView imageView, EditText editText){
        String tag = (String) imageView.getTag();
        String editTextViewText = String.valueOf(editText.getText());
        Log.d("Nayananga",String.valueOf(quizViewModel.getFileNameList().get(tag)));
        return Objects.requireNonNull(quizViewModel.getFileNameList().get(tag)).toLowerCase().equals(editTextViewText.toLowerCase());

    }

    public void home(View view) {
        finish();
    }

    public void submit(View view) {
        if(quizViewModel.getTotalGuesses() < 3){


            if(editText0.length()>0 && editText1.length() > 0 && editText2.length()>0){

                if (answerCheck(imageView0,editText0)){
                    editText0.setTag(1);
                    editText0.setBackgroundResource(R.drawable.backtext);
                    editText0.setEnabled(false);

                    if((int)editText0.getTag() != 1) quizViewModel.setCorrectAnswers(1);
                }else{
                    editText0.setBackgroundResource(R.drawable.backtextwrong);
                }

                if (answerCheck(imageView1,editText1)){
                    editText1.setTag(1);
                    editText1.setBackgroundResource(R.drawable.backtext);
                    editText1.setEnabled(false);

                    if((int)editText1.getTag() != 1) quizViewModel.setCorrectAnswers(1);
                }
                else{
                    editText1.setBackgroundResource(R.drawable.backtextwrong);
                }

                if (answerCheck(imageView2,editText2)){
                    editText2.setTag(1);
                    editText2.setBackgroundResource(R.drawable.backtext);
                    editText2.setEnabled(false);

                    if((int)editText2.getTag() != 1) quizViewModel.setCorrectAnswers(1);
                }else{
                    editText2.setBackgroundResource(R.drawable.backtextwrong);
                }

                if(quizViewModel.getCorrectAnswers() == 3){

                    quizViewModel.updateScore(3-quizViewModel.getTotalGuesses());
                    scoreBoardView.setText(String.format("Score %s", String.valueOf(quizViewModel.getScore())));

                    popupWindow = quizViewModel.setPopUpWindowCorrect(view, mContext);

                    submitButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.VISIBLE);

                }
                quizViewModel.setTotalGuesses(1);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please Fill All the Answers",
                        Toast.LENGTH_SHORT);
                toast.show();
            }


        }
        else{
            quizViewModel.updateScore(-1);
            scoreBoardView.setText(String.format("Score %s", String.valueOf(quizViewModel.getScore())));
//            editText0.setError(quizViewModel.getFileNameList().get(quizViewModel.getCorrectAnswerList().get(0)));
//            editText1.setError(quizViewModel.getFileNameList().get(quizViewModel.getCorrectAnswerList().get(1)));
//            editText2.setError(quizViewModel.getFileNameList().get(quizViewModel.getCorrectAnswerList().get(2)));
            popupWindow = quizViewModel.setPopUpWindowWrong(view, mContext);
            submitButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);

        }

    }

    public void next(View view) {
        popupWindow.dismiss();
        quizViewModel.resetTotalGuesses();
        quizViewModel.resetCorrectAnswers();
        loadNextFlag(imageView0);
        loadNextFlag(imageView1);
        loadNextFlag(imageView2);
        editText0.setEnabled(true);
        editText0.setText("");
        editText1.setEnabled(true);
        editText1.setText("");
        editText2.setEnabled(true);
        editText2.setText("");
        editText0.setBackground(originalDrawable);
        editText1.setBackground(originalDrawable);
        editText2.setBackground(originalDrawable);
        nextButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
    }
}
