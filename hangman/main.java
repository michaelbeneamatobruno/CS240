package hangman;

import java.io.File;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by micha on 1/28/2018.
 */

public class main {



    public static void main(String[] args) {

        int wordLength;
        int guesses;
        wordLength = Integer.parseInt(args[1]);
        if (wordLength < 2) {
            System.out.println("wordLength needs to be greater than 1");
            return;
        }
        guesses = Integer.parseInt(args[2]);
        if (guesses < 1) {
            System.out.println("guesses needs to be greater than 0");
            return;
        }
        EvilHangman theGame = new EvilHangman();
        theGame.startGame(new File(args[0]), wordLength);
        String guess;
        TreeSet<String> words = new TreeSet<>();
        char[] currentWord = new char[wordLength];
        for(int i=0; i < wordLength; i++)
        {
            currentWord[i]='_';
        }
        Scanner input = new Scanner(System.in);
        while (guesses > theGame.getNumGuesses() && theGame.getBlanks() > 0) {
            System.out.print("\nYou have " + (guesses - theGame.getNumGuesses()) + " guesses left.");
            System.out.print("\nHere is the list of the letters you have used so far: ");
            theGame.printGuessLetters();
            System.out.print("\nHere is the word you have so far: ");
            System.out.print(String.valueOf(currentWord));
            System.out.print("\nPlease guess a letter: ");
            guess = input.nextLine();
            while(theGame.invalidGuess(guess)){
                System.out.println("Invalid letter. Please guess a valid letter: ");
                guess = input.nextLine();
            }
            char validGuess = Character.toLowerCase(guess.charAt(0));
            try {
                words = (TreeSet<String>) theGame.makeGuess(validGuess);
                currentWord = theGame.updateCurrentWord(words.first(), validGuess, currentWord);
            } catch (IEvilHangmanGame.GuessAlreadyMadeException e) {
                System.out.print("You've already guessed that letter. Please try again.\n");
            }
        }
        if(theGame.wonGame()) System.out.print("You won!!! The correct word was: " + words.first());
        else System.out.print("You lost :( The Correct word was: " + theGame.getWinningWord(words));
        input.close();
    }
}
