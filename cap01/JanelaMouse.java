package cap01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JanelaMouse extends JFrame {
	private JPanel tela;
	private int px;
	private int py;
	private Point mouseClick = new Point();
	private boolean jogando;
	
	private final int FPS = 1000 / 20;	
	
	public JanelaMouse() {
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
		
		tela.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClick = e.getPoint();
				System.out.println(mouseClick);
			}
		});
		
		super.getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
	}
	
	private void atualizaJogo() {
		px = (int) mouseClick.getX();
		py = (int) mouseClick.getY();
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
		new JanelaMouse().iniciar();
	}

}
