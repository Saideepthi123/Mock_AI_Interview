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
        return Set.of();
    }

    List<String> validWords = new java.util.ArrayList<>();
    List<Set<Character>> charSets = new java.util.ArrayList<>();

    // Requirement 1: validation + preprocessing
    for (Object obj : words) {
        if (!(obj instanceof String)) {
            throw new IllegalArgumentException("All elements must be strings");
        }

        String word = (String) obj;
        Set<Character> chars = new HashSet<>();
        boolean hasDuplicate = false;

        for (int i = 0; i < word.length(); i++) {
            if (!chars.add(word.charAt(i))) {
                hasDuplicate = true;
                break;
            }
        }

        if (!hasDuplicate) {
            validWords.add(word);
            charSets.add(chars);
        }
    }

    // Backtracking state
    Set<Character> usedChars = new HashSet<>();
    Set<String> current = new HashSet<>();
    Set<String> best = new HashSet<>();

    backtrack(validWords, charSets, 0, usedChars, current, best);

    return best;
}

/**
 * Explore all subsets and keep the one with maximum distinct characters.
 */
private static void backtrack(
        List<String> words,
        List<Set<Character>> charSets,
        int index,
        Set<Character> usedChars,
        Set<String> current,
        Set<String> best
) {
    // Update best if current is better
    if (usedChars.size() > bestSize(best)) {
        best.clear();
        best.addAll(current);
    }

    // Base case
    if (index == words.size()) {
        return;
    }

    // Option 1: skip current word
    backtrack(words, charSets, index + 1, usedChars, current, best);

    // Option 2: include current word if no overlap
    Set<Character> chars = charSets.get(index);
    if (canUse(chars, usedChars)) {
        // Add chars
        for (char c : chars) usedChars.add(c);
        current.add(words.get(index));

        backtrack(words, charSets, index + 1, usedChars, current, best);

        // Backtrack
        current.remove(words.get(index));
        for (char c : chars) usedChars.remove(c);
    }
}

// Helper: check overlap
private static boolean canUse(Set<Character> chars, Set<Character> used) {
    for (char c : chars) {
        if (used.contains(c)) return false;
    }
    return true;
}

// Helper: compute total distinct chars in chosen set
private static int bestSize(Set<String> set) {
    Set<Character> tmp = new HashSet<>();
    for (String s : set) {
        for (int i = 0; i < s.length(); i++) {
            tmp.add(s.charAt(i));
        }
    }
    return tmp.size();
}
}