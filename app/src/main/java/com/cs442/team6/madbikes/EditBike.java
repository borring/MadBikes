package com.cs442.team6.madbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by SHE on 2015/11/8.
 */
public class EditBike extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_bike);
        Button submit=(Button)findViewById(R.id.edit_submit);
        Button reset=(Button)findViewById(R.id.edit_reset);
        submit.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.edit_submit:
                startActivity(new Intent(EditBike.this, ManageProfile.class));
                break;
        }
    }
}
