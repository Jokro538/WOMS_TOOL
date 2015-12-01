package com.example.jonas_pc.woms_tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas-PC on 18.11.2015.
 */
public class DBHandler extends SQLiteOpenHelper{

    //Version der Datenbank
    private static int DATABASE_VERSION = 3;

    //Name der Datenbank
    private static String DATABASE_NAME = "gierso";

    //Tabellennamen
    private String TABLE_AUFTRAG ="auftrag";
    private String TABLE_MANGEL ="mangel";
    private String TABLE_AUFNAHME ="aufnahme";
    private String TABLE_ABSCHLUSS ="abschluss";


    private String KEY_STATUS_FLAG ="status_flag";

    //Attribute der Tabelle Aufnahme
    private String KEY_OBJEKT    ="objekt";
    private String KEY_DATUM     ="datum";
    private String KEY_RAUM      ="raum";
    private String KEY_Aufnehmer ="aufnehmer";

    //Attribute der Tabelle Mangel
    private String KEY_MANGELID  ="mangelID";
    private String KEY_KATEGORIE ="kategorie";
    private String KEY_SUBKAT    ="subKat";
    private String KEY_Prio      ="prio";
    private String KEY_Beschreibung ="beschreibung";

    //Attribute der Tabelle Auftrag
    private String KEY_AuftragsID ="auftrID";
    private String KEY_BEARBEITER ="bearbeiter";

    //Attribute der Tabelle Abschluss
    private String KEY_ABSCHLUSSDATUM ="abschlussdatum";
    private String KEY_ABGESCHLOSSENDURCH="abgeschlossenDurch";

    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Anlegen der Aufnahmetabelle
    @Override
    public void onCreate (SQLiteDatabase db){
        String CREATE_AUFTRAG_TABLE = "CREATE TABLE " + TABLE_AUFTRAG + "("
                + KEY_AuftragsID     +" char(36) PRIMARY KEY,"
                + KEY_OBJEKT         +" char(36),"
                + KEY_KATEGORIE      +" char(50),"
                + KEY_RAUM           +" char(5),"
                + KEY_DATUM          +" date(10),"
                + KEY_Aufnehmer      +" char(40),"
                + KEY_MANGELID       +" number(5),"
                + KEY_STATUS_FLAG    +" char(1),"
                + KEY_Beschreibung   +" char(200),"
                + KEY_Prio           +" char(7)"
                +")";
        db.execSQL(CREATE_AUFTRAG_TABLE);

    /*    String CREATE_MANGEL_TABLE = "CREATE TABLE" + TABLE_MANGEL + "("
      /*       //   +KEY_OBJEKT+"char(36) FOREIGN KEY,"
                +
            //    +KEY_RAUM+"char(5) FOREIGN KEY,"
                +
                +
            //    +KEY_Prio+"number(1),"
                +KEY_MANGELID+"number(5) PRIMARY KEY,"
                +")";
        db.execSQL(CREATE_MANGEL_TABLE);
        */


 /*       String CREATE_AUFTRAG_TABLE = "CREATE TABLE" + TABLE_AUFTRAG + "("
                +KEY_ABGESCHLOSSENDURCH+"char(36) FOREIGN KEY,"
                +KEY_BEARBEITER+"char(36),"
                +KEY_AuftragsID+"number(5) PRIMARY KEY,"
                +KEY_MANGELID+"number(5) FOREIGN KEY,"
                +")";
        db.execSQL(CREATE_AUFTRAG_TABLE);

        String CREATE_ABSCHLUSS_TABLE = "CREATE TABLE" + TABLE_ABSCHLUSS + "("
                +KEY_ABGESCHLOSSENDURCH+"char(36) PRIMARY KEY,"
                +KEY_ABSCHLUSSDATUM+"date(10),"
                +KEY_AuftragsID+"number(5) FOREIGN KEY,"
                +")";
        db.execSQL(CREATE_ABSCHLUSS_TABLE);
        */
    }

