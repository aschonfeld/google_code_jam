package com.google.qualification2013;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class FairAndSquare {
	
	BigInteger NEG_ONE = new BigInteger("-1");
	
	Map<BigInteger, String> saved_squares = new HashMap<BigInteger, String>();
	
	private static Boolean isPalindrome(String s){
		if(s.length() == 1){
			return true;
		}
		boolean ret = true;
		for(int i=0;i<(s.length()/2);i++){
			if(s.charAt(i) != s.charAt((s.length() - 1) - i)) return false;
		}
		return ret;
	}
	
	public static List<Double> calcFairAndSquares(double B){
		
		double maxSqrt = Math.sqrt(B);
		List<Double> fairAndSquares = new ArrayList<Double>();
		for(double i=0;i<maxSqrt;i++){
			if(isPalindrome(String.format("%.0f", i))){
				double perfectSquare = i * i;
				if(isPalindrome(String.format("%.0f", perfectSquare))){
					fairAndSquares.add(perfectSquare);
				}
			}
		}
		return fairAndSquares;
	}
	
	public int testRange(double A, double B, List<Double> fairAndSquares){
		int total = 0;
		for(Double d : fairAndSquares){
			if(d > B)
				break;
			if(d < A)
				continue;
			total++;
		}
		return total;
	}
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		FairAndSquare tester = new FairAndSquare();
		double maxB = 0;
		int T = in.nextInt();
		double[][] tests = new double[T][2];
		for(int t=0; t<T; t++) {
			double A = in.nextDouble();
			double B = in.nextDouble();
			if(B > maxB) maxB = B;
			tests[t] = new double[]{A, B};
		}
		List<Double> fairAndSquares = calcFairAndSquares(maxB);
		String outputFile = "C:\\Workspace\\GoogleCodeJam\\results\\qualification2013\\C-small-practice.out";
		File fout = new File(outputFile);
		fout.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for(int t=0;t<T;t++){
			double[] range = tests[t];
			bw.write(String.format("Case #%d: %d%n", t+1, tester.testRange(range[0], range[1], fairAndSquares)));
		}
		bw.close();
	}
}