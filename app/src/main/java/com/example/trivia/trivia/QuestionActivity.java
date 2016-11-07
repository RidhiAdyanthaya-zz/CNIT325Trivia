package com.example.trivia.trivia;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity implements AsyncResponse{

    LinearLayout layout;
    TextView tQuestion;
    Question question;
    String url;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (((Button)v).getText().equals(question.getCorrectAnswer())) {
                AlertDialog.Builder alert = new AlertDialog.Builder(QuestionActivity.this);
                alert.setMessage("Your answer is correct!");
                alert.setPositiveButton("New Question", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadURL aTask = new LoadURL();
                        aTask.delegate = QuestionActivity.this;
                        aTask.execute(url);
                        finish();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
            else {
                AlertDialog.Builder alert = new AlertDialog.Builder(QuestionActivity.this);
                alert.setMessage("Your answer is incorrect!");
                alert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        tQuestion = (TextView) findViewById(R.id.question);
        layout = (LinearLayout) findViewById(R.id.layout);


        url = getIntent().getStringExtra("url");

        question = (Question) getIntent().getSerializableExtra("question");
        String[] answers = question.getAnswers();

        tQuestion.setText(question.getQuestion());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(700, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 20;

        int numAnswers = 0;
        switch (question.getType()) {
            case "boolean":
                numAnswers = 2;
                break;
            case "multiple":
                numAnswers = 4;
                break;
            default:
                break;
        }



        for (int i = 0; i < numAnswers; i++) {
            layout.addView(new Button(getApplicationContext()), params);
            ((Button) layout.getChildAt(i + 1)).setText(answers[i]);
            layout.getChildAt(i + 1).setOnClickListener(onClickListener);
        }
    }

    @Override
    public void processFinish(String output){
        Question question = new Question(output);
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("question", question);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
