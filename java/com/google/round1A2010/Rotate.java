package com.google.round1A2010;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Rotate {

    public String findWinners(String[][] board, int N, int K){
        StringBuilder red_w = new StringBuilder();
        StringBuilder blue_w = new StringBuilder();
        for(int k=0;k<K;k++){
            red_w.append("R");
            blue_w.append("B");
        }
        String red =red_w.toString();
        String blue = blue_w.toString();

        StringBuilder empty_b = new StringBuilder();
        for(int n=0;n<N;n++){
            empty_b.append(".");
        }
        String empty = empty_b.toString();

        Set<String> winners = new HashSet<String>();
        //check horizontal
        for(int i=(N-1);i>=0;i--){
            StringBuilder row = new StringBuilder();
            for(int j=0;j<N;j++){
                row.append(board[i][j]);
            }
            String r = row.toString();
            if(r.indexOf(red) > -1){
                winners.add("Red");
            }
            if(r.indexOf(blue) > -1){
                winners.add("Blue");
            }
            if(winners.size() == 2) return "Both";
            if(r.equals(empty)) break;
        }

        //check vertical
        for(int i=0;i<N;i++){
            StringBuilder column = new StringBuilder();
            for(int j=0;j<N;j++){
                column.append(board[j][i]);
            }
            String c = column.toString();
            if(c.indexOf(red) > -1){
                winners.add("Red");
            }
            if(c.indexOf(blue) > -1){
                winners.add("Blue");
            }
            if(winners.size() == 2) return "Both";
        }

        //check diagonal
        for(int i=0;i<N;i++){
            StringBuilder diag = new StringBuilder();
            for(int j=i;j>=0;j--){
                diag.append(board[j][Math.abs(j-i)]);
            }
            String d = diag.toString();
            if(d.indexOf(red) > -1){
                winners.add("Red");
            }
            if(d.indexOf(blue) > -1){
                winners.add("Blue");
            }
            if(winners.size() == 2) return "Both";
        }
        for(int i=1;i<=N;i++){
            StringBuilder diag = new StringBuilder();
            for(int j=(N-1);j>=i;j--){
                diag.append(board[j][Math.abs(i+((N-1)-j))]);
            }
            String d = diag.toString();
            if(d.indexOf(red) > -1){
                winners.add("Red");
            }
            if(d.indexOf(blue) > -1){
                winners.add("Blue");
            }
            if(winners.size() == 2) return "Both";
        }

        for(int i=0;i<N;i++){
            StringBuilder diag = new StringBuilder();
            for(int j=i;j>=0;j--){
                diag.append(board[Math.abs(j-i)][Math.abs(j-N) - 1]);
            }
            String d = diag.toString();
            if(d.indexOf(red) > -1){
                winners.add("Red");
            }
            if(d.indexOf(blue) > -1){
                winners.add("Blue");
            }
            if(winners.size() == 2) return "Both";
        }
        for(int i=(N-2);i>=0;i--){
            StringBuilder diag = new StringBuilder();
            for(int j=i;j>=0;j--){
                diag.append(board[Math.abs(j-N)-1][Math.abs(j-i)]);
            }
            String d = diag.toString();
            if(d.indexOf(red) > -1){
                winners.add("Red");
            }
            if(d.indexOf(blue) > -1){
                winners.add("Blue");
            }
            if(winners.size() == 2) return "Both";
        }
        for(String s : winners) return s;
        return "Neither";
    }

    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\aschonfeld\\Desktop\\tmp.out"));
        String firstLine = scan.nextLine();
        Boolean isFile = !Character.isDigit(firstLine.charAt(0));
        int T;
        if(isFile){
            scan = new Scanner(new FileReader(firstLine));
            T = scan.nextInt();
        }else{
            T = Integer.parseInt(firstLine);
        }
        Rotate r = new Rotate();
        for(int t=1;t<=T;t++){
            int N = scan.nextInt();
            int K = scan.nextInt();

            scan.nextLine();
            String[][] board = new String[N][N];
            for(int i=0;i<N;i++){
                String line = scan.nextLine();
                int j=0;
                for(Character c : line.toCharArray()){
                    board[i][j++] = c.toString();
                }
            }
            //rotate
            String [][] rotated_board = new String[N][N];
            for(int i=0;i<N;i++){
                for(int j=0;j<N;j++){
                    rotated_board[j][(N-1)-i] = board[i][j];
                }
            }
            //drop to bottom
            boolean moves_made = true;
            while(moves_made){
                moves_made = false;
                for(int i=(N-2);i>=0;i--){
                    for(int j=0;j<N;j++){
                        if(!rotated_board[i][j].equals(".") && rotated_board[i+1][j].equals(".")){
                            rotated_board[i+1][j] = rotated_board[i][j];
                            rotated_board[i][j] = ".";
                            moves_made = true;
                        }
                    }
                }
            }

            String winner = r.findWinners(rotated_board, N, K);
            System.out.printf("Case #%d: %s%n", t, winner);
            out.write(String.format("Case #%d: %s%n", t, winner));
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }

}
