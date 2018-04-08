package com.google.round1C2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class A {
	
	public int solve(int R, int C, int W){
		if(R*C == W) return W;
		if(W == 1) return R*C;
		
		int total = 0;
		Queue<Integer> sub_w = new LinkedList<Integer>();
		sub_w.add(C);
		int min_sub_w = W;
		while(!sub_w.isEmpty()){
			int curr = sub_w.poll();
			int div = curr / 2;
			int mod = curr % 2;
			int cut1 = div + mod;
			int cut2 = curr - (div + mod);
			if(cut1 >= W){
				sub_w.add(cut1);
				total++;
				if(cut1 < min_sub_w) min_sub_w = cut1;
			}
			if(cut2 > W){
				sub_w.add(cut2);
				total++;
				if(cut2 < min_sub_w) min_sub_w = cut2;
			}
		}
		
		total += min_sub_w;
		return total;
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
        A a = new A();
        for(int t=1;t<=T;t++){
            int R = scan.nextInt();
            int C = scan.nextInt();
            int W = scan.nextInt();
            int total = a.solve(R, C, W);
            System.out.printf("Case #%d: %d%n", t, total);
            out.write(String.format("Case #%d: %d%n", t, total));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
