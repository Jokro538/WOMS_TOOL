package com.example.jonas_pc.woms_tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

/**
 * Created by Jonas-PC on 03.12.2015.
 */
public class KategorieView extends Activity implements View.OnClickListener {

    Spinner San;
    Spinner Ele;
    Spinner Zimmer;
    Spinner Küche;

    CheckBox cBSani;
    CheckBox cbElektrik;
    CheckBox cbZimmer;
    CheckBox cbKüche;

    public String kategorie;
    public String anmerkung = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mangel);

        Button mgl = (Button)findViewById(R.id.btnMangelEnter);
        mgl.setOnClickListener(this);

         San=(Spinner)findViewById(R.id.spinnerSanitär);
         Ele=(Spinner)findViewById(R.id.spinnerElektrik);
         Zimmer=(Spinner)findViewById(R.id.spinnerZimmer);
         Küche=(Spinner)findViewById(R.id.spinnerKüche);

         cBSani = (CheckBox)findViewById(R.id.checkBoxSani);
         cbElektrik = (CheckBox) findViewById(R.id.checkBoxElektrik);
         cbZimmer = (CheckBox) findViewById(R.id.checkBoxZimmer);
         cbKüche = (CheckBox) findViewById(R.id.checkBoxKüche);


        San.setEnabled(false);
        San.setClickable(false);
        Ele.setEnabled(false);
        Ele.setClickable(false);
        Zimmer.setEnabled(false);
        Zimmer.setClickable(false);
        Küche.setEnabled(false);
        Küche.setClickable(false);
    }

//TODO: das verfickte an und aus der Spinner richtig machen!!!!!!!!!!!!!!!!!
    public void onCheckBoxClicked (View v){
        boolean checked = ((CheckBox) v).isChecked();

        switch (v.getId()){
            case R.id.checkBoxSani:
                if(checked){
                    San.setEnabled(true);
                    San.setClickable(true);
                    Ele.setEnabled(false);
                    Ele.setClickable(false);
                    Zimmer.setEnabled(false);
                    Zimmer.setClickable(false);
                    Küche.setEnabled(false);
                    Küche.setClickable(false);
                  /*  cbElektrik.setEnabled(false);
                    cbElektrik.setClickable(false);
                    cbZimmer.setEnabled(false);
                    cbZimmer.setClickable(false);
                    cbKüche.setEnabled(false);
                    cbKüche.setClickable(false); */
                    cbElektrik.setChecked(false);
                    cbKüche.setChecked(false);
                    cbZimmer.setChecked(false);

                    break;
                }
            case R.id.checkBoxElektrik:
                if (checked){
                    San.setEnabled(false);
                    San.setClickable(false);
                    Ele.setEnabled(true);
                    Ele.setClickable(true);
                    Zimmer.setEnabled(false);
                    Zimmer.setClickable(false);
                    Küche.setEnabled(false);
                    Küche.setClickable(false);
                 /*   cBSani.setEnabled(false);
                    cBSani.setClickable(false);
                    cbZimmer.setEnabled(false);
                    cbZimmer.setClickable(false);
                    cbKüche.setEnabled(false);
                    cbKüche.setClickable(false); */
                    cBSani.setChecked(false);
                    cbZimmer.setChecked(false);
                    cbKüche.setChecked(false);
                    break;
                }
            case R.id.checkBoxZimmer:
                if(checked){
                    San.setEnabled(true);
                    San.setClickable(true);
                    Ele.setEnabled(false);
                    Ele.setClickable(false);
                    Zimmer.setEnabled(true);
                    Zimmer.setClickable(true);
                    Küche.setEnabled(false);
                    Küche.setClickable(false);

                  /*  cBSani.setEnabled(false);
                    cBSani.setClickable(false);
                    cbElektrik.setEnabled(false);
                    cbElektrik.setClickable(false);
                    cbKüche.setEnabled(false);
                    cbKüche.setClickable(false); */
                    cBSani.setChecked(false);
                    cbElektrik.setChecked(false);
                    cbKüche.setChecked(false);


                    break;
                }
            case R.id.checkBoxKüche:
                if(checked){
                    San.setEnabled(false);
                    San.setClickable(false);
                    Ele.setEnabled(false);
                    Ele.setClickable(false);
                    Zimmer.setEnabled(false);
                    Zimmer.setClickable(false);
                    Küche.setEnabled(true);
                    Küche.setClickable(true);
                   /* cBSani.setEnabled(false);
                    cBSani.setClickable(false);
                    cbElektrik.setEnabled(false);
                    cbElektrik.setClickable(false);
                    cbZimmer.setEnabled(false);
                    cbZimmer.setClickable(false); */
                    cBSani.setChecked(false);
                    cbElektrik.setChecked(false);
                    cbZimmer.setChecked(false);



                    break;
                }
        }
    }

    public void onRadioButtonClicked (View v){
        boolean checked = ((RadioButton)v).isChecked();

        switch (v.getId()){
            case R.id.rB_fehlt:
                if(checked)
                    anmerkung = "fehlt";
                    break;
            case R.id.rB_tausch:
                if(checked)
                    anmerkung = "tausch";
                    break;
            case R.id.rB_defekt:
                if(checked)
                    anmerkung = "defekt";
                break;
        }
    }

    public void onClick (View v){

        //TODO: Übergabe von Kategorie und Anmerkung an die Auftragsklasse!!!
        Auftrag auftrag = new Auftrag();

        if (cBSani.isChecked()){
            kategorie =String.valueOf(San.getSelectedItem());
        }
        if (cbElektrik.isChecked()){
            kategorie =String.valueOf(Ele.getSelectedItem());
        }
        if (cbZimmer.isChecked()){
            kategorie =String.valueOf(Zimmer.getSelectedItem());
        }
        if (cbKüche.isChecked()){
            kategorie =String.valueOf(Küche.getSelectedItem());
        }
       //  auftrag.setKat(kategorie);

        finish();
    }
}
