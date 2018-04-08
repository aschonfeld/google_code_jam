package com.google.round1A2008;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.qualification2010.FilelineHandler;
import com.google.qualification2010.Utils;

public class Milkshakes {
	
	static int NO_SELECTION = 2;
	
	static FilelineHandler handler = new FilelineHandler(){

		@Override
		public List<String> formatFileline(String fileLine) {
			return Arrays.asList(fileLine.split(" "));
		}
		
	};
	
	public String findPossibleFlavorCombo(String caseNum, Integer flavors, List<List<Integer[]>> customer_flavors){
		Integer[] final_selections = new Integer[flavors + 1];
		for(int i=0;i<final_selections.length;i++){
			final_selections[i] = NO_SELECTION;
		}
		
		boolean customer_flavors_changed = true;
		
		while(customer_flavors_changed && (customer_flavors.size() > 0)){
			customer_flavors_changed = false;
			List<Integer> customers_to_remove = new ArrayList<Integer>();
			
			//handle customers with only one flavor selection
			for(int i=0;i<customer_flavors.size();i++){
				if(customer_flavors.get(i).size() == 1){
					Integer[] flavor = customer_flavors.get(i).get(0);
					if(final_selections[flavor[0]] == NO_SELECTION){
						final_selections[flavor[0]] = flavor[1];
						customers_to_remove.add(i);
						customer_flavors_changed = true;
						continue;
					}else if(final_selections[flavor[0]] == flavor[1]){
						customers_to_remove.add(i);
						customer_flavors_changed = true;
						continue;
					}else{
						return String.format("%s IMPOSSIBLE", caseNum);
					}
				}
			}
			
			for(int i=customers_to_remove.size()-1;i>=0;i--){
				customer_flavors.remove(customers_to_remove.get(i).intValue());
			}
			customers_to_remove.clear();
			
			//customers who have a favorite similar to the ones from the previous loop
			for(int i=0;i<customer_flavors.size();i++){
				List<Integer[]> curr_customer_flavors = customer_flavors.get(i);
				List<Integer> flavors_to_remove = new ArrayList<Integer>();
				for(int j=0; j<curr_customer_flavors.size(); j++){
					Integer[] flavor = curr_customer_flavors.get(j);
					if(final_selections[flavor[0]] == flavor[1]){
						customers_to_remove.add(i);
						customer_flavors_changed = true;
						break;
					}else if(final_selections[flavor[0]] != NO_SELECTION){
						flavors_to_remove.add(j);
						customer_flavors_changed = true;
					}
				}
				for(int j=flavors_to_remove.size()-1;j>=0;j--){
					curr_customer_flavors.remove(flavors_to_remove.get(j).intValue());
				}
				if(curr_customer_flavors.isEmpty()){
					return String.format("%s IMPOSSIBLE", caseNum);
				}
			}
			
			for(int i=customers_to_remove.size()-1;i>=0;i--){
				customer_flavors.remove(customers_to_remove.get(i).intValue());
			}
		}
		
		StringBuilder output = new StringBuilder(caseNum);
		for(int j=1;j<final_selections.length;j++){
			output.append(" ").append(final_selections[j] % NO_SELECTION);
		}
		return output.toString();
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		List<String> file_args = Utils.readFileIntoListOfStrings(args[0], handler);
	    if(file_args.size() == 0){
	    	return;
	    }
	    Milkshakes processor = new Milkshakes();
		int i = 0;
		Integer tests = Integer.parseInt(file_args.get(i++));
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		while(i < file_args.size()){
			Integer flavors = Integer.parseInt(file_args.get(i++));
			Integer customers = Integer.parseInt(file_args.get(i++));
			List<List<Integer[]>> customer_flavors = new ArrayList<List<Integer[]>>(customers);
			for(int j=0;j<customers;j++){
				Integer favoriteFlavorsCt = Integer.parseInt(file_args.get(i++));
				List<Integer[]> curr_customer_flavors = new ArrayList<Integer[]>(favoriteFlavorsCt);
				for(int k=0;k<favoriteFlavorsCt;k++){
					Integer flavor = Integer.parseInt(file_args.get(i++));
					Integer isMalted = Integer.parseInt(file_args.get(i++));
					curr_customer_flavors.add(new Integer[]{flavor, isMalted});
				}
				customer_flavors.add(curr_customer_flavors);
			}
			bw.write(processor.findPossibleFlavorCombo(Utils.formatCaseNumber(caseNum++), flavors, customer_flavors));
			bw.newLine();
		}
		bw.close();
	}

}
