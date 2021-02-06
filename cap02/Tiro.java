package cap02;

import java.awt.Color;
import java.awt.Graphics2D;

import base.Elemento;

public class Tiro extends Elemento {
	private boolean inimigo;
	
	public Tiro() {
		setAltura(5);
		setLargura(5);
	}
	
	public Tiro(boolean inimigo) {
		this();
		this.inimigo = inimigo;
	}
	
	@Override
	public void atualiza() {}
	
	@Override
	public void desenha(Graphics2D g2) {
		if (!isAtivo()) return;
		
		g2.setColor(inimigo ? Color.RED : Color.WHITE);
		g2.fillRect(getX(), getY(), getLargura(), getAltura());
	}
}
