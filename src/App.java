package src;
import java.io.*;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class App{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int row=-1, col=-1;//word length counter and counter for word comparison
        int[] find = {0,0}; // 1 0 for found, counter per kata
        char[][] matrix;
        String search;
        String namaf;
        System.out.println("Welcome to Word Search Solver!\nPlease insert the puzzle file name : ");
        namaf = sc.nextLine();
        try{
            namaf = (Path.of("../test", namaf)).toString();
            File myFile = new File(namaf);
            Scanner scnn = new Scanner(myFile);
            int m = 0; //total row 
            int n = 0; //total col
            String data=scnn.nextLine();
            while(data.length() != 0){
                String array[] = data.split(" ");
                n = 0;
                for (String i : array){
                    n++;
                }
                m++;
                data = scnn.nextLine();
            }
            scnn.close();

            File myFile2 = new File(namaf);
            Scanner scn = new Scanner(myFile2);
            matrix = new char[m][n];
            int i = 0;
            String data1=scn.nextLine();
            while(data1.length()!=0){
                String rows = data1.replaceAll("\\s","");
                char[] array2 = rows.toCharArray();
                for (int j = 0; j < n; j++){
                    matrix[i][j] = array2[j];
                }
                i++;
                data1 = scn.nextLine();
            }
            long startAllTime = System.nanoTime();
            while(scn.hasNextLine()){
                search = scn.nextLine();
                long startTime = System.nanoTime();
                System.out.println("Sedang mencari " + search);
                find[0] = 0;
                find[1] = 0;
                // cari huruf pertama
                for(int k = 0; k < m; k++){
                    for(int l = 0; l < n; l++){
                        if (matrix[k][l] == search.charAt(0)){
                            row = k;
                            col = l;
                            find = findArah(matrix, m, n, row, col, search, find);
                            if(find[0] == 1){ 
                                break;
                            }else{
                                row = -1;
                                col = -1;
                            }
                        }else{
                            find[1]++;
                        }
                    }
                    if(find[0] == 1){ 
                        break;
                    }
                }
                long elapsedTime = System.nanoTime()-startTime;
                System.out.println("Total execution time in milis: " + elapsedTime/1000000);
            }
            scn.close();
            long finalTime = System.nanoTime()-startAllTime;
            System.out.println("Total all execution time in milis: " + finalTime/1000000);
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        sc.close();
   
    }   

    public static void printMatrixJawab(char[][] matrix, int row, int col, int arah, int n, int m, int wlength, int attCounter){
        if(arah == 1){ // atas
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if(j == col && (i >= row-wlength+1 && i <= row)){
                        System.out.print(matrix[i][j] + " ");
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
        }else if(arah == 2){ // kanan atas
            int wc = wlength;
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if((i <= row && i >= row - wlength+1) && (j >= col && j <= col+wlength) && i+j == row+col && wc>0){
                        System.out.print(matrix[i][j] + " ");
                        wc--;
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
        }else if(arah == 3){ // kanan
            for(int i = 0; i < m;i++){
                for(int j = 0; j < n;j++){
                    if(i == row && (j >= col && j <= col+wlength-1)){
                        System.out.print(matrix[i][j] + " ");
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
        }else if(arah == 4){ // kanan bawah
            int wc = 0;
            for(int i = 0; i < m; i++){
                for (int j = 0; j < n; j++){
                    if(i >= row && j >= col && i-j == row-col && wc<=wlength-1){
                        System.out.print(matrix[i][j] + " ");
                        wlength--;
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
            
        }else if(arah == 5){ // bawah
            for(int i = 0; i < m; i++){
                for (int j = 0; j < n;j++){
                    if(j == col && (i >= row && i <= row+wlength-1)){
                        System.out.print(matrix[i][j] + " ");
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
        }else if(arah == 6){ // kiri bawah
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if(i + j == row+col && i >= row && wlength > 0 ){
                        System.out.print(matrix[i][j] + " ");
                        wlength--;
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
        }else if(arah == 7){ // kiri
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if(i == row && (j >= col-wlength+1 && j <= col)){
                        System.out.print(matrix[i][j] + " ");
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
        }else if(arah == 8){ // kiri atas
            int wc = wlength;
            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if(i >= row-wlength+1 && j >= col-wlength+1 && i-j == row-col && wc>0){
                        System.out.print(matrix[i][j] + " ");
                        wc--;
                    }else{
                        System.out.print("- ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("Banyak percobaan : " + attCounter);
    }

    public static int[] findArah(char[][] matrix, int m, int n, int row, int col, String search, int[] find){ 
        int wordlength = search.length()-1;
        if(row>-1 && col>-1){
            int wlc = 1, arah = 1, o = row, p = col;
            // o p counter buat index satu satu
            while(wlc < wordlength+1 && arah < 9){
                if(arah == 1){ //keatas
                    if(row < wordlength){
                        arah++;
                    }else{
                        o--;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }else if(arah == 2){ //ke kanan atas
                    if(n-col < wordlength | row < wordlength){
                        arah++;
                    }else{
                        o--;
                        p++;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }else if(arah == 3){ //ke kanan
                    if(n-col < wordlength){
                        arah++;
                    }else{
                        p++;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }else if(arah == 4){ // kanan bawah
                    if(n-col < wordlength | m-row < wordlength){
                        arah++;
                    }else{
                        o++;
                        p++;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }else if(arah == 5){ // bawah
                    if(m-row < wordlength){
                        arah++;
                    }else{
                        o++;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }else if(arah == 6){ // bawah kiri
                    if(m-row < wordlength | col < wordlength){
                        arah++;
                    }else{
                        o++;
                        p--;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }else if(arah == 7){ // kiri
                    if(col < wordlength){
                        arah++;
                    }else{
                        p--;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }else if(arah == 8){ // kiri atas
                    if(col < wordlength | row < wordlength){
                        arah++;
                    }else{
                        p--;
                        o--;
                        if(matrix[o][p]==search.charAt(wlc)){
                            wlc++;
                        }else{
                            wlc = 1;
                            arah++;
                            o = row;
                            p = col;
                        }
                    }
                    find[1]++;
                }
            }
            if(arah > 8){
                find[0] = 0;
            }else{
                printMatrixJawab(matrix, row, col, arah, n, m, search.length(), find[1]);
                find[0] = 1;
            }
        }
        return find;
    }
}