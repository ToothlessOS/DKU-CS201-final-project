package xyz.toothlessos.util;

import xyz.toothlessos.db.PeopleRecord;
import java.awt.*;
import javax.swing.*;

public class BinaryTree {

    Node<PeopleRecord> root;
    int size;
    int height;
    Graphics g; // The visualization of the tree

    public class Node<E>{
        public E content;
        Node<E> left;
        Node<E> right;

        Node(E content){
            this.content = content;
            this.left = null;
            this.right = null;
        }
    }

    BinaryTree(){
        this.root = null;
        this.size = 0;
        this.height = 0;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    // Traverse the tree
    public void traverse(){
        return;
    }

    /**
     * the required 'getinfo' method:
     * return the 'size' and 'height' attribute in array
     */
    public int[] getInfo(){
        return new int[]{this.size, this.height};
    }
}
