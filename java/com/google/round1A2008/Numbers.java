package com.google.round1A2008;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import com.google.qualification2010.FilelineHandler;
import com.google.qualification2010.Utils;

public class Numbers {

	static FilelineHandler handler = new FilelineHandler(){

		@Override
		public List<String> formatFileline(String fileLine) {
			return Arrays.asList(fileLine);
		}
		
	};
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		List<String> file_args = Utils.readFileIntoListOfStrings(args[0], handler);
	    if(file_args.size() == 0){
	    	return;
	    }
	    int i = 0;
		Integer tests = Integer.parseInt(file_args.get(i++));
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		double base = (3 + Math.sqrt(5));
		while(i < file_args.size()){
			Double n = Double.parseDouble(file_args.get(i++));
			String ret = new BigDecimal(Math.pow(base, n)).toString();
			System.out.print(n + " " + ret);
			int decimal = ret.indexOf(".");
			if(decimal > -1){
				ret = (ret.substring(0, decimal));
			}
			
			StringBuilder output = new StringBuilder(Utils.formatCaseNumber(caseNum++));
			output.append(" ");
			int ret_length = ret.length();
			if(ret_length < 3){
				for(int j=ret_length;j<3;j++){
					output.append(0);
				}
				output.append(ret);
			}else{
				output.append(ret.substring(ret_length - 3));
			}
			System.out.println(" " + output.toString());
			bw.write(output.toString());
			bw.newLine();
		}
		bw.close();
	}
}
