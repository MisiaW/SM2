package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseBUtton;
    private Button nextButton;
    private TextView questionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.button_true);
        falseBUtton = findViewById(R.id.button_false);
        nextButton = findViewById(R.id.button_next);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questions.length;
                if (currentIndex == 0) {
                    Toast.makeText(MainActivity.this, goodAnswerCount + "/" + questions.length, Toast.LENGTH_SHORT).show();
                    goodAnswerCount = 0;
                }
                setNextQuestion();
            }
        });

        setNextQuestion();
    }

    private Question[] questions = new Question[]{
            new Question(R.string.q_mieso, false),
            new Question(R.string.q_owoc, false),
            new Question(R.string.q_vegan, false),
            new Question(R.string.q_woda, false),
            new Question(R.string.q_lubie, true)
    };
    private int currentIndex = 0;
    int resultMessageId;
    boolean isTrueAnswer;

    private int goodAnswerCount = 0;

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            goodAnswerCount++;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

}
