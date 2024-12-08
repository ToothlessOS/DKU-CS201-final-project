package xyz.toothlessos;

import xyz.toothlessos.util.MyBST;
import xyz.toothlessos.util.MyHeap;
import xyz.toothlessos.db.PeopleRecord;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        MyBST bst = new MyBST();
        bst.insert(new PeopleRecord("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));
        bst.insert(new PeopleRecord("2", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));

        MyHeap heap = new MyHeap();
        heap.insert(new PeopleRecord("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));
        heap.insert(new PeopleRecord("2", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));

        bst.draw();
        heap.draw();

        System.out.println(bst.search("1","2"));
        System.out.println(heap.find("1"));
        System.out.println(bst.getInfo()[1]);
        System.out.println(heap.getInfo()[1]);
    }
}
