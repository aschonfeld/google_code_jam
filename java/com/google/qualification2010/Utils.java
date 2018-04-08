package com.google.qualification2010;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Utils {
	
	public static List<String> readFileIntoListOfStrings(String filename, FilelineHandler handler) throws IOException{
		BufferedReader br = null;
		List<String> file_args = new ArrayList<String>();
	    try {
	    	br = new BufferedReader(new FileReader(filename));
	        String line = br.readLine();

	        while (line != null) {
	            file_args.addAll(handler.formatFileline(line));
	        	line = br.readLine();
	        }
	    } catch(Exception e){
	    	System.out.println(e.toString());
	    } finally {
	    	if(br != null){
	    		br.close();
	    	}
	    }
	    return file_args;
	}
	
	public static String formatCaseNumber(Integer caseNum){
		return String.format("Case #%d:", caseNum);
	}
}
