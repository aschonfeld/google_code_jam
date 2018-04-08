package com.google.qualification2016;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class C {
	
	public static void printJamcoins(int N){
		//((int) Math.pow(2,N-3))
		for(int i=0; i<Math.pow(2,N-2); i++){
			System.out.println("1" + Integer.toBinaryString(i) + "1");
		}
	}
	
	public static Map<String, String> findJamcoins(int N, int J){
		Map<String, String> jamcoins = new HashMap<String, String>();
		int K = N - 2;
		char[] root = {'0', '1'};
		// allocate an int array to hold the counts:
	    int[] pos = new int[K];
	    // allocate a char array to hold the current combination:
	    char[] combo = new char[K];
	    // initialize to the first value:
	    for(int i = 0; i < K; i++)
	        combo[i] = root[0];
	    while(true){
	        // output the current combination:
	    	String jamcoin = "1" + String.valueOf(combo) + "1";
	    	String jamcoinDivisors = findJamcoinDivisors(jamcoin);
			if(jamcoinDivisors != null){
				jamcoins.put(jamcoin, jamcoinDivisors);
				System.out.println(jamcoins.size());
				if(jamcoins.size() == J){
					return jamcoins;
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
	    
	    
	    
	    return jamcoins;
	}
	
	public static String findJamcoinDivisors(String binary){
		StringBuilder divisors = new StringBuilder();
		String sep = "";
		for(int i=2;i<=10;i++){
			long base_i = asBase(binary, i);
			int base_i_divisor = findPrimeDivisor(base_i);
			if(base_i_divisor == -1){
				return null;
			}
			divisors.append(sep).append("" + base_i_divisor);
			sep = " ";
		}
		return divisors.toString();
	}

	public static int findPrimeDivisor(long n) {
	    int i;
	    for (i = 2; i <= n / 2; i++) {
	        if (n % i == 0) {
	            return i;
	        }
	    }
	    return -1;
	}
	
	public static long asBase(String binary, int base){
		long baseNum = 0L;
		for(int i=(binary.length() - 1);i>=0;i--){
			if(binary.charAt(i) == '1'){
				baseNum += Math.pow(base, (binary.length() - 1) - i);
			}
		}
		return baseNum;
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
            int N = scan.nextInt();
            int J = scan.nextInt();
            
            System.out.printf("Case #%d:%n", t + 1);
            out.write(String.format("Case #%d:%n", t + 1));
            
            Map<String, String> jamcoins = findJamcoins(N, J);
            
            for(Map.Entry<String, String> e : jamcoins.entrySet()){
            	System.out.printf("%s %s%n", e.getKey(), e.getValue());
                out.write(String.format("%s %s%n", e.getKey(), e.getValue()));
            }
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
