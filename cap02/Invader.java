package cap02;

import java.awt.Color;
import java.awt.Graphics2D;
import java.nio.channels.GatheringByteChannel;

import base.Elemento;

public class Invader extends Elemento {
	enum Tipos {PEQUENO, MEDIO, GRANDE, CHEFE};
	
	private Tipos tipo;
	private boolean aberto;
	
	public Invader(Tipos tipo) {
		this.tipo = tipo;
		setLargura(20);
		setAltura(20);
	}
	
	@Override
	public void atualiza() {
		aberto = !aberto;
	}
	
	@Override
	public void desenha(Graphics2D g2) {
		if (!isAtivo()) return;
		
		int largura = getLargura();
		
		switch (tipo) {
		case PEQUENO:
			largura -= 2;
			g2.setColor(Color.YELLOW);
			if (aberto) {
				g2.drawRect(getX(), getY(), largura, getAltura());
			} else {
				g2.fillRect(getX(), getY(), largura, getAltura());
			}
			break;
		case MEDIO:
			g2.setColor(Color.GREEN);
			if (aberto) {
				g2.drawRect(getX(), getY(), largura, getAltura());
			} else {
				g2.fillRect(getX(), getY(), largura, getAltura());
			}
			break;
		case GRANDE:
			largura += 4;
			g2.setColor(Color.RED);
			if (aberto) {
				g2.drawRect(getX(), getY(), largura, getAltura());
			} else {
				g2.fillRect(getX(), getY(), largura, getAltura());
			}
			break;
		case CHEFE:
			setLargura(72);
			setAltura(24);;
			g2.setColor(Color.BLUE);
			g2.fillOval(getX(), getY(), getLargura(), getAltura());
			if (aberto) {
				g2.fillOval(getX(), getY(), getLargura(), getAltura());
				g2.setColor(Color.WHITE);
				g2.fillRect(getX() + 8, getY() + 8, 8, 8);
				g2.fillRect(getX() + 32, getY() + 8, 8, 8);
				g2.fillRect(getX() + 56, getY() + 8, 8, 8);
			}
		}
	}
	
	public int getPremio() {
		switch (tipo) {
		case PEQUENO:
			return 300;
		case MEDIO:
			return 200;
		case GRANDE:
			return 100;
		default:
			return 1000;
		}
	}	
}
