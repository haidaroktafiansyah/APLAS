package org.aplas.basicappx;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
    private ArrayAdapter adaptervarname;

    MainActivity() {
        this.dist = new Distance();
        this.weight = new Weight();
        this.temp = new Temperature();
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convertBtn = (Button) findViewById(R.id.convertButton);
        inputTxt = (EditText) findViewById(R.id.inputText);
        outputTxt = (EditText) findViewById(R.id.outputText);
        unitOri = (Spinner) findViewById(R.id.oriList);
        unitConv = (Spinner) findViewById(R.id.convList);
        unitType = (RadioGroup) findViewById(R.id.radioGroup);
        roundBox = (CheckBox) findViewById(R.id.chkRounded);
        formBox = (CheckBox) findViewById(R.id.chkFormula);
        imgView = (ImageView) findViewById(R.id.img);

        unitType.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton resrab = (RadioButton) findViewById(checkedId);
                        ArrayAdapter<CharSequence> arr;
                        inputTxt.setText("0");
                        outputTxt.setText("0");
                        if(resrab.getText().equals("Temperature")){
                            arr = ArrayAdapter.createFromResource(unitType.getContext(), R.array.tempList, android.R.layout.simple_spinner_item);
                            imgView.setImageResource(R.drawable.temperature);
                            imgView.setTag(R.drawable.temperature);
                            arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            unitOri.setAdapter(arr);
                            unitConv.setAdapter(arr);
                        }
                        else if(resrab.getText().equals("Distance")){
                            arr = ArrayAdapter.createFromResource(unitType.getContext(), R.array.distList , android.R.layout.simple_spinner_item);
                            imgView.setImageResource(R.drawable.distance);
                            imgView.setTag(R.drawable.distance);
                            arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            unitOri.setAdapter(arr);
                            unitConv.setAdapter(arr);
                        }else{
                            arr = ArrayAdapter.createFromResource(unitType.getContext(), R.array.weightList, android.R.layout.simple_spinner_item);
                            imgView.setImageResource(R.drawable.weight);
                            imgView.setTag(R.drawable.weight);
                            arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            unitOri.setAdapter(arr);
                            unitConv.setAdapter(arr);
                        }

                    }
                });
    }

    @Override
    protected void onStart() {
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
    }

    ;

    protected double convertUnit(String sa, String sb, String sc, double da) {

        if (sa.equalsIgnoreCase("Temperature")) {
            return temp.convert(sb, sc, da);
        } else if (sa.equalsIgnoreCase("Distance")) {
            return dist.convert(sb, sc, da);
        } else {
            return weight.convert(sb, sc, da);
        }
    }

    ;

    protected String strResult(double da, boolean ba) {
        DecimalFormat dc = new DecimalFormat("#.#####");
        DecimalFormat dc2 = new DecimalFormat("#.##");
        if (ba) {
            return dc2.format(da);
        } else {
            return dc.format(da);
        }
    }

    ;

}