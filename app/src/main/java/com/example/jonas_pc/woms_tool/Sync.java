package com.example.jonas_pc.woms_tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jonas-PC on 19.11.2015.
 */
public class Sync {

    public Sync(JSONArray jAuftr, DBHandler db, String server ) {

        try {
            //Verbindung zum Server herstellen
            URL url = new URL(server);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //post Variable an JSON-Array übergeben
            String data = "data=" + jAuftr;
            con.setDoOutput(true);
            con.setInstanceFollowRedirects(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "UTF-8");
            con.setUseCaches(false);

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());

            //Daten an Server posten
            out.write(data);
            out.close();


            JSONArray mJsonArray = new JSONArray(readStream(con.getInputStream()).replace('"', '\"'));
            JSONObject mJsonObject;
            for (int i = 0; i < mJsonArray.length(); i++) {
                mJsonObject = mJsonArray.getJSONObject(i);

                Auftrag auftrag = new Auftrag();
                auftrag.setPrio(mJsonObject.getString("prio").toString());
                auftrag.setKat(mJsonObject.getString("kategorie").toString());
                auftrag.setDatum(mJsonObject.getString("datum").toString());
                auftrag.setBeschreibung(mJsonObject.getString("beschreibung").toString());
                auftrag.setRaum(mJsonObject.getString("beschreibung").toString());

                db.addAuftrag(auftrag);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //Methode um InputStream in String umzuwandeln
    public String readStream(InputStream in){
        BufferedReader br = null;
        String line ="";

        StringBuffer sb = null;
        try{
            //InputStream einlesen
            br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuffer();
            while ((line=br.readLine())!= null){
                //Stream to String
                sb.append(line.toString());
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
