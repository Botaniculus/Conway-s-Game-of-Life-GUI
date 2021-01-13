package com.matejprerovsky.gameoflifegui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener, Runnable{
	private final int DELAY = 150;
	private final int SCREEN_SIDE = 600;
	private final int SCREEN_SIZE = SCREEN_SIDE*SCREEN_SIDE;
	private final int UNIT_SIDE = 25;
	private final int UNIT_SIZE = UNIT_SIDE*UNIT_SIDE;
	private final int GAME_UNITS=SCREEN_SIZE/UNIT_SIZE;
	private final Game game;
	private Timer timer; 
	private Timer timerForDrawing; 
	private boolean paused = false;
	
	public void pauseGame() {
		if(paused) {
			paused=false;
		} else {
			paused=true;
		}
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
				int code = e.getKeyCode();
				if(code == KeyEvent.VK_SPACE)
					pauseGame();
				else if(code == KeyEvent.VK_MINUS) {
					timer.setDelay(timer.getDelay()+10);
					System.out.println("Delay "+timer.getDelay());
				}
				else if(code == KeyEvent.VK_PLUS) {
					timer.setDelay(timer.getDelay()-10);
					System.out.println("Delay "+timer.getDelay());
				} else if(code == KeyEvent.VK_BACK_SPACE) {
					game.clearCanvas();
					
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
					g.fillRoundRect((j)*UNIT_SIDE, (i)*UNIT_SIDE, UNIT_SIDE, UNIT_SIDE, 15, 15);
					g.drawRect((j)*UNIT_SIDE, (i)*UNIT_SIDE, UNIT_SIDE, UNIT_SIDE);
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!paused) {
			Thread thread = new Thread(this);
			thread.start();
			while(thread.isAlive()) {}
		}
		
		repaint();
		
	}
	public void run() {
		game.nextGeneration();
		
	}

}
