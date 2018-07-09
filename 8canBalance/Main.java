/* 
author: Michele Piccolini (mujina93)

Given a non-empty array, return true if there is a place to split the array so that the sum of the numbers on one side is equal to the sum of the numbers on the other side.

canBalance([1, 1, 1, 2, 1]) → true
canBalance([2, 1, 1, 2, 1]) → false
canBalance([10, 10]) → true
*/

import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        int[][] data = initData();
        boolean answer;
        for(int[] ary: data){
            System.out.println("Array: "+Arrays.toString(ary));
            answer = canBalance(ary);
            if(answer==true)
                System.out.println("Array can be balanced");
            else
                System.out.println("Array can't be balanced");
        }
    }

    public static int[][] initData(){
        int[][] data = {
            {1,1,1,2,1},
            {2,1,1,2,1},
            {10,10}
        };
        return data;
    }

    public static boolean canBalance(int[] ary){
        return canBalance(-1, ary.length, 0, 0, ary);
    }

    public static boolean canBalance(int left, int right, int leftSum, int rightSum, int[] ary){
        if(left == right -1){ // end - base case
            return leftSum == rightSum; // balance if true, else false
        } else if (leftSum <= rightSum){
            return canBalance(left+1, right, leftSum+ary[left+1], rightSum, ary); // advance left (arbitrary choice for the '==' case)
        } else {
            return canBalance(left, right-1, leftSum, rightSum+ary[right-1], ary); // advance right
        }
    }
}