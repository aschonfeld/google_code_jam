package com.google.round1B2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class C {

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
            int N = scan.nextInt();
            
            System.out.printf("Case #%d: %d%n", t, N);
            out.write(String.format("Case #%d: %d%n", t, N));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
