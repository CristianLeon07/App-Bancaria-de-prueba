package com.example.wpossprueba;

import static com.example.wpossprueba.LoginActivity.nombreusuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Historial extends AppCompatActivity {

    List<ListElementHistorial> elementos;
    AdminSQLiteOpenHelper  db;
    LisAdapterHistorial.ViewHolderTransaccion v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        init();
    }
    public void init(){
        consultarhistorialtarjetas();
        LisAdapterHistorial listAdapter = new LisAdapterHistorial(elementos, getApplication());
        RecyclerView recyclerView = findViewById(R.id.rv2);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerView.setAdapter(listAdapter);
    }
    public void consultarhistorialtarjetas(){

        elementos= new ArrayList<>();
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tarjetaenvia;
        String tarjetarecibe;
        String valortransaccion;
        String fecha;


        Cursor c = db.rawQuery("SELECT * FROM historial WHERE usuariotarjeta='" + nombreusuario + "'", null);
        if (c.moveToFirst()) {
            do  {tarjetaenvia=c.getString(1) ;
                tarjetarecibe=c.getString(2) ;
                valortransaccion=c.getString(3) ;
                fecha=c.getString(4) ;


                elementos.add(new ListElementHistorial( tarjetaenvia,tarjetarecibe,valortransaccion,fecha));

            } while (c.moveToNext());
            Collections.reverse(elementos);
        }

    }











    public void  irprincipal (View view) {
        Intent registro = new Intent(Historial.this, Principal.class);
        startActivity(registro);
        finish();
    };

    @Override
    public void onBackPressed() {

    }
}