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
			    InetAddress IPCliente = paqueteRecibido.getAddress();
			    String lineaRecibida = new String(paqueteRecibido.getData(),
			            0, paqueteRecibido.getLength(), COD_TEXTO);
			    
			    String[] cuadriculaString = new String(paqueteRecibido.getData(),
			            0, paqueteRecibido.getLength(), COD_TEXTO).split(",");
			    for(int i = 0; i < cuadriculaString.length; i++) {
			    	barquitas.add(cuadriculaString[i]);
			    }
			    
			    Util.printCuadricula(barquitas, IPCliente);
			     
			    int puertoCliente = paqueteRecibido.getPort();
			    System.out.printf("Recibido datagrama de %s:%d (%s)\n",
	                        IPCliente.getHostAddress(), puertoCliente, lineaRecibida);
			     
			    String respuesta = "#" + lineaRecibida + "#";
			    byte[] b = respuesta.getBytes(COD_TEXTO);
			    DatagramPacket paqueteEnviado = new DatagramPacket(
			    		b, b.length, IPCliente, puertoCliente);
			    serverSocket.send(paqueteEnviado);
			    barquitas.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


class ServerRespuesta implements Runnable{
	private static String COD_TEXTO = "UTF-8";
	private DatagramSocket serverSocket;
	private static int MAX_BYTES;
	private static DatagramPacket paqueteRecibido;
	
	public ServerRespuesta(DatagramSocket serverSocket, int MAX_BYTES, DatagramPacket paqueteRecibido) {
		super();
		this.serverSocket = serverSocket;
		ServerRecibida.MAX_BYTES = MAX_BYTES;
		ServerRecibida.paqueteRecibido = paqueteRecibido;
	}
	
	public void run() {
		try {
				byte[] datosRecibidos = new byte[MAX_BYTES];
				 
			    InetAddress IPCliente = paqueteRecibido.getAddress();
			    
			    DatagramPacket packetEnviado = 
			   		 new DatagramPacket(datosRecibidos, datosRecibidos.length,
			   				 IPCliente, paqueteRecibido.getPort());
			    serverSocket.connect(IPCliente, paqueteRecibido.getPort());
			    
			    serverSocket.send(packetEnviado);
			   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
