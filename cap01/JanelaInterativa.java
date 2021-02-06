package cap01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JanelaInterativa extends JFrame {
	private JPanel tela;
	private int px;
	private int py;
	private boolean jogando = true;
	private boolean[] controleTecla = new boolean[4];
	
	private final int FPS = 1000 / 20;
	
	public JanelaInterativa() {
		super.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				setTecla(e.getKeyCode(), false);
			}			

			@Override
			public void keyPressed(KeyEvent e) {
				setTecla(e.getKeyCode(), true);				
			}
		});
		
		tela = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int x = tela.getWidth() / 2 - 20 + px;
				int y = tela.getHeight() / 2 - 20 + py;
				g.setColor(Color.BLUE);
				g.fillRect(x, y, 40, 40);
				g.drawString("Agora eu estou em "
				+ x + "x" + y, 5, 10);
			}
		};
		
		add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
	}
	
	private void setTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_ESCAPE:
			jogando = false;
			dispose();
			break;
		case KeyEvent.VK_UP:
			controleTecla[0] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[1] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[2] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[3] = pressionada;
		}
	}
	
	private void atualizaJogo() {
		if (controleTecla[0]) py--;
		else if (controleTecla[1]) py++;
		
		if (controleTecla[2]) px--;
		else if (controleTecla[3]) px++;
	}
	
	public void iniciar() {
		long proxAnimacao = 0;
		while (jogando) {
			if (System.currentTimeMillis() >= proxAnimacao) { // timer
				atualizaJogo();
				tela.repaint();
				proxAnimacao = System.currentTimeMillis() + FPS; // proxima animação daquia fps ms
			}
		}
	}
	
	public static void main(String[] args) {
		new JanelaInterativa().iniciar();
	}
}
