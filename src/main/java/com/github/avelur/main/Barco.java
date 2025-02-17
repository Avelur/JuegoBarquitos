package com.github.avelur.main;

import java.util.ArrayList;

public class Barco {
	private int size;
	private ArrayList<String> position = new ArrayList<>();
	private boolean estado = true;
	
	public Barco(int size, String[] position) {
		super();
		this.size = size;
		for(int i = 0; i < size; i++) {
			this.position.add(position[i]);
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<String> getPosition() {
		return position;
	}

	public void setPosition(ArrayList<String> position) {
		this.position = position;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
