package xyz.toothlessos.util;

import xyz.toothlessos.db.PeopleRecord;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
        if (this.root == null) {
            root = new HeapNode<>(record);
            this.size++;
            return;
        }

        // Because of assignment requirements, we use ArrayDeque instead
        Queue<HeapNode<PeopleRecord>> queue = new ArrayDeque<>();
        queue.add(this.root);

        while (!queue.isEmpty()) {
            HeapNode<PeopleRecord> current = queue.poll();

            if (current.left == null) {
                current.left = new HeapNode<>(record);
                this.size++;
                // heapify (trickleUP)
                current.left.parent = current;
                trickleUP(current.left);
                return;
            } else {
                queue.add(current.left);
            }

            if (current.right == null) {
                current.right = new HeapNode<>(record);
                this.size++;
                // heapify (trickleUP)
                current.right.parent = current;
                trickleUP(current.right);
                return;
            } else {
                queue.add(current.right);
            }
        }
    }

    // Helper method for heapify / trickleUP
    // Again, this is a MIN heap!
    private void trickleUP(HeapNode<PeopleRecord> current) {
        // Base + recursive case
        if (current.parent != null && current.content.compareTo(current.parent.content) < 0) {
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
    private int computeHeight(int size) {
        int height = -1;
        for (int i = 0; i < size; i += (int) Math.pow(2, i)) {
            height++;
        }
        return height;
    }

    // Implementation of the required 'find' method
    public ArrayList<PeopleRecord> find(String FirstName, String LastName) {
        ArrayList<PeopleRecord> results = new ArrayList<>();
        find(this.root, FirstName, LastName, results);
        return results;
    }

    // Helper method for search
    private void find(HeapNode<PeopleRecord> node, String FirstName, String LastName,
                      ArrayList<PeopleRecord> results) {
        // Base + recursive cases
        if (node != null) {
            // Here we use pre-order
            if (node.content.getFamilyName().equals(LastName) || node.content.getGivenName().equals(FirstName)) {
                results.add(node.content);
            }
            find(node.left, FirstName, LastName, results);
            find(node.right, FirstName, LastName, results);
        }
    }

    // Pop: remove node on the top (root) of the heap
    public PeopleRecord pop() {
        if (this.root == null) {
            return null;
        }
        // First, exchange root node with the last node (Just change content, preserve tree structure)
        HeapNode<PeopleRecord> LastNode = findLastNode();
        PeopleRecord temp = this.root.content;
        this.root.content = LastNode.content;
        LastNode.content = temp;
        // Then, we can directly remove the root node
        // The removing procedures
        // Assuming LastNode is identified, and we have access to its parent
        if (LastNode.parent != null) {
            if (LastNode.parent.left == LastNode) {
                LastNode.parent.left = null; // Disconnect from parent
            } else if (LastNode.parent.right == LastNode) {
                LastNode.parent.right = null; // Disconnect from parent
            }
        }
        // Just make the reference 'null' won't help: you need to remove all pointers to the node
        // Not deleting, just make it eligible for garbage collection
        LastNode = null;
        // Finally, we perform trickleDOWN on the current 'root node'
        trickleDown(this.root);
        ;
        this.size--;
        // Return the results
        return temp;
    }

    // Helper method 1: Find the last node
    private HeapNode<PeopleRecord> findLastNode() {
        // We can iterate by levelOrder until we find null
        // Because of assignment requirements, we use ArrayDeque instead
        Queue<HeapNode<PeopleRecord>> queue = new ArrayDeque<>();
        ArrayList<HeapNode<PeopleRecord>> output = new ArrayList<>();
        queue.add(this.root);
        output.add(this.root);

        while (!queue.isEmpty()) {
            HeapNode<PeopleRecord> current = queue.poll();

            if (current.left != null) {
                queue.add(current.left);
                output.add(current.left);
            }

            if (current.right != null) {
                queue.add(current.right);
                output.add(current.right);
            }
        }
        return output.get(output.size() - 1);
    }

    // Helper method 2: TrickleDOWN the 'root'
    private void trickleDown(HeapNode<PeopleRecord> current) {
        // Base cases
        // Case 1: Reach the end of the tree
        if (current.left == null) {
            return;
        }
        if (current.right == null) {
            if (current.content.compareTo(current.left.content) > 0) {
                PeopleRecord temp = current.content;
                current.content = current.left.content;
                current.left.content = temp;
            }
            return;
        }
        // Case 2: Satisfy the conditions mid-way
        if (current.content.compareTo(current.left.content) < 0 &&
                current.content.compareTo(current.right.content) < 0) {
            return;
        }
        // Recursive case
        if (current.content.compareTo(current.left.content) > 0
                || current.content.compareTo(current.right.content) > 0) {
            if (current.left.content.compareTo(current.right.content) < 0) {
                PeopleRecord temp = current.content;
                current.content = current.left.content;
                current.left.content = temp;
                trickleDown(current.left);
            } else if (current.left.content.compareTo(current.right.content) > 0) {
                PeopleRecord temp = current.content;
                current.content = current.right.content;
                current.right.content = temp;
                trickleDown(current.right);
            }
        }
    }

    public class HeapVisualizer extends JFrame {
        private final MyHeap heap;

        public HeapVisualizer(MyHeap heap) {
            this.heap = heap;
            setTitle("Heap Visualizer");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            add(new HeapPanel(heap));
        }
    }

    class HeapPanel extends JPanel {
        private final MyHeap heap;
        private final int nodeSize = 40; // 节点圆形的直径
        private final int vGap = 60;     // 节点之间的垂直间隔

        public HeapPanel(MyHeap heap) {
            this.heap = heap;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (heap.root != null) {
                drawHeap(g, heap.root, getWidth() / 2, 50, getWidth() / 4);
            }
        }

        private void drawHeap(Graphics g, MyHeap.HeapNode<PeopleRecord> node, int x, int y, int hGap) {
            if (node.left != null) {
                // 绘制左子节点的连线
                g.drawLine(x, y, x - hGap, y + vGap);
                drawHeap(g, node.left, x - hGap, y + vGap, hGap / 2);
            }
            if (node.right != null) {
                // 绘制右子节点的连线
                g.drawLine(x, y, x + hGap, y + vGap);
                drawHeap(g, node.right, x + hGap, y + vGap, hGap / 2);
            }
            // 绘制节点本身
            g.setColor(Color.GRAY);
            g.fillOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
            g.setColor(Color.RED);
            String value = node.content.getGivenName() + " " + node.content.getFamilyName();
            g.drawString(value, x - value.length(), y + 5);
        }
    }

}
