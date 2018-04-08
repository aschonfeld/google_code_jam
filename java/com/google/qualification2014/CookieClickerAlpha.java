package com.google.qualification2014;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class CookieClickerAlpha {
	
	public String run(String caseNum, Double farmCost, Double acc, Double goal){
		Double time = new Double(0);
		Double v = new Double(2);
		Double total = new Double(0);
		while(total < goal){
			Double timeIfBuyFarm = (goal / (v + acc)) + (farmCost / v);
			Double timeIfNoFarm = (goal / v);
			if(timeIfBuyFarm < timeIfNoFarm){
				time += (farmCost / v);
				v += acc;
			}else{
				time += timeIfNoFarm;
				break;
			}
		}
		return String.format("%s %.7f", caseNum, time);
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
		
		CookieClickerAlpha tester = new CookieClickerAlpha();
		i++; //skip case count
		while(i < file_args.size()){
			String[] vals = file_args.get(i++).split(" ");
			Double c = Double.parseDouble(vals[0]);
			Double f = Double.parseDouble(vals[1]);
			Double x = Double.parseDouble(vals[2]);
			bw.write(tester.run(String.format("Case #%d:", caseNum++), c, f, x));
			bw.newLine();
		}
		bw.close();
	}
}