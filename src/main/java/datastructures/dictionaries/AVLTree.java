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
 *
 * A balanced implementation of BinarySearchTree.
 * @author Will Robertson
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    //private fields
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    /**
     * Goes to where key should be placed in tree- if it already exists,
     * change the value to the given value, else create a new leaf node at
     * that spot.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return The old value of key if already in tree, else null.
     */
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        } else if (this.root == null) {
            this.root = new AVLNode(key, value);
        } else {
            ArrayStack<AVLNode> path = new ArrayStack<>();
            AVLNode parent = null;
            AVLNode current = (AVLNode)this.root;
            //Find if element exists in tree, else get a handle on parent
            while (current != null) {
                if (key.compareTo(current.key) < 0) {
                    path.add(parent);
                    parent = current;
                    current = (AVLNode)current.children[LEFT];
                } else if (key.compareTo(current.key) > 0) {
                    path.add(parent);
                    parent = current;
                    current = (AVLNode)current.children[RIGHT];
                } else {
                    V temp = current.value;
                    current.value = value;
                    return temp;
                }
            }
            //If not in tree, add to last parent node
            if (key.compareTo(parent.key) < 0) {
                path.add(parent);
                current = new AVLNode(key, value);
                path.add(current);
                parent.children[LEFT] = current;
            } else {
                path.add(parent);
                current = new AVLNode(key, value);
                path.add(current);
                parent.children[RIGHT] = current;
            }
            balanceTree(path);
        }
        size++;
        return null;
    }

    /**
     * Starting at where the new node was just placed, iterates through the
     * node path and checks if balancing is needed.
     * @param path contains the node path back to the root.
     */
    private void balanceTree(ArrayStack<AVLNode> path) {
        AVLNode current = path.next();
        while (current != null) {
            setHeight(current);
            AVLNode parent;
            if (current == root) {
                parent = null;
            } else {
                parent = path.next();
            }
            int balance = calculateBalance(current);
            if (balance == 2) {
                if (calculateBalance((AVLNode) current.children[LEFT]) >= 0) {
                    leftLeftRotation(current, parent);
                } else {
                    leftRightRotation(current, parent);
                }
            } else if (balance == -2) {
                if (calculateBalance((AVLNode) current.children[RIGHT]) <= 0) {
                    rightRightRotation(current, parent);
                } else {
                    rightLeftRotation(current, parent);
                }
            }
            current = parent;
        }
    }

    /**
     * Rotation for right side and right heavy.
     * @param node Node where imbalance occurs
     * @param parent parent of node
     */
    private void rightRightRotation(AVLNode node, AVLNode parent) {
        AVLNode right = (AVLNode)node.children[RIGHT];
        if (node == root) {
            root = right;
        } else {
            if (parent.children[LEFT] == node) {
                parent.children[LEFT] = right;
            } else {
                parent.children[RIGHT] = right;
            }
        }
        node.children[RIGHT] = right.children[LEFT];
        right.children[LEFT] = node;
        setHeight(node);
        setHeight(right);
    }

    /**
     * Rotation for left side and left heavy.
     * @param node Node where the imbalance occurs
     * @param parent parent of node
     */
    private void leftLeftRotation(AVLNode node, AVLNode parent) {
        AVLNode left = (AVLNode)node.children[LEFT];
        if (node == root) {
            root = left;
        } else {
            if (parent.children[LEFT] == node) {
                parent.children[LEFT] = left;
            } else {
                parent.children[RIGHT] = left;
            }
        }
        node.children[LEFT] = left.children[RIGHT];
        left.children[RIGHT] = node;
        setHeight(node);
        setHeight(left);
    }

    /**
     * Rotation for right side and left heavy.
     * @param node Node where the imbalance occurs
     * @param parent parent of the node
     */
    private void rightLeftRotation(AVLNode node, AVLNode parent) {
        AVLNode right = (AVLNode)node.children[RIGHT];
        AVLNode rightLeft = (AVLNode)right.children[LEFT];
        if (node == root) {
            root = rightLeft;
        }
        else {
            if (parent.children[LEFT] == node) {
                parent.children[LEFT] = rightLeft;
            } else {
                parent.children[RIGHT] = rightLeft;
            }
        }
        node.children[RIGHT] = rightLeft.children[LEFT];
        right.children[LEFT] = rightLeft.children[RIGHT];
        rightLeft.children[LEFT] = node;
        rightLeft.children[RIGHT] = right;
        setHeight(node);
        setHeight(right);
        setHeight(rightLeft);
    }

    /**
     * Rotation for left side and right heavy.
     * @param node Node where the imbalance occurs
     * @param parent parent of the node
     */
    private void leftRightRotation(AVLNode node, AVLNode parent) {
        AVLNode left = (AVLNode)node.children[LEFT];
        AVLNode leftRight = (AVLNode)left.children[RIGHT];
        if (node == root) {
            root = leftRight;
        }
        else {
            if (parent.children[LEFT] == node) {
                parent.children[LEFT] = leftRight;
            } else {
                parent.children[RIGHT] = leftRight;
            }
        }
        node.children[LEFT] = leftRight.children[RIGHT];
        left.children[RIGHT] = leftRight.children[LEFT];
        leftRight.children[LEFT] = left;
        leftRight.children[RIGHT] = node;
        setHeight(node);
        setHeight(left);
        setHeight(leftRight);
    }

    /**
     * Sets the given nodes height to the accurate height.
     * @param node Node whose height to set
     */
    private void setHeight(AVLNode node) {
        if (node.children[LEFT] == null && node.children[RIGHT] == null) {
            node.height = 0;
        } else if (node.children[LEFT] == null) {
            node.height = 1 + ((AVLNode)node.children[RIGHT]).height;
        } else if (node.children[RIGHT] == null) {
            node.height = 1 + ((AVLNode)node.children[LEFT]).height;
        } else {
            node.height = 1 + Math.max(((AVLNode)(node.children[RIGHT])).height,
                            ((AVLNode)(node.children[LEFT])).height);
        }
    }

    /**
     * Calculates the AVL Balance property of the given node.
     * @param node Node whose balance to calculate
     * @return the balance of the node
     */
    private int calculateBalance(AVLNode node) {

        if (node.children[RIGHT] == null) {
            return +node.height;
        } else if (node.children[LEFT] == null) {
            return -node.height;
        } else {
            return ((AVLNode) node.children[LEFT]).height -
                    ((AVLNode) node.children[RIGHT]).height;
        }
    }


    /**
     * Nested class that extends BSTNode and adds a height field for AVL
     * property calculations.
     *
     * @author Will Robertson
     */
    private class AVLNode extends BSTNode {

        protected int height;
        public AVLNode(K key, V value) {
            super(key, value);
        }
    }
}
