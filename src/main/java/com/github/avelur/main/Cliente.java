package com.github.avelur.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {
	private final static int MAX_BYTES = 1400;
	private final static String COD_TEXTO = "UTF-8";
	private final static Scanner SCANNER = new Scanner(System.in);
	
	public static void main(String[] args) {
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
				InetAddress IPServidor = InetAddress.getByName(nomHost);
				byte[] b = lineaLeida.getBytes(COD_TEXTO);
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
}
