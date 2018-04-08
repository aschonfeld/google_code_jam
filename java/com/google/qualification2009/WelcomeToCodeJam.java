package com.google.qualification2009;
import java.awt.Point;
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


public class WelcomeToCodeJam {
	
	final String W = "welcome to code jam";
	final int M = 10000;
	String S;
	int[][] memo;
	
	public String run(String caseNum, String input){
		this.S = input;
		this.memo = new int[500][W.length()];
		for (int i = 0; i < S.length(); ++i)
			Arrays.fill(memo[i], -1);
		return String.format("%s %04d", caseNum, go(0, 0));
	}
	
	int go(int s, int w){
		if (w == W.length())
			return 1;
		if (s == S.length())
			return 0;
		if (memo[s][w] > -1)
			return memo[s][w];
		int res = go(s + 1, w);
		if (S.charAt(s) == W.charAt(w))
			res = (res + go(s + 1, w + 1)) % M;
		return memo[s][w] = res;
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
	    WelcomeToCodeJam tester = new WelcomeToCodeJam();
		int i = 0;
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		fout.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		//Integer cases = Integer.parseInt(file_args.get(i++));
		i++; //skip case count
		while(i < file_args.size()){
			System.out.println(i);
			bw.write(tester.run(String.format("Case #%d:", caseNum++), file_args.get(i++)));
			bw.newLine();
		}
		bw.close();
	}
}
