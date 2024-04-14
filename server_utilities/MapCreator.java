package server_utilities;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import game_utilities.AirBlock;
import game_utilities.Block;
import game_utilities.BreakableBlock;

public class MapCreator implements Serializable {
	
	private LinkedHashMap<String, int[][]> maps = new LinkedHashMap<>();
	
	private int GRID_SIZE = 21;
	private int BLOCK_SIZE = 35;
	
	public MapCreator() {
		maps.put("default", new int[][] {
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}, 
				{0,1,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}, 
				{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}, 
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		});
	}
	
	public ConcurrentHashMap<Integer, Block> getMap(String m) {
		ConcurrentHashMap<Integer, Block> map = new ConcurrentHashMap<>();
		int[][] blocks = maps.get(m);
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int o = 0; o < GRID_SIZE; o++) {
				int xPos = o * BLOCK_SIZE;
				int yPos = i * BLOCK_SIZE;
				Block block = null;
				switch (blocks[i][o]) {
				case 1:
					block = new BreakableBlock(xPos, yPos, i, o);
					block.setBounds(new Rectangle(BLOCK_SIZE, BLOCK_SIZE));
					block.x = xPos;
					block.y = yPos;
					map.put((i * GRID_SIZE) + o, block);
					break;
				}
			}
		}
		return map;
	}
}

