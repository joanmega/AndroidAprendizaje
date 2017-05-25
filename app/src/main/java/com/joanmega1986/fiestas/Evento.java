package com.joanmega1986.fiestas;


/**
 * Created by joanm on 26/09/2016.
 */

public class Evento {

    int numEvento;
    String evento;
    String descripcion;
    String lugar;
    String dia;
    String hora;

    public Evento(){

        numEvento = 0;
        evento = "evento";
        descripcion = "descripcion";
        lugar = "lugar";
        dia = "dia";
        hora = "hora";
    }

    public Evento(int unNumEvento, String unEvento, String unadDescripcion, String unLugar, String unDia, String unaHora){
        numEvento = unNumEvento;
        evento = unEvento;
        descripcion = unadDescripcion;
        lugar = unLugar;
        dia = unDia;
        hora = unaHora;
    }

    public int getNumEvento(){
        return numEvento;
    }

    public String getEvento(){
        return evento;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getLugar(){
        return lugar;
    }

    public String getDia(){
        return dia;
    }

    public String getHora(){
        return hora;
    }

    public void setNumEvento(int unNumEvento){
        numEvento = unNumEvento;
    }
    public void setEvento(String unEvento){
        evento = unEvento;
    }
    public void setDescripcion(String unaDescripcion){
        descripcion = unaDescripcion;
    }
    public void setLugar(String unLugar){
        lugar = unLugar;
    }
    public void setDia(String unDia){
        dia = unDia;
    }
    public void setHora(String unaHora){
        hora = unaHora;
    }

    public void imprimirEvento(){
        System.out.println("-------------------------------------------------------");
        System.out.println("Num. Evento: " + getNumEvento());
        System.out.println("Evento: " + getEvento());
        System.out.println("Descripción: " + getDescripcion());
        System.out.println("Lugar: " + getLugar());
        System.out.println("Dia: " + getDia());
        System.out.println("Hora: " + getHora());
        System.out.println("-------------------------------------------------------");
    }
}
