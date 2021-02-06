package base;

import java.awt.Color;
import java.awt.Graphics2D;

public class Elemento {
	private int x;
	private int y;
	private int largura;
	private int altura;
	private int velocidade;
	private boolean ativo;
	private Color cor;
	
	public Elemento() {
		
	}

	public Elemento(int x, int y, int largura, int altura) {
		this.x = x;
		this.y = y;
		this.largura = largura;
		this.altura = altura;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public void atualiza() {};
	
	public void desenha(Graphics2D g2) {
		g2.drawRect(getX(), getY(), getLargura(), getAltura());
	}
	
	public void incX(int dx) {
		x += dx;
	}
	
	public void incY(int dy) {
		y += dy;
	}
}
