package com.example.jonas_pc.woms_tool;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Jonas-PC on 08.12.2015.
 */
public class MyArrayAdapter {
    private Context _context;
    private DBHandler _db;

    public List<Auftrag> auftraege;

    public MyArrayAdapter(DBHandler db, Context context) {
        _context = context;
        _db = db;
    }

    public ArrayAdapter<String> getArrayAdapter() {
        //Aufträge aus DB ziehen
        auftraege = _db.getAllAuftrag();

        //Liste in die die Aufträge geschrieben werden sollen
        ArrayList<String> values;

        values = new ArrayList<String>();

        //Aufträge in die Liste schreiben
        for (Auftrag a:auftraege){
            //TODO: werte vollständig einfügen!!!
            values.add(a.getAuftrID()+" "+a.getPrio()+" ");
        }

        //neuen ArrayAdapter instanzieren und Liste mit den Aufträgen wird dem Konstruktor übergeben
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(_context,simple_list_item_1,values);

        return adapter;
    }
}
