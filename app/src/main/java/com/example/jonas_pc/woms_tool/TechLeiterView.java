package com.example.jonas_pc.woms_tool;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jonas-PC on 26.11.2015.
 */
public class TechLeiterView extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
    private Spinner hmeister;
    private TextView txtBearbeitung;
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_technik);
        Button btnZuweisen =(Button)this.findViewById(R.id.btnZuweisen);
    //    btnZuweisen.setOnClickListener(zuweisung());
        hmeister =(Spinner)findViewById(R.id.spinnerHmeister);
        txtBearbeitung=(TextView)findViewById(R.id.txtRepDate);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new TLDatePicker();
        newFragment.show(getFragmentManager(),"datePicker");

    }

    @Override
    public void onDateSet(DatePicker view, int jahr, int monat, int tag){
        Log.w("DatePicker","Datum = "+tag+monat+jahr);
        txtBearbeitung.setText(tag+"."+monat+"."+jahr);
    }
    public void zuweisung (){

        //Einlesen der neuen Formulardaten:

        String hausmeister = String.valueOf(hmeister.getSelectedItem());
        String repDate = txtBearbeitung.getText().toString();

        Toast.makeText(getApplicationContext(), "Auftrag weitergeleitet", Toast.LENGTH_LONG).show();
    }
}
