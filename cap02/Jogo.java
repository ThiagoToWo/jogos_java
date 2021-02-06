package cap02;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;

import base.Elemento;
import base.Texto;
import base.Util;
import cap02.Invader;
import cap02.Tanque;
import cap02.Tiro;
import cap02.Invader.Tipos;

public class Jogo extends JFrame {
	private JPanel tela;
	private final int LARGURA_DA_TELA = 480;
	private final int ALTURA_DA_TELA = 640;
	private boolean jogando = true;
	private final int FPS = 1000 / 20;
	private BufferedImage buffer;
	private Invader[][] invasores = new Invader[11][5];
	private Invader.Tipos[] tiposPorLinha = {Tipos.GRANDE, Tipos.GRANDE, Tipos.MEDIO, Tipos.MEDIO, Tipos.PEQUENO};
	private Tanque tanque;
	private Elemento vida = new Tanque();
	private Tiro tiroTanque;
	private Invader chefe;
	private Tiro tiroChefe;
	private boolean[] controleTeclas = new boolean[5];
	private int espacamento = 15;
	private int linhaBase = 60;
	private int contador;
	private int contadorEspera;
	private boolean moverInimigos;
	private int totalInimigos;
	private int destruidos;
	private int level = 1;
	private int pontos;
	private boolean novaLinha;
	private int dir;
	private Tiro[] tiros = new Tiro[3];
	private SecureRandom rnd = new SecureRandom();
	private int vidas = 5;
	private Graphics2D g2;
	private Texto texto = new Texto();
		
