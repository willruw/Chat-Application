package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 *
 * MoveToFrontList implementation of DeletelessDictionary.
 * @author Will Robertson
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    //private fields
    private Node<K, V> head;

    /**
     * If there is no previous mapping for key, associate given key with
     * given value and add to worklist. If key mapping already exists,
     * replace the old value with the given value.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return The old value if mapping already exists, else null.
     */
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        } else {
            Node<K, V> currNode = head;
            while (currNode != null) {
                if (currNode.key.equals(key)) {
                    if (currNode.prev != null && currNode.next != null) {
                        return foundMiddle(currNode, value);
                    } else if (currNode.prev == null) {
                        return foundFront(currNode, value);
                    } else {
                        return foundBack(currNode, value);
                    }
                } else {
                    currNode = currNode.next;
                }
            }
            //Either the list is empty or the key is not in the list
            if (head == null) {
                head = new Node<>(key, value);
            } else {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
            return null;
        }
    }

    /**
     * If key is found between two valid nodes, set the value (if applicable)
     * and move node to the front of the worklist.
     * @param node The node to move and get the value of
     * @param value If not null, set the node's value field to this value. If
     *              null, move node to front and don't set value.
     * @return Either the old value of the node if replaced, else the current
     * value of the node.
     */
    private V foundMiddle(Node<K, V> node, V value) {
        if (value != null) {
            V temp = node.value;
            node.value = value;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = head;
            node.prev = null;
            head.prev = node;
            head = node;
            return temp;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = head;
            node.prev = null;
            head.prev = node;
            head = node;
            return head.value;
        }
    }

    /**
     * If key is found in the front of the worklist, set the value (if
     * applicable).
     * @param node The node to set or get the value of
     * @param value If not null, set the node's value field to this value. If
     *              null, then don't set value.
     * @return Either the old value of the node if replaced, else the current
     * value of the node.
     */
    private V foundFront(Node<K, V> node, V value) {
        if (value != null) {
            V temp = node.value;
            node.value = value;
            return temp;
        } else {
            return head.value;
        }
    }

    /**
     * If key is found at the end of the worklist, set the value (if applicable)
     * and move node to the front of the worklist.
     * @param node The node to move and get the value of
     * @param value If not null, set the node's value field to this value. If
     *              null, move node to front and don't set value.
     * @return Either the old value of the node if replaced, else the current
     * value of the node.
     */
    private V foundBack(Node<K, V> node, V value) {
        if (value != null) {
            V temp = node.value;
            node.prev.next = null;
            node.next = head;
            node.prev = null;
            head.prev = node;
            head = node;
            return temp;
        } else {
            node.prev.next = null;
            node.next = head;
            node.prev = null;
            head.prev = node;
            head = node;
            return head.value;
        }
    }

    /**
     * If the key exists in the worklist, get the value associated with it.
     * @param key the key whose associated value is to be returned
     * @return The value associated with the key if in the list, else null.
     */
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else {
            Node<K, V> currNode = head;
            while (currNode != null) {
                if (currNode.key.equals(key)) {
                    if (currNode.prev != null && currNode.next != null) {
                        return foundMiddle(currNode, null);
                    } else if (currNode.prev == null) {
                        return foundFront(currNode, null);
                    } else {
                        return foundBack(currNode, null);
                    }
                } else {
                    currNode = currNode.next;
                }
            }
            return null;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }

    private class MoveToFrontListIterator extends SimpleIterator<Item<K, V>> {

        Node<K, V> currNode = head;
        @Override
        public Item<K, V> next() {
            if (currNode != null) {
                Item<K, V> item = new Item<>(currNode.key, currNode.value);
                currNode = currNode.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasNext() {
            return (currNode != null);
        }
    }

    private static class Node<K, V> {
        Node<K, V> prev;
        Node<K, V> next;
        K key;
        V value;
        public Node (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
