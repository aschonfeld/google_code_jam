package com.google.chinapractice2014;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;


public class BadHorse {
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for(int t=1; t<=T; t++) {
			int M = in.nextInt();
			Map<String, Set<String>> haters = new HashMap<String, Set<String>>();
			List<String> names = new ArrayList<String>();
			for(int m=0;m<M;m++){
				String name1 = in.next();
				String name2 = in.next();
				if(!haters.containsKey(name1)){
					haters.put(name1, new HashSet<String>());
					names.add(name1);
				}
				haters.get(name1).add(name2);
				if(!haters.containsKey(name2)){
					haters.put(name2, new HashSet<String>());
					names.add(name2);
				}
				haters.get(name2).add(name1);
			}
			Map<String, Integer> group_mappings = new HashMap<String, Integer>();
			for(String n : names)
				group_mappings.put(n, -1);
			Queue<String> q = new LinkedList<String>();
			q.add(names.get(0));
			group_mappings.put(names.get(0), 0);
			boolean yes = true;
			while(!q.isEmpty() && yes){
				String curr_name = q.poll();
				Integer curr_group = group_mappings.get(curr_name);
				for(String e : haters.get(curr_name)){
					if(group_mappings.get(e) == -1){
						q.add(e);
						group_mappings.put(e, curr_group == 0 ? 1 : 0);
					}else if(group_mappings.get(e) == curr_group){
						yes = false;
						break;
					}
				}
			}
			
			System.out.printf("Case #%d: %s%n", t, yes ? "Yes" : "No");
		}
	}
}