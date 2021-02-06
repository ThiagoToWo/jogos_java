package cap01;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JanelaAnimada extends JFrame {

	private JPanel tela;
	private final int FPS = 1000 / 20;
	private int ct = 0;
	private boolean animado = true;
	
	public JanelaAnimada() { // construtor
		/* tela com componentes movidos pela atualização do parâmetro ct
		 * no timer
		 */
		tela = new JPanel() { 
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLUE);
				g.drawLine(0, 240 + ct, 640, 240 + ct);
				g.drawRect(10, 25 + ct, 20, 20);
				g.drawOval(30 + ct, 20, 40, 30);
				g.setColor(Color.YELLOW);
				g.drawLine(320 - ct, 0, 320 - ct, 480);
				g.fillRect(110, 125, 120 - ct, 120 - ct);
				g.fillOval(230, 220, 240 + ct, 230);
				g.setColor(Color.RED);
				g.drawString("Eu seria um ótimo Score! " + ct, 5, 10);
			}
		};
		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
	}
	
	public void iniciaAnimacao() {
		long proxAnimacao = 0;
		while (animado) {
			if (System.currentTimeMillis() >= proxAnimacao) { // timer
				ct++;
				tela.repaint();
				proxAnimacao = System.currentTimeMillis() + FPS; // proxima animação daquia fps ms
				
				if (proxAnimacao == 100) { // condição de parada após 100 milisegundos
					animado = false;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new JanelaAnimada().iniciaAnimacao();
	}

}
