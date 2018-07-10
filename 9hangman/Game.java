/* 
author: Michele Piccolini (mujina93)

Hangman game class
*/

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import java.util.Arrays;

public class Game{
    private HangmanLexicon _lex;
    private long _seed;
    private int _wordIndex;
    private String _secretWord; // word to be guessed
    private int _N;
    private int[] _guessed;
    private int _correct;
    private int _guesses;
    private char[] _texture; // letter to be displayed
    private Scanner _reader;
    private boolean _playing;

    public Game(){
        // initialization
        _lex = new HangmanLexicon();
        _seed = -1; // -1 for actual random
        _wordIndex = rng(0, _lex.getWordCount());
        _secretWord = _lex.getWord(_wordIndex);
        _N = _secretWord.length();
        _guessed = new int[_N]; // all initialized to 0
        _texture = new char[_N];
        Arrays.fill(_texture, '-'); // all dashes initially
        _reader = new Scanner(System.in);
        _correct = 0; // number of guessed letters
        _guesses = 10; // number of available guesses
    }

    public void run(){
        System.out.println("Welcome to Hangman!\n");
        render();

        _playing = true;
        while(_playing){
            if(_guesses > 0){
                Character c = getInput();
                update(c);
                render();
            } else {
                System.out.println("You're completely hung!");
                System.out.println("The word was: "+_secretWord);
                System.out.println("Game Over.");
                _playing = false;
            }
        }
        // cleanup
        _reader.close();
    }

    public void render(){
        System.out.print("The word looks like this: ");
        System.out.println(new String(_texture));
        System.out.printf("You have %d guesses.\n",_guesses);
    }

    public boolean check(Character c){
        boolean foundSome = false;
        for(int i=0; i<_N; i++){
            if(c == _secretWord.charAt(i)){
                _guessed[i] = 1;
                _texture[i] = _secretWord.charAt(i); // reveal a letter
                _correct += 1; // 1 correct letter
                foundSome = true;
            }
        }
        return foundSome;
    }

    public void update(Character c){
        boolean foundSome = check(c);
        if(foundSome == true){
            System.out.println("The guess is correct!");
        } else {
            System.out.println("Nope");
        }
        if(_correct == _N){ // victory condition
            System.out.println("You win!");
            _playing = false; // stop playing
        }
    }

    public Character getInput(){
        System.out.println("Guess a letter: ");
        Character c = new Character(_reader.next().charAt(0)); // eats next token (a char)
        if(Character.isLetter(c)){
            c = Character.toUpperCase(c);
        } else {
            System.out.println("This is not a letter");
            return getInput();
        }
        _guesses -= 1; // 1 guess spent
        return c;
    }

    public int rng(int min, int max){
        int n;
        if(_seed == -1){ // no seed - random in modern way
            n = ThreadLocalRandom.current().nextInt(min,max);
        } else {
            Random rand = new Random();
            rand.setSeed(_seed);
            n = rand.nextInt((max-min)) + min;
        }
        return n;
    }
}