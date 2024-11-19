import static lingolava.Tuple.*;

import lingologs.Charact;
import lingologs.Script;
import lingologs.Texture;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Main {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    // ANSI-Farbcode zur Ausgabe von farbigem Text in der Konsole
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";


    // Methode, um die King James Bibel zu verarbeiten
    static public Couple<Script, Texture<Script>> processKingJamesBible(Path P) {
        try {
            String R = System.lineSeparator();
            List<String> L = Files.readAllLines(P); // Liest alle Zeilen der Datei in eine Liste

            L.removeIf(String::isEmpty); // Entfernt leere Zeilen aus der Liste
            String U = String.join(" ", L); // Fügt alle Zeilen zu einem String zusammen

            // Ersetzt bestimmte Muster und ungewünschter Charakters im Text durch Leerzeichen oder entferne sie
            U = U.replaceAll("\\b\\d{1,2}:\\d{1,2}\\b", " "); // Entfernt Versangaben
            U = U.replaceAll("\\*", " ");
            U = U.replaceAll("\uFEFF", " ");
            U = U.replaceAll("\\d{1,2}\\:\\d+", " ");
            U = U.replaceAll("-", " ");
            U = U.replaceAll("\\b[A-Za-z]+\\s+\\d+\\.\\d+[-–]\\d+(\\s+\\d+)?\\b\n", " ");  // Entfernt Versangaben
            U = U.replaceAll("1", "");
            U = U.replaceAll("\\s+", " "); // Ersetzt mehrere Leerzeichen durch eines

            // Konvertiert den gesamten Text in Kleinbuchstaben
            U = U.toLowerCase();

            // Ausgabe der Gesamtzeichenanzahl
            System.out.println("Chars: " + U.length() + R);

            // Erstellt ein neues Script-Objekt vom gesamten Text
            Script S = new Script(U);

            // Teilt den Text in Wörter auf (basierend auf Leerzeichen)
            Texture<Script> words = new Texture<>(S.split(Charact.SP));

            // Filtert die Wörter heraus, die nach der Bereinigung eine Länge von 0 haben
            Texture<Script> filteredWords = words.filter(w -> !cleanWord(w).isEmpty());

            // Gib das Script vom gesamten Text und die gefilterten Wörter zurück
            return new Couple<>(S, filteredWords);
        } catch (Exception E) {
            System.out.println(E.getMessage());
            return null;
        }
    }


    // Methode, um die Basic English Bibel zu verarbeiten
    static public Couple<Script, Texture<Script>> processBibleBasicEnglish(Path P) {
        try {
            String R = System.lineSeparator();
            List<String> L = Files.readAllLines(P); // Liest alle Zeilen der Datei in eine Liste
            L.removeIf(String::isEmpty); // Entfernt leere Zeilen aus der Liste
            String U = String.join(" ", L); // Fügt alle Zeilen zu einem String zusammen

            // Ersetzt bestimmte Muster und ungewünschter Charakters im Text durch Leerzeichen oder entferne sie
            U = U.replaceAll("\\b[A-Za-z]+\\s+\\d+\\.\\d+–\\d+\\s+\\d+\\b", " "); // Entfernt Versangaben
            U = U.replaceAll("[-–]", " ");
            U = U.replaceAll("\\u000C\\w+", " ");
            U = U.replaceAll("\\b\\d{1,2}\\b", " ");
            U = U.replaceAll("  ", " ");
            U = U.replaceAll(" .  ", " ");
            U = U.replaceAll(" .–", " ");
            U = U.replaceAll("\\d{1,2}\\.\\d+[-–]\\d{1,2}(\\.\\d+)?", " "); // Entfernt Versangaben
            U = U.replaceAll("(?<=\\w)-(?=\\w)", " ");
            U = U.replaceAll("\\*", " ");
            U = U.replaceAll("\\d+", " ");
            U = U.replaceAll("\\[KOSONG]", " ");
            U = U.replaceAll(" .–. ", " ");
            U = U.replaceAll(" .– ", " ");
            U = U.replaceAll("-", " ");
            U = U.replaceAll("\\s+", " "); // Ersetzt mehrere Leerzeichen durch eines

            // Konvertiert den gesamten Text in Kleinbuchstaben
            U = U.toLowerCase();

            // Ausgabe der Gesamtzeichenanzahl
            System.out.println("Chars: " + U.length() + R);

            // Erstellt ein neues Script-Objekt vom gesamten Text
            Script S = new Script(U);

            // Teilt den Text in Wörter auf (basierend auf Leerzeichen)
            Texture<Script> words = new Texture<>(S.split(Charact.SP));

            // Filtert die Wörter heraus, die nach der Bereinigung eine Länge von 0 haben
            Texture<Script> filteredWords = words.filter(w -> !cleanWord(w).isEmpty());

            // Gib das Script vom gesamten Text und die gefilterten Wörter zurück
            return new Couple<>(S, filteredWords);
        } catch (Exception E) {
            System.out.println(E.getMessage());
            return null;
        }
    }


    // Methode zum Bereinigen der Worte (entfernt anhängende Zeichen)
    static private Script cleanWord(Script word) {
        return word.replace("[.,;:!?()\"'’]", "");
    }


    // Methode zur Analyse des Textes (Anzahl der Wörter, einzigartige Wörter, Wortlängen)
    static public void analyzeText(Texture<Script> T) {
        System.out.println("Total words: " + T.extent()); // Gesamtanzahl der Wörter
        Texture<Integer> U = T.map(script -> cleanWord(script).length()); // Texture, das die Länge jedes bereinigten Wortes speichert
        T.sort(Comparator.comparingInt(script -> cleanWord(script).length())); // Sortiert nach Länge

        Set<Script> unique = new HashSet<>(); // Set zum Speichern der einzigartigen Wörter

        // Iteriere über jedes Script-Objekt in der Texture
        for (int i = 0; i < T.extent(); i++) {
            Script originalScript = T.at(i);
            Script cleanedWord = cleanWord(originalScript);  // Bereinige das Wort

            // Erstellt ein neues Script-Objekt mit dem bereinigten String
            Script cleanedScript = new Script(cleanedWord);

            // Füge das bereinigte Script dem Set hinzu
            if (!cleanedScript.toString().isEmpty()) {
                unique.add(cleanedScript);
            }
        }
        System.out.println("unique words: " + unique.size());
        System.out.println("most frequent word length: " + U.mode());
        System.out.println("shortest word: " + U.min());
        System.out.println("longest word: " + U.max());
        System.out.println("mean word length: " + U.mean());

        System.out.println();

        System.out.println("10 shortest words: ");
        StringBuilder shortestWords = new StringBuilder();
        // 1. Konvertiere das Set in eine Liste
        List<Script> sortedWords = new ArrayList<>(unique);

        // 2. Sortiere die Liste basierend auf der Länge der Wörter
        sortedWords.sort(Comparator.comparingInt(script -> cleanWord(script).length()));

        for (int i = 0; i < Math.min(10, sortedWords.size()); i++) {
            Script shortestWord = sortedWords.get(i);
            shortestWords.append(cleanWord(shortestWord)).append(", ");   // Bereinigtes Wort ausgeben
        }

        // Entfernt das letzte "," für eine saubere Ausgabe
        if (!shortestWords.isEmpty()) {
            shortestWords.setLength(shortestWords.length() - 2); // Entfernt das letzte Komma
        }
        System.out.println(shortestWords);

        System.out.println();

        System.out.println("10 longest words: ");
        sortedWords.sort(Comparator.comparingInt((Script script) -> cleanWord(script).length()).reversed());
        StringBuilder longestWords = new StringBuilder();

        for (int i = 0; i < Math.min(10, sortedWords.size()); i++) {
            Script longestWord = sortedWords.get(i);
            longestWords.append(cleanWord(longestWord)).append(", ");  // Bereinigtes Wort ausgeben und hinzufügen
        }

        // Entfernt das letzte "," für eine saubere Ausgabe
        if (!longestWords.isEmpty()) {
            longestWords.setLength(longestWords.length() - 2);
        }
        System.out.println(longestWords);

        System.out.println();
    }


    // Methode zur Analyse der Zeichenhäufigkeit im Text
    static public void analyzeCharacterFrequency(Script script) {
        Map<Charact, Integer> tally = script.tally(); // Zählt die Häufigkeit jedes Zeichens

        List<Map.Entry<Charact, Integer>> entries = new ArrayList<>(tally.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); // Sortiert die Zeichen nach Häufigkeit

        // Verwendung von StringBuilder, um die Häufigkeiten in einer Zeile zu sammeln
        StringBuilder result = new StringBuilder();

        // Durchläuft die sortierte Liste und fügt die Ergebnisse zum StringBuilder hinzu
        for (Map.Entry<Charact, Integer> entry : entries) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }

        // Entfernen des letzten Kommas und Leerzeichens
        if (!result.isEmpty()) {
            result.setLength(result.length() - 2);
        }

        // Ausgabe des Ergebnisses
        System.out.println(result);

    }


    // Methode zur Analyse der Sätze im Text
    static public void analyzeSentences(Script script) {
        // Split text in sentences
        String[] sentences = script.toString().split("[.;!?]"); // Teilt den Text in Sätze anhand von Satzzeichen
        int totalSentences = sentences.length;

        int totalWords = 0;
        int totalChars = 0;
        int totalCommas = 0;

        // Berechnet die Anzahl an Wörtern, Zeichen und Kommas pro Satz
        for (String sentence : sentences) {
            String[] words = sentence.trim().split("\\s+");
            int wordCount = words.length;
            int charCount = sentence.replace(" ", "").length();
            int commaCount = (int) sentence.chars().filter(ch -> ch == ',').count();

            totalWords += wordCount;
            totalChars += charCount;
            totalCommas += commaCount;
        }

        // Berechnet Durchschnitt der Wörter, Zeichen und Kommas pro Satz
        double avgWordsPerSentence = (double) totalWords / totalSentences;
        double avgCharsPerSentence = (double) totalChars / totalSentences;
        double avgCommasPerSentence = (double) totalCommas / totalSentences;

        System.out.println("Average words per sentence: " + avgWordsPerSentence);
        System.out.println("Average characters per sentence: " + avgCharsPerSentence);
        System.out.println("Average commas per sentence: " + avgCommasPerSentence);
    }


    // Definiere die alten englischen Pronomen als Script
    static Texture<Script> oldEnglishPronouns = new Texture<>(List.of(
            new Script("thou"), new Script("thee"), new Script("thy"), new Script("thine"), new Script("ye")));

    static public void analyzePronouns(Script script) {
        // Teile das übergebene Script in einzelne Wörter auf und konvertiere zu Texture<Script>
        Texture<Script> words = new Texture<>(script.split(Charact.SP));
        int oldEnglishPronounCount = 0;

        // Zählt die Anzahl der alten englischen Pronomen im Text
        for (int i = 0; i < words.extent(); i++) {
            Script word = cleanWord(words.at(i));
            if (oldEnglishPronouns.contains(new Script(word))) {
                oldEnglishPronounCount++;
            }
        }

        System.out.println("Number of old English pronouns: " + oldEnglishPronounCount);
        double pronounDensity = (double) oldEnglishPronounCount / words.extent() * 100;
        System.out.println("Old English pronoun density: " + pronounDensity + "%");
    }

    // Main Methode
    public static void main(String[] args) {
        String P = "./bible_data"; // Pfad zu den Bibeltexten
        Couple<Script, Texture<Script>> Coup1, Coup2;

        System.out.println();

        System.out.println(ANSI_GREEN + "### Processing both texts ###" + ANSI_RESET);

        System.out.println(ANSI_PURPLE + "**** King James Version ****" + ANSI_RESET);
        Coup1 = processKingJamesBible(Path.of(P + "/bible-english.txt")); // Dateiname

        System.out.println(ANSI_PURPLE + "**** Basic English Version ****" + ANSI_RESET);
        Coup2 = processBibleBasicEnglish(Path.of(P + "/Bible basic english.txt")); // Dateiname

        if (Coup1 != null && Coup2 != null) {
            Script S1 = Coup1.it0();
            Texture<Script> T1 = Coup1.it1();
            Script S2 = Coup2.it0();
            Texture<Script> T2 = Coup2.it1();

            // Wortanalyse
            System.out.println(ANSI_GREEN + "### Word Analysis ###" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "**** King James Version ****" + ANSI_RESET);
            analyzeText(T1);
            System.out.println(ANSI_PURPLE + "**** Basic English Version ****" + ANSI_RESET);
            analyzeText(T2);


            // Zeichenfrequenzanalyse
            System.out.println(ANSI_GREEN + "### Character Frequency Analysis ###" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "**** King James Version ****" + ANSI_RESET);
            analyzeCharacterFrequency(S1);
            System.out.println(ANSI_PURPLE + "**** Basic English Version ****" + ANSI_RESET);
            analyzeCharacterFrequency(S2);
            System.out.println();

            // Satzanalyse
            System.out.println(ANSI_GREEN + "### Sentence Analysis ###" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "**** King James Version ****" + ANSI_RESET);
            analyzeSentences(S1);
            System.out.println(ANSI_PURPLE + "**** Basic English Version ****" + ANSI_RESET);
            analyzeSentences(S2);
            System.out.println();

            // Old english pronoun Analyse
            System.out.println(ANSI_GREEN + "### Pronoun Analysis ###" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "**** King James Version ****" + ANSI_RESET);
            analyzePronouns(S1);
            System.out.println(ANSI_PURPLE + "**** Basic English Version ****" + ANSI_RESET);
            analyzePronouns(S2);
        }
    }

}
