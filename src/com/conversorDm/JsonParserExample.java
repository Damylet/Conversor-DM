package com.conversorDm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonParserExample {

        public static void parseResponse(String jsonString) {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            JsonObject rates = jsonObject.getAsJsonObject("rates");
            double conversionRate = rates.get("EUR").getAsDouble();
            System.out.println("Tasa de Conversión USD a EUR: " + conversionRate);
        }
    }


