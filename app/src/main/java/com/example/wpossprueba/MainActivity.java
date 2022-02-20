package com.example.wpossprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void  registro (View view) {
        Intent registro = new Intent(MainActivity.this, RegistroUsuario.class);
        startActivity(registro);
        finish();
    };
    public void  iniciarSesion (View view) {
        Intent registro = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(registro);
        finish();
    };
}