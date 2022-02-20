package com.example.wpossprueba;

import static com.example.wpossprueba.LoginActivity.nombreusuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Principal extends AppCompatActivity {

    List<ListElement> elements;
    AdminSQLiteOpenHelper  db;
    Button btnagregarTar;
    TextView mostrarUsuario, mostrarNombre;
     Spinner mSpinner;
    Dialog dialog;
    ListAdapter.ViewHolder v;
    public static String opcion;
    public String CapturaUsuario;
    public static String envio="";
    public  static  String recibir="";
    public  String saldo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mostrarUsuario = (TextView) findViewById(R.id.tvusuario);
        mostrarNombre=(TextView)findViewById(R.id.tvnombreusuario);
        dialog= new Dialog(this);
        opcion="";
        btnagregarTar = (Button) findViewById(R.id.btnaddtarjeta);

        consultarusuariot();
        init();


    }


    public void consultarusuariot(){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT usuario,nombre,correo,contrasena,conficontraseña FROM usuarios WHERE usuario='" + nombreusuario + "'", null);
        if (c.moveToFirst()) {

            do {mostrarUsuario.setText(c.getString(0) );
                mostrarNombre.setText(c.getString(1) );
                CapturaUsuario=c.getString(0);
               // Toast.makeText(this,"La tarjeta ya exite",Toast.LENGTH_SHORT).show();

            } while (c.moveToNext());

        }else {

        }
    }
    public void init(){
        consultartarjetas();
        ListAdapter listAdapter = new ListAdapter(elements, getApplication());
        RecyclerView recyclerView = findViewById(R.id.rv2);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerView.setAdapter(listAdapter);
    }
    public void consultartarjetas(){

        elements= new ArrayList<>();
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tipotarjeta;
        String octetos;
        String fechain;
        String fechafin;
        String saldo;

        Cursor c = db.rawQuery("SELECT tipotarjeta,octetos,fechain ,fechafin,saldo FROM tarjeta WHERE usuariotarjeta='" + nombreusuario + "'", null);
        if (c.moveToFirst()) {
            do  {tipotarjeta=c.getString(0) ;
                octetos=c.getString(1) ;
                fechain=c.getString(2) ;
                fechafin=c.getString(3) ;
                saldo=c.getString(4) ;

                elements.add(new ListElement( tipotarjeta,octetos,fechain,fechafin,saldo));

            } while (c.moveToNext());
        }

    }
    public boolean verificartipotarjeta(String tipotarjetas){
        elements= new ArrayList<>();
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT count(*) FROM tarjeta WHERE tipotarjeta='" + tipotarjetas + "'AND usuariotarjeta='" + CapturaUsuario + "'", null);

        if (c.moveToFirst()) {
            int a=c.getInt(0);
            if (a>=1){
                return false;
            }else
            return true;

        }else {
            return false;
        }

    }
    public void registrarTarjetas(View v) {
        db = new AdminSQLiteOpenHelper(getApplicationContext());
        //sSQLiteDatabase db = this.getWritableDatabase();;
        String octetos=octetos();
        String fechain=fechain();
        String fechafin=fechafin();
        int saldo=0;
        //ssaldo= Integer.parseInt(año);

        final  CharSequence[] opciones = {"Marketing","Vestuario","Cancelar"};

        final AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
        alerta.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Marketing")){
                    if ( verificartipotarjeta("Marketing")) {
                        Boolean verificar = db.registrartarjeta(mostrarUsuario.getText().toString(),"Marketing" ,octetos, fechain, fechafin, "0");
                        if (verificar)
                            Toast.makeText(Principal.this, "Tarjeta registrada", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Principal.this, "No se puede agregar esta tarjeta, ya existe", Toast.LENGTH_SHORT).show();

                    }
                    opcion="Marketing";
                    init();


                }
                else if (opciones[i].equals("Vestuario")){
                    if(verificartipotarjeta("Vestuario")){
                        Boolean verificar = db.registrartarjeta(mostrarUsuario.getText().toString(),"Vestuario" ,octetos, fechain, fechafin, "0");
                    if (verificar)
                       Toast.makeText(Principal.this, "Tarjeta registrada", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Principal.this, "No se puede agregar esta tarjeta, ya existe", Toast.LENGTH_SHORT).show();
                    }
                    opcion="Vestuario";
                    init();
                }else {
                    dialogInterface.cancel();
                }
            }
        });
        alerta.show();
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


    public  void tranferencia(View view) {


       abrirdialogotransferir(view);



    }
    private void  abrirdialogotransferir(View view) {
        View mview=getLayoutInflater().inflate(R.layout.transferencias,null);
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Principal.this);
        Button btnOk = mview.findViewById(R.id.btnenviar);
        EditText et1valor=(EditText) mview.findViewById(R.id.et);
        dialog.setContentView(R.layout.transferencias);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         mSpinner=(Spinner) mview.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Principal.this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.Opciones1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                Toast.makeText(parent.getContext(), "Enviar " + parent.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                envio =  parent.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        final Spinner sspinner=(Spinner) mview.findViewById(R.id.spinner3);
        ArrayAdapter<String> aadapter=new ArrayAdapter<String>(Principal.this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.Opciones2));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sspinner.setAdapter(aadapter);

        sspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                recibir=parent.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mBuilder.setView(mview);
        AlertDialog dialog=mBuilder.create();
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              // mSpinner.getItemAtPosition(0).toString();
                System.out.println(envio);
                System.out.println(recibir);
                saldo=et1valor.getText().toString();
                double valorTranferencia=Double.parseDouble(saldo);
                transferirDinero(valorTranferencia);


            }
        });
    }
    public void transferirDinero(double valorTranferencia){
        try {
            String enviatranferencia=envio;
            String recibetranfernencia=recibir;
            if (enviatranferencia.equals("Opciones..") || recibetranfernencia.equals("Opciones..")) {
                Toast.makeText(Principal.this, "No se puede hacer la transferencia ", Toast.LENGTH_SHORT).show();
                throw new Exception("No se puede hacer esta tranferencia");
            }
            if (enviatranferencia.equals("Marketing") && recibetranfernencia.equals("Marketing")) {
                Toast.makeText(Principal.this, "No se puede hacer la transferencia ", Toast.LENGTH_SHORT).show();
                throw new Exception("No se puede hacer esta tranferencia");
            }
            if (enviatranferencia.equals("Bienes") && recibetranfernencia.equals("Bienes")) {
                Toast.makeText(Principal.this, "No se puede hacer la transferencia ", Toast.LENGTH_SHORT).show();
                throw new Exception("No se puede hacer esta tranferencia");
            }
            verificarsaldo(valorTranferencia,enviatranferencia,recibetranfernencia);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }


    }

    public boolean verificarsaldo(double valorTranferencia,String enviatranferencia,String recibetranfernencia) throws Exception {
        elements = new ArrayList<>();
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        { Cursor c = db.rawQuery("SELECT * FROM tarjeta WHERE tipotarjeta='" +enviatranferencia+ "'AND usuariotarjeta='" + CapturaUsuario + "'", null);
            c.moveToFirst();
            int indexSaldo = c.getColumnIndex("saldo");
            double columSaldo = Double.parseDouble(c.getString(indexSaldo));
            if (columSaldo < valorTranferencia) {
                Toast.makeText(Principal.this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                throw new Exception("Fondo insuficiente");
            }
            double totalRestanteTarjeta = (columSaldo - valorTranferencia) - 2000;
            db.execSQL("UPDATE tarjeta SET saldo='" + totalRestanteTarjeta + "'WHERE usuariotarjeta='" + CapturaUsuario + "'AND  tipotarjeta='" + enviatranferencia + "' ");
            Cursor saldoTarjeta = db.rawQuery("SELECT saldo FROM tarjeta WHERE tipotarjeta='" + recibetranfernencia + "'AND usuariotarjeta='" + CapturaUsuario + "'", null);
            saldoTarjeta.moveToFirst();
            int indexSaldoTranferencia = saldoTarjeta.getColumnIndex("saldo");
            double columnaSaldoTranferencia = Double.parseDouble(saldoTarjeta.getString(indexSaldoTranferencia));
            double saldofinal = valorTranferencia + columnaSaldoTranferencia;

            db.execSQL("UPDATE tarjeta SET saldo='" + saldofinal + "'WHERE usuariotarjeta='" + CapturaUsuario + "'AND  tipotarjeta='" + recibetranfernencia + "' ");

            registrarhistorialtarjeta(CapturaUsuario,enviatranferencia,recibetranfernencia, valorTranferencia);

            init();





        }




        return true;
    }
    public void registrarhistorialtarjeta(String usuario,String tarjetaenvia,String  tarjetarecibe,double valortransaccion)  {
        //creamos un metodo registrarRecargas, donde le pasamos todos los parametros que va a recibir para registrarlos en la tabla.
        //con el getwritabledatabase obtenemos la base de datos en modo escritura
        //creamos un objeto de content values, llamado registrorecarga, que es quien va a retener los datos.
        SimpleDateFormat sdf = new SimpleDateFormat("  HH:mm:ss\ndd/MM/yyyy" );
        String fecha = sdf.format(new Date());

        AdminSQLiteOpenHelper dbHelper= new AdminSQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues registrohistorialtarjeta = new ContentValues();
        registrohistorialtarjeta.put("tarjetaenvia", tarjetaenvia);
        registrohistorialtarjeta.put("tarjetarecibe", tarjetarecibe);
        registrohistorialtarjeta.put("valortransacion", valortransaccion);

        registrohistorialtarjeta.put("fecha", fecha);
        registrohistorialtarjeta.put("usuariotarjeta", usuario);

        //cuando insertamos guardamos en un objeto tipo Long,mandamos la tabla, y null para en caso que el contentvalues este vacio le mande null
        //si el devuelve un numero diferente a -1 retorne falso, si es diferente retorne true
        Long resultado2 = db.insert("historial", null, registrohistorialtarjeta);
        if (resultado2==-1){
            System.out.println("No agregado");;
           // return false;
        }else{
            System.out.println("agregado correctamente");
            //return true;
        }
    }


    public void  irHistorial (View view) {
        Intent registro = new Intent(Principal.this, Historial.class);
        startActivity(registro);
        finish();
    };



}