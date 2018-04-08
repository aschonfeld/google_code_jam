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
import java.util.List;

public class ReverseWords {
	
	static FilelineHandler handler = new FilelineHandler(){

		@Override
		public List<String> formatFileline(String fileLine) {
			return Arrays.asList(new String[]{fileLine});
		}
		
	};
	
	public String reverseWordString(String caseNum, String input){
		String[] words = input.split(" ");
		StringBuilder reversedString = new StringBuilder(caseNum);
		for(int i=words.length-1;i>=0;i--){
			reversedString.append(" ").append(words[i]);
		}
		return reversedString.toString();
	}

	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		List<String> file_args = Utils.readFileIntoListOfStrings(args[0], handler);
	    if(file_args.size() == 0){
	    	return;
	    }
	    ReverseWords processor = new ReverseWords();
	    int i = 0;
		Integer tests = Integer.parseInt(file_args.get(i++));
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		while(i < file_args.size()){
			bw.write(processor.reverseWordString(Utils.formatCaseNumber(caseNum++), file_args.get(i++)));
			bw.newLine();
		}
		bw.close();
	}
}
