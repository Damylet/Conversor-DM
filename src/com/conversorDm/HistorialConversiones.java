package com.conversorDm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistorialConversiones {

        private final List<String> historial = new ArrayList<>();

    public void agregarConversion(String deMoneda, String aMoneda, double cantidad, double resultado) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String marcaTiempo = ahora.format(formatter);
        historial.add("De " + deMoneda + " a " + aMoneda + ": " + cantidad + " -> " + resultado + " en " + marcaTiempo);
    }

    public void mostrarHistorial() {
        for (String registro : historial) {
            System.out.println(registro);
        }
    }

    public String obtenerHistorial() {
        StringBuilder historialString = new StringBuilder();
        for (String registro : historial) {
            historialString.append(registro).append("\\n");
        }
        return historialString.toString();
    }


}

