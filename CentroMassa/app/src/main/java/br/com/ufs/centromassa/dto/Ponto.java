package br.com.ufs.centromassa.dto;

import java.io.Serializable;

import android.content.Intent;
import br.com.ufs.centromassa.Jogar;
import br.com.ufs.centromassa.MainActivity;

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
