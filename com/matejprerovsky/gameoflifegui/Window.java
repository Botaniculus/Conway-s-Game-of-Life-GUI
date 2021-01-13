package com.matejprerovsky.gameoflifegui;

import javax.swing.JFrame;

public class Window extends JFrame{

	public Window() {
		this.add(new Panel());
		this.setTitle("Conway's game of life");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		new Window();

	}

}
