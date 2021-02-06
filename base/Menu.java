package base;

import java.awt.Font;
import java.awt.Graphics2D;

public class Menu extends Texto {
	
	private short idx;
	private String rotulo;
	private String[] opcoes;
	private boolean selecionado;

	public Menu(String rotulo) {
		super();
		this.rotulo = rotulo;
		setAltura(120);
		setLargura(20);
	}

	public Menu(String rotulo, Font fonte) {
		super(fonte);
		this.rotulo = rotulo;
		setAltura(120);
		setLargura(20);
	}
	
	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public void addOpcoes(String ...opcoes) {
		this.opcoes = opcoes;
	}
	
	@Override
	public void desenha(Graphics2D g2) {
		if (opcoes == null) return;
		
		g2.setColor(getCor());
		super.desenha(g2, String.format("%s : <%s>", getRotulo(), opcoes[idx]),
				getX(), getY() + getAltura());
		
		if (isSelecionado()) {
			g2.drawLine(getX(), getY() + getAltura() + 5, getX() + getLargura(), 
					getY() + getAltura() + 5);
		}
	}
	
	public void trocaOpcao(boolean esquerda) {
		if (!isSelecionado() || !isAtivo()) return;
		
		idx += esquerda ? -1 :1;
		
		if (idx < 0) {
			idx = (short) (opcoes.length - 1);
		} else if (idx == opcoes.length) {
			idx = 0;
		}
	}
}
