package com.example.wpossprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText usuarioo,contraseña;
    //se crea una variable del tipo de la clase AdminSQLiteOpenHelper
    AdminSQLiteOpenHelper db;
    //se crea una variable statica donde capturaremos el nombre del usuario y poder llevarlo a mostrarlo al inicio
    public static  String nombreusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //enlazamos las variables
        usuarioo = findViewById(R.id.et5);
        contraseña = findViewById(R.id.et6);

    }
    public void ingresar(View view){
        //creamos un metodo ingresar, que se ejecuta con el boton que tiene el onclick ingresar
        db = new AdminSQLiteOpenHelper(this);
        String usuario= usuarioo.getText().toString();
        String contrasena= contraseña.getText().toString();
        if (usuario.equals("") || contrasena.equals("")) {
            Toast.makeText(LoginActivity.this, "ERROR: ingrese los campos", Toast.LENGTH_SHORT).show();
        }else{
            Boolean verificaup = db.verificarCredenciales(usuario,contrasena);
            if (verificaup==true){
                nombreusuario=usuario;
                Toast.makeText(LoginActivity.this, "Ingreso Exitoso!!", Toast.LENGTH_SHORT).show();
                Intent intentingresar= new Intent(LoginActivity.this,Principal.class);
                startActivity(intentingresar);
                usuarioo.setText("");
                contraseña.setText("");
            }else{
                Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void irAtras(View v){
        Intent irregistrar = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(irregistrar);
        finish();

    }
}