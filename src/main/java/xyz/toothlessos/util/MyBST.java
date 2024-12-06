package xyz.toothlessos.util;

import xyz.toothlessos.db.PeopleRecord;
import java.util.ArrayList;

public class MyBST extends BinaryTree {

    public MyBST() {
        super();
    }

    public void insert(PeopleRecord record) {
        this.root = insert(this.root, record, 0); // Note the syntax here!
    }

    // The helper method for 'insert'
    private Node<PeopleRecord> insert(Node<PeopleRecord> node, PeopleRecord record, int heightCounter) {
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

    public ArrayList<Node<PeopleRecord>> search(String name){
        ArrayList<Node<PeopleRecord>> results = new ArrayList<>();
        search(this.root, name, results);
        return results;
    }

    // Helper method for search
    private void search(Node<PeopleRecord> node, String name,
                        ArrayList<Node<PeopleRecord>> results) {
        // Base + recursive cases
        if (node != null){
            // Here we use pre-order
            if (node.content.getFamilyName().equals(name) || node.content.getGivenName().equals(name)){
                results.add(node);
            }
            search(node.left, name, results);
            search(node.right, name, results);
        }
    }

}
