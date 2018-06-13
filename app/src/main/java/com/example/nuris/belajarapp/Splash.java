package com.example.nuris.belajarapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by nuris on 13/04/2018.
 */

public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        /*menjalankan splash screen dan menu menggunakan delayed thread*/
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Intent mainMenu= new Intent(Splash.this,Dashboard.class);
                Splash.this.startActivity(mainMenu);
                Splash.this.finish();
                overridePendingTransition(R.layout.fadein,R.layout.fadeout);
            }
        }, 3000);
    }
}