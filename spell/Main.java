package spell;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String dicFileName = args[0];
        String inputWord = args[1];
        ISpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(dicFileName);
        System.out.print(corrector.suggestSimilarWord(inputWord));
    }
}
