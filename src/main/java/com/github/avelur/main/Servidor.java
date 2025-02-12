package com.github.avelur.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;

public class Servidor extends Thread{
	private static int MAX_BYTES = 1400;
    private static int numPuerto;
    
    public static void main(String[] args) throws IOException, InterruptedException {
    	numPuerto = Integer.parseInt(args[0]);
    	byte[] datosRecibidos = new byte[MAX_BYTES];
    	try (DatagramSocket serverSocket = new DatagramSocket(numPuerto)) {
    		while(true) {
    			DatagramPacket paqueteRecibido = new DatagramPacket(
		             datosRecibidos, datosRecibidos.length);
    			serverSocket.receive(paqueteRecibido);
    		
    			Thread th1 = new Thread(new ServerRecibida(serverSocket, MAX_BYTES, paqueteRecibido));
    			th1.start();
    		}
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ServerRecibida implements Runnable{
	private static String COD_TEXTO = "UTF-8";
	private DatagramSocket serverSocket;
	static int MAX_BYTES;
	static DatagramPacket paqueteRecibido;
    static HashMap<String, String> cuadricula = new HashMap<String, String>();
    static HashSet<String> barquitas = new HashSet<String>();
	
	public ServerRecibida(DatagramSocket serverSocket, int MAX_BYTES, DatagramPacket paqueteRecibido) {
		super();
		this.serverSocket = serverSocket;
		ServerRecibida.MAX_BYTES = MAX_BYTES;
		ServerRecibida.paqueteRecibido = paqueteRecibido;
	}
	
	public void run() {
		try {
			System.out.println(1);
				byte[] datosRecibidos = new byte[MAX_BYTES];
				 
			    InetAddress IPCliente = paqueteRecibido.getAddress();
			    System.out.println(2);
			    DatagramPacket packetEnviado = 
			   		 new DatagramPacket(datosRecibidos, datosRecibidos.length,
			   				 IPCliente, paqueteRecibido.getPort());
			    //serverSocket.connect(IPCliente, paqueteRecibido.getPort());
			    System.out.println(3);
			    //serverSocket.send(packetEnviado);
			    String lineaRecibida = new String(paqueteRecibido.getData(),
			            0, paqueteRecibido.getLength(), COD_TEXTO);
			    System.out.println(4);
			    String[] cuadriculaString = new String(paqueteRecibido.getData(),
			            0, paqueteRecibido.getLength(), COD_TEXTO).split(",");
			    for(int i = 0; i < cuadriculaString.length; i++) {
			    	barquitas.add(cuadriculaString[i]);
			    }
			    System.out.println(5);
			    printCuadricula(barquitas, IPCliente);
			     
			    int puertoCliente = paqueteRecibido.getPort();
			    System.out.printf("Recibido datagrama de %s:%d (%s)\n",
	                        IPCliente.getHostAddress(), puertoCliente, lineaRecibida);
			     
			    String respuesta = "#" + lineaRecibida + "#";
			    byte[] b = respuesta.getBytes(COD_TEXTO);
			    DatagramPacket paqueteEnviado = new DatagramPacket(
			    		b, b.length, IPCliente, puertoCliente);
			    serverSocket.send(paqueteEnviado);
			    barquitas.clear();
			//while (true) {}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printCuadricula(HashSet<String> barquitas, InetAddress IPCliente) {
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		System.out.println("Cuadricula de: " + IPCliente.getHostAddress() + ":" + IPCliente.getAddress());
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


class ServerRespuesta implements Runnable{
	private static String COD_TEXTO = "UTF-8";
	private DatagramSocket serverSocket;
	private static int MAX_BYTES;
	private static DatagramPacket paqueteRecibido;
    static HashMap<String, String> cuadricula = new HashMap<String, String>();
    static HashSet<String> barquitas = new HashSet<String>();
	
	public ServerRespuesta(DatagramSocket serverSocket, int MAX_BYTES, DatagramPacket paqueteRecibido) {
		super();
		this.serverSocket = serverSocket;
		ServerRecibida.MAX_BYTES = MAX_BYTES;
		ServerRecibida.paqueteRecibido = paqueteRecibido;
	}
	
	public void run() {
		try {
			System.out.println(1);
				byte[] datosRecibidos = new byte[MAX_BYTES];
				 
			    InetAddress IPCliente = paqueteRecibido.getAddress();
			    System.out.println(2);
			    DatagramPacket packetEnviado = 
			   		 new DatagramPacket(datosRecibidos, datosRecibidos.length,
			   				 IPCliente, paqueteRecibido.getPort());
			    //serverSocket.connect(IPCliente, paqueteRecibido.getPort());
			    System.out.println(3);
			    //serverSocket.send(packetEnviado);
			    String lineaRecibida = new String(paqueteRecibido.getData(),
			            0, paqueteRecibido.getLength(), COD_TEXTO);
			    System.out.println(4);
			    String[] cuadriculaString = new String(paqueteRecibido.getData(),
			            0, paqueteRecibido.getLength(), COD_TEXTO).split(",");
			    for(int i = 0; i < cuadriculaString.length; i++) {
			    	barquitas.add(cuadriculaString[i]);
			    }
			    System.out.println(5);
			    printCuadricula(barquitas, IPCliente);
			     
			    int puertoCliente = paqueteRecibido.getPort();
			    System.out.printf("Recibido datagrama de %s:%d (%s)\n",
	                        IPCliente.getHostAddress(), puertoCliente, lineaRecibida);
			     
			    String respuesta = "#" + lineaRecibida + "#";
			    byte[] b = respuesta.getBytes(COD_TEXTO);
			    DatagramPacket paqueteEnviado = new DatagramPacket(
			    		b, b.length, IPCliente, puertoCliente);
			    serverSocket.send(paqueteEnviado);
			    barquitas.clear();
			//while (true) {}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printCuadricula(HashSet<String> barquitas, InetAddress IPCliente) {
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		System.out.println("Cuadricula de: " + IPCliente.getHostAddress() + ":" + IPCliente.getAddress());
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
