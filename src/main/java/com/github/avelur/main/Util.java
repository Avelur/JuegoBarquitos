package com.github.avelur.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Util {

	static HashMap<String, String> cuadricula = new HashMap<>();
	
	public static void printCuadricula(HashSet<String> barquitos, InetAddress IPCliente) {
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		System.out.println("Cuadricula de: " + IPCliente.getHostAddress() + ":" + IPCliente.getAddress());
		for(int i = 0; i < 10; i++) {
    		for(int j = 0; j < 10; j++) {
    			String quad = abc[i]+(j+1);
    			String destroyed = abc[i]+(j+1) + "X";
    			if(barquitos.contains(quad)) {
    				cuadricula.put(abc[i]+(j+1), "\u25fc");
    			} else if (barquitos.contains(destroyed)) {
    				cuadricula.put(abc[i]+(j+1), "X");
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
	
	public static void printCuadricula(ArrayList<String> barquitos) {
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		System.out.println("Tu cuadricula: ");
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
    			String quad = abc[i]+(j+1);
    			String destroyed = abc[i]+(j+1) + "X";
    			if(barquitos.contains(quad)) {
    				cuadricula.put(abc[i]+(j+1), "\u25fc");
    			} else if (barquitos.contains(destroyed)) {
    				cuadricula.put(abc[i]+(j+1), "X");
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
	
	public static void disparar(ArrayList<String> barquitos, String fila) {
		/*
		Pattern pattern = Pattern.compile("\\w\\d");
		Matcher matcher = pattern.matcher(fila);
		*/
		for(int i = 0; i < barquitos.size(); i++) {
			if(barquitos.get(i).contains(fila)) {
				barquitos.set(i, fila + "X");
			} else if(!barquitos.get(i).contains(fila + "X")) barquitos.add(fila + "X");
    	} 
	}
	
	public static void enviarCuadricula(DatagramSocket Socket, ArrayList<String> barquitos, int numPuerto, 
			InetAddress Ip, String COD_TEXTO) throws IOException {
		String cuadricula = "";
		for(String s: barquitos) {
			cuadricula += s + ",";
		}
		byte[] b = cuadricula.getBytes(COD_TEXTO);
		DatagramPacket paqueteEnviado = new DatagramPacket(
				b, b.length, Ip, numPuerto);
		Socket.connect(Ip, numPuerto);
		Socket.send(paqueteEnviado);
	}
	
	public static void enviarMensaje(DatagramSocket Socket, String obj, int numPuerto, 
			InetAddress Ip, String COD_TEXTO) throws IOException {
		byte[] b = obj.getBytes(COD_TEXTO);
		DatagramPacket paqueteEnviado = new DatagramPacket(
				b, b.length, Ip, numPuerto);
		Socket.connect(Ip, numPuerto);
		Socket.send(paqueteEnviado);
	}
}
