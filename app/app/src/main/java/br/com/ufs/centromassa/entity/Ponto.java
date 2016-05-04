package br.com.ufs.centromassa.entity;

import java.io.Serializable;

public class Ponto implements Serializable {

	public Integer x;
	public Integer y;

	public Ponto() {
		super();
	}
	
	public Ponto(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
	
	public boolean isEmpty(){
		return x == null;
	}

}
