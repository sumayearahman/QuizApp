package com.sumayea.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView count;
    private TextView question;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT =5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    String quizData[][] = {
            //{"Country", "Right Answer", "Choice1", "Choice2", "Choice3"}
            {"China", "Beijing", "Jakarta", "Manali", "Dhaka"},
            {"India", "New Delhi", "Beijing", "Bangkok", "Seoul"},
            {"Indonesia", "Jakarta", "Manali", "New Delhi", "Kuala Lumpur"},
            {"Japan", "Tokyo", "Bangkok", "Taipei", "Jakarta"},
            {"Thailand", "Bangkok", "Berlin", "Havana", "Kingston"},
            {"Brazil", "Brasilia", "Havana", "Bangkok", "Copenhagen"},
            {"Canada", "Toronto", "Bern", "Copenhagen", "Jakartrta"},
            {"Cuba", "Havana", "Bern", "London", "Mexico"},
            {"Mexico", "Mexico City", "Ottawa","Berlin", "Santiago"},
            {"France", "Paris", "Toronto", "Copenhagen", "Tokyo"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = findViewById(R.id.count);
        question = findViewById(R.id.question);
        answerBtn1 = findViewById(R.id.b1);
        answerBtn2 = findViewById(R.id.b2);
        answerBtn3 = findViewById(R.id.b3);
        answerBtn4 = findViewById(R.id.b4);

        //Receive quizCategory from StartActivity
        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);

        Log.v("CATEGORY_TAG", quizCategory + "");

        //Create quizArray from quizData
        for (int i= 0; i<quizData.length; i++) {

            //Prepare Array
            ArrayList<String> tempArray = new ArrayList<>();
            tempArray.add(quizData[i][0]); //country
            tempArray.add(quizData[i][1]); //right answer
            tempArray.add(quizData[i][2]); //choice1
            tempArray.add(quizData[i][3]); //choice2
            tempArray.add(quizData[i][4]); //choice 3

            //Add tempArray to quizArray
            quizArray.add(tempArray);
        }

        showNextQuiz();

    }

    public void showNextQuiz(){

        //Update quiz count
        count.setText("Q" + quizCount);

        //Generate random number between 0 and 10(quizArray's size -1)
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        //Pick one Quiz set
        ArrayList<String> quiz = quizArray.get(randomNum);

        //Set question and right answer
        //Array format: {"Country", "Right Answer", "Choice1", "Choice2", "Choice3"}
        question.setText(quiz.get(0));
        rightAnswer= quiz.get(1);

        //Remove country from quiz and shuffle choices
        quiz.remove(0);
        Collections.shuffle(quiz);

        //Set choices
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        //Remove this quiz from quizArray
        quizArray.remove(randomNum);



    }

    public void checkAnswer(View view){

        //Get pushed button
        Button answerBtn =(Button) findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)){
            //Correct
            alertTitle="Correct!";
            rightAnswerCount++;
        }
        else {
            //Wrong
            alertTitle= "Wrong...";

        }

        //Create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer : " + rightAnswer);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (quizCount== QUIZ_COUNT){
                    //Show result
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT" , rightAnswerCount);
                    startActivity(intent);
                }
                else {
                    quizCount++;
                    showNextQuiz();

                }

            }
        });

        builder.setCancelable(false);
        builder.show();
    }
}
