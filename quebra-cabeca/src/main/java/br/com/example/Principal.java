package br.com.example;
import br.com.example.design.Tela;
import br.com.example.domain.Game;

public class Principal {

	public static void main(String[] args) {
		Game game = new Game();

		Tela tela = new Tela(game);

		tela.pack();
		tela.setVisible(true);

	}

}
