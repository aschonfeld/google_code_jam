package com.google.round1C2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class B {
	
	int K;
	int L;
	int S;
	String keyboard;
	String word;
	
	public B(int K, int L, int S, String keyboard, String word){
		this.K = K;
		this.L = L;
		this.S = S;
		this.keyboard = keyboard;
		this.word = word;
	}
	
	// The method that prints all possible strings of length k.  It is
    //  mainly a wrapper over recursive function printAllKLengthRec()
    private List<Integer> getAllKLength(char set[], int k) {
        int n = set.length;  
        List<Integer> scores = new LinkedList<Integer>();
        getAllKLengthRec(set, "", n, k, scores);
        return scores;
    }
 
    // The main recursive method to print all possible strings of length k
    private void getAllKLengthRec(char set[], String prefix, int n, int k, List<Integer> scores) {
         
    	// Base case: k is 0, print prefix
        if (k == 0) {
        	scores.add(scoreString(prefix));
            return;
        }
 
        // One by one add all characters from set and recursively 
        // call for k equals to k-1
        for (int i = 0; i < n; ++i) {
             
            // Next character of input added
            String newPrefix = prefix + set[i]; 
             
            // k is decreased, because we have added a new character
            getAllKLengthRec(set, newPrefix, n, k - 1, scores); 
        }
    }
    
    private int scoreString(String str){
    	int score = 0;
		for(int i=0;i<=str.length()-this.L;i++){
			if(str.substring(i, i+L).equals(this.word)) score++;
		}
		return score;
    }
    
	public String solve(){
		double pct = 0d;
		Map<Character, Double> c_pct = new HashMap<Character, Double>();
		
		for(int i=0;i<K;i++){
			Character c = keyboard.charAt(i);
			if(!c_pct.containsKey(c)){
				c_pct.put(c, 0d);
			}
			c_pct.put(c, c_pct.get(c) + 1);
		}
		StringBuffer most_times_sb = new StringBuffer(word);
		while((most_times_sb.length() + L) <= S){
			most_times_sb.append(word);
		}
		if(S - most_times_sb.length() > 0){
			most_times_sb.append(word.substring(0, S - most_times_sb.length()));
		}
		
		int most_times = 0;
		for(int i=0;i<=most_times_sb.length()-L;i++){
			if(most_times_sb.substring(i, i+L).equals(word)) most_times++;
		}
		
		double w_pct = 1d;
		for(Character c : word.toCharArray()){
			if(!c_pct.containsKey(c))
				return String.format("%.7f", pct);
			w_pct = w_pct * (c_pct.get(c) / K);
			if(w_pct < 10e-6) return String.format("%.7f", new Double(most_times));
		}
		
		List<Integer> scores = getAllKLength(keyboard.toCharArray(), S);
		double total = 0;
		for(Integer s : scores) total += s;
		
		
		return String.format("%.7f", new Double(most_times) - (total / scores.size()));
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
            int K = scan.nextInt();
            int L = scan.nextInt();
            int S = scan.nextInt();
            String keyboard = scan.next();
            String word = scan.next();
            
            B b = new B(K, L, S, keyboard, word);
            String soln = b.solve();
            System.out.printf("Case #%d: %s%n", t, soln);
            out.write(String.format("Case #%d: %s%n", t, soln));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
