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
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {

    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] table = (Dictionary<K, V>[]) new Dictionary[DEFAULT_CAPACITY];

    //private K[] keys = (K[]) new Comparable[DEFAULT_CAPACITY];
    //private int keyIndex = 0;
    private final static int DEFAULT_CAPACITY = 29;
    private final static int[] primes = {59, 127, 257, 521, 1049, 2099, 4201,
            8419, 16843, 33703, 67409, 134837, 200003};
    private int prime = 0;
    private int powerOf2 = 262144;
    private final static float LOAD_FACTOR = 0.75f;
    //private boolean primeRehash = true;
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
        int index = hash(key.hashCode());
        if (table[index] != null) {
            V temp = table[index].insert(key, value);
            if (temp != null) {
                return temp;
            } else {
                //keys[keyIndex] = key;
                //keyIndex++;
                size++;
                return null;
            }
        } else {
            table[index] = newChain.get();
            table[index].insert(key, value);
            //keys[keyIndex] = key;
            //keyIndex++;
            size++;
            return null;
        }
    }

/*    private void insertRehash(K key, V value) {
        int index = hash(key.hashCode(), table.length);
        if (table[index] == null) {
            table[index] = newChain.get();
        }
        table[index].insert(key, value);
        size++;
    }*/
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

    private int hash(int hashcode) {
        if (table.length <= primes[12]) {
            return (hashcode & 0x7fffffff) % table.length; //Make sign-bit zero
        } else {
            return supplementalHash(hashcode) & (table.length - 1);
        }
    }

/*    private int hash(int hashcode, int capacity) {
        if (primeRehash) {
            return (hashcode & 0x7fffffff) % capacity; //Make sign-bit zero
        } else {
            return supplementalHash(hashcode) & (capacity - 1);
        }
    }*/

/*    private int hashPrimeRehash(int hashcode, int capacity) {
        return (hashcode & 0x7fffffff) % capacity;
    }

    private int hashPowerOf2Rehash(int hashcode, int capacity) {
        return supplementalHash(hashcode) & (capacity - 1);
    }*/

    private int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private void rehashMap() {
        //Haven't reached end of primes
        if (table.length < primes[12]) {
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
            //Use power of 2
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

    /*private void rehashMap() {
        Dictionary<K, V>[] tempTable = table;
        if (prime <= 12) {
            table = new Dictionary[primes[prime]];
            K[] newKeySet = (K[]) new Comparable[primes[prime]];
            prime++;
            if (prime == 13) {
                primeRehash = false;
            }
            this.size = 0;
            for (int i = 0; i < keyIndex; i++) {
                this.insertRehash(keys[i],
                        tempTable[hashPrimeRehash(keys[i].hashCode(),
                                tempTable.length)].find(keys[i]));
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
                        tempTable[hashPowerOf2Rehash(keys[i].hashCode(),
                                tempTable.length)].find(keys[i]));
                newKeySet[i] = keys[i];
            }
            keys = newKeySet;
        }
    }*/

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHashTableIterator();
    }

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

/*    private class ChainingHashTableIterator extends SimpleIterator<Item<K, V>> {
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
    }*/

}
