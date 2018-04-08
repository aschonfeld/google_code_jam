package com.google.qualification2009;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AlienLanguage {
	
	public List<String> readFileIntoListOfStrings(String filename) throws IOException{
		BufferedReader br = null;
		List<String> file_args = new ArrayList<String>();
	    try {
	    	br = new BufferedReader(new FileReader(filename));
	        String line = br.readLine();

	        while (line != null) {
	            file_args.addAll(Arrays.asList(line.split(" ")));
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
	
	public int findPossibleWordCount(List<String> words, List<Set<Character>> test){
		if(test.isEmpty()){
			return 0;
		}
		int i = 0;
		List<String> possibleWords = new ArrayList<String>(words);
		while(i < test.size() && !possibleWords.isEmpty()){
			Set<Character> subPattern = test.get(i);
			List<String> invalidWords = new ArrayList<String>();
			for(String w : possibleWords){
				if(!subPattern.contains(w.charAt(i))){
					invalidWords.add(w);
				}
			}
			possibleWords.removeAll(invalidWords);
			i++;		
		}
		return possibleWords.size();
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			return;
		}
		
		BufferedReader br = null;
		List<String> file_args = new ArrayList<String>();
	    try {
	    	br = new BufferedReader(new FileReader(args[0]));
	        String line = br.readLine();
	        
	        while (line != null) {
	            file_args.addAll(Arrays.asList(line.split(" ")));
	        	line = br.readLine();
	        }
	    } catch(Exception e){
	    	System.out.println(e.toString());
	    } finally {
	    	if(br != null){
	    		br.close();
	    	}
	    }
	    
		if(file_args.size() == 0){
	    	return;
	    }
	    AlienLanguage tester = new AlienLanguage();
		int i = 0;
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		fout.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		while(i < file_args.size()){
			Integer wordLength = Integer.parseInt(file_args.get(i++));
			Integer wordCount = Integer.parseInt(file_args.get(i++));
			Integer testCount = Integer.parseInt(file_args.get(i++));
			
			List<String> words = new ArrayList<String>(wordCount);
			for(int j=0; j<wordCount; j++){
				words.add(file_args.get(i++));
			}
			
			for(int j=0; j<testCount;j++){
				String testStr = file_args.get(i++);
				boolean inSet = false;
				List<Set<Character>> test = new ArrayList<Set<Character>>(testCount);
				Set<Character> subPattern = new HashSet<Character>();
				for(int k=0;k<testStr.length();k++){
					if(testStr.charAt(k) == '('){
						inSet = true;
					}else if(testStr.charAt(k) == ')'){
						inSet = false;
						test.add(subPattern);
						subPattern = new HashSet<Character>();
					}else{
						subPattern.add(testStr.charAt(k));
						if(!inSet){
							test.add(subPattern);
							subPattern = new HashSet<Character>();
						}
					}
				}
				bw.write(String.format("Case #%d: %d", caseNum++, tester.findPossibleWordCount(words, test)));
				bw.newLine();
				
			}
		}
		bw.close();
	}
}
