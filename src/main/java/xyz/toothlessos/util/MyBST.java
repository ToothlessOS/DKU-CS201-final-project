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

    public ArrayList<Node<PeopleRecord>> search(String FirstName, String LastName) {
        ArrayList<Node<PeopleRecord>> results = new ArrayList<>();
        search(this.root, FirstName, LastName, results);
        return results;
    }

    // Helper method for search
    private void search(Node<PeopleRecord> node, String FirstName, String LastName,
                        ArrayList<Node<PeopleRecord>> results) {
        // Base + recursive cases
        if (node != null){
            // Here we use pre-order
            if (node.content.getFamilyName().equals(LastName) && node.content.getGivenName().equals(FirstName)){
                results.add(node);
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
            result.add(root);
            getAllRecordsRec(root.left, result);
            getAllRecordsRec(root.right, result);
        }

    }

}
