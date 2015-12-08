package com.example.jonas_pc.woms_tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Jonas-PC on 26.11.2015.
 */
public class LogInView extends Activity implements View.OnClickListener {
   //Login Strings
    String admin;
    String adminPW;
    String TLeiter;
    String TLeiterPW;
    String HMeister;
    String HMeisterPW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ImageView imgViewLogin = (ImageView) this.findViewById(R.id.imgViewLogin);
        imgViewLogin.setImageResource(R.drawable.gierso_logo);

        Button btnLogin = (Button) this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        //Rollen für Login:
        //Administration:
        admin ="admin";
        adminPW ="admin";
        //Technische Leitung:
        TLeiter ="technik";
        TLeiterPW="technik";
        //Hausmeister:
        HMeister="hausmeister";
        HMeisterPW="hausmeister";

    }


    public void onClick(View v) {
        EditText user = (EditText) findViewById(R.id.editUser);
        EditText passwort = (EditText) findViewById(R.id.editPW);
        //Admin-Login
        if (user.getText().toString().equals(admin) && passwort.getText().toString().equals(adminPW)) {
            Toast.makeText(getApplicationContext(), "Zugriff als Administrator", Toast.LENGTH_LONG).show();
            Intent AuftragAnlegenView = new Intent(this, AuftragAnlegenView.class);
            startActivity(AuftragAnlegenView);
        }
        //Login für Technische Leitung:
        else if (user.getText().toString().equals(TLeiter) && passwort.getText().toString().equals(TLeiterPW)){
            Toast.makeText(getApplicationContext(), "Zugriff als Administrator", Toast.LENGTH_LONG).show();
            Intent Technik = new Intent(this, TechLeiterView.class);
            startActivity(Technik);
        }
        //Login für Haushandwerker:
        else if (user.getText().toString().equals(HMeister) && passwort.getText().toString().equals(HMeisterPW)){
            Toast.makeText(getApplicationContext(), "Zugriff als Administrator", Toast.LENGTH_LONG).show();
            Intent hausmeisterView = new Intent(this, HausmeisterView.class);
            startActivity(hausmeisterView);
        }
        //Fehlermeldung bei falschen Login:
        else {
            Toast.makeText(getApplicationContext(), "Fehlerhaftes Login", Toast.LENGTH_LONG).show();
        }
    }


}
