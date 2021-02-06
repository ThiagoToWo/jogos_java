package base;

import java.awt.Graphics2D;

public abstract class Cenario {
	protected int largura;
	protected int altura;
	
	public Cenario(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
	}
	
	public abstract void carregar();
	public abstract void descarragar();
	public abstract void atualizar();
	public abstract void desenhar(Graphics2D g);
}
