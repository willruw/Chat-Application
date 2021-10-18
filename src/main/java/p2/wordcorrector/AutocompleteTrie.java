
package p2.wordcorrector;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;

import java.util.Map.Entry;

public class AutocompleteTrie extends HashTrieMap<Character, AlphabeticString, Integer> {

    public AutocompleteTrie() {
        super(AlphabeticString.class);
    }

    public String autocomplete(String key) {
        @SuppressWarnings("unchecked")
        HashTrieNode current = (HashTrieNode) this.root;
        for (Character item : key.toCharArray()) {
            current = current.pointers.find(item);
            if (current == null) {
                return null;
            }
        }

        String result = key;

        while (current.pointers.size() == 1) {
            if (current.value != null) {
                return null;
            }
            Entry<Character, HashTrieNode> next = current.iterator().next();
            result += next.getKey();
            current = next.getValue();
        }

        // Switch this to return null to only complete if we're at the end of
        // the word
        if (current.pointers.size() != 0) {
            return result;
        }
        return result;
    }
}