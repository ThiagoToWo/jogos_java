package cap01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.SecureRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UmJogo extends JFrame {
	private final int FPS = 1000 / 20;
	
	private JPanel tela;
	private boolean jogando = true;
	private boolean fimDeJogo = false;
	
	private Elemento tiro;
	private Elemento jogador;
	private Elemento[] blocos;
	
	private int pontos;
	private final int larguraPadrao = 50;
	private int linhaLimite = 350;
	private SecureRandom rnd = new SecureRandom();
	
	private boolean[] controleTecla = new boolean[4];
	
	public UmJogo() {
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
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
		
		tiro = new Elemento(0, 0, 1, 0);
		jogador = new Elemento(0, 0, larguraPadrao, larguraPadrao);
		jogador.velocidade = 5;
		
		blocos = new Elemento[5];
		for (int i = 0; i < blocos.length; i++) {
			int espaço = i * larguraPadrao + 10 * (i + 1);
			blocos[i] = new Elemento(espaço, 0, larguraPadrao, larguraPadrao);
			blocos[i].velocidade = 1;
		}
		
		tela = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				// tiro
				g.setColor(Color.RED);
				g.fillRect(tiro.x, tiro.y, tiro.largura, tela.getHeight());
				
				// jogador
				g.setColor(Color.GREEN);
				g.fillRect(jogador.x, jogador.y, jogador.largura, jogador.altura);
				
				// blocos
				g.setColor(Color.BLUE);
				for (Elemento bloco : blocos) {
					g.fillRect(bloco.x, bloco.y, bloco.largura, bloco.altura);
				}
				
				// linha limite
				g.setColor(Color.GRAY);
				g.drawLine(0, linhaLimite, tela.getWidth(), linhaLimite);
				
				// placar
				g.drawString("Pontos: " + pontos, 0, 10);
			}
		};
		
		add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		
		// atualizar posição inicial
		jogador.x = tela.getWidth() / 2 - jogador.largura / 2;
		jogador.y = tela.getHeight() - jogador.altura;
		tiro.altura = tela.getHeight() - jogador.altura;
	}
	
	private void setTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_ESCAPE: // dá e tira da pause
			jogando = !jogando;
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
		if (fimDeJogo) return;
		
		
		if (controleTecla[2]) jogador.x -= jogador.velocidade;
		else if (controleTecla[3]) jogador.x += jogador.velocidade;
		
		// se o jogador sair na esquerda, ele surge na direita
		if (jogador.x < 0) jogador.x = tela.getWidth() - jogador.largura;
		
		// se o jogador sair na direita, ele surge na esquerda
		if (jogador.x + jogador.largura> tela.getWidth()) jogador.x = 0;
		
		// atualiza a centralização do tiro com a posição do jogador
		tiro.y = 0;
		tiro.x = jogador.x + jogador.largura / 2;
		
		for (Elemento bloco : blocos) {
			// se um bloco passar totalmente da linha limite o jogo para de atualizar
			if (bloco.y > linhaLimite) {
				fimDeJogo = true;
				break;
			}
			
			// se o bloco colide com o tiro, ele sobe com o dobro de sua velocidade
			if (colide(bloco, tiro) && bloco.y > 0) {
				bloco.y -= bloco.velocidade * 2;
				tiro.y = bloco.y;
			} else {
				int sorte = rnd.nextInt(10);
				if (sorte == 0) bloco.y += bloco.velocidade + 1;
				else if (sorte == 5) bloco.y -= bloco.velocidade;
				else bloco.y += bloco.velocidade;
			}
			
			pontos += blocos.length;
		}
	}
	
	private boolean colide(Elemento a, Elemento b) {
		return a.x + a.largura >= b.x && a.x <= b.x + b.largura;
	}
	
	private void iniciar() {
		long proxAnimacao = 0;
		while (jogando) {
			if (System.currentTimeMillis() >= proxAnimacao) {
				atualizaJogo();
				tela.repaint();
				proxAnimacao = System.currentTimeMillis() + FPS;
			}
		}
	}
	
	public static void main(String[] args) {
		new UmJogo().iniciar();
	}
	
	class Elemento {
		int x;
		int y;
		int largura;
		int altura;
		float velocidade;
		
		public Elemento(int x, int y, int largura, int altura) {
			this.x = x;
			this.y = y;
			this.altura = altura;
			this.largura = largura;
		}
	}

}
