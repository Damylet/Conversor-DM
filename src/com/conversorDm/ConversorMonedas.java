package com.conversorDm;

import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class ConversorMonedas {


        private static final String API_URL_TEMPLATE = "https://v6.exchangerate-api.com/v6/8f50d1811c3cd97efcba6597/latest/%s";
        private static HistorialConversiones historial = new HistorialConversiones();

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Conversor de Monedas ---");
                System.out.println("1. Convertir moneda");
                System.out.println("2. Ver historial de conversiones");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        convertirMoneda(scanner);
                        break;
                    case 2:
                        historial.mostrarHistorial();
                        break;
                    case 3:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción no válida, intenta de nuevo.");
                }
            }
        }

        private static void convertirMoneda(Scanner scanner) {
            System.out.print("Introduce la moneda origen (ej. USD): ");
            String monedaOrigen = scanner.next().toUpperCase();

            System.out.print("Introduce la moneda destino (ej. EUR): ");
            String monedaDestino = scanner.next().toUpperCase();

            System.out.print("Introduce la cantidad en " + monedaOrigen + ": ");
            double cantidad = scanner.nextDouble();

            try {
                String apiUrl = String.format(API_URL_TEMPLATE, monedaOrigen);
                String respuesta = HttpClient.get(apiUrl);
                JsonObject jsonObject = JsonParser.parseString(respuesta).getAsJsonObject();

                if (jsonObject.has("conversion_rates")) {
                    JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

                    if (conversionRates.has(monedaDestino)) {
                        double tasaConversion = conversionRates.get(monedaDestino).getAsDouble();
                        double cantidadConvertida = cantidad * tasaConversion;
                        System.out.printf("Cantidad Convertida: %.2f %s\n", cantidadConvertida, monedaDestino);

                        historial.agregarConversion(monedaOrigen, monedaDestino, cantidad, cantidadConvertida);
                    } else {
                        System.out.println("Código de moneda destino no válido.");
                    }
                } else {
                    System.out.println("La respuesta de la API no contiene las tasas de cambio.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




