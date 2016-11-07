package com.example.trivia.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TriviaMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_main);
    }

    public void solo(View view) {
        Intent play = new Intent(this, PlayActivity.class);
        startActivity(play);
    }

    public void pvp(View view) {
        /*
        empty and does nothing right now, needs to connect the player to another player for PvP
         */
    }
}
