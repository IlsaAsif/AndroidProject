package com.example.flyingfish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {

    private Button StartGameAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_over );

        StartGameAgain = (Button)findViewById( R.id.play_again_btn );
        StartGameAgain.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent( GameOverActivity.this, MainActivity.class);
                startActivity( mainIntent );


            }
        } );
    }
}
