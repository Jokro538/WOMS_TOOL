package com.example.jonas_pc.woms_tool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AuftragAnlegenView extends ActionBarActivity implements View.OnClickListener {

    Button btnAuftragAnlegen;
    private String prio = "hoch";
    private String belegung = "ja";
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private String zusatz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bild aufnehmen
        this.imageView = (ImageView) this.findViewById(R.id.imageView);
        Button btnBild = (Button) this.findViewById(R.id.btnBild);
        btnBild.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btnAuftragAnlegen = (Button) findViewById(R.id.btnAuftragAnlegen);
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

    //Funktion für das Aufnehmen und Anzeigen der Bilder

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                String filestring = selectedImageUri.getPath();

                Bitmap bm = (Bitmap) data.getExtras().get("data");
                Bitmap bmpPhoto = bm;
                String bmpPhotoPath = filestring;
                imageView.setImageBitmap(bmpPhoto);

            }
        }
    }

    //Auslesen der Prioriät durch RadioButtons
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.prio1:
                if (checked)
                    prio = "hoch";
                break;
            case R.id.prio2:
                if (checked)
                    prio = "mittel";
                break;
            case R.id.prio3:
                if (checked)
                    prio = "niedrig";
                break;

            //Listener für die Beeinträchtigung der Belegung
            case R.id.rBel_Ja:
                if (checked)
                    belegung = "ja";
                break;
            case R.id.rBel_Nein:
                if (checked)
                    belegung = "nein";
                break;

        }

    }

    //Methode für die Auswahl der Mangelkategorien:

    public void onClickMangel(View v) {
        Intent KategorieView = new Intent(this, KategorieView.class);
        startActivity(KategorieView);
    }


    @Override
    public void onClick(View v) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        //Datenbank und Auftrag werden instanziert
        DBHandler db = new DBHandler(this);
        Auftrag auftrag = new Auftrag();

        //Werte aus dem Formular werden in die zugehörigen Variablen geschireben
        EditText RaumNr = (EditText) findViewById(R.id.inRaum);
        String raum = RaumNr.getText().toString();

        EditText beschr = (EditText) findViewById(R.id.editBeschreibung);
        String beschreibung = beschr.getText().toString();

        // Date datum =java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String datum = sdf.format(Calendar.getInstance().getTime());


        // String kategorie = String.valueOf(Kat.getSelectedItem());

        //Status wird auf Update gesetzt und das Auftragsformular wird befüllt
        auftrag.setStatusFlag("U");
        auftrag.setRaum(raum);
        auftrag.setBeschreibung(beschreibung);
        auftrag.setDatum(datum);
        // auftrag.setKat(kategorie);
        // auftrag.setAnmerkung(anmerkung);
        auftrag.setPrio(prio);
        auftrag.setBelegung(belegung);

        //Auftrag in Datenbank schreiben
        db.addAuftrag(auftrag);
        db.close();


        try {

            //zieht sich alle Kontakte als JSON Array und übergibt sie dem Object Sync,
            //das die Syncronisation mit dem Server durchführt
            JSONArray jAuftr = db.auftraegeAsJSON();
            Sync sync = new Sync(jAuftr, db, "http://10.0.2.2/gierso_index.php");


            //Neuer ArrayAdapter wird instanziert, da nach Sync sich die Kontakte verändert haben (könnten)
            //und baut neue ListView auf.
            //  adapter=new MyArrayAdapter(db,this);
            //  ContactView = (ListView) findViewById(R.id.ShowContacts);
            //   ContactView.setAdapter(adapter.getArrayAdapter());

            //Label, welches die Anzahl der Kontakte anzeigt. Der String für die Anzahl der Kontakte wird in der Methode AnzahlKontakte zusammengesetzt.
            //  AnzahlKontakte();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(getApplicationContext(), "Auftrag angelegt", Toast.LENGTH_LONG).show();
    }

}