package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

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
 *
 * HashTable with separate chaining implementation of Dictionary.
 * @author Will Robertson
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {

    //private fields
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] table = (Dictionary<K, V>[]) new Dictionary[DEFAULT_CAPACITY];
    private final static int DEFAULT_CAPACITY = 29;
    private final static int[] primes = {59, 127, 257, 521, 1049, 2099, 4201,
            8419, 16843, 33703, 67409, 134837, 200003};
    private final static int LAST_PRIME = 12;
    private int prime = 0;
    private int powerOf2 = 262144;
    private final static float LOAD_FACTOR = 0.75f;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
    }

    /**
     * Inserts the given key with the given value into the table.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return null if key doesn't already exist in map, else previous value
     */
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (size >= table.length * LOAD_FACTOR) {
            rehashMap();
        }
        int index = hash(key.hashCode());
        if (table[index] != null) {
            V temp = table[index].insert(key, value);
            if (temp != null) {
                return temp;
            } else {
                size++;
                return null;
            }
        } else {
            table[index] = newChain.get();
            table[index].insert(key, value);
            size++;
            return null;
        }
    }

    /**
     * Checks if the given key exists in the table.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the given key if it exists in the
     * table, else null.
     */
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = hash(key.hashCode());
        if (table[index] == null) {
            return null;
        } else {
            return table[index].find(key);
        }
    }

    /**
     * Returns the index for the given key's hashcode.
     *
     * @param hashcode the hashcode of the key.
     * @return the index in the table that the key belongs to.
     */
    private int hash(int hashcode) {
        if (table.length <= primes[LAST_PRIME]) {
            return (hashcode & 0x7fffffff) % table.length; //Make sign-bit zero
        } else {
            return supplementalHash(hashcode) & (table.length - 1);
        }
    }

    /**
     * Supplemental hash function to insert additional randomness into the
     * index.
     *
     * @param h the hashcode of the key.
     * @return a new (more random) int that can be used in calculating an index.
     */
    private int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Grow the map and rehash the values to the new-sized table. First uses
     * a hardcoded list of prime numbers, then switches to powers of 2.
     */
    private void rehashMap() {
        //If we haven't resized to the last prime in the list, go to the next
        // prime, else use a power of 2
        if (table.length < primes[LAST_PRIME]) {
            Dictionary<K, V>[] tempTable = table;
            table = new Dictionary[primes[prime]];
            this.size = 0;
            prime++;
            Iterator<Item<K, V>> itr;
            Item<K, V> item;
            for (int i = 0; i < tempTable.length; i++) {
                if (tempTable[i] != null) {
                    itr = tempTable[i].iterator();
                    while (itr.hasNext()) {
                        item = itr.next();
                        this.insert(item.key, item.value);
                    }
                }
            }
        } else {
            Dictionary<K, V>[] tempTable = table;
            Iterator<Item<K, V>> itr;
            table = new Dictionary[powerOf2];
            this.size = 0;
            powerOf2 <<= 1;
            Item<K, V> item;
            for (int i = 0; i < tempTable.length; i++) {
                if (tempTable[i] != null) {
                    itr = tempTable[i].iterator();
                    while (itr.hasNext()) {
                        item = itr.next();
                        this.insert(item.key, item.value);
                    }
                }
            }
        }
    }

    /**
     * Gets the iterator for the ChainingHashTable.
     *
     * @return the iterator for this hash table.
     */
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHashTableIterator();
    }

    /**
     * Nested class that defines the implementation of the iterator for this
     * hash table.
     */
    private class ChainingHashTableIterator extends SimpleIterator<Item<K, V>> {
        private int tableIndex;
        private int itemCount;
        Iterator<Item<K, V>> itr = (table[tableIndex] == null) ? null :
                table[tableIndex].iterator();

        @Override
        public Item<K, V> next() {
            if (itemCount < size) {
                for (int i = tableIndex; i < table.length && (itemCount < size); i++,
                        tableIndex = i) {
                    if (itr != null && itr.hasNext()) {
                        itemCount++;
                        return itr.next();
                    } else if ((i != (table.length - 1)) && (table[i + 1] != null)){
                        itr = table[i + 1].iterator();
                    }
                }
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            return (itemCount < size);
        }
    }
}
