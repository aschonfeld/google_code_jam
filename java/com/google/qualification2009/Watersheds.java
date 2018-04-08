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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Watersheds {
	
	public String findBasins(String caseNum, Integer[][] map, Integer height, Integer width){
		StringBuilder output = new StringBuilder(caseNum);
		output.append("\n");
		String[][] basinStrMap = new String[height][width];
		Map<Point, Point> basinMap = new HashMap<Point, Point>();
		Map<Point, Integer> basins = new HashMap<Point, Integer>();
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				int curr = map[i][j];
				int[] basin_coord = null;
				int basin_val = Integer.MAX_VALUE;
				//check north
				if((i != 0) && (map[i-1][j] < curr)){
					basin_coord = new int[]{i-1, j};
					basin_val = map[i-1][j];
				}
				//check west
				if((j != 0) && (map[i][j-1] < curr) && (map[i][j-1] < basin_val)){
					basin_coord = new int[]{i, j-1};
					basin_val = map[i][j-1];
				}
				//check east
				if((j != (width-1)) && (map[i][j+1] < curr) && (map[i][j+1] < basin_val)){
					basin_coord = new int[]{i, j+1};
					basin_val = map[i][j+1];
				}
				//check south
				if((i != (height-1)) && (map[i+1][j] < curr) && (map[i+1][j] < basin_val)){
					basin_coord = new int[]{i+1, j};
					basin_val = map[i+1][j];
				}
				if(basin_coord != null){
					basinMap.put(new Point(j, i), new Point(basin_coord[1], basin_coord[0]));
				}else{
					basins.put(new Point(j, i), 0);
				}
			}
		}
		int asciiBasin = 97; //'a' character in ascii
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				Point curr = new Point(j, i);
				while(!basins.containsKey(curr)){
					curr = new Point(basinMap.get(curr).x, basinMap.get(curr).y);
				}
				if(basins.get(curr) == 0){
					basinStrMap[i][j] = Character.toString((char) asciiBasin);
					basins.put(curr, asciiBasin++);
				}else{
					basinStrMap[i][j] = Character.toString((char) basins.get(curr).intValue());
				}
			}
		}
		
		String newLine = "";
		for(int i=0;i<height;i++){
			String sep = "";
			output.append(newLine);
			for(int j=0; j<width; j++){
				output.append(sep).append(basinStrMap[i][j]);
				sep = " ";
			}
			newLine = "\n";
		}
		return output.toString();
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
	    Watersheds tester = new Watersheds();
		int i = 0;
		int caseNum = 1;
		String outputFile = args[0].replace("\\test_inputs\\", "\\results\\").replace(".in",".out");
		File fout = new File(outputFile);
		fout.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		Integer maps = Integer.parseInt(file_args.get(i++));
		while(i < file_args.size()){
			String[] mapDimensions = file_args.get(i++).split(" ");
			int j=0;
			Integer height = Integer.parseInt(mapDimensions[j++]);
			Integer width = Integer.parseInt(mapDimensions[j++]);
			
			Map<Integer[], Integer> altitudes = new HashMap<Integer[], Integer>();
			Integer[][] map = new Integer[height][width];
			for(j=0;j<height;j++){
				String[] altStrs = file_args.get(i++).split(" ");
				for(int k=0;k<altStrs.length;k++){
					map[j][k] = Integer.parseInt(altStrs[k]);
				}
			}
			bw.write(tester.findBasins(String.format("Case #%d:", caseNum++), map, height, width));
			bw.newLine();
		}
		bw.close();
	}
}
