package com.example.wpossprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroUsuario extends AppCompatActivity {
    private EditText Regusuario,Regnombre,Regcorreo,Regcontraseña,Regconficontraseña;
    private Button Registrar,Cancelar;

    //creamos una variable de tipo de la clase AdminHelper y a su ves de la clase Register.
    AdminSQLiteOpenHelper  db;
    RegistroUsuario objValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        Regusuario=  findViewById(R.id.et1);
        Regnombre =  findViewById(R.id.et2);
        Regcorreo = findViewById(R.id.et3);
        Regcontraseña=findViewById(R.id.et4);
        Regconficontraseña = findViewById(R.id.et5);
        Registrar =  findViewById(R.id.btnregistrar);
        Cancelar =  findViewById(R.id.btncancelar);
        objValidar = new RegistroUsuario();
    }
    //metodo para validar el correo electronico, y recibe la cadena del correo.
    public  boolean isEmail(String cadena) {
        boolean resultado;
        if (Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            resultado = true;
        } else {
            resultado = false;
        }

        return resultado;
    }
    //metodo para registrar los datos del usuario
    public void registrar(View v) throws Exception {
        db = new AdminSQLiteOpenHelper(this);

        String regusuario = Regusuario.getText().toString().trim();
        String regnombre = Regnombre.getText().toString().trim();
        String regcorreo = Regcorreo.getText().toString().trim();
        String regcontrasena = Regcontraseña.getText().toString().trim();
        String regconficontraseña = Regconficontraseña.getText().toString();

       try {
           Pattern patternName = Pattern.compile("[a-z A-Z]{1,30}");
           Matcher matcherName = patternName.matcher(regnombre);
           boolean nombreRegex = matcherName.matches(); // Devuelve true si el textToSearch coincide exactamente con la expresión regular
           if (!nombreRegex) {
               Toast.makeText(this, "ERROR: El campo nombre solo acepta Letras", Toast.LENGTH_SHORT).show();
               throw new Exception("No se puede guardar este nombre");
           }
           if (!nombreRegex) {
               Toast.makeText(this, "ERROR: El campo nombre solo acepta Letras", Toast.LENGTH_SHORT).show();


               throw new Exception("No se puede guardar este nombre");

           }
           if (regusuario.equals("") || regnombre.equals("") || regcorreo.equals("") || regcontrasena.equals("") || regconficontraseña.equals("")) {
               Toast.makeText(this, "ERROR: Campos vacios, por favor ingrese los datos", Toast.LENGTH_SHORT).show();
               return;
           } else {
               Boolean verificar = db.verificarUsuario(regusuario);
               if (objValidar.isEmail(Regcorreo.getText().toString())) {
                   if (Regcontraseña.length() >= 5) {
                       if (regcontrasena.equals(regconficontraseña)) {
                           if (verificar == false) {
                               Boolean insert = db.registrar(regusuario, regnombre, regcorreo, regcontrasena, regconficontraseña);

                               if (insert == true) {
                                   Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                   Regusuario.setText("");
                                   Regnombre.setText("");
                                   Regcorreo.setText("");
                                   Regcontraseña.setText("");
                                   Regconficontraseña.setText("");
                                   registrartarjeta(regusuario);
                                   Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                   startActivity(intent);
                                   finish();
                               } else {
                                   Toast.makeText(RegistroUsuario.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                               }
                           } else {
                               Toast.makeText(RegistroUsuario.this, "Usuario Existente", Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                       }
                   } else {
                       Toast.makeText(this, "ERROR: Ingrese una contraseña mas larga", Toast.LENGTH_SHORT).show();
                   }
               } else {
                   Toast.makeText(this, "Ingrese un Correo valido", Toast.LENGTH_SHORT).show();
               }
           }
       }catch (Exception e){
            System.err.println(e.getMessage());
        }




    }
    public void  registrartarjeta(String usuario){
        db = new AdminSQLiteOpenHelper(this);

        String octetos=octetos();
        String fechain=fechain();
        String fechafin=fechafin();
        String tipotarjeta="Bienes";
        Boolean insert = db.registrartarjeta(usuario,tipotarjeta,octetos,fechain,fechafin,"1000000");


    }
    public String octetos(){
        Random random = new Random();

        String octetos;

        int valorEntero = (int) Math.floor(Math.random()*(9999-1000+1)+1000);
        int valorEntero1 = (int) Math.floor(Math.random()*(9999-1000+1)+1000);
        int valorEntero2 = (int) Math.floor(Math.random()*(9999-1000+1)+1000);
        int valorEntero3 = (int) Math.floor(Math.random()*(9999-1000+1)+1000);


        octetos = valorEntero + "  " + valorEntero1 + "  " + valorEntero2 + "  " + valorEntero3;

        return octetos;

    }
    private String fechain() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String fechafin() {
        int año;
        String añoStirng;
        DateFormat dateFormat = new SimpleDateFormat("yy");
        Date date = new Date();

         año= Integer.parseInt(dateFormat.format(date));
         año=año+5;
         añoStirng=String.valueOf(año);

         return añoStirng;
    }


    //metodo para cancelar el registro e ir a la zona de login
    public void cancelarRegi(View view){
        Intent  i3 = new Intent(RegistroUsuario.this,MainActivity.class);
        startActivity(i3);
        finish();
    }
}
