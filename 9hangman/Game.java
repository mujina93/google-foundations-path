/* 
author: Michele Piccolini (mujina93)

Hangman game class
*/

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import java.util.Arrays;
import acm.program.*;

public class Game extends ConsoleProgram { // base program: a window with a console

    public void init(){
        // members initialization
        _lex = new HangmanLexicon();
        _seed = -1; // -1 for actual random
        _wordIndex = rng(0, _lex.getWordCount());
        _secretWord = _lex.getWord(_wordIndex);
        _N = _secretWord.length();
        _guessed = new int[_N]; // all initialized to 0
        _texture = new char[_N];
        Arrays.fill(_texture, '-'); // all dashes initially
        _correct = 0; // number of guessed letters (!=number of correct trials)
        _errors = 0; // number of errors (wrong trials) so far
        _totalGuesses = 8; // maximum starting number of available guesses
        _guesses = _totalGuesses; // number of available guesses

        // gui: add canvas
        canvas = new HangmanCanvas();
        add(canvas); // add canvas to the window
        
        // graphical settings
        setFont("Monospace-bold-18");
    }

    public void run(){
        canvas.reset(); // print initial state

        println("Welcome to Hangman!\n");
        render();

        _playing = true;
        while(_playing){
            if(_guesses > 0){
                Character c = getInput();
                update(c);
                if(_playing){
                    render(); // when you win, _playing=false -> don't render
                }
            } else {
                println("You're completely hung!");
                println("The word was: "+_secretWord);
                println("Game Over.");
                _playing = false;
            }
        }
    }

    public void render(){
        // prints updated game state on console
        print("The word looks like this: ");
        println(new String(_texture));
        println(String.format("You have %d guesses.\n",_guesses));
        // updates on canvas
        canvas.displayWord(new String(_texture));
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
        if(foundSome == false){
            _errors += 1; // 1 error done
            _guesses -= 1; // 1 guess less left
        }
        return foundSome;
    }

    public void update(Character c){
        boolean foundSome = check(c);
        if(foundSome == true){
            println("The guess is correct!");
        } else {
            println("Nope");
            // update display with new piece of drawing
            // (passing the character and the number of errors so far)
            try{
                canvas.noteIncorrectGuess(c, _errors);
            } catch(Exception e){
                System.out.println("Exception occurred when noting incorrect guess on canvas");
            }
        }
        if(_correct == _N){ // victory condition
            println("You win!");
            _playing = false; // stop playing
            // last render
            canvas.displayWord(new String(_texture));
        }
    }

    public Character getInput(){
        String input = readLine("Guess a letter: ");
        Character c;
        try{
            c = input.charAt(0); // first character
        }catch(StringIndexOutOfBoundsException e){
            c = Character.MIN_VALUE; // empty character
        }
        if(Character.isLetter(c)){
            c = Character.toUpperCase(c);
        } else {
            println("Inputted character is not a letter");
            return getInput();
        }
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

    // private members
    private HangmanLexicon _lex;
    private long _seed;
    private int _wordIndex;
    private String _secretWord; // word to be guessed
    private int _N;
    private int[] _guessed;
    private int _correct;
    private int _totalGuesses;
    private int _guesses;
    private int _errors;
    private char[] _texture; // letter to be displayed
    private boolean _playing;
    private HangmanCanvas canvas; // canvas where to draw the hangman

}