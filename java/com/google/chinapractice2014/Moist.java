package com.google.chinapractice2014;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Moist {
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for(int t=1; t<=T; t++) {
			int N = in.nextInt();
			in.nextLine();
			String[] skaters = new String[N];
			for(int n=0;n<N;n++){
				skaters[n] = in.nextLine();
			}
			int cost = 0;
			for(int n=1;n<N;n++){
				int curr_idx = n;
				int unsorted = skaters[curr_idx-1].compareTo(skaters[curr_idx]);
				while(unsorted > 0){
					cost++;
					skaters[curr_idx] = skaters[curr_idx-1];
					curr_idx--;
					if(curr_idx == 0) break;
					unsorted = skaters[curr_idx-1].compareTo(skaters[curr_idx]);
				}
			}
			System.out.printf("Case #%d: %d%n", t, cost);
		}
	}
}