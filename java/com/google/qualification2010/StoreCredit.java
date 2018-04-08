package com.google.qualification2010;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class StoreCredit {
	
	static FilelineHandler handler = new FilelineHandler(){

		@Override
		public List<String> formatFileline(String fileLine) {
			return Arrays.asList(fileLine.split(" "));
		}
		
	};
	
	private List<Integer> get_valid_prices_by_index(List<Integer> indices, List<Integer> valid_prices){
		List<Integer> vp_by_idx = new ArrayList<Integer>();
		for(Integer i : indices){
			vp_by_idx.add(valid_prices.get(i));
		}
		return vp_by_idx;
	}
	private int subset_sum(Collection<Integer> valid_prices, Integer[] prices){
		int sum = 0;
		for(Integer vp : valid_prices){
			sum += prices[vp];
		}
		return sum;		
	}
	
	private String outputResult(String caseNum, Collection<Integer> selected_prices){
		StringBuilder builder = new StringBuilder(caseNum);
		for(Integer i : selected_prices){
			builder.append(" " + (i + 1));
		}
		return builder.toString();
	}
	
	public String runTest(String caseNum, Integer credit, Integer items, Integer[] prices){
		List<List<Integer>> subsets = new ArrayList<List<Integer>>();
		List<Integer> valid_items = new ArrayList<Integer>();
		for(int i=0;i<items;i++){
			if(credit > prices[i]){
				subsets.add(Arrays.asList(new Integer[]{valid_items.size()}));
				valid_items.add(i);
			}
		}
		
		while(!subsets.isEmpty()){
			List<List<Integer>> ret_subsets = new ArrayList<List<Integer>>();
			for(List<Integer> s : subsets){
				int last = s.get(s.size()-1);
				if(last < (items - 1)){
					for(int i=(last+1);i<valid_items.size();i++){
						List<Integer> ret_subset = new ArrayList<Integer>(s);
						ret_subset.add(i);
						List<Integer> vp_by_idx = get_valid_prices_by_index(ret_subset, valid_items);
						switch(credit.compareTo(subset_sum(vp_by_idx, prices))){
							case 0:
								return outputResult(caseNum, vp_by_idx);
							case 1:
								ret_subsets.add(ret_subset);
						}
					}
				}
			}
			subsets.clear();
			subsets.addAll(ret_subsets);
		}
		
		return "";
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		List<String> file_args = Utils.readFileIntoListOfStrings(args[0], handler);
	    if(file_args.size() == 0){
	    	return;
	    }
	    StoreCredit tester = new StoreCredit();
		int i = 0;
		Integer tests = Integer.parseInt(file_args.get(i++));
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		while(i < file_args.size()){
			Integer credit = Integer.parseInt(file_args.get(i++));
			Integer items = Integer.parseInt(file_args.get(i++));
			Integer[] prices = new Integer[items];
			for(int j=0; j<items; j++){
				prices[j] = Integer.parseInt(file_args.get(i++));
			}
			bw.write(tester.runTest(Utils.formatCaseNumber(caseNum++), credit, items, prices));
			bw.newLine();
		}
		bw.close();
	}
}
