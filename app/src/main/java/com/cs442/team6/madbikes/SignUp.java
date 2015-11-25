package com.cs442.team6.madbikes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by SHE on 2015/11/1.
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener {

    public Context signUpContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Button submit= (Button)findViewById(R.id.submit);
        Button cancel= (Button)findViewById(R.id.cancel);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    switch(v.getId()){
        case R.id.submit:
            EditText et_phone = (EditText) findViewById(R.id.Pnumber);
            et_phone.setInputType(InputType.TYPE_CLASS_PHONE);
            EditText et_email = (EditText) findViewById(R.id.email);
            et_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            EditText et_name = (EditText) findViewById(R.id.Name);
            et_name.setInputType(InputType.TYPE_CLASS_TEXT);
            EditText et_address = (EditText) findViewById(R.id.user_address);
            et_address.setInputType(InputType.TYPE_CLASS_TEXT);
            EditText et_password = (EditText) findViewById(R.id.sign_up_pass);
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            EditText et_confirm_password = (EditText) findViewById(R.id.confirm_pass);
            et_confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            if(!(et_password.getText().toString()).equals((et_confirm_password.getText().toString().trim()))){
                Toast.makeText(SignUp.this,"INCORRECT PASSWORD. VERIFY PASSWORD", Toast.LENGTH_LONG).show();}
            if(et_phone.getText().toString().length()!=10){
                Toast.makeText(SignUp.this,"WRONG PHONE NUMBER FORMAT", Toast.LENGTH_LONG).show();
            } if(!et_email.getText().toString().contains("@")){
            Toast.makeText(SignUp.this,"WRONG EMAIL FORMAT", Toast.LENGTH_LONG).show();}
            if(new Utilities(signUpContext).isExisted(et_email.getText().toString())==false){
                new Utilities(this).addUser(et_email.getText().toString(),et_name.getText().toString(),et_phone.getText().toString(),et_password.getText().toString());
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                this.startActivity(intent);}
            else
                Toast.makeText(SignUp.this,"USERNAME EXISTED, TRY ANOTHER USERNAME", Toast.LENGTH_LONG).show();
            break;
        case R.id.cancel:
            Intent intent1 = new Intent(SignUp.this, MainActivity.class);
            this.startActivity(intent1);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.mp) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

