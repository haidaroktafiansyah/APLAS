package org.aplas.basicappx;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Distance dist;
    private Weight weight;
    private Temperature temp;
    private Button convertBtn;
    private EditText inputTxt;
    private EditText outputTxt;
    private Spinner unitOri;
    private Spinner unitConv;
    private RadioGroup unitType;
    private CheckBox roundBox;
    private CheckBox formBox;
    private ImageView imgView;
    private AlertDialog startDialog;

    MainActivity(){
      this.dist = new Distance() ;
      this.weight = new Weight();
      this.temp = new Temperature();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convertBtn = (Button)findViewById(R.id.convertButton);
        inputTxt = (EditText) findViewById(R.id.inputText);
        outputTxt = (EditText)findViewById(R.id.outputText);
        unitOri = (Spinner)findViewById(R.id.oriList);
        unitConv = (Spinner)findViewById(R.id.convList);
        unitType = (RadioGroup)findViewById(R.id.radioGroup);
        roundBox = (CheckBox)findViewById(R.id.chkRounded);
        formBox = (CheckBox)findViewById(R.id.chkFormula);
        imgView = (ImageView)findViewById(R.id.img);
    }

    @Override
    protected void onStart(){
        super.onStart();
        
        startDialog = new AlertDialog.Builder(MainActivity.this).create();
        startDialog.setTitle("Application started");
        startDialog.setMessage("This app can use to convert any units");
        startDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        startDialog.show();
    };

    protected double convertUnit(String sa, String sb, String sc, double da){

        if (sa.equalsIgnoreCase("Temperature")){
            return temp.convert(sb,sc,da);
        }else if(sa.equalsIgnoreCase("Distance")){
            return dist.convert(sb,sc,da);
        }else{
            return weight.convert(sb,sc,da);
        }
    };

    protected String strResult(double da, boolean ba){
        DecimalFormat dc = new DecimalFormat("#.#####");
        DecimalFormat dc2 = new DecimalFormat("#.##");
        if(ba){
            return dc2.format(da);
        }else{
            return dc.format(da);
        }
    };

}