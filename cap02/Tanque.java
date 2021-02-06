package cap02;

import java.awt.Color;
import java.awt.Graphics2D;

import base.Elemento;

public class Tanque extends Elemento {
	private final int cano = 8;
	private final int escotilha = 10;
	
	public Tanque() {
		setLargura(30);
		setAltura(15);
	}
	
	@Override
	public void atualiza() {}
	
	@Override
	public void desenha(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		g2.fillRect(getX() + getLargura() / 2 - cano / 2, getY() - cano, cano, cano); // cano
		g2.fillRect(getX(), getY(), getLargura(), getAltura());
		g2.setColor(Color.YELLOW);
		g2.fillOval(getX() + getLargura() / 2 - escotilha / 2, 
				getY() + getAltura() / 2 - escotilha / 2, escotilha, escotilha); // escotilha
	}
}
