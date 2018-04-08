package com.google.qualification2013;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class TicTacToeTomek {
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for(int t=1; t<=T; t++) {
			String[][] game = new String[4][4];
			boolean finished = true;
			for(int i=0;i<4;i++){
				String val = in.next();
				int j = 0;
				for(char c : val.toCharArray()){
					game[i][j++] = Character.toString(c);
					if('.' == c){
						finished = false;
					}
				}
			}
			in.nextLine();
			int i = 0;
			StringBuilder soln = new StringBuilder();
			String soln_str;
			Set<String> soln_vals = new HashSet<String>();
			while(i < 4){
				soln = new StringBuilder();
				//check vertical lines
				for(int j=0;j<4;j++){
					soln.append(game[j][i]);
				}
				soln_str = soln.toString();
				if(soln_str.startsWith("X") || soln_str.endsWith("X")){
					if(soln_str.replace("T", "X").equals("XXXX")){
						soln_vals.add("X");
					}
				}
				else if(soln_str.startsWith("O") || soln_str.endsWith("O")){
					if(soln_str.replace("T", "O").equals("OOOO")){
						soln_vals.add("O");
					}
				}
				//check horizontal lines
				soln = new StringBuilder();
				//check vertical lines
				for(int j=0;j<4;j++){
					soln.append(game[i][j]);
				}
				soln_str = soln.toString();
				if(soln_str.startsWith("X") || soln_str.endsWith("X")){
					if(soln_str.replace("T", "X").equals("XXXX")){
						soln_vals.add("X");
					}
				}
				else if(soln_str.startsWith("O") || soln_str.endsWith("O")){
					if(soln_str.replace("T", "O").equals("OOOO")){
						soln_vals.add("O");
					}
				}
				i++;
			}
			//check diagonals
			soln = new StringBuilder();
			for(int j=0;j<4;j++){
				soln.append(game[j][j]);
			}
			soln_str = soln.toString();
			if(soln_str.startsWith("X") || soln_str.endsWith("X")){
				if(soln_str.replace("T", "X").equals("XXXX")){
					soln_vals.add("X");
				}
			}
			else if(soln_str.startsWith("O") || soln_str.endsWith("O")){
				if(soln_str.replace("T", "O").equals("OOOO")){
					soln_vals.add("O");
				}
			}
			soln = new StringBuilder();
			for(int j=0;j<4;j++){
				soln.append(game[j][3-j]);
			}
			soln_str = soln.toString();
			if(soln_str.startsWith("X") || soln_str.endsWith("X")){
				if(soln_str.replace("T", "X").equals("XXXX")){
					soln_vals.add("X");
				}
			}
			else if(soln_str.startsWith("O") || soln_str.endsWith("O")){
				if(soln_str.replace("T", "O").equals("OOOO")){
					soln_vals.add("O");
				}
			}
			String result = finished ? "Draw" : "Game has not completed";
			if(soln_vals.contains("X")){
				if(soln_vals.contains("O")){
					result = "Draw";
				}else{
					result = "X won";
				}
			}else if(soln_vals.contains("O")){
				result = "O won";
			}
			System.out.printf("Case #%d: %s%n", t, result);
		}
	}
}