package com.conversorDm;

import com.google.gson.JsonObject;

public class Filtro {

public static void filtrarMonedas(JsonObject rates){
    rates.entrySet().forEach(entry -> {
        System.out.println( "Monedas: "+ entry.getKey()+ "Tasa");
    } );
}


}
