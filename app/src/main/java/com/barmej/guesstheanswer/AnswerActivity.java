package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    private TextView mAnswerTextView;
    private Button mReturnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        mAnswerTextView = findViewById(R.id.text_view_answer);
        mReturnButton = findViewById(R.id.button_return);

        String answer = getIntent().getStringExtra("question_answer");
        if(answer != null)
            mAnswerTextView.setText(answer);

        mReturnButton.setOnClickListener(listener ->{
            Intent intent = new Intent(AnswerActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}