package com.google.qualification2015;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
	
public class B {
	
	public int run(List<Integer> diners, int minute){
		List<Integer> curr_diners = new LinkedList<Integer>(diners);
		int min_time = diners.get(0);
		int curr_minute = 0;
		while(!(curr_diners.get(0) == 1)){
        	List<Integer> special = simulate_special(curr_diners);
        	curr_minute++;
        	if(curr_minute + special.get(0) < min_time){
        		min_time = curr_minute + special.get(0);
        	}
        	curr_diners = special;
        }
        return min_time + minute;
	}
	
	public int run2(List<Integer> diners){
		List<Integer> curr_diners = new LinkedList<Integer>(diners);
		int min_time = diners.get(0);
		int curr_minute = 0;
		while(!(curr_diners.get(0) == 1)){
			if(curr_minute + curr_diners.get(0) < min_time){
        		min_time = curr_minute + curr_diners.get(0);
        	}
			
			if(curr_diners.get(0) % 2 == 1){
				curr_diners = simulate_eat(curr_diners);
			}else{
				curr_diners = simulate_special(curr_diners);
			}
        	curr_minute++;
        }
        return min_time;
	}
	
	public Integer check_all(List<Integer> diners){
		int minute = 0;
		List<Integer> curr_diners = new LinkedList<Integer>(diners);
		int min = run(curr_diners, minute);
		curr_diners = simulate_eat(curr_diners);
		minute++;
		while(!curr_diners.isEmpty()){
			int curr_min = run(curr_diners, minute);
			if(curr_min < min){
				min = curr_min;
			}
			minute++;
			curr_diners = simulate_eat(curr_diners);
		}
		return min;
	}
	
	private List<Integer> simulate_eat(List<Integer> diners){
		List<Integer> updated_diners = new LinkedList<Integer>();
		for(Integer diner : diners){
			if((diner - 1) > 0){
				updated_diners.add(diner - 1);
			}
		}
		Collections.sort(updated_diners, Collections.reverseOrder());
		return updated_diners;
	}
	
	private List<Integer> simulate_special(List<Integer> diners){
		List<Integer> updated_diners = new LinkedList<Integer>();
		int max_diner = diners.get(0);
		if(max_diner == 1){
			updated_diners.add(max_diner);
		}else if(max_diner % 2 == 1){
			updated_diners.add((max_diner/2) + 1);
			updated_diners.add(max_diner/2);
		}else if(max_diner % 2 == 0){
			updated_diners.add(max_diner/2);
			updated_diners.add(max_diner/2);
		}
		updated_diners.addAll(diners.subList(1, diners.size()));
		Collections.sort(updated_diners, Collections.reverseOrder());
		return updated_diners;
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
            int D = scan.nextInt();
            List<Integer> diners = new LinkedList<Integer>();
            for(int d=0;d<D;d++){
            	int diner_pancakes = scan.nextInt();
            	diners.add(diner_pancakes);
            }
            Collections.sort(diners, Collections.reverseOrder());
            
            B b = new B();
            int time = b.run2(diners);
            System.out.printf("Case #%d: %d%n", t, time);
            out.write(String.format("Case #%d: %d%n", t, time));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}
