package com.github.avelur.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Servidor extends Thread{
	private final static int MAX_BYTES = 1400;
    private final static String COD_TEXTO = "UTF-8";
    private static int usuarios = 2;
    private static int numPuerto;
    private static Thread[] conexiones = new Thread[usuarios];
    static HashMap<String, String> cuadricula = new HashMap<>();
    static HashSet<String> barquitas = new HashSet<String>();
    
    public static void main(String[] args) throws IOException, InterruptedException {
    	System.out.print("\u25fc");
    	System.out.println("\u25fb");
    	barquitas.add("A3");barquitas.add("B3");barquitas.add("C3");barquitas.add("D3");
    	barquitas.add("A6");barquitas.add("A7");barquitas.add("A8");
    	barquitas.add("F1");barquitas.add("F2");barquitas.add("F3");
    	barquitas.add("C8");barquitas.add("C9");
    	barquitas.add("I5");barquitas.add("J5");
    	barquitas.add("G9");barquitas.add("H9");
    	barquitas.add("E8");
    	barquitas.add("G6");
    	barquitas.add("D5");
    	barquitas.add("B1");
    	
    	printCuadricula(barquitas);
    	
		numPuerto = Integer.parseInt(args[0]);
		for (int i = 0; i < usuarios; i++) {
			conexiones[i] = new Thread();
			conexiones[i].start();
		}
	}

	public void run() {
    	try (DatagramSocket serverSocket = new DatagramSocket(numPuerto)) {
			while (true) {
				 byte[] datosRecibidos = new byte[MAX_BYTES];
				 
			     DatagramPacket paqueteRecibido = new DatagramPacket(
			             datosRecibidos, datosRecibidos.length);
			     serverSocket.receive(paqueteRecibido);
			     InetAddress IPCliente = paqueteRecibido.getAddress();
			     
			     DatagramPacket packetEnviado = 
			    		 new DatagramPacket(datosRecibidos, datosRecibidos.length,
			    				 IPCliente, paqueteRecibido.getPort());
			     serverSocket.connect(IPCliente, paqueteRecibido.getPort());
			     serverSocket.send(packetEnviado);
			     
			     String lineaRecibida = new String(paqueteRecibido.getData(),
			             0, paqueteRecibido.getLength(), COD_TEXTO);
			     
			     int puertoCliente = paqueteRecibido.getPort();
			     System.out.printf("Recibido datagrama de %s:%d (%s)\n",
	                        IPCliente.getHostAddress(), puertoCliente, lineaRecibida);
			     
			     String respuesta = "#" + lineaRecibida + "#";
			     byte[] b = respuesta.getBytes(COD_TEXTO);
			     DatagramPacket paqueteEnviado = new DatagramPacket(
			     		b, b.length, IPCliente, puertoCliente);
			     serverSocket.send(paqueteEnviado);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printCuadricula(HashSet<String> barquitas) {
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
