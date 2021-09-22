package com.barmej.guesstheanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewQuestion;

    private String[] QUESTIONS;
    private String[] ANSWERS_DETAILS;
    private static final boolean[] ANSWERS = {
            false,
            true,
            true,
            false,
            true,
            false,
            false,
            false,
            false,
            true,
            true,
            false,
            true
    };

    private String mCurrentQuestion, mCurrentAnswerDetail;
    private boolean mCurrentAnswer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String appLang = sharedPreferences.getString("app_lang", "");
        if(!appLang.equals(""))
            LocaleHelper.setLocale(this, appLang);

        setContentView(R.layout.activity_main);
        mTextViewQuestion = findViewById(R.id.text_view_question);

        // Get Questions array
        QUESTIONS = getResources().getStringArray(R.array.questions);
        // Get questions details array
        ANSWERS_DETAILS = getResources().getStringArray(R.array.answers_details);

        // Show the questions
        showQuestion();


        findViewById(R.id.button_change_question).setOnClickListener(listener -> showQuestion());

        findViewById(R.id.button_true).setOnClickListener(listener -> checkAnswer(true));

        findViewById(R.id.button_false).setOnClickListener(listener -> checkAnswer(false));

        findViewById(R.id.button_share_question_1).setOnClickListener(listener -> shareQuestion());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuChangeLang){
            showLanguageDialog();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void showLanguageDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        String language = "en";
                        switch(which){
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                            case 2:
                                language = "fr";
                                break;
                        }
                        saveLanguage(language);
                        LocaleHelper.setLocale(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create();
        alertDialog.show();
    }

    private void saveLanguage(String language){
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_lang", language);
        editor.apply();
    }

    private void showQuestion(){
        int randomIndex = (int)(Math.random() *  QUESTIONS.length);
        mCurrentQuestion = QUESTIONS[randomIndex];
        mCurrentAnswerDetail = ANSWERS_DETAILS[randomIndex];
        mCurrentAnswer = ANSWERS[randomIndex];

        // Show question
        mTextViewQuestion.setText(mCurrentQuestion);
    }

    private void checkAnswer(boolean answer){
        Toast.makeText(this, "Yor ur answer are " + (answer == mCurrentAnswer), Toast.LENGTH_SHORT).show();
        if(answer == mCurrentAnswer)
            showQuestion();
        else {
            Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
            intent.putExtra("question_answer", mCurrentAnswerDetail);
            startActivity(intent);
        }
    }

    private void shareQuestion(){
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra("question text extra", mCurrentQuestion);
        startActivity(intent);
    }
}