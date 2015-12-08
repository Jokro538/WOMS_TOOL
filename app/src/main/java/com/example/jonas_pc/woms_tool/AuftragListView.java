package com.example.jonas_pc.woms_tool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by Jonas-PC on 08.12.2015.
 */
public class AuftragListView extends Activity {

    DBHandler db;
    MyArrayAdapter adapter;
    ListView auftragView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auftraglist);

        //Instanzieren der Datenbank
        db = new DBHandler(this);

        //Instanzieren des ArrayAdapters und Objekte der ListView hinzufügen
        adapter=new MyArrayAdapter(db,this);
        auftragView=(ListView)findViewById(R.id.showAuftrag);
        auftragView.setAdapter(adapter.getArrayAdapter());
      //  auftragView.setOnItemClickListener();

        db.close();
    }
}
