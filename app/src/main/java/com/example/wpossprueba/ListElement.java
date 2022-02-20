package com.example.wpossprueba;

public class ListElement {
    String  tipoTarjeta, octetos, fechain, fechafin, saldo;

    public ListElement(String s, String vienes, String s1, String s2, String s3, String s4) {

    }

    public ListElement(String tipoTarjeta, String octetos, String fechain, String fechafin, String saldo) {
        this.tipoTarjeta = tipoTarjeta;
        this.octetos = octetos;
        this.fechain = fechain;
        this.fechafin = fechafin;
        this.saldo = saldo;
    }

    public String getOctetos() {
        return octetos;
    }

    public void setOctetos(String octetos) {
        this.octetos = octetos;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }


    public String getFechain() {
        return fechain;
    }

    public void setFechain(String fechain) {
        this.fechain = fechain;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
