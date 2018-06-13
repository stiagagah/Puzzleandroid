package com.example.nuris.belajarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by nuris on 13/04/2018.
 */

public class Dashboard extends AppCompatActivity {

    Button proses;
    EditText n1,n2,n3,n4,n5,n6,n7,n8,n9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        n1 = (EditText) findViewById(R.id.nomer1);
        n2 = (EditText) findViewById(R.id.nomer2);
        n3 = (EditText) findViewById(R.id.nomer3);
        n4 = (EditText) findViewById(R.id.nomer4);
        n5 = (EditText) findViewById(R.id.nomer5);
        n6 = (EditText) findViewById(R.id.nomer6);
        n7 = (EditText) findViewById(R.id.nomer7);
        n8 = (EditText) findViewById(R.id.nomer8);
        n9 = (EditText) findViewById(R.id.nomer9);
        proses = (Button) findViewById(R.id.btnproses);
        // proses itu ntuk mengirim nilai 3x3 yakni pada variabel n1 sd n9 ke intent baru GAMEHILL.class

        proses.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Dashboard.this,GamesHill.class);
                mIntent.putExtra("n1", n1.getText().toString());
                mIntent.putExtra("n2", n2.getText().toString());
                mIntent.putExtra("n3", n3.getText().toString());
                mIntent.putExtra("n4", n4.getText().toString());
                mIntent.putExtra("n5", n5.getText().toString());
                mIntent.putExtra("n6", n6.getText().toString());
                mIntent.putExtra("n7", n7.getText().toString());
                mIntent.putExtra("n8", n8.getText().toString());
                mIntent.putExtra("n9", n9.getText().toString());
                startActivity(mIntent);
            }
        });

    }}
