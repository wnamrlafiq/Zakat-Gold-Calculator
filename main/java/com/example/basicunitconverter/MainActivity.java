package com.example.basicunitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText textweight, textGoldValue;
    Button btnconvert, btnclear, button, btninfo;
    TextView out1, out2, out3;
    RadioGroup radioGroup;
    RadioButton Keep, Wear;
    SharedPreferences sharedPref1, sharedPref;
    float gweight, gvalue, total1, total2, total3;
    float typeofgold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textweight = (EditText) findViewById(R.id.textweight);
        textGoldValue = (EditText) findViewById(R.id.textGoldValue);
        btnconvert = (Button) findViewById(R.id.btnconvert);
        btnclear = (Button) findViewById(R.id.btnclear);
        button = (Button) findViewById(R.id.button);
        btninfo = (Button) findViewById(R.id.btninfo);
        out1 = (TextView) findViewById(R.id.out1);
        out2 = (TextView) findViewById(R.id.out2);
        out3 = (TextView) findViewById(R.id.out3);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        Keep = (RadioButton) findViewById(R.id.Keep);
        Wear = (RadioButton) findViewById(R.id.Wear);


        load();
        btnconvert.setOnClickListener(this);
        btnclear.setOnClickListener(this);
        button.setOnClickListener(this);
        btninfo.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == btninfo.getId()) {

            Intent intent = new Intent(this, info.class);
            startActivity(intent);
        }

        if (v.getId() == button.getId()) {
            Intent intent = new Intent(this, aboutus.class);
            startActivity(intent);
        }


        if (errorhandling() == true) {
            Toast.makeText(this, "PLEASE ENTER DETAILS", Toast.LENGTH_LONG).show();
        }
        else {
            if (v.getId() == btnconvert.getId()) {


                try {

                    if (Keep.isChecked())
                        typeofgold = 85;
                    else if (Wear.isChecked())
                        typeofgold = 200;

                    gweight = Float.parseFloat(textweight.getText().toString());
                    gvalue = Float.parseFloat(textGoldValue.getText().toString());
                    //int value3 = typeofgold;
                    //nisob 2.5%
                    total1 = gweight * gvalue;   //total value of gold (weigh x value)
                    total2 = (gweight - typeofgold) * gvalue;    //total gold value payable
                    total3 = (float) (total2 * 0.025);    //total zakat nisob


                    SharedPreferences.Editor editor = sharedPref1.edit();
                    editor.putFloat("weight", gweight);
                    editor.apply();

                    SharedPreferences.Editor editor1 = sharedPref.edit();
                    editor1.putFloat("gold", gvalue);
                    editor1.apply();


                } catch (NumberFormatException nfe) {

                    Toast.makeText(this, "PLEASE ENTER DETAILS", Toast.LENGTH_LONG).show();
                }

                display();
            } else if (v.getId() == btnclear.getId()) {
                clearbutton();
            }
        }


    }

    public void display() {
        out1.setText("Total value of the gold: \n RM " + String.format("%.2f", total1));

        if (total2 > 0)
            out2.setText("Gold value that is zakat payable: \n RM " + String.format("%.2f", total2));
        else
            out2.setText(("Gold value that is zakat payable: \n RM " + String.format("0")));

        if (total3 > 0)
            out3.setText("Total zakat: \n RM " + String.format("%.2f", total3));
        else
            out3.setText("Total zakat: \n RM " + String.format("0"));


    }

    public boolean errorhandling() {
        boolean error = true;
        if (textGoldValue.getText().toString().length() == 0 && textweight.getText().toString().length() == 0) {
            textweight.setError("Enter gold weight");
            textGoldValue.setError("Enter gold value");
        }
        else if (textGoldValue.getText().toString().length() == 0) {
            textGoldValue.setError("Enter gold value");
        }
        else if (textweight.getText().toString().length() == 0) {
            textweight.setError("Enter gold weight");
        }
        else if (!Keep.isChecked() && !Wear.isChecked()) {
            Keep.setError("");
            Wear.setError("");
            Toast.makeText(this, "PLEASE SELECT TYPE OF GOLD", Toast.LENGTH_LONG).show();
        }
        else{
           error = false;
        }
        return error;
    }

    public void load() {
        sharedPref1 = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gweight = sharedPref1.getFloat("weight", 0); //load data weight
        textweight.setText(" " + gweight);


        sharedPref = this.getSharedPreferences("gold", Context.MODE_PRIVATE);
        gvalue = sharedPref.getFloat("gold", 0); //load data gold value
        textGoldValue.setText(" " + gvalue);

    }

    public void clearbutton(){
        gweight = 0;
        gvalue=0;
        radioGroup.clearCheck();
        textweight.setText("");
        textGoldValue.setText("");
        out1.setText("");
        out2.setText("");
        out3.setText("");
        Toast.makeText(this, "Content Cleared", Toast.LENGTH_SHORT).show();
    }


}






