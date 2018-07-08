/* 
author: Michele Piccolini (mujina93)

Consider the leftmost and righmost appearances of some value in an array. We'll say that the "span" is the number of elements between the two inclusive. A single value has a span of 1. Returns the largest span found in the given array. (Efficiency is not a priority.)

maxSpan([1, 2, 1, 1, 3]) → 4
maxSpan([1, 4, 2, 1, 4, 1, 4]) → 6
maxSpan([1, 4, 2, 1, 4, 4, 4]) → 6 */

import java.util.HashMap;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        int[][] data = initData();
        try {
            for(int[] row : data){
                System.out.println("Analyzing row "+Arrays.toString(row));
                int maximumSpan = maxSpan(row);
                System.out.printf("Max span is %d\n\n",maximumSpan);
            }
        } catch(Exception e) {
            System.out.println("Exception thrown: "+e);
        }
    }

    public static int[][] initData(){
        int[][] ary = new int[][]{
            {1,2,1,1,3}, // simple case
            {1,0,4,0,0,2,3,5,1,4,7,6}, // case in which you find a big span for 4 (span:8) but you must go on and find span:9 for 1
            {1,4,2,1,4,4,5,1,4}, // case in which you find span:8 for 4, and then you stop before checking for 1 since at most you would find span:8 again
            {1,0,0,1,5,5,4,2,2,4} // worst case: all numbers must be checked (the iteration goes on with i=0,...,12)
        };
        return ary;
    }

    public enum Side {
        LEFT, RIGHT;
    }

    public static int maximumPossibleSpan(int i, int N){
        // NOTE: the 'i' received here is the index that you are currently at
        // in the for loop. It's not the last index. (In that case, this function
        // would return N - 1 - i/2)
        //
        // elements of the list that remain to be explored:
        // N - 1 - i
        // add to them some more cells, to account for maximum possible spans
        // that were not found yet (if they exist)
        // e.g. when analyzing this: {1,0,4,*,*,*,*,*,*,4,7,6}
        // suppose we have analyzed up to 4 on the left and 4 on the right
        // in this case, the remainingCells are 6 (elements between the two
        // 4's, extremes excluded). We still have some "open values": 1, 7, 6
        // 1 is open from the left, and the maximum possible span for 1 would
        // be 9 (if we find a 1 at the right-most position among the
        // unexplored ones). Therefore, we must account for this, and we must
        // go on searching. The final computation gives:
        int maxPosSpan = N - i/2; // integer division
        System.out.printf("i: %d, Max possible span: %d\n",i,maxPosSpan);
        return maxPosSpan;
    }

    public static Side toggleSide(Side side){
        if(side == Side.LEFT){
            return Side.RIGHT;
        } else {
            return Side.LEFT;
        }
    }

    public static int at(int index, Side side, int N){
        if(side == Side.LEFT){
            return index/2;
        } else {
            return N - 1 - index/2; // integer division
        }
    }

    public static int updateMaxSpan(int maxSpan, int left, int right){
        if(left == -1 || right == -1){ // one of the extremes has not been assigned yet
            return 0;
        }
        else {
            // update the maximum
            int newSpan = right - left + 1;
            if(newSpan > maxSpan){
                return newSpan;
            } else {
                return maxSpan;
            }
        }
    }

    public static boolean leftUnassigned(int[] tuple){
        return tuple[0] == -1;
    }

    public static boolean rightUnassigned(int[] tuple){
        return tuple[1] == -1;
    }

    public static int maxSpan(int[] s) throws Exception{
        // O(N)
        int N = s.length;
        int maximumSpan = 0;
        Side d = Side.LEFT; // determines whether you eath the array from left or right
        // store value -> (left_position,right_position)
        HashMap<Integer,int[]> occurrencies = new HashMap<Integer,int[]>();

        // start eating the array alternating from left and right
        // and look for spans
        for(int i=0; maximumPossibleSpan(i,N) > maximumSpan; i++){
            int I = at(i,d,N);
            int el = s[I];
            System.out.printf("i: %d, accessing at: %d, value: %d\n",i,I,el);
            
            int[] tuple = occurrencies.get(el);
            System.out.println("Retrieved tuple: "+Arrays.toString(tuple));
            
            if(tuple == null){
                System.out.println("Initializing tuple with extremes");
                if(d == Side.LEFT){
                    occurrencies.put(el,new int[]{I,-1});
                    System.out.println("Left extreme now assigned");
                } else {
                    occurrencies.put(el,new int[]{-1,I});
                    System.out.println("Right extreme now assigned");
                }
            } else if(!leftUnassigned(tuple) && !rightUnassigned(tuple)){
                // we are done with this: both extremes were found
                System.out.println("Maximum span already found for this value");
            } else if(leftUnassigned(tuple) && 
                      !rightUnassigned(tuple) &&
                      d==Side.RIGHT){
                // update left extreme
                occurrencies.put(el,new int[]{I,tuple[1]});
                System.out.println("Left extreme now assigned");
                maximumSpan = updateMaxSpan(maximumSpan,I,tuple[1]);
                System.out.printf("Span: %d\n",tuple[1]-I+1);
                System.out.printf("Current maximum span: %d\n",maximumSpan);
            } else if(rightUnassigned(tuple) && 
                      !leftUnassigned(tuple) &&
                      d==Side.LEFT){
                // update right extreme
                occurrencies.put(el,new int[]{tuple[0],I});
                System.out.println("Right extreme now assigned");
                maximumSpan = updateMaxSpan(maximumSpan,tuple[0],I);
                System.out.printf("Span: %d\n",I-tuple[0]+1);
                System.out.printf("Current maximum span: %d\n",maximumSpan);
            } else if(leftUnassigned(tuple) && d==Side.LEFT){
                // update left and close (right is already assigned)
                occurrencies.put(el,new int[]{I,tuple[1]});
                System.out.println("Left extreme now assigned");
                maximumSpan = updateMaxSpan(maximumSpan,I,tuple[1]);
                System.out.printf("Span for this value: %d\n",tuple[1]-I+1);
                System.out.printf("Current maximum span: %d\n",maximumSpan);
            } else if(rightUnassigned(tuple) && d==Side.RIGHT){
                // update right and close (left is already assigned)
                System.out.println("Right extreme now assigned");
                occurrencies.put(el,new int[]{tuple[0],I});
                maximumSpan = updateMaxSpan(maximumSpan,tuple[0],I);
                System.out.printf("Span for this value: %d\n",I-tuple[0]+1);
                System.out.printf("Current maximum span: %d\n",maximumSpan);
            } else {
                // unforeseen cases
                throw new Exception();
            }

            d = toggleSide(d); // at next iteration, eat from the other side
        }
        System.out.printf("Returning: %d\n",maximumSpan);
        return maximumSpan;
    }
}