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
    	barquitos2.add("F3");barquitos2.add("G3");
    	barquitos2.add("F7");barquitos2.add("F8");
    	barquitos2.add("A10");barquitos2.add("B10");
    	barquitos2.add("C8");
    	barquitos2.add("H1");
    	barquitos2.add("D2");
    	barquitos2.add("A1");
    	
    	if(Integer.parseInt(args[2]) == 1) {
    		Util.printCuadricula(barquitos);
    	} else Util.printCuadricula(barquitos2);
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
					Util.enviarCuadricula(clientSocket, barquitos, numPuerto, IPServidor, COD_TEXTO);
					cuadriculaEnviada = true;
				} else {
					Util.enviarCuadricula(clientSocket, barquitos2, numPuerto, IPServidor, COD_TEXTO);
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
