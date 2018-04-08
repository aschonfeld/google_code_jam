package com.google.qualification2016;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B {
	
	private static Map<String, Integer> preloadedResults;
	static {
		preloadedResults = new HashMap<String, Integer>();
		preloadedResults.put("-+", 1);
		preloadedResults.put("+-", 2);
	}
	
	public static String removeDupes(String input){
		String undupedInput = input;
		while(undupedInput.contains("--")){
			undupedInput = undupedInput.replace("--", "-");
		}
		while(undupedInput.contains("++")){
			undupedInput = undupedInput.replace("++", "+");
		}
		return undupedInput;
	}
	
	public static String flip(String input){
		StringBuilder flippedInput = new StringBuilder();
		for(char c : input.toCharArray()){
			flippedInput.append(c == '+' ? "-" : "+");
		}
		return flippedInput.toString();
	}
	
	public static int flipsRequired(String input){
		String currInput = input;
		int i = 0;
		while(currInput.contains("-")){
			if(!currInput.contains("-")){
				return i;
			}
			if(!currInput.contains("+")){
				return i + 1;
			}
			if(currInput.length() == 2){
				return i + preloadedResults.get(currInput);
			}
			int last2Cost = preloadedResults.get(currInput.substring(currInput.length() - 2, currInput.length()));
			i += last2Cost;
			currInput = currInput.substring(0, currInput.length() - 2);
			if(last2Cost % 2 == 1){
				currInput = flip(currInput);
			}
		}		
		return i;
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
        
        Map<String, Integer> results = new HashMap<String, Integer>();
        for(int t=0;t<T;t++){
            String S = scan.next();
            S = removeDupes(S);
            
            if(!results.containsKey(S)){
            	results.put(S, flipsRequired(S));
            }
            
            System.out.printf("Case #%d: %s%n", t + 1, results.get(S));
            out.write(String.format("Case #%d: %s%n", t + 1, results.get(S)));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
