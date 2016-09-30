package br.com.example.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.example.domain.Game;
import br.com.example.domain.Posicao;

public class Tela extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txPalavra;
	private Game game;
	private JLabel lbQtdJogadas;
	private JPanel centerPanel;
	private JButton btReset;
	private JButton btIniciar;
	
	
	public Tela(Game game) {
		super("Quebra Cabeça");
		
		this.game = game;
		lbQtdJogadas = new JLabel("0");
		txPalavra = new JTextField(20);
		
		this.setResizable(false);
		this.getContentPane().setPreferredSize(new Dimension(470, 540));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(new JLabel("Palavra:"));
		topPanel.add(txPalavra);
		
		btReset = new JButton("Reset");
		btReset.setEnabled(false);
		btIniciar = new JButton("Iniciar");
		
		btIniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.setProximaPalavra(null);
				if (txPalavra.getText() == null || txPalavra.getText().isEmpty()){
					game.nextWord();
				} else {
					if (txPalavra.getText().trim().length() != 8 ){
						JOptionPane.showMessageDialog(null, "A palavra deve conter 8 caracteres");
					} else {
						if (txPalavra.getText().trim().split(" ").length > 1){
							JOptionPane.showMessageDialog(null, "A palavra contem espaços");
						} else {
							game.setProximaPalavra(txPalavra.getText()); 
						}
					}
				}
				
				if (game.getProximaPalavra() != null){
					lbQtdJogadas.setText("0");
					
					btReset.setEnabled(true);
					txPalavra.setText(game.getProximaPalavra());
					txPalavra.setEnabled(false);
					
					((JButton) e.getSource()).setEnabled(false);
					game.setQuantidadeJogadas(0);
					
					initGame();
					
					revalidate();
					repaint();
				}
			}
		});
		
		
		btReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		topPanel.add(btIniciar);
		topPanel.add(btReset);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
		bottomPanel.add(new JLabel("Quantidade de Jogadas:"));
		bottomPanel.add(lbQtdJogadas);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
		
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}
	
	private void reset(){
		lbQtdJogadas.setText("0");
		game.setQuantidadeJogadas(0);
		
		btReset.setEnabled(false);
		btIniciar.setEnabled(true);
		txPalavra.setText(null);
		txPalavra.setEnabled(true);
		
		centerPanel.removeAll();
		
		revalidate();
		repaint();
	}
	
	private void initGame(){
		Insets insets = centerPanel.getInsets();
		centerPanel.removeAll();
		
		String shuffleWord = game.shuffle();
				
		game.setMapPosicoes(new HashMap<JPanel, Posicao>());
		for (int i = 0; i < 8; i++) {
			
			String labelText = String.valueOf(shuffleWord.charAt(i));
			Peca peca = new Peca(labelText);
			
			peca.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					JPanel panel = (JPanel) e.getSource();

					Posicao posicao = game.getMapPosicoes().get(panel);
					Posicao proximaPosicao = game.proximaPosicao(posicao);

					if (proximaPosicao != null) {
						Dimension size = panel.getPreferredSize();
						panel.setBounds(proximaPosicao.getX() + insets.left, proximaPosicao.getY() + insets.top, size.width, size.height);
						game.getMapPosicoes().put(panel, proximaPosicao);
					
						game.addQuantidadeJogadas();
						lbQtdJogadas.setText(String.valueOf(game.getQuantidadeJogadas()));
						
						
						if (game.isEndGame()){
							JOptionPane.showMessageDialog(null, "Parabens, você finalizou o jogo em :" + game.getQuantidadeJogadas() + " jogadas");
							reset();
						}
					}

					System.out.println(proximaPosicao);
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});

			centerPanel.add(peca);

			Dimension size = peca.getPreferredSize();
			Posicao posicao = game.getPosicoes().get(i);
			peca.setBounds(posicao.getX() + insets.left, posicao.getY() + insets.top, size.width, size.height);

			game.getMapPosicoes().put(peca, posicao);
		}
	}
	
	

}
