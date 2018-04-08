package com.google.round1A2008;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import com.google.qualification2010.FilelineHandler;
import com.google.qualification2010.Utils;

public class MinimumScalarProduct {

	static FilelineHandler handler = new FilelineHandler(){

		@Override
		public List<String> formatFileline(String fileLine) {
			return Arrays.asList(fileLine.split(" "));
		}
		
	};
	
	public String findMinimumScalarProduct(String caseNum, Integer coords, Long[] v1, Long[] v2){
		Long minProduct = 0l;
		Arrays.sort(v1);
		Arrays.sort(v2);
		
		int j=coords;
		for(int i=0;i<coords;i++){
			minProduct += (v1[i] * v2[--j]);
		}
		return String.format("%s %d", caseNum, minProduct);
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		List<String> file_args = Utils.readFileIntoListOfStrings(args[0], handler);
	    if(file_args.size() == 0){
	    	return;
	    }
	    MinimumScalarProduct processor = new MinimumScalarProduct();
		int i = 0;
		Integer tests = Integer.parseInt(file_args.get(i++));
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		while(i < file_args.size()){
			Integer coords = Integer.parseInt(file_args.get(i++));
			Long[] v1 = new Long[coords];
			for(int j=0; j<coords; j++){
				v1[j] = Long.parseLong(file_args.get(i++));
			}
			Long[] v2 = new Long[coords];
			for(int j=0; j<coords; j++){
				v2[j] = Long.parseLong(file_args.get(i++));
			}
			bw.write(processor.findMinimumScalarProduct(Utils.formatCaseNumber(caseNum++), coords, v1, v2));
			bw.newLine();
		}
		bw.close();
	}
}
