package xyz.toothlessos.util;

import xyz.toothlessos.db.PeopleRecord;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

public class MyHeap extends BinaryTree {
    // Override of attributes
    HeapNode<PeopleRecord> root;

    public MyHeap() {
        super();
    }

    // The child class HeapNode have additional 'parent' pointer to help with TrickleUP
    public class HeapNode<E> extends Node<E> {
        // Override of attributes
        HeapNode<E> parent;
        HeapNode<E> left;
        HeapNode<E> right;

        HeapNode(E content) {
            super(content);
            this.parent = null;
        }
    }

    // Node: Here we want to implement as a min heap
    public void insert(PeopleRecord record) {
        // Since heap is 'left leaning' + 'add by layer'
        // It makes sense that the traversal should go by levelOrder
        // Here we do not need a 'height counter' as in BST, since our heap is a complete binary tree

        // Simple case for an empty tree
        if (this.root == null){
            root = new HeapNode<>(record);
            this.size++;
            return;
        }

        // Because of assignment requirements, we use ArrayDeque instead
        Queue<HeapNode<PeopleRecord>> queue = new ArrayDeque<>();
        queue.add(this.root);

        while (!queue.isEmpty()){
            HeapNode<PeopleRecord> current = queue.poll();

            if (current.left == null){
                current.left = new HeapNode<>(record);
                this.size++;
                // heapify (trickleUP)
                trickleUP(current.left);
                return;
            }else{
                queue.add(current.left);
            }

            if (current.right == null){
                current.right = new HeapNode<>(record);
                this.size++;
                // heapify (trickleUP)
                trickleUP(current.right);
                return;
            }else{
                queue.add(current.right);
            }
        }
    }

    // Helper method for heapify / trickleUP
    // Again, this is a MIN heap!
    private void trickleUP(HeapNode<PeopleRecord> current) {
        // Base + recursive case
        if (current.parent != null && current.content.compareTo(current.parent.content) < 0){
            // Swap the position of current.parent and current
            // Here, we want to preserve the shape of the tree: just exchanging the values
            PeopleRecord temp = current.parent.content; // Intermediate var
            current.parent.content = current.content;
            current.content = temp;

            trickleUP(current.parent);
        }
    }

    // Overwrite the 'getInfo' method
    @Override
    public int[] getInfo() {
        this.height = computeHeight(this.size);
        return super.getInfo();
    }

    // Helper function for computing the height of a perfect binary tree give size
    private int computeHeight(int size){
        int height = -1;
        for(int i = 0; i < size; i += (int) Math.pow(2, i)){
            height++;
        }
        return height;
    }

    // Implementation of the required 'find' method
    public ArrayList<HeapNode<PeopleRecord>> find(String name){
        ArrayList<HeapNode<PeopleRecord>> results = new ArrayList<>();
        find(this.root, name, results);
        return results;
    }

    // Helper method for search
    private void find(HeapNode<PeopleRecord> node, String name,
                        ArrayList<HeapNode<PeopleRecord>> results) {
        // Base + recursive cases
        if (node != null){
            // Here we use pre-order
            if (node.content.getFamilyName().equals(name) || node.content.getGivenName().equals(name)){
                results.add(node);
            }
            find(node.left, name, results);
            find(node.right, name, results);
        }
    }
}
