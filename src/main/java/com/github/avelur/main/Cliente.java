package com.github.avelur.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	private final static int MAX_BYTES = 1400;
	private final static String COD_TEXTO = "UTF-8";
	private final static Scanner SCANNER = new Scanner(System.in);
	private static boolean cuadriculaEnviada = false;
    static ArrayList<String> barquitos = new ArrayList<>();
    static ArrayList<String> barquitos2 = new ArrayList<>();
    
    
    
	
	public static void main(String[] args) throws UnknownHostException {
		String nomHost = args[0];
		byte[] datosRecibidos = new byte[MAX_BYTES];
		InetAddress IPServidor = InetAddress.getByName(nomHost);
		System.out.print("\u25fc");
    	System.out.println("\u25fb");
    	
    	
    	Barco AB4 = new Barco(4, new String[]{"A3", "B3", "C3", "D3"});
    	Barco AB3 = new Barco(3, new String[]{"A6", "A7", "A8"});
    	Barco AB31 = new Barco(3, new String[]{"F1", "F2", "F3"});
    	Barco AB21 = new Barco(2, new String[]{"C8", "C9"});
    	Barco AB22 = new Barco(2, new String[]{"I5", "J5"});
    	Barco AB23 = new Barco(2, new String[]{"G9", "H9"});
    	Barco AB1 = new Barco(1, new String[]{"E8"});
    	Barco AB11 = new Barco(1, new String[]{"G6"});
    	Barco AB12 = new Barco(1, new String[]{"D5"});
    	Barco AB13 = new Barco(1, new String[]{"B1"});
    	
    	Barco[] barquitosPlayerA = {
    			AB4, AB3, AB31, AB21, AB22, AB23, AB1, AB11, AB12, AB13
    	};
    	
    	
    	/*
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
    	*/
    	
    	Barco BB4 = new Barco(4, new String[]{"I4", "I5", "I6", "I7"});
    	Barco BB3 = new Barco(3, new String[]{"H10", "I10", "J10"});
    	Barco BB31 = new Barco(3, new String[]{"B3", "B4", "B5"});
    	Barco BB21 = new Barco(2, new String[]{"F3", "G3"});
    	Barco BB22 = new Barco(2, new String[]{"F7", "F8"});
    	Barco BB23 = new Barco(2, new String[]{"A10", "B10"});
    	Barco BB1 = new Barco(1, new String[]{"C8"});
    	Barco BB11 = new Barco(1, new String[]{"H1"});
    	Barco BB12 = new Barco(1, new String[]{"D2"});
    	Barco BB13 = new Barco(1, new String[]{"A1"});
    	
    	Barco[] barquitosPlayerB = {
    			BB4, BB3, BB31, BB21, BB22, BB23, BB1, BB11, BB12, BB13
    	};
    	
    	/*
    	barquitos2.add("I4");barquitos2.add("I5");barquitos2.add("I6");barquitos2.add("I7");
    	barquitos2.add("H10");barquitos2.add("I10");barquitos2.add("J10");
    	barquitos2.add("B3");barquitos2.add("B4");barquitos2.add("B5");
    	barquitos2.add("F3");barquitos2.add("G3");
    	barquitos2.add("F7");barquitos2.add("F8");
    	barquitos2.add("A10");barquitos2.add("B10");
    	barquitos2.add("C8");
    	barquitos2.add("H1");
    	barquitos2.add("D2");
    	barquitos2.add("A1");
    	*/
    	
    	System.out.println("Tu cuadricula: ");
    	if(Integer.parseInt(args[2]) == 1) {
    		ArrayList<String> temporal = new ArrayList<String>();
    		for(int i = 0; i < barquitosPlayerA.length; i++) {
    			temporal.addAll(barquitosPlayerA[i].getPosition());
    		}
    		Util.printCuadricula(temporal);
	    	for(int i = 0; i < barquitosPlayerA.length; i++) {
	    		System.out.println(barquitosPlayerA[i].getPosition());
	    	}
    	} else {
    		ArrayList<String> temporal = new ArrayList<String>();
    		for(int i = 0; i < barquitosPlayerB.length; i++) {
    			temporal.addAll(barquitosPlayerB[i].getPosition());
    		}
    		Util.printCuadricula(temporal);
    	}
    	
    	
		int numPuerto = Integer.parseInt(args[1]);
		System.out.print("Por favor, introducete: ");
		String nombreUsusario = "@user@" + SCANNER.nextLine();
		
		try(DatagramSocket clientSocket = new DatagramSocket();
			InputStreamReader isrStdIn = new InputStreamReader(System.in, COD_TEXTO);
			BufferedReader brStdIn = new BufferedReader(isrStdIn)){
			/*
			DatagramPacket paqueteRecibido = 
					new DatagramPacket(datosRecibidos, 
							datosRecibidos.length);
			clientSocket.receive(paqueteRecibido);
			String lineaRecibida = new String(paqueteRecibido.getData(),
					0, paqueteRecibido.getLength(), COD_TEXTO);
			System.out.println(lineaRecibida);
			*/
			String lineaLeida = "";
			DatagramPacket pasarJugador = new DatagramPacket(nombreUsusario.getBytes(), nombreUsusario.getBytes().length,
					IPServidor, numPuerto);
			clientSocket.connect(IPServidor, numPuerto);
			clientSocket.send(pasarJugador);
			
			
			while((lineaLeida += brStdIn.readLine()) != null && lineaLeida.length() > 0) {
				if(Integer.parseInt(args[2]) == 1 && !cuadriculaEnviada) {
					//Util.enviarCuadricula(clientSocket, barquitos, numPuerto, IPServidor, COD_TEXTO);
					cuadriculaEnviada = true;
				} else {
					//Util.enviarCuadricula(clientSocket, barquitos2, numPuerto, IPServidor, COD_TEXTO);
					cuadriculaEnviada = true;
				}
				
				System.out.print("A que fila disparamos?:>");
			}
		}catch (SocketException ex) {
			System.out.println("Excepcion de sockets");
			ex.printStackTrace();
		}catch (IOException ex) {
			System.out.println("Excepcion de E/S");
			ex.printStackTrace();
		}
	}
}
