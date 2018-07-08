/* 
author: Michele Piccolini (mujina93)

Given two strings, base and remove, return a version of the base string where all instances of the remove string have been removed (not case sensitive). You may assume that the remove string is length 1 or more. Remove only non-overlapping instances, so with "xxx" removing "xx" leaves "x".

withoutString("Hello there", "llo") → "He there"
withoutString("Hello there", "e") → "Hllo thr"
withoutString("Hello there", "x") → "Hello there"
*/

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        String[] bases = initBase();
        String[] removes = initRemove();
        for(int i=0; i<bases.length; i++){
            System.out.printf("String: %s\n",bases[i]);
            System.out.printf("Removing: %s\n",removes[i]);
            String cleaned = remove(bases[i], removes[i]);
            System.out.println("Cleaned: "+cleaned);
        }
    }

    public static String[] initBase(){
        String[] data = {
            "Hello there",
            "Hello there",
            "Hello there"
        };
        return data;
    }

    public static String[] initRemove(){
        String[] data = {
            "llo",
            "e",
            "x"
        };
        return data;
    }

    public static class Duo{
        private int ind; // index where the research started
        private int adv; // current advancement in the research
        public Duo(int index){
            ind = index;
            adv = 0;
        }
        public void advance(){
            adv += 1;
        }
        public int getAdv(){
            return adv;
        }
        public int getInd(){
            return ind;
        }
    }

    public static String remove(String base, String remove){
        base = base.toLowerCase();
        remove = remove.toLowerCase();
        int N = remove.length();
        
        Queue<Duo> researches = new LinkedList<>();
        Queue<Duo> buffer = new LinkedList<>();
        List<Integer> toBeRemoved = new ArrayList<>();

        char c;
        Duo probe;
        Duo temp;
        for(int i=0; i<base.length(); i++){
            c = base.charAt(i);
            System.out.printf("At letter: %c\n",c);
            // enqueue new research (that starts at 0)
            researches.add(new Duo(i));
            // dequeue all pending researches
            while(researches.peek()!=null){
                // dequeue next research
                probe = researches.remove();
                // check if c can make this research advance
                int adv = probe.getAdv();
                System.out.printf("probe: ind %d adv %d\n",probe.getInd(),probe.getAdv());
                if(c == remove.charAt(adv)){
                    // check if research is complete
                    if(adv == N - 1){
                        System.out.println("Substring found!");
                        // clear all pending researches
                        // (I don't need to clear the buffer, since
                        // when I complete a research I am sure that
                        // this was the first one in researches, since
                        // the oldest ones are always on the front 
                        // of the queue)
                        while(researches.peek()!=null){
                            researches.remove();
                        }
                        // store indices to be removed
                        int from = probe.getInd();
                        for(int j=0; j <= adv; j++){
                            toBeRemoved.add(from + j);
                        }
                    } else { // if research is not complete...
                        // advance research
                        probe.advance();
                        // enqueue advanced research for next lookup
                        // (enqueue in the buffer, then outside the while,
                        // the researches will pass from the buffer to the
                        // main queue again)
                        buffer.add(probe);
                    }
                } else {
                    // this research failed (we found a hole in it
                    // i.e. we found an interruption in the sequence
                    // of characters that would have formed the
                    // string 'remove')
                    // We don't do anything, since we have already
                    // dequeued it.
                }
            }
            // enqueue the buffered researches
            while(buffer.peek()!=null){
                temp = buffer.remove();
                researches.add(temp);
            }
        }

        // remove substrings from 'base'
        ArrayList<Character> baseL = new ArrayList<Character>();
        for(char C: base.toCharArray()){
            baseL.add(C);
            //System.out.printf("Adding %c ",C);
        }
        Collections.sort(toBeRemoved, Collections.reverseOrder());
        for(int k: toBeRemoved){
            //System.out.printf("Removing %d\n",k);
            baseL.remove(k);
        }
        //System.out.printf("Returning %s\n",baseL.toString());
        String ret = "";
        for(Character K: baseL){
            ret += K.toString();
        }
        return ret;
    }
}