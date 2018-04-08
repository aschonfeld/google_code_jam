package com.google.round1B2016;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class A {
	
	public static String[] digits = new String[]{"ZERO","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE"};
	
	public static Map<Character, Integer> convertStringToCharMap(String s){
		Map<Character, Integer> digitchars = new HashMap<Character, Integer>();
		for(char c : s.toCharArray()){
			int count = 1;
			if(digitchars.containsKey(c)){
				count += digitchars.get(c);
			}
			digitchars.put(c, count);
		}
		return digitchars;
	}
	
	public static String findDigits(List<Map<Character, Integer>> digitMaps, String S){
		Map<Character, Integer> digitchars = convertStringToCharMap(S);
		
		String returnVal = "";
		for(int i=0;i<digitMaps.size();i++){
			int containsDigit = 0;
			Map<Character, Integer> digitMap = digitMaps.get(i);
			for(Map.Entry<Character, Integer> e : digitMap.entrySet()){
				Character c = e.getKey();
				if(!digitchars.containsKey(c)){
					containsDigit = 0;
					break;
				}else if(digitchars.get(c) < e.getValue()){
					containsDigit = 0;
					break;
				}else{
					int charCt = digitchars.get(c) / e.getValue();
					if(containsDigit == 0 || charCt < containsDigit)
						containsDigit = charCt;
				}
			}
			if(containsDigit > 0){
				for(int j=0;j<containsDigit;j++)
					returnVal += "" + i;
			}
		}
		return returnVal;
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
        
        List<Map<Character, Integer>> digitMaps = new ArrayList<Map<Character, Integer>>();
        for(String d : digits)
        	digitMaps.add(convertStringToCharMap(d));

        Map<Integer, String> results = new HashMap<Integer, String>();
        for(int t=0;t<T;t++){
            String S = scan.next();
            
            String returnVal = findDigits(digitMaps, S);
            
            System.out.printf("Case #%d: %s%n", t + 1, returnVal);
            out.write(String.format("Case #%d: %s%n", t + 1, returnVal));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
