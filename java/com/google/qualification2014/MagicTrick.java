package com.google.qualification2014;
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


public class MagicTrick {
	
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
	            file_args.add(line);
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
	    int i = 0;
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		fout.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		i++; //skip case count
		while(i < file_args.size()){
			Integer answer1 = Integer.parseInt(file_args.get(i));
			i = i + answer1;
			Set<String> answer1Vals = new HashSet<String>(Arrays.asList(file_args.get(i).split(" ")));
			i = i + (4 - answer1);
			i++;
			Integer answer2 = Integer.parseInt(file_args.get(i));
			i = i + answer2;			
			Set<String> answer2Vals = new HashSet<String>(Arrays.asList(file_args.get(i).split(" ")));
			i = i + (4 - answer2);
			i++;
			answer1Vals.retainAll(answer2Vals);
			switch(answer1Vals.size()){
				case 1:
					bw.write(String.format("Case #%d: %s", caseNum++, answer1Vals.iterator().next()));
					break;
				case 0:
					bw.write(String.format("Case #%d: Volunteer cheated!", caseNum++));
					break;
				default:
					bw.write(String.format("Case #%d: Bad magician!", caseNum++));
					break;
			}
			bw.newLine();
		}
		bw.close();
	}
}