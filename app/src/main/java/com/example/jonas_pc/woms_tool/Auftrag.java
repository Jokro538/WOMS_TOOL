package com.example.jonas_pc.woms_tool;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Jonas-PC on 18.11.2015.
 */
public class Auftrag {

    //Variablen
    private String _auftrID;
    private String _raum;
    private int _prio;
    private String _status_flag;
    private Date _datum;
    private String _beschreibung;
    private String _kategorie;

    public Auftrag(){
        this._auftrID= UUID.randomUUID().toString();
    }

    public Auftrag(String auftrID,String beschreibung,String raum,String status_flag,int prio,Date datum,String kategorie){
        this._auftrID=auftrID;
        this._raum=raum;
        this._status_flag=status_flag;
        this._datum=datum;
        this._prio=prio;
        this._beschreibung=beschreibung;
        this._kategorie=kategorie;
    }

   //Auftrags-ID Get/Set
    public String getAuftrID(){return this._auftrID;}

    public void setAuftrID(String auftrID){this._auftrID=auftrID;}

    //Status-ID Get/Set
    public String getStatusFlag(){return this._status_flag;}

    public void setStatusFlag(String status_flag){this._status_flag=status_flag;}

    //Raumnummer
    public String getRaum(){return this._raum;}

    public void setRaum(String raum){this._raum=raum;}

    //Priorität
    public int getPrio(){return this._prio;}

    public void setPrio(int prio){this._prio=prio;}

    //Datum
    public Date getDatum(){return this._datum;}

    public void setDatum(Date datum){this._datum=datum;}

    //Beschreibung
    public String getBeschreibung(){return this._beschreibung;}

    public void setBeschreibung(String beschreibung){this._beschreibung=beschreibung;}

    //Kategorie
    public String getKat(){return this._kategorie;}

    public void setKat(String kategorie){this._kategorie=kategorie;}


}
