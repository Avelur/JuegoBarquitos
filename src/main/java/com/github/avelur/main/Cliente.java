package com.github.avelur.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Cliente {
	private final static int MAX_BYTES = 1400;
	private final static String COD_TEXTO = "UTF-8";
	private final static Scanner SCANNER = new Scanner(System.in);
    static HashMap<String, String> cuadricula = new HashMap<>();
    static ArrayList<String> barquitos = new ArrayList<>();
    static ArrayList<String> barquitos2 = new ArrayList<>();
	
	public static void main(String[] args) {
		System.out.print("\u25fc");
    	System.out.println("\u25fb");
    	barquitos.add("A3");barquitos.add("B3");barquitos.add("C3");barquitos.add("D3");
    	barquitos.add("A6");barquitos.add("A7");barquitos.add("A8");
    	barquitos.add("F1");barquitos.add("F2");barquitos.add("F3");
    	barquitos.add("C8");barquitos.add("C9");
    	barquitos.add("I5");barquitos.add("J5");
    	barquitos.add("G9");barquitos.add("H9");
    	barquitos.add("E8");
    	barquitos.add("G6");
    	barquitos.add("D5");
    	barquitos.add("B1");
    	
    	barquitos2.add("I4");barquitos2.add("I5");barquitos2.add("I6");barquitos2.add("I7");
    	barquitos2.add("H10");barquitos2.add("I10");barquitos2.add("J10");
    	barquitos2.add("B3");barquitos2.add("B4");barquitos2.add("B5");
    	barquitos2.add("G3");barquitos2.add("H3");
    	barquitos2.add("F7");barquitos2.add("F8");
    	barquitos2.add("A10");barquitos2.add("B10");
    	barquitos2.add("C8");
    	barquitos2.add("H1");
    	barquitos2.add("D2");
    	barquitos2.add("A1");
    	
    	if(Integer.parseInt(args[2]) == 1) {
    		printCuadricula(barquitos);
    	} else printCuadricula(barquitos2);
    	
		String nomHost = args[0];
		int numPuerto = Integer.parseInt(args[1]);
		System.out.print("Por favor, introducete: ");
		String nombreUsusario = SCANNER.nextLine();
		
		try(DatagramSocket clientSocket = new DatagramSocket();
				InputStreamReader isrStdIn = new InputStreamReader(System.in, 
						COD_TEXTO);
				BufferedReader brStdIn = new BufferedReader(isrStdIn)){
			String lineaLeida = nombreUsusario + ":> ";
			System.out.print("Linea:>");
			
			while((lineaLeida += brStdIn.readLine()) != null
					&& lineaLeida.length() > 0) {
				byte[] datosRecibidos = new byte[MAX_BYTES];
				
				String cuadricula = "";
				InetAddress IPServidor = InetAddress.getByName(nomHost);
				if(Integer.parseInt(args[2]) == 1) {
					for(String s: barquitos) {
						cuadricula += s + ",";
					}
				} else {
					for(String s: barquitos2) {
						cuadricula += s + ",";
					}
				}
				
				byte[] b = cuadricula.getBytes(COD_TEXTO);
				DatagramPacket paqueteEnviado = new DatagramPacket(
						b, b.length, IPServidor, numPuerto);
				clientSocket.connect(IPServidor, numPuerto);
				clientSocket.send(paqueteEnviado);
				
				DatagramPacket paqueteRecibido = 
						new DatagramPacket(datosRecibidos, 
								datosRecibidos.length);
				clientSocket.receive(paqueteRecibido);
				String lineaRecibida = new String(paqueteRecibido.getData(),
						0, paqueteRecibido.getLength(), COD_TEXTO);
				System.out.println(lineaRecibida);
				
				lineaLeida = nombreUsusario + ":> ";
				System.out.print("Linea:>");
			}
		}catch (SocketException ex) {
			System.out.println("Excepcion de sockets");
			ex.printStackTrace();
		}catch (IOException ex) {
			System.out.println("Excepcion de E/S");
			ex.printStackTrace();
		}
	}
	
	public static void printCuadricula(ArrayList<String> barquitas) {
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		System.out.println("Tu cuadricula: ");
		for(int i = 0; i < 10; i++) {
    		for(int j = 0; j < 10; j++) {
    			String quad = abc[i]+(j+1);
    			if(barquitas.contains(quad)) {
    				cuadricula.put(abc[i]+(j+1), "\u25fc");
    			} else cuadricula.put(abc[i]+(j+1), "\u25fb");
    		}
    	}
    	
    	for(int i = 0; i < 10; i++) {
    		if(i == 0) {
    			System.out.print("  ");
    			for(int k = 0; k < 10; k++) {
        	    	System.out.print(k+1 + "|");
            	}
    			System.out.println();
    		}
    		System.out.print(abc[i].toLowerCase() + " ");
    		
    		for(int j = 0; j < 10; j++) {
    			String quad = abc[i]+(j+1);
    			System.out.print(cuadricula.get(quad) + " ");
        	}
    		System.out.println();
    	}
	}
}
