package main;

import java.awt.Color;

import javax.swing.*;

public class Main {
	
	public static void main(String[] args) {	
		
		JFrame frame = new JFrame("Immersive Media Ducksign");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GamePanel gPanel = new GamePanel();
		gPanel.setBackground(Color.CYAN);
		gPanel.loadMap();
		gPanel.setFocusTraversalKeysEnabled(false);
		frame.add(gPanel);
		frame.pack();

		frame.setVisible(true);
		gPanel.requestFocusInWindow();

		gPanel.startGameThread();
		
	}

}