package br.com.example.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Peca extends JPanel {

	private static final long serialVersionUID = 1L;
	private String text;

	public Peca(String text) {
		this.text = text;

		JLabel label = new JLabel(text);
		label.setBackground(Color.white);
		label.setForeground(Color.white);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 25));

		this.setPreferredSize(new Dimension(150, 150));
		this.setBackground(new Color(19, 155, 19, 90));
		this.setBorder(BorderFactory.createLineBorder(Color.white, 5, false));
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.CENTER);

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
