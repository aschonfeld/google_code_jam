package com.google.qualification2014;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MinesweeperMaster {
	
	int[][] cells;
	int R;
	int C;
	int M;
	
	
	public String run(String caseNum, int R, int C, int M){
		cells = new int[R][C];
		for(int[] row : cells)
			Arrays.fill(row, 0);
		this.R = R;
		this.C = C;
		this.M = M;
		StringBuilder results = new StringBuilder(caseNum);
		int openCells = (R*C)-M;
		//base cases
		if((R > 1) && (C > 1) && (openCells == 2 || openCells == 3)){
			results.append("\nImpossible");
			return results.toString();
		}
				
		int minX, minY, minAdj;
		for(int y=0; y<(M/R); y++){
			for(int x=0;x<C;x++){
				addMine(x,y);
			}
		}
		this.M = M % R;
		while(this.M > 0){
			minX = -1;
			minY = -1;
			minAdj = 9;
			for(int y=0;y<R;y++){
				for(int x=0;x<C;x++){
					if(cells[y][x] == -2)//no mines
						continue;
					Map<Integer, Set<Integer>> adj = adjacentCellValues(x, y);
					
					int adjCt = 0;
					for(Map.Entry<Integer, Set<Integer>> e : adj.entrySet()){
						if(e.getKey() != -2){
							adjCt += e.getValue().size();
						}
					}
					if(adjCt < minAdj){
						boolean valid = true;
						List<int[]> adjMineCells = adjacentCells(x, y);
						Set<Integer> adjMineCellIds = adjacentCellIds(x, y);
						for(int[] p : adjMineCells){
							if(cells[p[1]][p[0]] == -2)
								continue;
							Map<Integer, Set<Integer>> mineAdjVals = adjacentCellValues(p[0], p[1]);
							if(!mineAdjVals.containsKey(0)){
								valid = false;
								break;
							}
							mineAdjVals.get(0).removeAll(adjMineCellIds);
							if(mineAdjVals.get(0).size() == 0){
								valid = false;
								break;
							}
						}
						if(valid){
							minX = x;
							minY = y;
							minAdj = adjCt;
						}
					}
				}
			}
			if(minX == -1){
				results.append("\nImpossible");
				return results.toString();
			}
			addMine(minX, minY);
			this.M--;
		}
		
		int[] click = null;
		for(int y=0;y<R;y++){
			for(int x=0;x<C;x++){
				if(howManyCellsCanThisOpen(x, y, new HashSet<Integer>()).size() == openCells){
					click = new int[]{x, y};
					break;
				}
				if(click != null)
					break;
				
			}
		}
		if(click == null){
			results.append("\nImpossible");
			return results.toString();
		}else{
			cells[click[1]][click[0]] = 2;
		}
		for(int y=0;y<R;y++){
			String sep = "";
			results.append("\n");
			for(int x=0;x<C;x++){
				results.append(sep);
				switch(cells[y][x]){
					case -2:
						results.append("*");
						break;
					case 2:
						results.append("c");
						break;
					default:
						results.append(".");
						break;
				}
				sep = " ";
			}
		}
		return results.toString();
	}
	
	private void addMine(int x, int y){
		cells[y][x] = -2;
		for(int[] point : adjacentCells(x, y)){
			if(cells[point[1]][point[0]] == 0)
				cells[point[1]][point[0]] = 1;
		}
	}
	
	private Set<Integer> howManyCellsCanThisOpen(int x, int y, Set<Integer> openCells){
		switch(cells[y][x]){
			case -2:
				break;
			case 1:
				openCells.add(cellID(x, y));
				break;
			case 0:
				openCells.add(cellID(x, y));
				for(int [] adj : adjacentCells(x, y)){
					if(!openCells.contains(cellID(adj[0], adj[1])))
						howManyCellsCanThisOpen(adj[0], adj[1], openCells);
				}
				break;
		}
		return openCells;
	}
	
	private int cellID(int x, int y){
		return (y * C) + (x + 1);
	}
	
	private Set<Integer> adjacentCellIds(int x, int y){
		Set<Integer> ids = new HashSet<Integer>();
		for(int[] adj : adjacentCells(x, y)){
			ids.add(cellID(adj[0], adj[1]));
		}
		return ids;
	}
	private Map<Integer, Set<Integer>> adjacentCellValues(int x, int y){
		Map<Integer, Set<Integer>> valueSets = new HashMap<Integer, Set<Integer>>();
		for(int[] point : adjacentCells(x, y)){
			int val = cells[point[1]][point[0]];
			if(!valueSets.containsKey(val)){
				valueSets.put(val, new HashSet<Integer>());
			}
			valueSets.get(val).add(cellID(point[0], point[1]));
		}
		return valueSets;
	}
	
	private List<int[]> adjacentCells(int x, int y){
		List<int[]> adj_cells = new ArrayList<int[]>(8);
		if(validCell(x-1, y)) adj_cells.add(new int[]{x-1, y});//west
		if(validCell(x+1, y)) adj_cells.add(new int[]{x+1, y});//east
		if(validCell(x, y-1)) adj_cells.add(new int[]{x, y-1});//north
		if(validCell(x, y+1)) adj_cells.add(new int[]{x, y+1});//south
		if(validCell(x-1, y-1)) adj_cells.add(new int[]{x-1, y-1});//northwest
		if(validCell(x+1, y-1)) adj_cells.add(new int[]{x+1, y-1});//northeast
		if(validCell(x-1, y+1)) adj_cells.add(new int[]{x-1, y+1});//southwest
		if(validCell(x+1, y+1)) adj_cells.add(new int[]{x+1, y+1});//southeast
		return adj_cells;
	}
	
	private boolean validCell(int x, int y){
		if (x < 0) return false;
		if (x > (C-1)) return false;
		if (y < 0) return false;
		if (y > (R-1)) return false;
		return true;
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		
		BufferedReader br = null;
		List<String> file_args = new ArrayList<String>();
	    try {
	    	br = new BufferedReader(new FileReader(args[0]));
	        String line = br.readLine();
	        
	        while (line != null) {
	            file_args.add(line);
	        	line = br.readLine();
	        }
	    } catch(Exception e){
	    	System.out.println(e.toString());
	    } finally {
	    	if(br != null){
	    		br.close();
	    	}
	    }
	    
		if(file_args.size() == 0){
	    	return;
	    }
	    int i = 0;
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		fout.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		MinesweeperMaster tester = new MinesweeperMaster();
		i++; //skip case count
		while(i < file_args.size()){
			String[] vals = file_args.get(i++).split(" ");
			int R = Integer.parseInt(vals[0]);
			int C = Integer.parseInt(vals[1]);
			int M = Integer.parseInt(vals[2]);
			bw.write(tester.run(String.format("Case #%d:", caseNum++), R, C, M));
			bw.newLine();
		}
		bw.close();
	}
}