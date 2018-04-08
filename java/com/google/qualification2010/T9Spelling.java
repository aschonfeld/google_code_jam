package com.google.qualification2010;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class T9Spelling {
	
	private static final Map<Character, String> t9Map;
    static {
        Map<Character, String> aMap = new HashMap<Character, String>();
        aMap.put('a', "2");
        aMap.put('b', "22");
        aMap.put('c', "222");
        aMap.put('d', "3");
        aMap.put('e', "33");
        aMap.put('f', "333");
        aMap.put('g', "4");
        aMap.put('h', "44");
        aMap.put('i', "444");
        aMap.put('j', "5");
        aMap.put('k', "55");
        aMap.put('l', "555");
        aMap.put('m', "6");
        aMap.put('n', "66");
        aMap.put('o', "666");
        aMap.put('p', "7");
        aMap.put('q', "77");
        aMap.put('r', "777");
        aMap.put('s', "7777");
        aMap.put('t', "8");
        aMap.put('u', "88");
        aMap.put('v', "888");
        aMap.put('w', "9");
        aMap.put('x', "99");
        aMap.put('y', "999");
        aMap.put('z', "9999");
        aMap.put(' ', "0");
        t9Map = Collections.unmodifiableMap(aMap);
    }

	static FilelineHandler handler = new FilelineHandler(){

		@Override
		public List<String> formatFileline(String fileLine) {
			return Arrays.asList(new String[]{fileLine});
		}
		
	};
	
	
	public String formatT9(String caseNum, String input){
		Character lastChar = null;
		StringBuilder t9output = new StringBuilder(caseNum);
		t9output.append(" ");
		for(char c : input.toLowerCase().toCharArray()){
			String t9rep = t9Map.get(c);
			if(lastChar != null && lastChar == t9rep.charAt(0)){
				t9output.append(" ");
			}
			t9output.append(t9rep);
			lastChar = t9rep.charAt(0);
		}
		return t9output.toString();
	}
	
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		List<String> file_args = Utils.readFileIntoListOfStrings(args[0], handler);
	    if(file_args.size() == 0){
	    	return;
	    }
	    T9Spelling processor = new T9Spelling();
	    int i = 0;
		Integer tests = Integer.parseInt(file_args.get(i++));
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		while(i < file_args.size()){
			bw.write(processor.formatT9(Utils.formatCaseNumber(caseNum++), file_args.get(i++)));
			bw.newLine();
		}
		bw.close();
	}
}
