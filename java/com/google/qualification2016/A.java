package com.google.qualification2016;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class A {
	
	public static String lastNumberCounted(int N){
		if(N == 0){
			return "INSOMNIA";
		}
		
		Set<String> digits = new HashSet<String>();
		findDigits(digits, Integer.toString(N));
		//System.out.println(Arrays.toString(digits.toArray(new String[digits.size()])));
		int i = 1;
		while(digits.size() < 10){
			i++;
			findDigits(digits, Integer.toString(i * N));
			//System.out.println(Arrays.toString(digits.toArray(new String[digits.size()])));
		}
		return Integer.toString(i * N);
	}
	
	public static void findDigits(Set<String> digits, String number){
		String currNum = number;
		for(String d : digits){
			currNum = currNum.replaceAll(d, "");
		}
		for(char ch : currNum.toCharArray()){
			digits.add("" + ch);
		}
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
        
        Map<Integer, String> results = new HashMap<Integer, String>();
        for(int t=0;t<T;t++){
            int N = scan.nextInt();
            
            if(!results.containsKey(N)){
            	results.put(N, lastNumberCounted(N));
            }
            
            System.out.printf("Case #%d: %s%n", t + 1, results.get(N));
            out.write(String.format("Case #%d: %s%n", t + 1, results.get(N)));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
