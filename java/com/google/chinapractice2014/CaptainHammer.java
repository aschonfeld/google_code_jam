package com.google.chinapractice2014;
import java.io.IOException;
import java.util.Scanner;


public class CaptainHammer {
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for(int t=1; t<=T; t++) {
			int V = in.nextInt();
			int D = in.nextInt();
			double d = (9.8 * D) / Math.pow(V, 2);
			if (d > 1) d = 1d;
			double angle = (90 * Math.asin(d)) / Math.PI;
			System.out.printf("Case #%d: %.7f%n", t, angle);
		}
	}
}