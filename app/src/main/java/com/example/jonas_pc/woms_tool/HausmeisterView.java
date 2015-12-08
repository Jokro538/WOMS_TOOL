package com.example.jonas_pc.woms_tool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Jonas-PC on 26.11.2015.
 */
public class HausmeisterView extends Activity implements View.OnClickListener {

    private ImageView imgHa;
    private static final int CAMERA_REQUEST = 1888;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button abschluss = (Button) findViewById(R.id.btnAbschluss);
        abschluss.setOnClickListener(this);

        //neues Bild aufnehmen:
        imgHa = (ImageView) findViewById(R.id.imgViewHa);
        Button btnBildHa = (Button) findViewById(R.id.btnNewImg);
        btnBildHa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    //Anzeigen des Bildes in der ImageView
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                String filestring = selectedImageUri.getPath();

                Bitmap bm = (Bitmap) data.getExtras().get("data");
                Bitmap bmpPhoto = bm;
                String bmpPhotoPath = filestring;
                imgHa.setImageBitmap(bmpPhoto);

            }
        }
    }


    public void onClick(View v) {

    }
}
