package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// ------------------------------------------------------------
// Core function
// ------------------------------------------------------------

class MaxUniqueCharacters {

    /**
     * Return a subset of {@code words} that maximizes unique characters with no repeats.
     *
     * Rules:
     * - You may select a subset of the given strings.
     * - No character may appear more than once across the entire selected subset.
     * - Any word that contains a duplicate character within itself is invalid and cannot be selected.
     *
     * Output:
     * - Return the selected subset as a {@code Set<String>}.
     *
     * Errors:
     * - Throw an appropriate type error if {@code words} is not a collection of strings
     *   (i.e., any element is not a {@code String}).
     */
    static Set<String> maximizeUniqueCharacters(List<?> words) {
        if (words == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }
        List<String> stringWords = new ArrayList<>();
        for (Object obj : words) {
            if (!(obj instanceof String)) {
                throw new IllegalArgumentException("All elements must be strings");
            }
            stringWords.add((String) obj);
        }

        // Filter words with unique characters
        List<String> validWords = new ArrayList<>();
        for (String word : stringWords) {
            if (hasUniqueChars(word)) {
                validWords.add(word);
            }
        }

        // Sort by length descending to try larger words first
        validWords.sort((a, b) -> Integer.compare(b.length(), a.length()));

        // Backtracking to find the best subset
        int[] bestScore = {0};
        Set<String> bestSubset = new HashSet<>();
        backtrack(validWords, 0, new HashSet<>(), new HashSet<>(), bestScore, bestSubset);

        return bestSubset;
    }

    private static boolean hasUniqueChars(String word) {
        Set<Character> seen = new HashSet<>();
        for (char c : word.toCharArray()) {
            if (!seen.add(c)) {
                return false;
            }
        }
        return true;
    }

    private static void backtrack(List<String> words, int index, Set<Character> usedChars, Set<String> currentSubset, int[] bestScore, Set<String> bestSubset) {
        if (index == words.size()) {
            int score = usedChars.size();
            if (score > bestScore[0]) {
                bestScore[0] = score;
                bestSubset.clear();
                bestSubset.addAll(currentSubset);
            }
            return;
        }

        String word = words.get(index);

        // Skip this word
        backtrack(words, index + 1, usedChars, currentSubset, bestScore, bestSubset);

        // Try to add this word if no conflict
        boolean canAdd = true;
        for (char c : word.toCharArray()) {
            if (usedChars.contains(c)) {
                canAdd = false;
                break;
            }
        }
        if (canAdd) {
            currentSubset.add(word);
            for (char c : word.toCharArray()) {
                usedChars.add(c);
            }
            backtrack(words, index + 1, usedChars, currentSubset, bestScore, bestSubset);
            // Backtrack
            currentSubset.remove(word);
            for (char c : word.toCharArray()) {
                usedChars.remove(c);
            }
        }
    }
}