# Microsoft_AI_Interview
Maximize Unique Characters from a List of Strings
You are given a collection of strings words, where each element is a complete string (a “word”).

Each string is drawn from some fixed alphabet (depending on the input), for example:

letters a–z (like Wordle word lists), or
digits 0–9 (e.g., numbers represented as strings), or
any mix of characters.
Your task is to select a subset of these strings such that:

No character is used more than once across the entire chosen subset. If a character appears in one selected string, it must not appear in any other selected string.
The chosen subset maximizes the total number of distinct characters used (equivalently: maximizes the total number of characters across the chosen strings, since selected strings must be character-disjoint).
Important clarification: words is intended to be a container such as a list/array/set of strings, where each element is one candidate string. A single string value (for example "abc") is not considered a valid words input.

Important rule: invalid strings
If a string contains a repeated character within itself, it can never be selected.

Examples:

"hello" is invalid (two ls)
"1001" is invalid (repeats 1 and 0)
"abc" is valid
Invalid strings are simply unselectable — your function should not throw an error because of them.

Input / Output
Input: a list/array/container of strings words.
Output: the chosen subset as a set-like collection of strings.
It must contain only strings that appear in the input.
Any two selected strings must be character-disjoint.
The selection must be optimal (maximum possible number of distinct characters).
Error handling
If the input is not a collection of strings (i.e., any element is not a string), your function should raise/throw an appropriate type error according to the language’s conventions.
Notes
Characters are treated as case-sensitive unless explicitly stated otherwise (e.g., 'A' and 'a' are different characters).
The test suite for this drill is designed so that the optimal answer is unique, allowing the result to be asserted directly.