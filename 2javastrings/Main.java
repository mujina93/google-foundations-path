
public class Main {
    public static void main(String[] args){
        // definition
        String message = "Hello World!";

        // concatenation (does not change in place)
        message = message.concat(" Lovely day, isn't it?");

        // change case
        message = message.toLowerCase(); //toUpperCase

        System.out.println(message);

        // characters
        char a = 'a'; // single quotation marks

        // replace
        message = message.replace('o','0');
        message = message.replace('e','3');
        message = message.replace('i','1');

        System.out.println(message);

        // characters array
        char[] characters = message.toCharArray();
        // same as ['H','e','l',...]

        // in-loop
        for(char c : characters) {
            System.out.print(c);
        }
        System.out.println();
    }
}