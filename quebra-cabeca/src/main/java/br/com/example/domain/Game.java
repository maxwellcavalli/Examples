package br.com.example.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import br.com.example.design.Peca;

public class Game {

	public final static int POSICAO_LIMITE = 465;
	public final static int TAMANHO_DEFAULT = 155;

	private String proximaPalavra;
	private int quantidadeJogadas;
	private List<Posicao> posicoes;
	private Map<JPanel, Posicao> mapPosicoes;
	private List<String> palavras = new ArrayList<>();

	public Game() {
		palavras.add("PARADOXO");
		palavras.add("ANALOGIA");
		palavras.add("OBSOLETO");

		calculatePositions();
	}
	
	private JPanel getPanelByPosition(Posicao posicao){
		for (JPanel panel : mapPosicoes.keySet()){
			Posicao posicaoPanel = mapPosicoes.get(panel);
			if (posicaoPanel.getX() == posicao.getX() && 
				posicaoPanel.getY() == posicao.getY()){
				return panel;
			}
		}
		
		return null;
	}
	

	public boolean isEndGame(){
		StringBuilder word = new StringBuilder();
		
		for (Posicao posicao : posicoes){
			Peca peca = (Peca) getPanelByPosition(posicao);
			if (peca != null){
				word.append(peca.getText());
			} else {
				word.append(" ");
			}
		}
		
		if (word.toString().trim().equalsIgnoreCase(proximaPalavra)){
			return true;
		} else {
			return false;
		}
	}
	

	public void addQuantidadeJogadas() {
		quantidadeJogadas++;
	}

	public Posicao proximaPosicao(Posicao posicaoAtual) {
		boolean isMovimentoDireita = posicaoAtual.getX() + TAMANHO_DEFAULT < POSICAO_LIMITE;
		boolean isMovimentoEsquerda = posicaoAtual.getX() - TAMANHO_DEFAULT >= 0;
		boolean isMovimentoBaixo = posicaoAtual.getY() + TAMANHO_DEFAULT < POSICAO_LIMITE;
		boolean isMovimentoCima = posicaoAtual.getY() - TAMANHO_DEFAULT >= 0;

		if (isMovimentoEsquerda) {
			Posicao novaPosicao = new Posicao(posicaoAtual.getX() - TAMANHO_DEFAULT, posicaoAtual.getY());
			if (isPosicaoUsed(novaPosicao)) {
				return novaPosicao;
			}
		}

		if (isMovimentoDireita) {
			Posicao novaPosicao = new Posicao(posicaoAtual.getX() + TAMANHO_DEFAULT, posicaoAtual.getY());
			if (isPosicaoUsed(novaPosicao)) {
				return novaPosicao;
			}
		}

		if (isMovimentoBaixo) {
			Posicao novaPosicao = new Posicao(posicaoAtual.getX(), posicaoAtual.getY() + TAMANHO_DEFAULT);
			if (isPosicaoUsed(novaPosicao)) {
				return novaPosicao;
			}
		}

		if (isMovimentoCima) {
			Posicao novaPosicao = new Posicao(posicaoAtual.getX(), posicaoAtual.getY() - TAMANHO_DEFAULT);
			if (isPosicaoUsed(novaPosicao)) {
				return novaPosicao;
			}
		}

		return null;
	}

	private boolean isPosicaoUsed(Posicao novaPosicao) {
		// verificar se a posicao ja esta ocupada por outro componente
		for (JPanel panel : mapPosicoes.keySet()) {
			Posicao posicao = mapPosicoes.get(panel);

			if (novaPosicao.getX() == posicao.getX() && novaPosicao.getY() == posicao.getY()) {
				return false;
			}
		}

		return true;
	}

	public void nextWord() {
		Random random = new Random();
		int prox = random.nextInt(palavras.size());
		this.proximaPalavra = palavras.get(prox);
	}

	private void calculatePositions() {
		int x = 0;
		int y = 0;
		posicoes = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			posicoes.add(new Posicao(x, y));

			x = x + TAMANHO_DEFAULT;
			if ((i + 1) % 3 == 0) {
				x = 0;
				y = y + TAMANHO_DEFAULT;
			}
		}
	}

	public String shuffle() {
		return shuffle(proximaPalavra);
	}

	public String shuffle(String text) {
		char[] characters = text.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			int randomIndex = (int) (Math.random() * characters.length);
			char temp = characters[i];
			characters[i] = characters[randomIndex];
			characters[randomIndex] = temp;
		}
		return new String(characters);
	}

	public String getProximaPalavra() {
		return proximaPalavra;
	}

	public void setProximaPalavra(String proximaPalavra) {
		this.proximaPalavra = proximaPalavra;
	}

	public List<Posicao> getPosicoes() {
		return posicoes;
	}

	public void setPosicoes(List<Posicao> posicoes) {
		this.posicoes = posicoes;
	}

	public Map<JPanel, Posicao> getMapPosicoes() {
		return mapPosicoes;
	}

	public void setMapPosicoes(Map<JPanel, Posicao> mapPosicoes) {
		this.mapPosicoes = mapPosicoes;
	}

	public int getQuantidadeJogadas() {
		return quantidadeJogadas;
	}

	public void setQuantidadeJogadas(int quantidadeJogadas) {
		this.quantidadeJogadas = quantidadeJogadas;
	}

}
