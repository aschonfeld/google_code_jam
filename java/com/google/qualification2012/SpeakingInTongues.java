package com.google.qualification2012;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpeakingInTongues {
	static String[][] testStrings = new String[][]{
		new String[]{
				"ejp mysljylc kd kxveddknmc re jsicpdrysi",
				"our language is impossible to understand"
		},
		new String[]{
				"rbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcd",
				"there are twenty six factorial possibilities"
		},
		new String[]{
				"de kr kd eoya kw aej tysr re ujdr lkgc jv",
				"so it is okay if you want to just give up"
		}
	};
	public static void main(String[] args){
		Map<Character, Character> googlerese = new HashMap<Character, Character>();
		googlerese.put('a', 'y');
		googlerese.put('o', 'e');
		googlerese.put('z', 'q');
		googlerese.put('q', 'z');
		//intialize rest of mapping
		for(String[] ts : testStrings){
			for(int i=0;i<ts[0].length();i++){
				Character ts0 = ts[0].charAt(i);
				if(ts0 != ' '){
					googlerese.put(ts0, ts[1].charAt(i));
				}
			}
		}
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        scan.nextLine();
        for(int t=1;t<=T;t++){
            String G = scan.nextLine();
            StringBuilder S = new StringBuilder();
            for(int i=0;i<G.length();i++){
            	Character Gi = G.charAt(i);
            	if(Gi == ' '){
            		S.append(" ");
            	}else{
            		S.append(googlerese.get(Gi));
            	}
            }
        	System.out.printf("Case #%d: %s%n", t, S.toString());
        }		
		
	}
	
	
}
