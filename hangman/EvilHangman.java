package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by micha on 1/28/2018.
 */

public class EvilHangman implements hangman.IEvilHangmanGame {
    private Set<String> wordList;
    private Set<String> currentList;
    private Set<Character> alreadyGuessed;
    private int numGuesses = 0;
    private String currentWord;
    public int wordLength = 0;
    private int blanks = 0;
    public EvilHangman(){
        alreadyGuessed = new TreeSet<>();
        numGuesses = 0;
    }
    public void startGame(File dictionary, int wordLength) {
        this.wordLength = wordLength;
        blanks = wordLength;
        try {
            FileReader file = new FileReader(dictionary);
            Scanner s = new Scanner(file);
            wordList = new TreeSet<>();
            while(s.hasNext()) {
                wordList.add(s.next().toLowerCase());
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        currentList = new TreeSet<>();
        for(String word:wordList) {
            if(word.length() == wordLength){
                currentList.add(word);
            }
        }
    }
    public Set<String> makeGuess(char guess) throws IEvilHangmanGame.GuessAlreadyMadeException {
        if(alreadyGuessed.contains(guess))throw new GuessAlreadyMadeException() ;
        Map<String, TreeSet<String>> setsMap = new TreeMap<>();
        for(String i:currentList){
            currentWord = generateWord(i, guess);
            if(setsMap.get(currentWord) == null){
                TreeSet<String> sets = new TreeSet<>();
                sets.add(i);
                setsMap.put(currentWord,sets);
            }
            else{
                setsMap.get(currentWord).add(i);
            }
        }
        int largestSetSize = 0;
        Set<String> currentSet = new TreeSet<>();
        for (Map.Entry<String, TreeSet<String>> s:setsMap.entrySet()){
            if(s.getValue().size() > largestSetSize){
                largestSetSize = s.getValue().size();
                currentSet = s.getValue();
            }
        }
        alreadyGuessed.add(guess);
        numGuesses++;
        currentList = currentSet;
        return currentSet;
    }
    public String generateWord(String word, char guess){
        StringBuilder currentWord = new StringBuilder();
        for(char i:word.toCharArray()){
            if(i != guess){
                currentWord.append('_');
            }
            else currentWord.append(i);
        }
        return currentWord.toString();
    }
    public int getNumGuesses() {
        return numGuesses;
    }
    public int getBlanks() {
        return blanks;
    }
    public void printGuessLetters() {
        for(char i:alreadyGuessed) {
            System.out.print(i);
        }
    }
    public boolean invalidGuess(String guess) {
        if (guess.isEmpty()) return true;
        if (guess.length() > 1) return true;
        if (!Character.isLetter(guess.charAt(0))) return true;
        return false;
    }
    public char[] updateCurrentWord(String word, char validGuess, char[] currentWord) {
        String temp;
        temp = generateWord(word, validGuess);
        for(int i = 0; i < wordLength; i++){
            if (temp.toCharArray()[i] != '_'){
                currentWord[i] = temp.toCharArray()[i];
                blanks--;
            }
        }
        alreadyGuessed.add(validGuess);
        return currentWord;
    }
    public boolean wonGame() {
        return (currentList.size() == 1 && blanks == 0);
    }
    public String getWinningWord(Set<String> words) {
        String[] winWord = new String[words.size()];
        words.toArray(winWord);
        Random random = new Random();
        random.setSeed(37);
        return winWord[random.nextInt(winWord.length)];
    }
}
