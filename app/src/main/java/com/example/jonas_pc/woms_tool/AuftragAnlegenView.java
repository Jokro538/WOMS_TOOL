package com.example.jonas_pc.woms_tool;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuftragAnlegenView extends ActionBarActivity implements View.OnClickListener {

    Button btnAuftragAnlegen;
    private int prio = 0;
    private Spinner Kat,SubKat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAuftragAnlegen=(Button)findViewById(R.id.btnAuftragAnlegen);
        btnAuftragAnlegen.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Auslesen der Prioriät durch RadioButtons
    public void onRadioButtonClicked (View view){
        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.prio1:
                if (checked)
                     prio = 1;
                break;
            case R.id.prio2:
                if(checked)
                    prio =2;
                break;
            case R.id.prio3:
                if(checked)
                    prio=3;
                break;

        }

    }

    //Spinner für die Auswahl der Mängelkategorien
    public void KatToSpinner(){
        Kat = (Spinner)findViewById(R.id.spinnerMangel);
        List<String> list =new ArrayList<String>();
        list.add("Wasser");
        list.add("Elektrik");
        list.add("Raum");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Kat.setAdapter(adapter);
    }



    @Override
    public void onClick(View v){
        //Datenbank und Auftrag werden instanziert
        DBHandler db = new DBHandler(this);
        Auftrag auftrag = new Auftrag();

        //Werte aus dem Formular werden in die zugehörigen Variablen geschireben
        EditText RaumNr=(EditText)findViewById(R.id.inRaum);
        String raum = RaumNr.getText().toString();

        EditText beschr = (EditText)findViewById(R.id.editBeschreibung);
        String beschreibung = beschr.getText().toString();

      //  Date datum =java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        int prioritaet = prio;

        String kategorie = String.valueOf(Kat.getSelectedItem());

        //Status wird auf Update gesetzt und das Auftragsformular wird befüllt
        auftrag.setStatusFlag("U");
        auftrag.setRaum(raum);
        auftrag.setBeschreibung(beschreibung);
    //    auftrag.setDatum(datum);
        auftrag.setKat(kategorie);
        auftrag.setPrio(prioritaet);

        //Auftrag in Datenbank schreiben
        db.addAuftrag(auftrag);
        db.close();

        Toast.makeText(getApplicationContext(),"Auftrag angelegt",Toast.LENGTH_LONG).show();
    }
}