	public Jogo() {
		setTitle("INVASORES ESPACIAIS");
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				setTeclas(e.getKeyCode(), false);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				setTeclas(e.getKeyCode(), true);
			}
		});
		
		buffer = new BufferedImage(LARGURA_DA_TELA, ALTURA_DA_TELA, BufferedImage.TYPE_INT_RGB);
		g2 = buffer.createGraphics();
		
		tela = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null);
			}
		};
		
		getContentPane().add(tela);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(LARGURA_DA_TELA, ALTURA_DA_TELA);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected void setTeclas(int tecla, boolean pressionada) {
		switch(tecla) {
		case KeyEvent.VK_ESCAPE:
			jogando = false;
			dispose();
			break;
		case KeyEvent.VK_UP:
			controleTeclas [0] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			controleTeclas [1] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			controleTeclas [2] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			controleTeclas [3] = pressionada;
			break;
		case KeyEvent.VK_SPACE:
			controleTeclas [4] = pressionada;
		}
	}
	
	public void carregarJogo() {
		// criar e configurar tanque
		tanque = new Tanque();
		tanque.setVelocidade(3);
		tanque.setAtivo(true);
		tanque.setX(tela.getWidth() / 2 - tanque.getLargura() / 2);
		tanque.setY(tela.getHeight() - tanque.getAltura() - linhaBase);

		// criar e configurar tiro
		tiroTanque = new Tiro();
		tiroTanque.setVelocidade(-15);

		// criar e configurar chefe
		chefe = new Invader(Invader.Tipos.CHEFE);

		// criar e configurar tiro do chefe
		tiroChefe = new Tiro(true);
		tiroChefe.setVelocidade(20);
		tiroChefe.setAltura(15);

		// criar e configurar tiros das naves
		for (int i = 0; i < tiros.length; i++) {
			tiros[i] = new Tiro(true);
		}
		
		// criar e configurar invasores
		for (int i = 0; i < invasores.length; i++) {
			for (int j = 0; j < invasores[i].length; j++) {
				Invader e = new Invader(tiposPorLinha[j]);
				e.setAtivo(true);
				e.setX(i * e.getLargura() + (i + 1) * espacamento );
				e.setY(j * e.getAltura() + j * espacamento + linhaBase );
				invasores[i][j] = e;
			}
		}
		
		// configurar variáveis do jogo
		dir = 1;

		totalInimigos = invasores.length * invasores[0].length;

		contadorEspera = totalInimigos / level;
	}
	
	public void iniciarJogo() {
		long proxAtualizacao = 0;
		while (jogando) {
			if (System.currentTimeMillis() >= proxAtualizacao) {
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, LARGURA_DA_TELA, ALTURA_DA_TELA);
				
				// impede o jogador de sair da tela
				if (tanque.getX() <= 0) tanque.setX(0);
				if (tanque.getX() + tanque.getLargura() >= tela.getWidth()) 
					tanque.setX(tela.getWidth() - tanque.getLargura());
				
				// terminar o jogo se perder as vidas
				if (vidas == 0) {
					jogando = false;;
				}
				
				// se destruiu tudo, aumenta de nível e recarrega o jogo
				if (destruidos == totalInimigos) {
					destruidos = 0;
					level++;
					carregarJogo();
					continue;
				}
				// aumenta a velocidade dos inimigos quando sobe de nível
				if (contador > contadorEspera) {
					moverInimigos = true;
					contador = 0;
					contadorEspera = totalInimigos - level  * level;
				} else {
					contador++;
				}
				
				// movimenta o tanque
				if (tanque.isAtivo()) {
					if (controleTeclas[2]) {
						tanque.setX(tanque.getX() - tanque.getVelocidade());
					} else if (controleTeclas[3]){
						tanque.setX(tanque.getX() + tanque.getVelocidade());
					}
				}
				
				// atirar com o tanque
				if (controleTeclas[4] && !tiroTanque .isAtivo()) {
					tiroTanque.setX(tanque.getX() + tanque.getLargura() / 2
							- tiroTanque.getLargura() / 2);
					tiroTanque.setY(tanque.getY() - tiroTanque.getAltura());
					tiroTanque.setAtivo(true);
				}				
				
				if (chefe .isAtivo()) {
					chefe.incX(tanque.getVelocidade() - 1);
					// atirar quando ocorrer colisão X com o tanque
					if (!tiroChefe .isAtivo() && Util.colideX(chefe, tanque)) {
						addTiroInimigo(chefe, tiroChefe);						
					}
					// o chefe desaparece quando sai da tela
					if (chefe.getY() > tela.getWidth()) {
						chefe.setAtivo(false);
					}
				}
								
				boolean colideBordas = false;
				// ciclo de verificação dos inimigos
				for (int j = invasores[0].length - 1; j >= 0; j--) {
					for (int i = 0; i < invasores.length; i++) {
						Invader inv = invasores[i][j];
						
						if (!inv.isAtivo()) continue;
						
						// destrói inimigo quando este recebe um tiro
						if (Util.colidiu(tiroTanque, inv)) {
							inv.setAtivo(false);
							tiroTanque.setAtivo(false);
							destruidos++;
							pontos += inv.getPremio() * level;
							continue;
						}
						
						// trata a movimentação dos inimigos
						if (moverInimigos) {
							inv.atualiza();

							if (novaLinha) { // vai para uma nova linha
								inv.setY(inv.getY() + inv.getAltura() + espacamento);
							} else { // caso contrário vai p lados
								inv.incX(espacamento * dir);
							}

							// verifica se vão colidir com as bordas da tela
							if (!novaLinha && !colideBordas) {
								int pxEsq = inv.getX() - espacamento;
								int pxDir = inv.getX() + inv.getLargura() + espacamento;
								if (pxEsq <= 0 || pxDir >= tela.getWidth())
									colideBordas = true;
							}

							// decide quando os inimigos vão atirar (IA)
							if (!tiros[0].isAtivo() && inv.getX() < tanque.getX()) {
								addTiroInimigo(inv, tiros[0]);
							} else if (!tiros[1].isAtivo() && inv.getX() > tanque.getX()
									&& inv.getX() < tanque.getX() + tanque.getLargura()) {
								addTiroInimigo(inv, tiros[1]);
							} else if (!tiros[2].isAtivo() && inv.getX() > tanque.getX() + tanque.getLargura()) {
								addTiroInimigo(inv, tiros[2]);
							}

							// condição para o chefe aparecer
							if (!chefe.isAtivo() && rnd.nextInt(500) == destruidos) {
								chefe.setX(0);
								chefe.setAtivo(true);
							}
						}
					}
				}
				
				// decide se vai para uma nova linha ou para os lados
				if (moverInimigos && novaLinha) {
					dir *= -1;
					novaLinha = false;
				} else if (moverInimigos && colideBordas){
					novaLinha = true;
				}
				
				moverInimigos = false;
				
				// trata o tiro do chefe
				if (tiroChefe.isAtivo()) {
					tiroChefe.incY(tiroChefe.getVelocidade());
					
					if (Util.colidiu(tiroChefe, tanque)) {
						vidas --;
						tiroChefe.setAtivo(false);
					} else if (tiroChefe.getY() > tela.getHeight() - linhaBase
							- tiroChefe.getAltura()) {
						tiroChefe.setAtivo(false);
					} else {
						tiroChefe.desenha(g2);
					}
				}
				
				// trata o tiro do tanque
				if (tiroTanque.isAtivo()) {
					tiroTanque.incY(tiroTanque.getVelocidade());

					if (Util.colidiu(tiroTanque, chefe)) {
						pontos += chefe.getPremio() * level;
						chefe.setAtivo(false);
						tiroTanque.setAtivo(false);

					} else if (tiroTanque.getY() < 0) {
						tiroTanque.setAtivo(false);
					}

					tiroTanque.desenha(g2);
				}
				
				// trata os tiros dos inimigos
				for (int i = 0; i < tiros.length; i++) {
					if (tiros[i].isAtivo()) {
						tiros[i].incY(+10);

						if (Util.colidiu(tiros[i], tanque)) {
							vidas--;
							tiros[i].setAtivo(false);

						} else if (tiros[i].getY() > tela.getHeight() - linhaBase - tiros[i].getAltura())
							tiros[i].setAtivo(false);

						tiros[i].desenha(g2);
					}
				}
				
				// desenha as naves inimigas nas posições atuais
				for (int i = 0; i < invasores.length; i++) {
					for (int j = 0; j < invasores[i].length; j++) {
						Invader e = invasores[i][j];
						e.desenha(g2);
					}
				}
				
				// atualiza e desenha o tanque
				tanque.atualiza();
				tanque.desenha(g2);
				
				// atualiza e desenha o chefe
				chefe.atualiza();
				chefe.desenha(g2);
				
				// desenha os textos do jogo
				g2.setColor(Color.WHITE);

				texto.desenha(g2, String.valueOf(pontos), 10, 20);
				texto.desenha(g2, "Level " + level, tela.getWidth() - 100, 20);
				if (vidas != 0) texto.desenha(g2, String.valueOf(vidas), 10, tela.getHeight() - 10);
				else texto.desenha(g2, "Game Over", 10, tela.getHeight() - 10);
				// Linha base
				g2.setColor(Color.GREEN);
				g2.drawLine(0, tela.getHeight() - linhaBase, tela.getWidth(), tela.getHeight() - linhaBase);
				
				for (int i = 1; i < vidas; i++) {
					vida.setX(i * vida.getLargura() + i * espacamento);
					vida.setY(tela.getHeight() - vida.getAltura());
					vida.desenha(g2);
				}
				
				tela.repaint();
				
				proxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}
	
	private void addTiroInimigo(Invader inimigo, Tiro tiro) {
		tiro.setAtivo(true);
		tiro.setX(inimigo.getX() + inimigo.getLargura() / 2 - tiro.getLargura() / 2);
		tiro.setY(inimigo.getY() + inimigo.getAltura());
	}

	public static void main(String[] args) {
		Jogo j = new Jogo();
		j.carregarJogo();
		j.iniciarJogo();
	}

}
