package com.google.qualification2013;

import java.util.*;

public class Treasure {

    private Map<Integer, List<Chest>> chests = new HashMap<Integer, List<Chest>>();
    private int chest_ct = 1;

    public void add_chest(int key_type, int keys, int[] enclosed_key_types){
        if(!chests.containsKey(key_type)){
            chests.put(key_type, new LinkedList<Chest>());
        }
        chests.get(key_type).add(new Treasure.Chest(this.chest_ct++, key_type, keys, enclosed_key_types));
    }

    public String search(int[] keys){
        Queue<Integer> curr_keys = new LinkedList<Integer>();
        for(int k : keys) curr_keys.add(k);
        Set<Integer> opened = new HashSet<Integer>();
        String sep = "";
        StringBuilder output = new StringBuilder();
        while(!curr_keys.isEmpty()){
            int key = curr_keys.poll();
            Chest selected = null;
            int best_wt = 0;
            if(chests.containsKey(key)){
            	for(Chest c : chests.get(key)){
            		if(!opened.contains(c.id)) {
            			int wt = 1;
            			for (Integer k : c.enclosed_key_types) {
            				if(chests.containsKey(k)){
	            				for (Chest c2 : chests.get(k))
	            					if (c.id != c2.id && !opened.contains(c2.id)) wt++;
            				}
            			}
            			if (wt > best_wt) {
            				selected = c;
            				best_wt = wt;
            			}else if(wt == best_wt){
            				if(c.id < selected.id){
            					selected = c;
                				best_wt = wt;
            				}
            			}
            		}
            	}
            }
            if(selected != null){
	            this.chests.get(selected.key_type).remove(selected);
	            opened.add(selected.id);
	            output.append(sep).append(selected.id);
	            sep = " ";
	            for(int k : selected.enclosed_key_types) curr_keys.add(k);
            }
        }
        if(opened.size() < (chest_ct - 1))
            return "IMPOSSIBLE";
        return output.toString();
    }



    public class Chest{

        int id;
        int key_type;
        int keys;
        int[] enclosed_key_types;

        public Chest(int id, int key_type, int keys, int[] enclosed_key_types){
            this.id = id;
            this.key_type = key_type;
            this.keys = keys;
            this.enclosed_key_types = enclosed_key_types;
        }
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        for(int t=1;t<=T;t++){
            int K = scan.nextInt();//keys
            int N = scan.nextInt();//chests
            int[] key_types = new int[K];
            for(int k=0;k<K;k++){
                key_types[k] = scan.nextInt();
            }
            Treasure treasure = new Treasure();
            for(int n=0;n<N;n++){
                int c_type = scan.nextInt();
                int c_keys = scan.nextInt();
                int[] c_enclosed_key_types = new int[c_keys];
                for(int ck=0;ck<c_keys;ck++){
                    c_enclosed_key_types[ck] = scan.nextInt();
                }
                treasure.add_chest(c_type, c_keys, c_enclosed_key_types);
            }

            System.out.printf("Case #%d: %s%n", t, treasure.search(key_types));
        }
    }
}