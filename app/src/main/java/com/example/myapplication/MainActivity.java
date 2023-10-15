package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_CURRENT_INDEX = "current Index";
    public static final String KEY_EXTRA_ANSWER = "myApplication.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("QUIZ_TAG", "Wywołana została metoda onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }


    private Button trueButton;
    private Button falseBUtton;
    private Button nextButton;
    private Button tellmeButton;
    private TextView questionTextView;
    private Button promptButton;
    private boolean answerWasShown;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("QUIZ_TAG", "Wywołuje onCreate");

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.button_true);
        falseBUtton = findViewById(R.id.button_false);
        nextButton = findViewById(R.id.button_next);
        questionTextView = findViewById(R.id.question_text_view);
        tellmeButton = findViewById(R.id.button_tellme);
        promptButton = findViewById(R.id.button_prompt);

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

        tellmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown = false;
                if (currentIndex == 0) {
                    Toast.makeText(MainActivity.this, goodAnswerCount + "/" + questions.length, Toast.LENGTH_SHORT).show();
                    goodAnswerCount = 0;
                }
                setNextQuestion();
            }
        });

        setNextQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("QUIZ_TAG", "Wywołuje onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QUIZ_TAG", "Wywołuje onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("QUIZ_TAG", "Wywołuje onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("QUIZ_TAG", "Wywołuje onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("QUIZ TAG", "Wywołuje onDestroy");
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
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                goodAnswerCount++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println(requestCode);
        System.out.println(resultCode);
        System.out.println( data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false));
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}
