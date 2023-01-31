package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ListFIFOQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {

    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] table = (Dictionary<K, V>[]) new Dictionary[DEFAULT_CAPACITY];
    private WorkList<Item<K, V>> entrySet = new ListFIFOQueue<>();
    private K[] keys = (K[]) new Comparable[DEFAULT_CAPACITY];
    private int keyIndex = 0;
    private final static int DEFAULT_CAPACITY = 29;
    private final static int[] primes = {59, 127, 257, 521, 1049, 2099, 4201,
            8419, 16843, 33703, 67409, 134837, 200003};
    private int prime = 0;
    private int powerOf2 = 18;
    private final static float LOAD_FACTOR = 0.75f;
    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (size >= table.length * LOAD_FACTOR) {
            rehashMap();
        }
        int index = hash(key.hashCode(), table.length);
        if (table[index] != null) {
            V temp = table[index].find(key);
            table[index].insert(key, value);
            if (temp != null) {
                return temp;
            } else {
                //entrySet.add(new Item<K, V>(key, value));
                keys[keyIndex] = key;
                keyIndex++;
                size++;
                return null;
            }
        } else {
            table[index] = newChain.get();
            table[index].insert(key, value);
            //entrySet.add(new Item<>(key, value));
            keys[keyIndex] = key;
            keyIndex++;
            size++;
            return null;
        }
    }

    private void insertRehash(K key, V value) {
        int index = hash(key.hashCode(), table.length);
        if (table[index] != null) {
            table[index].insert(key, value);
            size++;
        } else {
            table[index] = newChain.get();
            table[index].insert(key, value);
            size++;
        }
    }
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = hash(key.hashCode(), table.length);
        if (table[index] == null) {
            return null;
        } else {
            return table[index].find(key);
        }
    }

    private int hash(int hashcode, int capacity) {
        if (prime <= 12) {
            return (hashcode & 0x7fffffff) % capacity; //Make sign-bit zero
        } else {
            return supplementalHash(hashcode) & (capacity - 1);
        }
    }

    private int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private void rehashMap() {
        Dictionary<K, V>[] tempTable = table;
        if (prime <= 12) {
            table = new Dictionary[primes[prime]];
            K[] newKeySet = (K[]) new Comparable[primes[prime]];
            prime++;
            this.size = 0;
            for (int i = 0; i < keyIndex; i++) {
                this.insertRehash(keys[i],
                        tempTable[hash(keys[i].hashCode(), tempTable.length)].find(keys[i]));
            }
            for (int i = 0; i < keyIndex; i++) {
                newKeySet[i] = keys[i];
            }
            keys = newKeySet;
        } else {
            table = new Dictionary[powerOf2];
            K[] newKeySet = (K[]) new Comparable[powerOf2];
            powerOf2 <<= 1;
            this.size = 0;
            for (int i = 0; i < keyIndex; i++) {
                this.insertRehash(keys[i],
                        tempTable[hash(keys[i].hashCode(), tempTable.length)].find(keys[i]));
            }
            for (int i = 0; i < keyIndex; i++) {
                newKeySet[i] = keys[i];
            }
            keys = newKeySet;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHashTableIterator();
    }

    private class ChainingHashTableIterator extends SimpleIterator<Item<K, V>> {
        private int index;
        @Override
        public Item<K, V> next() {
            if (index < keyIndex) {
                Item<K, V> item = new Item<K, V>(keys[index],
                        table[hash(keys[index].hashCode(), table.length)].find(keys[index]));
                index++;
                return item;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            return index < keyIndex;
        }
    }

}
