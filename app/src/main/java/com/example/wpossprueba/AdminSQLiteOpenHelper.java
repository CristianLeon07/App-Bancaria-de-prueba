package com.example.wpossprueba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import static com.example.wpossprueba.IniciarSesion.nombreusuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//creamos una clase abstracta sqliteopenhelper
    public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
        //creamos la base de datos
        public AdminSQLiteOpenHelper(Context context) {
            super(context, "proyecto1.db",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //creamos las tablas, definiendo los atributos de las mismas
            db.execSQL("create table usuarios( usuario text primary key ,nombre text,correo text,contrasena text,conficontraseña text)");
            db.execSQL("create table   tarjeta(id integer primary key autoincrement,tipotarjeta text,octetos text," +
                       " fechain text, fechafin text, saldo text, usuariotarjeta text references usuarios(usuario))");
            db.execSQL("create table opciones( nombre text primary key )");
            db.execSQL("create table   historial(id integer primary key autoincrement,tarjetaenvia text,tarjetarecibe text," +
                    " valortransacion text, fecha text, usuariotarjeta text references usuarios(usuario))");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //borramos las tablas en caso que existan, asi aseguramos estar desistalando
            db.execSQL("DROP TABLE IF EXISTS usuarios");
            db.execSQL("DROP TABLE IF EXISTS tarjeta");
            onCreate(db);
        }
        public boolean registrar(String usuario,String nombre, String correo , String contraseña,String conficontraseña)  {
            //creamos un metodo registrar, donde le pasamos todos los parametros que va a recibir para registrarlos en la tabla.
            //con el getwritabledatabase obtenemos la base de datos en modo escritura
            //creamos un objeto de content values, llamado registro, que es quien va a retener los datos.
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("usuario", usuario);
            registro.put("nombre", nombre);
            registro.put("correo", correo);
            registro.put("contrasena", contraseña);
            registro.put("conficontraseña", conficontraseña);

            //usuario text primary key,correo text,nidentidad text,nombre text,apellidos text,telefono text,contrasena text
            //cuando insertamos guardamos en un objeto tipo Long,mandamos la tabla, y null para en caso que el contentvalues este vacio le mande null
            //si el devuelve un numero igu a -1 retorne falso, si es diferente retorne true
            Long results = db.insert("usuarios", null, registro);
            if (results==-1){
                return false;
            }else
                return true;
        }
        public boolean registrartarjeta(String usuario,String tipotarjeta,String  octetos,String fechain,String fechafin,String saldo)  {
            //creamos un metodo registrarRecargas, donde le pasamos todos los parametros que va a recibir para registrarlos en la tabla.
            //con el getwritabledatabase obtenemos la base de datos en modo escritura
            //creamos un objeto de content values, llamado registrorecarga, que es quien va a retener los datos.
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues registrotarjeta = new ContentValues();
            registrotarjeta.put("tipotarjeta", tipotarjeta);
            registrotarjeta.put("octetos", octetos);
            registrotarjeta.put("fechain", fechain);
            registrotarjeta.put("fechafin", fechafin);
            registrotarjeta.put("saldo", saldo);
            registrotarjeta.put("usuariotarjeta", usuario);

            //cuando insertamos guardamos en un objeto tipo Long,mandamos la tabla, y null para en caso que el contentvalues este vacio le mande null
            //si el devuelve un numero diferente a -1 retorne falso, si es diferente retorne true
            Long resultado2 = db.insert("tarjeta", null, registrotarjeta);
            if (resultado2==-1){
                return false;
            }else{
                return true;
            }
        }

        //para login verificar usuario existente

        public boolean verificarUsuario(String usuario){
            //creamos un metodo verificarUsuario que recibe como parametro el usuario
            //creamos una estrucutra de control cursor para recorrer la base de datos y con el rawquery hacemos la consulta
            //nos aseguramos que existe al menos un reigstro con el > 0
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from usuarios where usuario=?",new String[]{usuario});
            if (cursor.getCount()>0){
                return true;
            }else{
                return false;
            }
        }
        //para login verificar las credenciales
        public boolean verificarCredenciales(String usuario, String contrasena){
            SQLiteDatabase bd = this.getWritableDatabase();
            Cursor cursor = bd.rawQuery("select * from usuarios where usuario=? and contrasena=?",new String[]{usuario,contrasena});
            if (cursor.getCount()>0){
                return true;
            }else{
                return false;
            }
        }

    }


