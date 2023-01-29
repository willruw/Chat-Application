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
            this.root = new AVLNode(key, value);
        } else {
            ArrayStack<AVLNode> stack = new ArrayStack<>();
            AVLNode parent = null;
            AVLNode current = (AVLNode)this.root;
            //Find if element exists in tree, else get a handle on parent
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
            //If not in tree, add to last parent node
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
        AVLNode current = stack.next();
        while (current != null) {
            setHeight(current);
            AVLNode parent;
            if (current == root) {
                parent = null;
            } else {
                parent = stack.next();
            }
            setHeight(current);
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

    private void rightRightRotation(AVLNode node, AVLNode parent) {
        AVLNode right = (AVLNode)node.children[RIGHT];
        if (node == root) {
            root = right;
        } else if (parent.children[LEFT] == node) {
            parent.children[LEFT] = right;
        } else {
            parent.children[RIGHT] = right;
        }
        node.children[RIGHT] = right.children[LEFT];
        right.children[LEFT] = node;
        setHeight(node);
        setHeight(right);
    }

    private void leftLeftRotation(AVLNode node, AVLNode parent) {
        AVLNode left = (AVLNode)node.children[LEFT];
        if (node == root) {
            root = left;
        } else if (parent.children[LEFT] == node) {
                parent.children[LEFT] = left;
        } else {
                parent.children[RIGHT] = left;
        }
        node.children[LEFT] = left.children[RIGHT];
        left.children[RIGHT] = node;
        setHeight(node);
        setHeight(left);
    }

    private void rightLeftRotation(AVLNode node, AVLNode parent) {
        AVLNode right = (AVLNode)node.children[RIGHT];
        AVLNode rightLeft = (AVLNode)right.children[LEFT];
        if (node == root) {
            root = rightLeft;
        }
        else if (parent.children[LEFT] == node) {
                parent.children[LEFT] = rightLeft;
        } else {
                parent.children[RIGHT] = rightLeft;
        }
        node.children[RIGHT] = rightLeft.children[LEFT];
        right.children[LEFT] = rightLeft.children[RIGHT];
        rightLeft.children[LEFT] = node;
        rightLeft.children[RIGHT] = right;
        setHeight(node);
        setHeight(right);
        setHeight(rightLeft);
    }

    private void leftRightRotation(AVLNode node, AVLNode parent) {
        AVLNode left = (AVLNode)node.children[LEFT];
        AVLNode leftRight = (AVLNode)left.children[RIGHT];
        if (node == root) {
            root = leftRight;
        }
        else if (parent.children[LEFT] == node) {
                parent.children[LEFT] = leftRight;
        } else {
                parent.children[RIGHT] = leftRight;
        }
        node.children[LEFT] = leftRight.children[RIGHT];
        left.children[RIGHT] = leftRight.children[LEFT];
        leftRight.children[LEFT] = left;
        leftRight.children[RIGHT] = node;
        setHeight(node);
        setHeight(left);
        setHeight(leftRight);
    }

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

    private int calculateBalance(AVLNode node) {
        if (node.children[RIGHT] == null) {//Has only left child
            return node.height;
        } else if (node.children[LEFT] == null) {//Has only right child
            return (-1 * node.height);
        } else {
            return ((AVLNode) node.children[LEFT]).height -
                    ((AVLNode) node.children[RIGHT]).height;
        }
    }
    private class AVLNode extends BSTNode {

        int height;
        public AVLNode(K key, V value) {
            super(key, value);
        }
    }
}
