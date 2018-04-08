package com.google.qualification2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class D {
	
	public String small_dataset(int X, int R, int C){
		String winner = "RICHARD";
        switch(X){
            case 4:
            	switch(R*C){
	            	case 16:
	            	case 12:
	            		winner = "GABRIEL";
	            		break;
            	}
            	break;
            case 3:
            	switch(R*C){
	            	case 12:
	            	case 9:
	            	case 6:
	            		winner = "GABRIEL";
	            		break;
            	}
            	break;
            case 2:
            	switch(R*C){
	            	case 16:
	            	case 12:
	            	case 8:
	            	case 6:
	            	case 4:
	            	case 2:
	            		winner = "GABRIEL";
	            		break;
            	}
            	break;
            case 1:
            	winner = "GABRIEL";
            	break;
        
        }
        return winner;
	}
	
	public String large_dataset(int X, int R, int C){
		int grid_size = R*C;
		if(grid_size % X == 0 && grid_size >= (X * (X-1))){
			return "GABRIEL";
		}
		return "RICHARD";
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
            int X = scan.nextInt();
            int R = scan.nextInt();
            int C = scan.nextInt();
            
            D d = new D();
            String winner = d.large_dataset(X, R, C);
            System.out.printf("Case #%d: %s%n", t, winner);
            out.write(String.format("Case #%d: %s%n", t, winner));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
