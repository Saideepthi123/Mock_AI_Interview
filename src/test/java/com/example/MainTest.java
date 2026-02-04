package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// ------------------------------------------------------------
// JUnit test class
// ------------------------------------------------------------

public class MainTest {

    private int score(Set<String> chosen) {
        Set<Character> used = new HashSet<>();
        for (String w : chosen) {
            for (int i = 0; i < w.length(); i++) {
                used.add(w.charAt(i));
            }
        }
        return used.size();
    }

    private void assertValid(List<String> words, Set<String> chosen) {
        assertTrue(new HashSet<>(words).containsAll(chosen));

        Set<Character> used = new HashSet<>();
        for (String w : chosen) {
            Set<Character> within = new HashSet<>();
            for (int i = 0; i < w.length(); i++) {
                char ch = w.charAt(i);
                assertTrue(within.add(ch), "Word has internal duplicates: " + w);
                assertTrue(used.add(ch), "Character is reused: " + ch);
            }
        }
    }

    @Test
    void testUniqueOptimumBasic() {
        // Unique optimum is {"ab", "cd", "efg"} with 7 distinct chars.
        List<String> words = Arrays.asList("ab", "cd", "efg", "a", "cdef");

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertValid(words, chosen);
        assertEquals(new HashSet<>(Arrays.asList("ab", "cd", "efg")), chosen);
        assertEquals(7, score(chosen));
    }

    @Test
    void testRejectInternalDuplicateWords() {
        // "aa" is invalid and cannot be selected.
        // Unique optimum is {"bc", "def"} => 5 distinct chars.
        List<String> words = Arrays.asList("aa", "bc", "def");

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertValid(words, chosen);
        assertEquals(new HashSet<>(Arrays.asList("bc", "def")), chosen);
        assertEquals(5, score(chosen));
    }

    @Test
    void testDigitsSupported() {
        // Strings may be digits; "1001" is invalid due to repeated digits.
        // Unique optimum is {"2357", "19", "04"} => 8 distinct chars.
        List<String> words = Arrays.asList("2357", "19", "04", "1001");

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertValid(words, chosen);
        assertEquals(new HashSet<>(Arrays.asList("2357", "19", "04")), chosen);
        assertEquals(8, score(chosen));
    }

    @Test
    void testEmptyInput() {
        List<String> words = Collections.emptyList();

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertEquals(Collections.emptySet(), chosen);
    }

    @Test
    void testEmptyStringWord() {
        // The empty string is valid (uses 0 characters). It should not break anything.
        // Unique optimum is {"ab", "cd"}; including "" is allowed but doesn't increase score.
        List<String> words = Arrays.asList("", "ab", "cd", "a");

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertValid(words, chosen);

        Set<String> withoutEmpty = new HashSet<>(chosen);
        withoutEmpty.remove("");

        assertEquals(new HashSet<>(Arrays.asList("ab", "cd")), withoutEmpty);
        assertEquals(4, score(chosen));
    }

    @Test
    void testAllConflictBestSingleWord() {
        // "abc" is uniquely optimal; all other options conflict in a way that prevents ties.
        List<String> words = Arrays.asList("abc", "ab", "bc", "ac");

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertValid(words, chosen);
        assertEquals(new HashSet<>(Collections.singletonList("abc")), chosen);
        assertEquals(3, score(chosen));
    }

    @Test
    void testDuplicateStringsInInput() {
        // Duplicates in input; output is a set anyway.
        List<String> words = Arrays.asList("ab", "ab", "cd");

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertValid(words, chosen);
        assertEquals(new HashSet<>(Arrays.asList("ab", "cd")), chosen);
        assertEquals(4, score(chosen));
    }

    @Test
    void testLargerUniqueOptimum() {
        // Unique optimum: {"ab","cd","ef","gh","ijkl"} => 12 distinct chars.
        List<String> words = Arrays.asList("ab", "cd", "ef", "gh", "ijkl", "a", "c", "e", "g");

        Set<String> chosen = MaxUniqueCharacters.maximizeUniqueCharacters(words);
        assertValid(words, chosen);
        assertEquals(new HashSet<>(Arrays.asList("ab", "cd", "ef", "gh", "ijkl")), chosen);
        assertEquals(12, score(chosen));
    }

    @Test
    void testNonStringThrowsTypeError() {
        List<Object> words = Arrays.asList("ab", 123);
        assertThrows(IllegalArgumentException.class, () -> MaxUniqueCharacters.maximizeUniqueCharacters(words));
    }
}