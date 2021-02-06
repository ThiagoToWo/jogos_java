package base;

import javax.swing.JComponent;
import javax.swing.JPanel;

import cap02.Invader;
import cap02.Tanque;

public class Util {

	public static boolean colidiu(Elemento a, Elemento b) {
		if (!a.isAtivo() || !b.isAtivo()) return false;
		
		// vértice direita superior dos elementos
		final int plA = a.getX() + a.getLargura();
		final int plB = b.getX() + b.getLargura();
		
		// vértice esquerdo inferior dos elementos
		final int paA = a.getY() + a.getAltura();
		final int paB = b.getY() + b.getAltura();
		
		return (plA > b.getX() && a.getX() < plB && paA > b.getY() && a.getY() < paB); 			
	}

	public static boolean colideX(Elemento a, Elemento b) {
		if (!a.isAtivo() || !b.isAtivo()) return false;
		
		// vértice direita superior dos elementos
		final int plA = a.getX() + a.getLargura();
		final int plB = b.getX() + b.getLargura();
		
		return plA >= b.getX() && a.getX() <= plB;
	}
	
	public static void centralizarX(Elemento aoCentro, Elemento base) {
		aoCentro.setX(base.getX() + base.getLargura() / 2 - aoCentro.getLargura() / 2);
	}
	
	public static void centralizarY(Elemento aoCentro, Elemento base) {
		aoCentro.setY(base.getY() + base.getAltura() / 2 - aoCentro.getAltura() / 2);
	}

	public static void centralizarX(Elemento aoCentro, JComponent base) {
		aoCentro.setX(base.getX() + base.getWidth() / 2 - aoCentro.getLargura() / 2);
	}
	
	public static void centralizarY(Elemento aoCentro, JComponent base) {
		aoCentro.setY(base.getY() + base.getWidth() / 2 - aoCentro.getLargura() / 2);
	}
}
