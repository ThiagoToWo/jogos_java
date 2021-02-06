package cap03;

import java.awt.Color;

import base.Elemento;

public class Bola extends Elemento {
	private static final int VELOCIDADE_INICIAL = 3;
	private int dirX = -1;
	private int dirY = -1;
	private float velX;
	private float velY;
	
	public Bola() {
		velX = velY = VELOCIDADE_INICIAL;
		setLargura(10);
		setAltura(10);
		setCor(Color.WHITE);
	}
	
	public void setVelocidade(float vel) {
		velX = velY = vel;
	}
	
	public int getVelocidade() {
		return (int) velX;
	}
	
	public void incX() {
		incX((int) velX * dirX);		
	}
	
	public void incY() {
		incY((int) velY * dirY);		
	}
	
	public void inverteX() {
		dirX *= -1;
	}
	
	public void inverteY() {
		dirY *= -1;
	}
}
