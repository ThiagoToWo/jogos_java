package base;

import java.awt.Font;
import java.awt.Graphics2D;

public class Ponto extends Texto {
	public static final int TAMANHO_FONTE = 60;
	public static final Font FONTE = new Font("Consolas", Font.PLAIN, TAMANHO_FONTE);
	private short ponto;
	
	public Ponto() {
		super.setFonte(FONTE);
	}

	public short getPonto() {
		return ponto;
	}

	public void setPonto(short ponto) {
		this.ponto = ponto;
	}
	
	public void add() {
		ponto++;
	}
	
	public void desenha(Graphics2D g2) {
		super.desenha(g2, Short.toString(ponto), getX(), getY());
	}
}
