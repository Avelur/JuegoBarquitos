package com.github.avelur.main;

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
    			if(barquitos.contains(quad)) {
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
	
	public static void printCuadricula(ArrayList<String> barquitos) {
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		System.out.println("Tu cuadricula: ");
		for(int i = 0; i < 10; i++) {
    		for(int j = 0; j < 10; j++) {
    			String quad = abc[i]+(j+1);
    			if(barquitos.contains(quad)) {
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
