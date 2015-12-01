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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ImageView imgViewLogin = (ImageView) this.findViewById(R.id.imgViewLogin);
        imgViewLogin.setImageResource(R.drawable.gierso_logo);

        Button btnLogin = (Button) this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }


    public void onClick(View v) {
        EditText user = (EditText) findViewById(R.id.editUser);
        EditText passwort = (EditText) findViewById(R.id.editPW);

        if (user.getText().toString().equals("admin") && passwort.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Herzlich willkommen", Toast.LENGTH_LONG).show();
            Intent AuftragAnlegenView = new Intent(this, AuftragAnlegenView.class);
            startActivity(AuftragAnlegenView);
        } else {
            Toast.makeText(getApplicationContext(), "Fehlerhastes Login", Toast.LENGTH_LONG).show();
        }
    }


}
