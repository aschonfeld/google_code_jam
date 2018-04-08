package com.google.qualification2012;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by aschonfeld on 4/10/2015.
 */
public class RecycledNumbers {

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
            int A = scan.nextInt();
            int B = scan.nextInt();

            Set<String> pairs =new HashSet<String>();
            for(int n=A;n<=B;n++){
                String nStr = n + "";
                for(int j=1;j<nStr.length();j++){
                    String mStr = nStr.substring(j) + nStr.substring(0,j);
                    if(!mStr.startsWith("0")) {
                        int m = Integer.parseInt(mStr);
                        if(nStr.length() == mStr.length() && n < m && m <= B){
                        	pairs.add(nStr + "-" + mStr);
                        }
                    }

                }
            }

            System.out.printf("Case #%d: %d%n", t, pairs.size());
            out.write(String.format("Case #%d: %d%n", t, pairs.size()));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
