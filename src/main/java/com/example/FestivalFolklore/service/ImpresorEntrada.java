package com.example.FestivalFolklore.service;

import org.springframework.stereotype.Component;

@Component
public class ImpresorEntrada {
    public void imprimir(String datosEntrada) {
        // Simulación de impresión
        System.out.println("Imprimiendo entrada:");
        System.out.println(datosEntrada);
    }
}