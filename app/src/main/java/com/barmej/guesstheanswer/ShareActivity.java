package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    private String mQuestionText;
    private TextView mTitleShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mQuestionText = getIntent().getStringExtra("question text extra");

        if(mQuestionText != null){
            mTitleShare = findViewById(R.id.edit_text_share_title);
            ((Button)findViewById(R.id.button_share_quesion)).setOnClickListener(listener -> {
                String questionTitle = mTitleShare.getText().toString();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, questionTitle + "\n" + mQuestionText);
                startActivity(shareIntent);

                // Save title
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.SHARE_TITLE, questionTitle);
                editor.apply();
            });

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE);
            String questionTitle = sharedPreferences.getString(Constants.SHARE_TITLE, "");
            mTitleShare.setText(questionTitle);
        }
    }
}