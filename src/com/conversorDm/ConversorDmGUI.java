package com.conversorDm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
        public class ConversorDmGUI {
            private static final String API_URL_TEMPLATE = "https://v6.exchangerate-api.com/v6/8f50d1811c3cd97efcba6597/latest/%s";
            private HistorialConversiones historial = new HistorialConversiones();

            public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new ConversorDmGUI().crearYMostrarGUI();
                    }
                });
            }

            private void crearYMostrarGUI() {
                JFrame frame = new JFrame("Conversor de Monedas");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 400);

                JPanel panel = new JPanel();
                frame.add(panel);
                colocarComponentes(panel);

                frame.setVisible(true);
            }

            private void colocarComponentes(JPanel panel) {
                panel.setLayout(null);

                JLabel etiquetaMonedaOrigen = new JLabel("Moneda Origen:");
                etiquetaMonedaOrigen.setBounds(10, 20, 120, 25);
                panel.add(etiquetaMonedaOrigen);

                JTextField textoMonedaOrigen = new JTextField(20);
                textoMonedaOrigen.setBounds(150, 20, 165, 25);
                panel.add(textoMonedaOrigen);

                JLabel etiquetaMonedaDestino = new JLabel("Moneda Destino:");
                etiquetaMonedaDestino.setBounds(10, 50, 120, 25);
                panel.add(etiquetaMonedaDestino);

                JTextField textoMonedaDestino = new JTextField(20);
                textoMonedaDestino.setBounds(150, 50, 165, 25);
                panel.add(textoMonedaDestino);

                JLabel etiquetaCantidad = new JLabel("Cantidad:");
                etiquetaCantidad.setBounds(10, 80, 120, 25);
                panel.add(etiquetaCantidad);

                JTextField textoCantidad = new JTextField(20);
                textoCantidad.setBounds(150, 80, 165, 25);
                panel.add(textoCantidad);

                JButton botonConvertir = new JButton("Convertir");
                botonConvertir.setBounds(10, 110, 150, 25);
                panel.add(botonConvertir);

                JLabel etiquetaResultado = new JLabel("Cantidad Convertida:");
                etiquetaResultado.setBounds(10, 140, 300, 25);
                panel.add(etiquetaResultado);

                JTextArea areaHistorial = new JTextArea();
                areaHistorial.setBounds(10, 170, 350, 150);
                panel.add(areaHistorial);

                botonConvertir.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String monedaOrigen = textoMonedaOrigen.getText().toUpperCase();
                        String monedaDestino = textoMonedaDestino.getText().toUpperCase();
                        double cantidad = Double.parseDouble(textoCantidad.getText());

                        try {
                            String apiUrl = String.format(API_URL_TEMPLATE, monedaOrigen);
                            String respuesta = HttpClient.get(apiUrl);
                            JsonObject jsonObject = JsonParser.parseString(respuesta).getAsJsonObject();

                            if (jsonObject.has("conversion_rates")) {
                                JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

                                if (conversionRates.has(monedaDestino)) {
                                    double tasaConversion = conversionRates.get(monedaDestino).getAsDouble();
                                    double cantidadConvertida = cantidad * tasaConversion;
                                    etiquetaResultado.setText("Cantidad Convertida: " + cantidadConvertida + " " + monedaDestino);

                                    historial.agregarConversion(monedaOrigen, monedaDestino, cantidad, cantidadConvertida);
                                    areaHistorial.setText(historial.obtenerHistorial());
                                } else {
                                    etiquetaResultado.setText("Código de moneda destino no válido.");
                                }
                            } else {
                                etiquetaResultado.setText("La respuesta de la API no contiene las tasas de cambio.");
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }
