package com.google.veterans2013;

import java.util.Scanner;

import com.google.qualification2013.Treasure;

public class Oceanview {

	public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        for(int t=1;t<=T;t++){
            int N = scan.nextInt();//houses
            int[] houses = new int[N];
            for(int n=0;n<N;n++){
            	houses[n] = scan.nextInt();
            }
            
            int[] P = new int[N];
            int[] M = new int[N+1];
            int L = 0;
            for(int i=0;i<N;i++){
            	int lo = 1;
            	int hi = L;
            	while(lo <= hi){
            		int mid = (int) Math.ceil((lo+hi)/2);
            		if(houses[M[mid]] < houses[i]){
            			lo = mid+1;
            		}else{
            			hi = mid-1;
            		}
            	}
            	
            	int newL = lo;
            	P[i] = M[newL-1];
            	M[newL] = i;
            	
            	if(newL > L){
            		L = newL;
            	}
            }
            
            System.out.printf("Case #%d: %d%n", t, N - L);
        }
    }
}
