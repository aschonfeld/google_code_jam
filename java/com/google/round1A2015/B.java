package com.google.round1A2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class B {
	
	static class Solver implements Callable<String> {
		int B;
		double N;
		int[] M;
		boolean all_equal;
		
		public Solver(int B, double N, int[] M, boolean all_equal){
			this.B = B;
			this.N = N;
			this.M = M;
			this.all_equal = all_equal;
		}
		
		@Override
		public String call() throws Exception {
			Integer barber = 0;
			if(all_equal){
				barber = new Double(N % B).intValue();
            	if(barber == 0) barber = B;
			}else{
	        	List<Integer> barber_selections = new LinkedList<Integer>();
	            int[] curr_times = new int[B];
	            boolean all_done = false;
	            while(!all_done){
	            	all_done = true;
	            	List<Integer> curr_selections = new LinkedList<Integer>();
	            	for(int b=0;b<B;b++){
	            		if(curr_times[b] % M[b] == 0){
	            			barber = b + 1;
	            			curr_selections.add(barber);
	            			if(curr_times[b] == 0){
	            				all_done = false;
	            			}
	            		}else{
	            			all_done = false;
	            		}
	            		curr_times[b]++;
	            	}
	            	if(all_done) break;
	            	barber_selections.addAll(curr_selections);
	            }
	            Integer size = barber_selections.size();
	            int barber_idx = new Double(N % (size.doubleValue())).intValue();
	            if(barber_idx == 0){
	            	barber_idx = barber_selections.size();
	            }
	            barber_idx--;
	            barber = barber_selections.get(barber_idx);
			}
            return barber.toString();
		}
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
        
        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(7);
        Future<String>[] ts = new Future[T];
        for(int t=0;t<T;t++){
            int B = scan.nextInt();
            double N = scan.nextDouble();
            Set<Integer> times = new HashSet<Integer>();
            int[] M = new int[B];
            for(int b=0;b<B;b++){
            	M[b]= scan.nextInt();
            	times.add(M[b]);
            }
            
            ts[t] = service.submit(new Solver(B, N, M, times.size() == 1));
        }
        
        for (int t=0; t<T; t++) {
			while (!ts[t].isDone()) {
				Thread.sleep(500);
			}
			System.out.printf("Case #%d: %s%n", (t+1), ts[t].get());
            out.write(String.format("Case #%d: %s%n", (t+1), ts[t].get()));
		}
		service.shutdown();
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
