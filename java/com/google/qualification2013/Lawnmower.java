package com.google.qualification2013;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Lawnmower {
	
	public String testLawn(int[][] lawn, int N, int M){
		int[] maxr = new int[N];
		int[] maxc = new int[M];
		//rows
		for(int n=0;n<N;n++){
			int maxCut = 0;
			for(int m=0;m<M;m++){
				if(lawn[n][m] > maxCut){
					maxCut = lawn[n][m];
				}
			}
			maxr[n]= maxCut;		
		}
		//cols
		for(int m=0;m<M;m++){
			int maxCut = 0;
			for(int n=0;n<N;n++){
				if(lawn[n][m] > maxCut){
					maxCut = lawn[n][m];
				}
			}
			maxc[m] = maxCut;
		}
		//compare
		for(int n=0;n<N;n++){
			for(int m=0;m<M;m++){
				if(lawn[n][m] != maxr[n] && lawn[n][m] != maxc[m]) return "NO";
			}
		}
		return "YES";
	}
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		Lawnmower tester = new Lawnmower();
		
		int T = in.nextInt();
		for(int t=1; t<=T; t++) {
			int N = in.nextInt();
			int M = in.nextInt();
			int[][] lawn = new int[N][M];
			for(int n=0;n<N;n++){
				for(int m=0;m<M;m++){
					lawn[n][m] = in.nextInt();
				}
			}
			
			System.out.printf("Case #%d: %s%n", t, tester.testLawn(lawn, N, M));
		}
	}
}