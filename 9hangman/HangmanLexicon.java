/*
* File: HangmanLexicon.java
* -------------------------
* This file contains a stub implementation of the HangmanLexicon
* class that you will reimplement for Part III of the assignment.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HangmanLexicon {
    public HangmanLexicon(){
        // imports words from outside
        String FILENAME = "words.txt";
        BufferedReader br = null;
        FileReader fr = null;

        _words = new ArrayList<String>();
        _word_count = 0;

        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while((sCurrentLine = br.readLine()) != null){
                _words.add(sCurrentLine);
                _word_count += 1;
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    /** Returns the number of words in the lexicon. */
    public int getWordCount() {
        return _word_count;
    }
    /** Returns the word at the specified index. */
    public String getWord(int index) {
        try{
            return _words.get(index);
        } catch(IndexOutOfBoundsException e){
            System.out.println("getWord: illegal index");
            return "";
        }
    }
    // private members
    private int _word_count;
    private ArrayList<String> _words;
}