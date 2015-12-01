package com.example.jonas_pc.woms_tool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AuftragAnlegenView extends ActionBarActivity implements View.OnClickListener {

    Button btnAuftragAnlegen;
    private String prio = "hoch";
    private String belegung = "ja";
    private Spinner Kat, SubKat;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });

        btnAuftragAnlegen = (Button) findViewById(R.id.btnAuftragAnlegen);
        btnAuftragAnlegen.setOnClickListener(this);
        Kat = (Spinner) findViewById(R.id.spinnerMangel);
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
        //auf passenden requestCode prüfen
        if (requestCode == CAMERA_REQUEST) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);
            imageView.setImageBitmap(bitmap);
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
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


        String kategorie = String.valueOf(Kat.getSelectedItem());

        //Status wird auf Update gesetzt und das Auftragsformular wird befüllt
        auftrag.setStatusFlag("U");
        auftrag.setRaum(raum);
        auftrag.setBeschreibung(beschreibung);
        auftrag.setDatum(datum);
        auftrag.setKat(kategorie);
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