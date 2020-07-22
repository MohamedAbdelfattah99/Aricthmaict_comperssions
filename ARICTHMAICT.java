package aricthmaict;

import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;
import java.util.*;
import java.io.*;

/**
 * @author Ta7a
 * @author moussa
 */
public class ARICTHMAICT {

    static void bubbleSort(Vector<Symbols> arr) {
        int n = arr.size();
        Symbols temp = new Symbols();
        Symbols temp1 = new Symbols();
        Symbols temp2 = new Symbols();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n - i; j++) {
                temp1 = arr.get(j - 1);
                temp2 = arr.get(j);
                if (temp1.symbol > temp2.symbol) {
                    temp.freq = temp1.freq;
                    temp.symbol = temp1.symbol;

                    temp1.freq = temp2.freq;
                    temp1.symbol = temp2.symbol;

                    temp2.freq = temp.freq;
                    temp2.symbol = temp.symbol;
                }
            }
        }
    }
    public static Scanner input = new Scanner(System.in);
    public static Vector<Symbols> freq = new Vector<>();
    public static String text = null;

    public static void calc_freq() {
        Vector<Character> myChar = new Vector<>();
        double  size = text.length();
        double  counter = 0;
        boolean bool = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (int j = 0; j < myChar.size(); j++) {
                if (myChar.get(j) == c) {
                    bool = true;
                }
            }

            if (!bool) {
                myChar.add(c);
                for (int k = 0; k < text.length(); k++) {
                    if (c == text.charAt(k)) {
                        counter++;
                    }
                }
                Symbols temp = new Symbols();
                temp.freq = counter / size;
                temp.symbol = c;
                freq.add(temp);
            }

            counter = 0;
            bool = false;
        }
    }
    
    public static void set_low_high() {
        bubbleSort(freq);
        freq.get(0).low = 0;
        freq.get(0).high = freq.get(0).freq;
        freq.get(0).range = freq.get(0).high - freq.get(0).low;
        for (int i = 1; i < freq.size(); i++) {
            freq.get(i).low = freq.get(i - 1).high;
            freq.get(i).high = freq.get(i).low + freq.get(i).freq;
            freq.get(i).range = freq.get(i).high - freq.get(i).low;
        }
    }
    public static Symbols search(char x) {
        for (int i = 0; i < freq.size(); i++) {
            Symbols temp = new Symbols();
            if (x == freq.get(i).symbol) {
                temp = freq.get(i);
                return temp;
            }
        }
        return null;
    }
    public static double sum = 0;
    public static Symbols next = new Symbols();

    public static void Compress() {
        char symbol;
        Symbols curr = new Symbols();
        for (int i = 0; i < text.length(); i++) {
            symbol = text.charAt(i);
            next = search(symbol);
            if (i == 0) {
                curr.lower = next.low;
                curr.upper = next.high;
                curr.range = next.range;

            } else {
                curr.range = curr.upper - curr.lower;
                curr.upper = curr.lower + (curr.range * (next.high));
                curr.lower = curr.lower + (curr.range * (next.low));
            }
        }
        sum = (curr.upper + curr.lower)/2;

    }
       public static String answer = "";
    public static void decompress() {
        
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < freq.size(); j++) {
                if (sum > freq.get(j).low && sum < freq.get(j).high) {
                    answer += freq.get(j).symbol;
                    sum = (sum - freq.get(j).low) / (freq.get(j).high - freq.get(j).low);
                    break;
                }
            }
        }
    }

    public static void Output() {
       // set_low_high();
        //Compress();
    }
     public static  Scanner x;
     public static void ReadInput() {
        try {
            x = new Scanner(new File("input.txt"));
        } catch (Exception e) {
            System.out.println("File Is Not Exist");
        }
        while (x.hasNext()) {
            text = x.nextLine();
        }
        
        
        calc_freq();
        set_low_high();
        Compress();
        x.close();
    }
     public static void WriteCompressed() throws IOException{
     File file =new File("Compressedtext.txt");
     if (!file.exists()){
         file.createNewFile();
     }
     PrintWriter pw= new PrintWriter(file);
     pw.print(sum);
     pw.close();     
     }
      public static void WriteDecompressed() throws IOException{
     File file =new File("Decompressedtext.txt");
     if (!file.exists()){
         file.createNewFile();
     }
     PrintWriter pw= new PrintWriter(file);
     decompress();
     pw.print(answer);
     pw.close();     
     }

    public static void Readandwrite() throws IOException{
         ReadInput();
         WriteCompressed();
         WriteDecompressed();
    }
    //AAAABBBCCCABDC
//BILL GATES

    public static void main(String[] args) throws IOException {
  
        Readandwrite();
        
    }

}
