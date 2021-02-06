package base;

import java.awt.Font;
import java.awt.Graphics2D;

public class Texto extends Elemento {
	private Font fonte;
	
	public Texto() {
		fonte = new Font("Tahoma", Font.PLAIN, 16);
	}
	
	public Texto(Font fonte) {
		this.fonte = fonte;
	}
	
	public Font getFonte() {
		return fonte;
	}

	public void setFonte(Font fonte) {
		this.fonte = fonte;
	}

	public void desenha(Graphics2D g2, String texto) {
		desenha(g2, texto, getX(), getY());
	}
	
	public void desenha(Graphics2D g2, String texto, int x, int y) {
		if (getCor() != null) g2.setColor(getCor());
		g2.setFont(fonte);
		g2.drawString(texto, x, y);
	}
}
