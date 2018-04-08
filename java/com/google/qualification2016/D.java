package com.google.qualification2016;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class D {
	
	public static String findTiles(int K, int C, int S){
		if(((double)S) <= (((double)K)/2)){
			return "IMPOSSIBLE";
		}
		StringBuilder tiles = new StringBuilder();
		String sep = "";
		if(((double)S) > (((double)K)/2)){
			for(int tile=1;tile<=S;tile++){
				tiles.append(sep).append("" + tile);
				sep = " ";
			}
			return tiles.toString();
		}
		int[] leadCts = findLeadCts(K, C);
		int tileCt = 0;
		int tile = 0;
		for(int leadCt : leadCts){
			if(leadCt <= S && tileCt < S){
				tiles.append(sep).append("" + (tile+1));
				sep = " ";
				tileCt++;
			}
			tile++;
		}
		if(tiles.length() == 0){
			return "IMPOSSIBLE";
		}
		return tiles.toString();
	}
	
	public static int[] findLeadCts(int K, int C){
		int[] leadCts = new int[((int) Math.pow(K, C))];
		Arrays.fill(leadCts, 0);
		char[] root = {'L', 'G'};
		// allocate an int array to hold the counts:
	    int[] pos = new int[K];
	    // allocate a char array to hold the current combination:
	    char[] combo = new char[K];
	    // initialize to the first value:
	    for(int i = 0; i < K; i++)
	        combo[i] = root[0];

	    while(true){
	        // output the current combination:
	    	String comboStr = updateToC(String.valueOf(combo), C);
	    	for(int i=0;i<comboStr.length();i++){
	    		if(comboStr.charAt(i) == 'L'){
	    			leadCts[i]++;
	    		}
	    	}

	        // move on to the next combination:
	        int place = K - 1;
	        while(place >= 0)
	        {
	            if(++pos[place] == root.length)
	            {
	                // overflow, reset to zero
	                pos[place] = 0;
	                combo[place] = root[0];
	                place--; // and carry across to the next value
	            }
	            else
	            {
	                // no overflow, just set the char value and we're done
	                combo[place] = root[pos[place]];
	                break;
	            }
	        }
	        if(place < 0)
	            break;  // overflowed the last position, no more combinations
	    }
	    return leadCts;
	}
	
	public static String updateToC(String input, int C){
		String newInput = input;
		StringBuilder defaultGold = new StringBuilder();
		for(int i=0;i<input.length(); i++){
			defaultGold.append("G");
		}
		for(int i=1;i<C;i++){
			StringBuilder currInput = new StringBuilder();
			for(char c : newInput.toCharArray()){
				if(c == 'L'){
					currInput.append(input);
				}else{
					currInput.append(defaultGold);
				}
			}
			newInput = currInput.toString();
		}
		return newInput;
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
        
        for(int t=0;t<T;t++){
            int K = scan.nextInt();
            int C = scan.nextInt();
            int S = scan.nextInt();
            
            String tiles = findTiles(K, C, S);
            
            System.out.printf("Case #%d: %s%n", t + 1, tiles);
            out.write(String.format("Case #%d: %s%n", t + 1, tiles));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
