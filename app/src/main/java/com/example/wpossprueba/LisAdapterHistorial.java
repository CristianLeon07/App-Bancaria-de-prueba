package com.example.wpossprueba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LisAdapterHistorial extends RecyclerView.Adapter<LisAdapterHistorial.ViewHolderTransaccion> {
    private List<ListElementHistorial> mData;
    private LayoutInflater mInflater;
    private Context context;
    AdminSQLiteOpenHelper  db;


    public LisAdapterHistorial(List<ListElementHistorial> itemList, Context context){
        this.mInflater= LayoutInflater.from(context);
        this.context=context;
        this.mData= itemList;
    }

    @NonNull
    @Override
    public ViewHolderTransaccion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= mInflater.inflate(R.layout.listelementhistorial, null);
        return new LisAdapterHistorial.ViewHolderTransaccion(view);
    }
    @Override
    public void onBindViewHolder(final LisAdapterHistorial.ViewHolderTransaccion holder, final int position) {
        holder.bindData(mData.get(position));

    }



    @Override
    public int getItemCount(){ return  mData.size();}





    public void  setItems(List<ListElementHistorial> items){mData=items;}

    public class ViewHolderTransaccion extends  RecyclerView.ViewHolder{

        TextView enviar;
        TextView recibir;
        TextView valor;
        TextView fecha;


        ViewHolderTransaccion(View itemView){
            super(itemView);


            enviar = itemView.findViewById(R.id.tvenvia);
            recibir = itemView.findViewById(R.id.tvrecibe);
            valor= itemView.findViewById(R.id.tvvalorenvio);
            fecha = itemView.findViewById(R.id.tvfechaenvio);


        }
        void  bindData(final ListElementHistorial item){
            enviar.setText(item.getEnvia());
            recibir.setText(item.getRecibe());
            valor.setText(item.getValor());
            fecha.setText(item.getFecha());



        }

    }


}
