package com.example.wpossprueba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

 public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
     AdminSQLiteOpenHelper  db;


    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInflater= LayoutInflater.from(context);
        this.context=context;
        this.mData= itemList;
    }
    @Override
    public int getItemCount(){ return  mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void  setItems(List<ListElement> items){mData=items;}

    public class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView tipoTageta;
        TextView octetos;
        TextView fechin;
        TextView fechfin;
        TextView saldo;

        ViewHolder(View itemView){
            super(itemView);

            iconImage = itemView.findViewById(R.id.iconImagenView);
            tipoTageta = itemView.findViewById(R.id.tvtitular);
            octetos = itemView.findViewById(R.id.tvocteto1);
            fechin = itemView.findViewById(R.id.tvfechain);
            fechfin = itemView.findViewById(R.id.tvfechafin);
            saldo = itemView.findViewById(R.id.tvsaldo);


        }
        void  bindData(final ListElement item){
            //iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            tipoTageta.setText(item.getTipoTarjeta());
            octetos.setText(item.getOctetos());
            fechin.setText(item.getFechain());
            fechfin.setText(item.getFechafin());
            saldo.setText(item.getSaldo());


        }

    }
}


