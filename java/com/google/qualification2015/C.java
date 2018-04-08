package com.google.qualification2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class C {

	static Map<String, Integer> CHAR_MAP = new HashMap<String, Integer>();
	static {
		CHAR_MAP.put("1", 0);
		CHAR_MAP.put("i", 1);
		CHAR_MAP.put("j", 2);
		CHAR_MAP.put("k", 3);
	}

	static String[][] QUATERNIONS = new String[][]{
		{"1", "i", "j", "k"},
		{"i", "-1", "k", "-j"},
		{"j", "-k", "-1", "i"},
		{"k", "j", "-i", "-1"}
	};
	
	public boolean is_ijk(String[] substrings, int max){
		int idx = 0;
		String prev = substrings[0];
		for(int i=1;i<max;i++){
			boolean hasNeg = prev.startsWith("-");
			String next = QUATERNIONS[CHAR_MAP.get(prev.replaceAll("-", ""))][CHAR_MAP.get(substrings[i])];
			if(hasNeg){
				if(next.startsWith("-")){
					prev = next.replaceAll("-", "");
				}else{
					prev = "-" + next;
				}
			}else{
				prev = next;
			}
		}
		return prev.equals("-1");		
	}

	public String run(int L, int X, String l){
		StringBuilder inputBuilder = new StringBuilder(l);
		for(int i=1;i<X;i++){
			inputBuilder.append(l);
		}
		String input = inputBuilder.toString();
		char[] input_chars = input.toCharArray();
		int max = L*X;
		String[] substrings = new String[max];
		for(int c=0;c<max;c++){
			substrings[c] = "" + input_chars[c];
		}
		
		if(input.equals("ijk")){
			return "YES";
		}
		if(l.length() == 1){
			return "NO";
		}
		if(input.length() < 4){
			return "NO";
		}
		return is_ijk(substrings, max) ? "YES" : "NO";
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
		for(int t=1;t<=T;t++){
			int L = scan.nextInt();
			int X = scan.nextInt();
			String l = scan.next();
			C c = new C();
			String result = c.run(L, X, l);

			System.out.printf("Case #%d: %s%n", t, result);
			out.write(String.format("Case #%d: %s%n", t, result));
		}
		if(isFile){
			scan.close();
		}
		out.close();
	}
}
