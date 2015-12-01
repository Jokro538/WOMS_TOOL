package com.example.jonas_pc.woms_tool;

import java.util.UUID;

/**
 * Created by Jonas-PC on 18.11.2015.
 */
public class Auftrag {

    //Variablen
    private String _auftrID;
    private String _raum;
    private String _prio;
    private String _status_flag;
    private String _datum;
    private String _beschreibung;
    private String _kategorie;
    private String _belegung;
    private String _aufnehmer;
    private String _objekt;

    public Auftrag(){
        this._auftrID = UUID.randomUUID().toString();
    }

    public Auftrag(String auftrID,String beschreibung,String raum,String status_flag,String prio,String datum,String kategorie,String belegung, String aufnehmer, String objekt){
        this._auftrID=auftrID;
        this._raum=raum;
        this._status_flag=status_flag;
        this._datum=datum;
        this._prio=prio;
        this._beschreibung=beschreibung;
        this._kategorie=kategorie;
        this._belegung=belegung;
        this._aufnehmer=aufnehmer;
        this._objekt=objekt;

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
    public String getPrio(){return this._prio;}

    public void setPrio(String prio){this._prio=prio;}

    //Datum
    public String getDatum(){return this._datum;}

    public void setDatum(String datum){this._datum=datum;}

    //Beschreibung
    public String getBeschreibung(){return this._beschreibung;}

    public void setBeschreibung(String beschreibung){this._beschreibung=beschreibung;}

    //Kategorie
    public String getKat(){return this._kategorie;}

    public void setKat(String kategorie){this._kategorie=kategorie;}

    //Belegung
    public String getBelegung(){return this._belegung;}

    public void setBelegung(String belegung){this._belegung=belegung;}


}
