package com.github.avelur.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    		
    			Thread th1 = new Thread(new ServerRecibida(serverSocket, MAX_BYTES, numPuerto, paqueteRecibido));
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
	private int numPuerto;
    static HashMap<String, String> cuadricula = new HashMap<String, String>();
    static HashSet<String> barquitosJugadorA = new HashSet<String>();
    static HashSet<String> barquitosJugadorB = new HashSet<String>();
    private static String[] JUGADORES = new String[2];
	
	public ServerRecibida(DatagramSocket serverSocket, int MAX_BYTES, int numPuerto, DatagramPacket paqueteRecibido) {
		super();
		this.serverSocket = serverSocket;
		ServerRecibida.MAX_BYTES = MAX_BYTES;
		this.numPuerto = numPuerto;
		ServerRecibida.paqueteRecibido = paqueteRecibido;
	}
	
	public void run() {
		try {
			Thread respuestaThread;
		    InetAddress IPCliente = paqueteRecibido.getAddress();
		    String lineaRecibida = new String(paqueteRecibido.getData(),
		            0, paqueteRecibido.getLength(), COD_TEXTO);
			Pattern nombre = Pattern.compile("@user@[a-z1-9]+");
			Matcher matchNombre = nombre.matcher(lineaRecibida);
			
		    if(JUGADORES[0] != null && JUGADORES[1] == null && matchNombre.find()) {
		    	JUGADORES[1] = lineaRecibida;
		    	System.out.println("El jugador " + JUGADORES[1] + " había prestado!");
		    } else if(JUGADORES[0] == null && matchNombre.find()) {
		    	JUGADORES[0] = lineaRecibida;
		    	System.out.println("El jugador " + JUGADORES[0] + " había prestado!");
		    } else {
		    	String incorrectNombre = "Por favor, introducete el nombre correcto!";
		    	respuestaThread = new Thread(new ServerRespuesta(serverSocket, incorrectNombre, numPuerto, IPCliente, "UTF-8"));
		    	respuestaThread.start();
		    }
		    
		    if(JUGADORES[0] != null && JUGADORES[1] != null) {			    
			    String[] cuadriculaString = new String(paqueteRecibido.getData(),
			            0, paqueteRecibido.getLength(), COD_TEXTO).split(",");
			    for(int i = 0; i < cuadriculaString.length; i++) {
			    	barquitosJugadorA.add(cuadriculaString[i]);
			    }
		    	Util.printCuadricula(barquitosJugadorA, IPCliente);
		    }
		     
		    int puertoCliente = paqueteRecibido.getPort();
		    System.out.printf("Recibido datagrama de %s:%d (%s)\n",
                       IPCliente.getHostAddress(), puertoCliente, lineaRecibida);
		     
		    String respuesta = "#" + lineaRecibida + "#";
		    byte[] b = respuesta.getBytes(COD_TEXTO);
		    DatagramPacket paqueteEnviado = new DatagramPacket(
		    		b, b.length, IPCliente, puertoCliente);
		    serverSocket.send(paqueteEnviado);
		    barquitosJugadorA.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ServerRespuesta implements Runnable{
	private DatagramSocket serverSocket;
	private Object datos;
	private int numPuerto;
	private InetAddress IP;
	private String COD_TEXTO = "UTF-8";
	
	
	
	public ServerRespuesta(DatagramSocket serverSocket, Object datos, int numPuerto, InetAddress IP,
			String COD_TEXTO) {
		super();
		this.serverSocket = serverSocket;
		this.datos = datos;
		this.numPuerto = numPuerto;
		this.IP = IP;
		this.COD_TEXTO = COD_TEXTO;
	}

	public void run() {
		try {
			String dato = datos.toString();
			Util.enviarMensaje(serverSocket, dato, numPuerto, IP, COD_TEXTO);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