    //Datenbank aktualisieren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        //alte Tabelle löschen
      //  db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ABSCHLUSS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_AUFTRAG);
      //  db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MANGEL);
      //  db.execSQL("DROP TABLE IF EXISTS "+ TABLE_AUFNAHME);

        //neue Tabelle anlegen
        onCreate(db);
    }

    //Neuen Auftrag anlegen
    public void addAuftrag (Auftrag auftrag){
        SQLiteDatabase db = this.getWritableDatabase();

        //Eigenschaften des Auftrages werden gelesen und übergeben
        ContentValues values = new ContentValues();
        values.put(KEY_AuftragsID, auftrag.getAuftrID());
        values.put(KEY_RAUM, auftrag.getRaum());
        values.put(KEY_STATUS_FLAG, auftrag.getStatusFlag());
        values.put(KEY_DATUM, auftrag.getDatum());
        values.put(KEY_Prio, auftrag.getPrio());
        values.put(KEY_KATEGORIE, auftrag.getKat());

        //Zeile einfügen
        db.insert(TABLE_AUFTRAG, null, values);
        //Datenbankverbindung schließen
        db.close();

    }

    //Einzelnen Auftrag lesen
    public Auftrag getAuftrag(String auftrID){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_AUFTRAG,new String[]{
            KEY_STATUS_FLAG,
                KEY_Prio,
                KEY_AuftragsID,
                KEY_DATUM,
                KEY_KATEGORIE,
                KEY_Beschreibung},KEY_AuftragsID+"=?",
                new String[]{auftrID},null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();


        } else {
            return null;
        }
        Auftrag auftrag = new Auftrag(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9));
        return auftrag;
    }

    //Alle Aufträge lesen
    public List<Auftrag> getAllAuftrag(){
        List<Auftrag> auftragList =new ArrayList<Auftrag>();

        //Alle Aufträge, die nicht gelöscht werden sollen abrufen
        String selectQuery = "SELECT * FROM" + TABLE_AUFTRAG + "WHERE status_flag IS NOT 'D'"+
                "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Cursor durchlaufen und AUftrags Array füllen
        if (cursor.moveToFirst()){
            do {
                Auftrag auftrag = new Auftrag();
                auftrag.setAuftrID(cursor.getString(0));
                auftrag.setRaum(cursor.getString(1));
                auftrag.setBeschreibung(cursor.getString(2));
             //   auftrag.setDatum(cursor.getString(3));
                auftrag.setKat(cursor.getString(4));
             //   auftrag.setPrio(cursor.getString(5));
                auftrag.setStatusFlag(cursor.getString(6));

                auftragList.add(auftrag);
            } while (cursor.moveToNext());
        }
        return auftragList;
    }

    //Ausgabe aller Aufträge als JSON-Object

    public JSONArray auftraegeAsJSON() throws JSONException {
        JSONArray auftragList = new JSONArray();
        //Alle Aufträge aus DB abrufen
        String selectQuery = "SELECT * FROM " + TABLE_AUFTRAG + " WHERE status_flag='U' OR status_flag='D'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Cursor durchlaufen und JSON-Object hinzufügen
        if (cursor.moveToFirst()){
            do {
                JSONObject auftrag = new JSONObject();

                auftrag.put("auftrID",cursor.getString(0).toString());
             //   auftrag.put("objekt",cursor.getString(1).toString());
                auftrag.put("kategorie",cursor.getString(2).toString());
                auftrag.put("raum",cursor.getString(3).toString());
                auftrag.put("datum",cursor.getString(4).toString());
              //  auftrag.put("aufnehmer",cursor.getString(5).toString());
             //   auftrag.put("mangelID",cursor.getString(6).toString());
                auftrag.put("status_flag",cursor.getString(7).toString());
              //  auftrag.put("beschreibung",cursor.getString(8).toString());
                auftrag.put("prio",cursor.getString(9).toString());

                auftragList.put(auftrag);

            } while (cursor.moveToNext());
        }
        return auftragList;
       // Log.d(auftragList);
    }


}
