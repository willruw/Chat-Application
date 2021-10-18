package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Supplier;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(new Supplier<Dictionary<A, HashTrieNode>>() {
                @Override
                public Dictionary<A, HashTrieNode> get() {
                    return new MoveToFrontList<>();
                }
            });
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            throw new NotYetImplementedException();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        throw new NotYetImplementedException();
    }

    @Override
    public V insert(K key, V value) {
        throw new NotYetImplementedException();
    }

    @Override
    public V find(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean findPrefix(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
