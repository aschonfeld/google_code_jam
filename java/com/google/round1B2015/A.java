package com.google.round1B2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class A {
	
	static class Solver implements Callable<Integer> {
		
		Double N;
		
		public Solver(Double N){
			this.N = N;
		}
		
		private Double reverseNum(Double n){
			String curr_str = String.format("%.0f", n);
	    	curr_str = new StringBuilder(curr_str).reverse().toString();
	    	return Double.parseDouble(curr_str);
		}
		
		@Override
		public Integer call(){
			if(N.equals(1d)) return 1;
			int ct = 1;
			List<Double> curr_iteration_vals = new LinkedList<Double>();
			curr_iteration_vals.add(new Double(1));
	        Set<Double> observed = new HashSet<Double>();
	        observed.addAll(curr_iteration_vals);
	        while(!curr_iteration_vals.isEmpty()){
	        	Queue<Double> vals_to_check = new LinkedList<Double>();
	            vals_to_check.addAll(curr_iteration_vals);
	            curr_iteration_vals.clear();
	            ct++;
		        while(!vals_to_check.isEmpty()){
		        	Double curr = vals_to_check.poll();
		        	Double next = curr + 1;
		        	if(next.equals(N)) return ct;
		        	if(!observed.contains(next)){
		        		curr_iteration_vals.add(next);
		        		observed.add(next);
		        	}
		        	Double rev = reverseNum(curr);
		        	if(rev.equals(N)) return ct;
		        	if(!observed.contains(rev)){
		        		curr_iteration_vals.add(rev);
		        		observed.add(rev);
		        	}
		        }
	        }
	        return ct;
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
        //ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(7);
        //Future<Integer>[] ts = new Future[T];
        for(int t=0;t<T;t++){
            Double N = scan.nextDouble();
            Integer ct = new Solver(N).call();
            System.out.printf("Case #%d: %d%n", t+1, ct);
            out.write(String.format("Case #%d: %d%n", t+1, ct));
            //ts[t] = service.submit(new Solver(N));
        }
        
//        for (int t=0; t<T; t++) {
//			while (!ts[t].isDone()) {
//				Thread.sleep(500);
//			}
//			System.out.printf("Case #%d: %d%n", t+1, ts[t].get());
//            out.write(String.format("Case #%d: %d%n", t+1, ts[t].get()));
//		}
//		service.shutdown();
		
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
