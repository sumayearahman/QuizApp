package com.sumayea.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView result =(TextView) findViewById(R.id.result);
        TextView totalScorelabel= (TextView)findViewById(R.id.totalScorelabel);

        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);

        SharedPreferences settings = getSharedPreferences("quizApp", Context.MODE_PRIVATE);
        int totalScore= settings.getInt("totalScore", 0);
        totalScore += score;


         result.setText(score + " /5");
         totalScorelabel.setText("Total Score : " + totalScore);



        //Update total score
        SharedPreferences.Editor editor =settings.edit();
        editor.putInt("totalScore",totalScore);
        editor.commit();
    }

    public void returnTop(View view){
        Intent intent = new Intent(ResultActivity.this,StartActivity.class);
        startActivity(intent);
    }
    //public void onClickReset(View view){


    //}
}
