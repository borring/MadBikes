package com.cs442.team6.madbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNew extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);
        Button submit=(Button)findViewById(R.id.submit);
        Button cancel=(Button)findViewById(R.id.cancel);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
        case R.id.submit:
            EditText brand = (EditText) findViewById(R.id.brand);
            EditText address = (EditText) findViewById(R.id.address);
            EditText condition = (EditText) findViewById(R.id.condition);
            EditText price = (EditText) findViewById(R.id.new_price);
            String brand1 = brand.getText().toString().trim();
            String address1 = address.getText().toString().trim();
            String condition1 = condition.getText().toString().trim();
            String price1 = price.getText().toString().trim();
            if(!brand1.equals("")&&!address1.equals("")&&!condition1.equals("")&&!price1.equals("")){
                 startActivity(new Intent(AddNew.this, ManageProfile.class));}
            else{
                 Toast.makeText(getApplicationContext(), "No enough information", Toast.LENGTH_LONG).show();
            }
        break;
        case R.id.cancel:
        startActivity(new Intent(AddNew.this, ManageProfile.class));
        break;
        }
    }



}
