package com.cs442.team6.madbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AfterSignUp extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sign_up);

        Button main= (Button) findViewById(R.id.button3);
        main.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v.getId()==R.id.button3){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
