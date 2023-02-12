package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.*;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 *
 * HashTrieMap implementation of TrieMap.
 * @author Will Robertson
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>,
            HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers =
                    new ChainingHashTable<A, HashTrieNode>(AVLTree::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return new HashTrieNodeIterator();
        }

        private class HashTrieNodeIterator extends SimpleIterator<Entry<A,
                HashTrieNode>> {
            Iterator<Item<A, HashTrieNode>> itr = pointers.iterator();
            @Override
            public Entry<A, HashTrieNode> next() {
                if (itr.hasNext()) {
                    Item<A, HashTrieNode> item = itr.next();
                    return new AbstractMap.SimpleEntry<>(item.key, item.value);
                } else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public boolean hasNext() {
                return itr.hasNext();
            }
        }

    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    /**
     * Inserts the given key into the HashTrieMap with the given value.
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return The old value if there was already a mapping for the key,
     * otherwise null.
     */
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> iterator = key.iterator();
        HashTrieNode currNode = (HashTrieNode)this.root;
        A tempKey;
        while(iterator.hasNext()) {
            tempKey = iterator.next();
            if (currNode.pointers.find(tempKey) == null) {
                currNode.pointers.insert(tempKey, new HashTrieNode());
            }
            currNode = currNode.pointers.find(tempKey);
        }
        if (currNode.value != null) {
            V tempValue = currNode.value;
            currNode.value = value;
            return tempValue;
        }
        currNode.value = value;
        size++;
        return null;
    }

    /**
     * Find and fetch the value for the given key.
     * @param key the key whose associated value is to be returned
     * @return the value of the key if there is a mapping for it, otherwise
     * null.
     */
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> iterator = key.iterator();
        HashTrieNode currNode = (HashTrieNode)this.root;
        A tempKey;
        while(iterator.hasNext()) {
            tempKey = iterator.next();
            if (currNode.pointers.find(tempKey) == null) {
                return null;
            }
            currNode = currNode.pointers.find(tempKey);
        }
        return currNode.value;
    }

    /**
     * Check if there is at least one key mapping for the given prefix.
     * @param key The prefix of a key whose presence in this map is to be tested
     * @return true if there is a mapping, otherwise false.
     */
    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            return false;
        }
        Iterator<A> iterator = key.iterator();
        HashTrieNode currNode = (HashTrieNode)this.root;
        A tempKey;
        while(iterator.hasNext()) {
            tempKey = iterator.next();
            if (currNode.pointers.find(tempKey) == null) {
                return false;
            }
            currNode = currNode.pointers.find(tempKey);
        }
        return true;
    }

    /**
     * Deletes the mapping for the given key, if it exists.
     * @param key key whose mapping is to be removed from the map
     */
    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
        /*
        if (key == null) {
            throw new IllegalArgumentException();
        } else {
            Iterator<A> iterator = key.iterator();
            HashTrieNode currNode = (HashTrieNode) this.root;
            if ((!iterator.hasNext()) && currNode.value != null) {
                currNode.value = null;
                size--;
            } else {
                deleteNodes(currNode, iterator);
            }
        }
         */
    }

    /**
     * Recursive deletion helper. Operates on O(d) time, where d is the
     * number of characters in the key. More specifically, at worst performs
     * 2 * d, which is O(d).
     * @param currNode The next node to check
     * @param iterator The iterator of the original key
     * @return true if the node has no children && the value of the current
     * key isn't null, otherwise false
     */
    private boolean deleteNodes(HashTrieNode currNode,
                                Iterator<A> iterator) {
        throw new UnsupportedOperationException();
        /*
        if (!iterator.hasNext()) {
            if (currNode.value != null) { //Only if there is a value for the key
                currNode.value = null;
                size--;
            }
            return currNode.pointers.isEmpty(); //If node has children
        } else {
            A tempKey = iterator.next();
            HashTrieNode nextNode = currNode.pointers.get(tempKey);
            if ((nextNode != null) && (deleteNodes(nextNode, iterator))) {
                currNode.pointers.remove(tempKey);
                return (currNode.value == null) && (currNode.pointers
                        .isEmpty()); //Returns whether this node can be deleted
            } else {
                return false;
            }
        }
         */
    }

    /**
     * Clears the map by returning it to the state it was in when it was first
     * constructed.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
        /*
        this.root = new HashTrieNode();
        size = 0;
         */
    }
}
