package xyz.toothlessos.util;

import xyz.toothlessos.db.PeopleRecord;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyBST extends BinaryTree {

    public MyBST() {
        super();
    }

    public void insert(PeopleRecord record) {
        this.root = insert(this.root, record, 0); // Note the syntax here!
    }

    // The helper method for 'insert'
    public Node<PeopleRecord> insert(Node<PeopleRecord> node, PeopleRecord record, int heightCounter) {
        // Base case
        if (node == null){
            node = new Node<>(record);
            // Keep track of the parameters
            this.size++;
            if (heightCounter >= this.height){
                this.height = heightCounter;
            }
            return node;
        }
        // Recursive case
        else {
            if (record.compareTo(node.content) < 0){
                node.left = insert(node.left, record, ++heightCounter);
            }
            else if (record.compareTo(node.content) > 0){
                node.right = insert(node.right, record, ++heightCounter);
            }
        }

        return node;
    }

    public ArrayList<PeopleRecord> search(String FirstName, String LastName) {
        ArrayList<PeopleRecord> results = new ArrayList<>();
        search(this.root, FirstName, LastName, results);
        return results;
    }

    // Helper method for search
    private void search(Node<PeopleRecord> node, String FirstName, String LastName,
                        ArrayList<PeopleRecord> results) {
        // Base + recursive cases
        if (node != null){
            // Here we use pre-order
            if (node.content.getFamilyName().equals(LastName) && node.content.getGivenName().equals(FirstName)){
                results.add(node.content);
            }
            search(node.left, FirstName, LastName, results);
            search(node.right, FirstName, LastName, results);
        }
    }

    public ArrayList<PeopleRecord> getAllRecords() {
        ArrayList<PeopleRecord> results = new ArrayList<>();
        getAllRecordsRec(root, results);
        return results;
    }

    private void getAllRecordsRec(Node root, ArrayList result) {
        if (root != null){
            result.add(root.content);
            getAllRecordsRec(root.left, result);
            getAllRecordsRec(root.right, result);
        }
    }

    public class BSTVisualizer extends JFrame {
        private final MyBST bst;

        public BSTVisualizer(MyBST bst) {
            this.bst = bst;
            setTitle("Binary Search Tree Visualizer");
            setSize(8000, 6000);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            add(new BSTPanel(bst));
        }
    }

    public class BSTPanel extends JPanel {
        private final MyBST bst;
        private final int nodeSize = 40; // 节点圆形的直径
        private final int vGap = 60;    // 节点之间的垂直间隔

        public BSTPanel(MyBST bst) {
            this.bst = bst;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bst.root != null) {
                drawTree(g, bst.root, getWidth() / 2, 50, getWidth() / 4);
            }
        }

        private void drawTree(Graphics g, BinaryTree.Node<PeopleRecord> node, int x, int y, int hGap) {
            if (node.left != null) {
                // 绘制左子节点的连线
                g.drawLine(x, y, x - hGap, y + vGap);
                drawTree(g, node.left, x - hGap, y + vGap, hGap / 2);
            }
            if (node.right != null) {
                // 绘制右子节点的连线
                g.drawLine(x, y, x + hGap, y + vGap);
                drawTree(g, node.right, x + hGap, y + vGap, hGap / 2);
            }
            // 绘制节点本身
            g.setColor(Color.GRAY);
            g.fillOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
            g.setColor(Color.RED);
            String value = node.content.getGivenName() + " " + node.content.getFamilyName();
            g.drawString(value, x - value.length() * 3, y + 5);
        }
    }
}




