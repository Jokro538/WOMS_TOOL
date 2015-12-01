package com.example.jonas_pc.woms_tool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Jonas-PC on 01.12.2015.
 */
public class TLDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c =Calendar.getInstance();
        int jahr = c.get(Calendar.YEAR);
        int monat = c.get(Calendar.MONTH);
        int tag = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,jahr,monat,tag);

    }

    public void onDateSet(DatePicker view, int jahr, int monat, int tag){

    }
}
