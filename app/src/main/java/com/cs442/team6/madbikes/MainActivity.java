package com.cs442.team6.madbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Utilities util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "EMAIL US", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button signin=(Button)findViewById(R.id.sign_in_button);
        Button signup=(Button)findViewById(R.id.sign_up_button);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);

        DummyData dum = new DummyData(this);
        dum.fill();
        util = new Utilities(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_button:
                //  if (username.equals("cshe") && password.equals("123")) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                this.startActivity(intent);
                //  } else {
                //  Toast.makeText(getApplicationContext(), "Not Valid", Toast.LENGTH_LONG).show();
                //  }
                break;

            case R.id.sign_in_button:
                EditText user = (EditText) findViewById(R.id.username);
                EditText pass = (EditText) findViewById(R.id.password);
                String username = user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if (util.authenticate(username, password)) {
                    Intent signin_intent = new Intent(MainActivity.this, MapsActivity.class);
                    this.startActivity(signin_intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Valid", Toast.LENGTH_LONG).show();
                }
        }

    }
}

 //   @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

 //   @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
