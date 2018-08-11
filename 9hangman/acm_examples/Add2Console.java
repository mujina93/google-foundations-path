/*
* File: Add2Console.java
* ----------------------
* This program adds two numbers and prints their sum. Because
* this version is a ConsoleProgram, the input and output appear
* on the console.
*/
import acm.program.*;

public class Add2Console extends ConsoleProgram {
    public void run() {
        setFont("Monospace-bold-18");
        println("This program adds two numbers.");
        int n1 = readInt("Enter n1: ");
        int n2 = readInt("Enter n2: ");
        int total = n1 + n2;
        println("The total is " + total + ".");
    }
}