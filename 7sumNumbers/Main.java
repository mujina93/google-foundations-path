/* 
author: Michele Piccolini (mujina93)

Given a string, return the sum of the numbers appearing in the string, ignoring all other characters. A number is a series of 1 or more digit chars in a row. (Note: Character.isDigit(char) tests if a char is one of the chars '0', '1', .. '9'. Integer.parseInt(string) converts a string to an int.)

sumNumbers("abc123xyz") → 123
sumNumbers("aa11b33") → 44
sumNumbers("7 11") → 18
*/

public class Main{
    public static void main(String[] args){
        String[] data = initData();
        int sum;
        for(String s: data){
            System.out.println("String: "+s);
            sum = sumNumbers(s);
            System.out.printf("Sum: %d\n",sum);
        }
    }

    public static String[] initData(){
        String[] data = {
            "abc123xyz",
            "aa11b33",
            "7 11"
        };
        return data;
    }

    public static int sumNumbers(String s){
        int start = 0;
        boolean number = false;
        int sum = 0;
        for(int i=0; i<s.length(); i++){
            if(Character.isDigit(s.charAt(i))){
                if(number==false){
                    number = true;
                    start = i;
                }
                if(i==s.length()-1){
                    number = false;
                    sum += Integer.parseInt(s.substring(start,i+1));
                }
            } else {
                if(number==true){
                    number = false;
                    sum += Integer.parseInt(s.substring(start,i));
                }
            }
        }
        return sum;
    } 
}