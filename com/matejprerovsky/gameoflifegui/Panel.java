package com.matejprerovsky.gameoflifegui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener{
	private final int DELAY = 150;
	private final int SCREEN_SIDE = 800;
	private final int UNIT_SIDE = 20;
	private final Game game;
	private Timer timer; 
	private boolean paused = false;
	private int generation=0;
	
	public void pauseGame() {
		paused = (paused) ? false : true;
	}
	public Panel() {
		game = new Game(SCREEN_SIDE, UNIT_SIDE);
		game.randomizeCanvas();
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(SCREEN_SIDE, SCREEN_SIDE));
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					pauseGame();
					break;

				case KeyEvent.VK_MINUS:
					timer.setDelay(timer.getDelay()+10);
					System.out.println("Delay "+timer.getDelay());
					break;
					
				case KeyEvent.VK_PLUS:
					timer.setDelay(timer.getDelay()-10);
					System.out.println("Delay "+timer.getDelay());
					break;
				
				case KeyEvent.VK_BACK_SPACE:
					game.clearCanvas();
					generation=0;
					if(!paused)
						pauseGame();
					break;
				
				case KeyEvent.VK_R:
					generation=0;
					game.randomizeCanvas();
					break;
				}
				
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
				int x=(e.getX()/UNIT_SIDE);
			    int y=(e.getY()/UNIT_SIDE);
			    game.addCell(x, y);
		    }
		});
		timer = new Timer(DELAY, this);
		timer.start();
		
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i = 0; i<SCREEN_SIDE/UNIT_SIDE; i++) {
			g.drawLine(i*UNIT_SIDE, 0, i*UNIT_SIDE, SCREEN_SIDE);
			g.drawLine(0, i*UNIT_SIDE, SCREEN_SIDE, i*UNIT_SIDE);
		}
		
		int[][] grid = game.getGrid();
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++) {
				if(grid[i][j] == 0) continue;
				else {
					g.setColor(Color.MAGENTA);
					//g.fillRoundRect((j)*UNIT_SIDE, (i)*UNIT_SIDE, UNIT_SIDE, UNIT_SIDE, 15, 15);
					g.fillOval((j)*UNIT_SIDE, (i)*UNIT_SIDE, UNIT_SIDE, UNIT_SIDE);
					g.drawRect((j)*UNIT_SIDE, (i)*UNIT_SIDE, UNIT_SIDE, UNIT_SIDE);
				}
			}
		}
		g.setColor(Color.white);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
		FontMetrics metrics = getFontMetrics(g.getFont());
		
		g.drawString("[ Generations: " + generation + " ]", (SCREEN_SIDE - metrics.stringWidth("[ Generations: " + generation + " ]"))/2, g.getFont().getSize());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!paused) {
			game.nextGeneration();
			generation++;
		}
		repaint();
	}
	

}
