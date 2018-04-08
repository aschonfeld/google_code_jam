package com.google.round1B2015;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class B {
	
	public int compute(int R, int C, int N){
		if(N == 0) return 0;
		int total_poosible_zero = (((R/2) + (R%2)) * ((C/2) + (C%2))) + ((R/2) * (C/2));
		if(N <= total_poosible_zero) return 0;
		int[][] cells = new int[R][C];
		if(N == R * C){
			for(int i=0;i<R;i++){
				Arrays.fill(cells[i], 1);
			}
		}else{
	        int ct = N;
	        while(ct > 0){
	        	int best_score = 17;
	        	int selected_i = -1;
	        	int selected_j = -1;
	        	for(int i=0;i<R;i++){
	        		for(int j=0;j<C;j++){
	        			if(cells[i][j] > 0) continue;
	        			List<Point> adj = findAdjacent(i,j, cells);
	        			int score = adj.size() + countUnfilled(adj, cells);
	        			if(score < best_score){
	        				selected_i = i;
	        				selected_j = j;
	        				best_score = score;
	        			}
	        		}
	        	}
	        	cells[selected_i][selected_j] = 1;
	        	ct--;
	        }
		}
        Set<Point> examined = new HashSet<Point>();
        int unhappiness = 0;
        for(int i=0;i<R;i++){
    		for(int j=0;j<C;j++){
    			if(cells[i][j] == 0) continue;
    			for(Point p : findAdjacent(i,j, cells)){
    				if(!examined.contains(p) && cells[p.x][p.y] > 0){
    					unhappiness++;
    				}
    			}
    			examined.add(new Point(i,j));
    		}
        }
        return unhappiness;
	}
	
	private List<Point> findAdjacent(int i, int j, int[][]cells){
		List<Point> adj = new ArrayList<Point>(8);
		for(int i2=i-1;i2<=i+1;i2++){
			for(int j2=j-1;j2<=j+1;j2++){
				if(i2 == i && j2 == j) continue;
				if(i2 != i && j2 != j) continue;
				try{
					int val = cells[i2][j2];
					adj.add(new Point(i2,j2));
				}catch(Exception e){}
			}
		}
		return adj;
	}
	
	private int countUnfilled(List<Point> adj, int[][] cells){
		int ct = 0;
		for(Point p : adj){
			if(cells[p.x][p.y] == 0) ct++;
		}
		return ct;
	}

	public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\aschonfe\\Desktop\\tmp.out"));
        String firstLine = scan.nextLine();
        Boolean isFile = !Character.isDigit(firstLine.charAt(0));
        int T;
        if(isFile){
            scan = new Scanner(new FileReader(firstLine));
            T = scan.nextInt();
        }else{
            T = Integer.parseInt(firstLine);
        }
        B b = new B();
        for(int t=1;t<=T;t++){
        	int R = scan.nextInt();
        	int C = scan.nextInt();
            int N = scan.nextInt();
            int score = b.compute(R, C, N);
            
            
            System.out.printf("Case #%d: %d%n", t, score);
            out.write(String.format("Case #%d: %d%n", t, score));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
