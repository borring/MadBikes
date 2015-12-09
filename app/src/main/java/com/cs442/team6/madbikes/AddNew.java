package com.cs442.team6.madbikes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;

public class AddNew extends AppCompatActivity implements View.OnClickListener{
    private static final String[] m={"New","Like New","Very Good","Good","Old"};
    private TextView view ;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    public Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);
        Button submit=(Button)findViewById(R.id.submit);
        Button cancel=(Button)findViewById(R.id.cancel);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        view = (TextView) findViewById(R.id.add_condition);
        spinner = (Spinner) findViewById(R.id.spinner_condition);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            view.setText("Condition："+m[arg2]);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    public void onClick(View v){
        switch(v.getId()){
        case R.id.submit:
            EditText brand = (EditText) findViewById(R.id.brand);
            EditText address = (EditText) findViewById(R.id.address);
          //  Spinner condition = (Spinner) findViewById(R.id.Spinner01);
         //   EditText condition= (EditText)findViewById(R.id.condition);
            EditText price = (EditText) findViewById(R.id.new_price);
            String brand1 = brand.getText().toString().trim();
            String address1 = address.getText().toString().trim();
            String condition1 = spinner.getSelectedItem().toString();
            String price1 = price.getText().toString().trim();
            if(!brand1.equals("")&&!address1.equals("")&&!condition1.equals("")&&!price1.equals("")){
                Utilities newBike = new Utilities(this);
              //  public void addBike(int UID, String bname, double lat, double lng, String state, float rate)
                Geocoder geocoder = new Geocoder(AddNew.this);
                List<Address> addresses= null;
                Address addressL= null;
                try {
                    addresses= geocoder.getFromLocationName(address1, 1);
                } catch (IOException e) {
                    Log.e("AddrToGp", e.toString());
                }
                addressL= addresses.get(0);
                double geoLatitude = addressL.getLatitude()*1000000;
                double geoLongitude = addressL.getLongitude()*1000000;
                SharedPreferences spref = getSharedPreferences(newBike.AUTH_FILE, Context.MODE_PRIVATE);
                String username = spref.getString(newBike.AUTH_NAME, "N/A");
                Log.d("addnew", "Username is " + username);
                newBike.addBike(newBike.getUID(username), brand1, address1, geoLatitude, geoLongitude, condition1, Float.parseFloat(price1));
                newBike.close();

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
