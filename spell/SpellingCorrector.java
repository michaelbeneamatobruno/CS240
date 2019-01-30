package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by testtake on 5/15/18.
 */

public class SpellingCorrector implements ISpellCorrector {
    public ITrie trie;

    public SpellingCorrector() {
        trie = new Trie();
    }

    public Trie getTrie() {
        return (Trie) trie;
    }


    public void useDictionary(String dictionary) throws IOException {
        Scanner sc = new Scanner(new File(dictionary));
        sc.useDelimiter("([^a-zA-Z]+)");
        while (sc.hasNext()) {
            trie.add(sc.next().toLowerCase());
        }
        sc.close();
    }
    public String suggestSimilarWord(String inputWord) {
        String best;
        Set<String> suggestions = getSuggestions(inputWord);
        best = getBestSuggestion(suggestions);
        if (best == null) {
            best = getBestSuggestion(editDistance2(suggestions));
        }
        return best;
    }


    public Set<String> editDistance2(Set<String> ed1) {
        Set<String> ed2 = new HashSet<>();
        for (String i:ed1) {
            if (i != null) {
                ed2.addAll(getSuggestions(i));
            }
        }
        return ed2;
    }

    public Set<String> getSuggestions(String inputWord) {
        Set<String> suggestions = new HashSet<>();
        suggestions.addAll(deletion(inputWord));
        suggestions.addAll(transposition(inputWord));
        suggestions.addAll(alteration(inputWord));
        suggestions.addAll(insertion(inputWord));
        return suggestions;
    }
    public Set<String> deletion(String inputWord) {
        Set<String> suggestions = new HashSet<>();
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder sb = new StringBuilder(inputWord);
            suggestions.add(sb.deleteCharAt(i).toString());
        }
        return suggestions;
    }
    public Set<String> transposition(String inputWord) {
        Set<String> suggestions = new HashSet<>();
        for (int i = 0; i < inputWord.length() - 1; i++) {
            StringBuilder sb = new StringBuilder(inputWord);
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(i + 1));
            sb.setCharAt(i + 1, temp);
            suggestions.add(sb.toString());
        }
        return suggestions;
    }
    public Set<String> alteration(String inputWord) {
        Set<String> suggestions = new HashSet<>();
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder sb = new StringBuilder(inputWord);
            int asciiValue = (int) sb.charAt(i) - 97;
            for (int j = 0; j < 25; j++) {
                if (j != asciiValue) {
                    char currentChar = (char) (j + 97);
                    sb.setCharAt(i, currentChar);
                    suggestions.add(sb.toString());
                }
            }

            suggestions.add(null);
        }
        return suggestions;
    }
    public Set<String> insertion(String inputWord) {
        Set<String> suggestions = new HashSet<>();
        for (int i = 0; i < inputWord.length(); i++) {
            for (int j = 0; j < 25; j++) {
                StringBuilder sb = new StringBuilder(inputWord);
                char currentChar = (char) (j + 97);
                sb.insert(i, currentChar);
                suggestions.add(sb.toString());
            }
            suggestions.add(null);
        }
        return suggestions;
    }

    public String getBestSuggestion(Set<String> suggestions) {
        String best = null;
        String check;
        for (String i:suggestions) {
            if (i != null) {
                if (trie.find(i) != null) {
                    best = compareSuggestions(best, i);
                }
            }
        }
        return best;
    }
    public String compareSuggestions(String best, String check) {
        if (best == null) return check;
        if (trie.find(best).getValue() > trie.find(check).getValue()) return best;
        else if (trie.find(check).getValue() > trie.find(check).getValue()) return check;
        else {
            int compare = best.compareTo(check);
            if (compare < 0) return best;
            else return check;
        }
    }
}
