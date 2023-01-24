package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.worklists.ArrayStack;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        } else if (this.root == null) {
            this.root = new AVLNode(key, value); // Create a new root
        } else {
            ArrayStack<AVLNode> stack = new ArrayStack<>();
            // Locate the parent node
            AVLNode parent = null;
            AVLNode current = (AVLNode)this.root;
            while (current != null) {
                if (key.compareTo(current.key) < 0) {
                    stack.add(parent);
                    parent = current;
                    current = (AVLNode)current.children[LEFT];
                } else if (key.compareTo(current.key) > 0) {
                    stack.add(parent);
                    parent = current;
                    current = (AVLNode)current.children[RIGHT];
                } else {
                    V temp = current.value;
                    current.value = value;
                    return temp;
                }
            }
            // Create the new node and attach it to the parent node
            if (key.compareTo(parent.key) < 0) {
                current = new AVLNode(key, value);
                stack.add(current);
                parent.children[LEFT] = current;
            } else {
                current = new AVLNode(key, value);
                stack.add(current);
                parent.children[RIGHT] = new AVLNode(key, value);
            }
            balanceTree(stack);
        }
        size++;
        return null;
    }

    private void balanceTree(ArrayStack<AVLNode> stack) {

    }


    public AVLNode find(K key, V value) {
        AVLNode prev = null;
        AVLNode current = (AVLNode)this.root;

        int child = -1;

        while (current != null) {
            int direction = Integer.signum(key.compareTo(current.key));

            // We found the key!
            if (direction == 0) {
                return current;
            }
            else {
                // direction + 1 = {0, 2} -> {0, 1}
                child = Integer.signum(direction + 1);
                prev = current;
                current = (AVLNode)current.children[child];
            }
        }

        // If value is not null, we need to actually add in the new value
        if (value != null) {
            current = new AVLNode(key, null);
            if (this.root == null) {
                this.root = current;
            }
            else {
                assert(child >= 0); // child should have been set in the loop
                // above
                prev.children[child] = current;
            }
            this.size++;
        }

        return current;
    }
    private class AVLNode extends BSTNode {

        AVLNode parent;
        int height;
        public AVLNode(K key, V value) {
            super(key, value);
        }
    }
}
