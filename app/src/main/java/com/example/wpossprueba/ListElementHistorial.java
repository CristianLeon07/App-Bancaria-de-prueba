package com.example.wpossprueba;

public class ListElementHistorial {

    String envia, recibe, valor, fecha;


    public ListElementHistorial(String s, String vienes, String s1, String s2, String s3, String s4) {

    }

    public ListElementHistorial(String envia, String recibe, String valor, String fecha) {
        this.envia = envia;
        this.recibe = recibe;
        this.valor = valor;
        this.fecha = fecha;

    }

    public String getEnvia() {
        return envia;
    }

    public void setEnvia(String envia) {
        this.envia = envia;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }


    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}

