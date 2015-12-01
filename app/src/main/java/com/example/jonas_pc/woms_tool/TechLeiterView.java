package com.example.jonas_pc.woms_tool;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Jonas-PC on 26.11.2015.
 */
public class TechLeiterView extends Activity implements View.OnClickListener {
    private Spinner hmeister;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_technik);
        Button btnZuweisen =(Button)this.findViewById(R.id.btnZuweisen);
        btnZuweisen.setOnClickListener(this);
        hmeister =(Spinner)findViewById(R.id.spinnerHmeister);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new TLDatePicker();
        newFragment.show(getFragmentManager(),"datePicker");
    }
    public void onClick (View v){

        //Einlesen der neuen Formulardaten:

        String hausmeister = String.valueOf(hmeister.getSelectedItem());


        Toast.makeText(getApplicationContext(), "Auftrag weitergeleitet", Toast.LENGTH_LONG).show();
    }
}
