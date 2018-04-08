package com.google.qualification2014;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class DeceitfulWar {
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for(int t=1; t<=T; t++) {
			int N = in.nextInt();
			Double[] naomi = new Double[N];
			List<Double> naomiL = new ArrayList<Double>(N);
			for(int n=0;n<N;n++){
				naomi[n] = in.nextDouble();
				naomiL.add(naomi[n]);
			}
			Double[] ken = new Double[N];
			List<Double> kenL = new ArrayList<Double>(N);
			for(int n=0;n<N;n++){
				ken[n] = in.nextDouble();
				kenL.add(ken[n]);
			}
			Collections.sort(naomiL);
			Collections.sort(kenL);
			int totalWar = 0;
			for(int n=N-1; n>=0; n--){
				if(naomiL.get(n) > kenL.get(n)){
					totalWar++;
					kenL.remove(0);
				}else{
					kenL.remove(n);
				}
				naomiL.remove(n);
			}
			
			naomiL.clear();
			for(Double d : naomi) naomiL.add(d);
			kenL.clear();
			for(Double d: ken) kenL.add(d);
			Collections.sort(naomiL);
			Collections.sort(kenL);
			int totalDeceitfulWar = 0;
			for(int n=N-1; n>=0; n--){
				if(naomiL.get(n) < kenL.get(n)){
					naomiL.remove(0);
					kenL.remove(n);
				}else{
					totalDeceitfulWar++;
					naomiL.remove(n);
					kenL.remove(n);
				}
			}
			
			System.out.printf("Case #%d: %d %d%n", t, totalDeceitfulWar, totalWar);
		}
	}
}