package com.matejprerovsky.gameoflifegui;

import java.util.Random;

public class Game{
		private final int SIDE;
		private final int LIVE_CELL = 1;
		private final int DEAD_CELL = 0;
		
		private static int[][] canvas;
		
		public Game(int screenSide, int unitSide) {
			this.SIDE=screenSide/unitSide;
			canvas = new int[screenSide/unitSide][screenSide/unitSide];
		}
		
		public int[][] getGrid() {
			return canvas;
		}
		
		public void randomizeCanvas() {
			Random random = new Random();
			for(int i=0; i<SIDE; i++) {
				for(int j=0; j<SIDE; j++) {
					int r = random.nextInt(4);
					canvas[i][j] = (r==0) ? LIVE_CELL : DEAD_CELL;
				}
			}
		}
		
		public void addCell(int x, int y) {
			if(canvas[y][x]==0)
				canvas[y][x]=1;
			else
				canvas[y][x]=0;
		}
		public void clearCanvas() {
			for(int i=0; i<SIDE; i++) {
				for(int j=0; j<SIDE; j++) {
					canvas[i][j] = DEAD_CELL;
				}
			}
		}
		
		private int neighborsCount(int x, int y) {
			int count = 0;
			for(int i=y-1; i<=y+1; i++) {
				for(int j=x-1; j<=x+1; j++) {				
					if(i==y && j==x) continue;
					count+=(canvas[(i==-1 || i==SIDE)?((i==-1)?(i+SIDE):0):i][(j==-1 || j==SIDE)?((j==-1)?(j+SIDE):0):j] == LIVE_CELL) ? 1 : 0;
					
				}
			}
			return count;
		}
		
		public int[][] nextGeneration() {
			int[][] copy = new int[SIDE][SIDE];
			String outString="";
	 		for(int i=0; i<SIDE; i++) {
				for(int j=0; j<SIDE; j++) {
					int neighbors = neighborsCount(j, i);
					if(canvas[i][j] == DEAD_CELL) {
						copy[i][j] = (neighbors==3) ? LIVE_CELL : DEAD_CELL;
					} else{
						copy[i][j] = ((neighbors<2) || (neighbors>3)) ? DEAD_CELL : LIVE_CELL;
					}
				}	
			}
	 		canvas=copy.clone();
	 		return canvas;
		}

}
