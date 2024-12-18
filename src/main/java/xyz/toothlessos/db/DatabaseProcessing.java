package xyz.toothlessos.db;

import xyz.toothlessos.util.MyBST;
import xyz.toothlessos.util.MyHashmap;
import xyz.toothlessos.util.MyHeap;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class DatabaseProcessing {
    private MyBST bst;
    private MyHeap heap;
    private MyHeap imageHeap;
    private MyHashmap hashmap;

    public DatabaseProcessing() {
        bst = new MyBST();
        heap = new MyHeap();
        hashmap = new MyHashmap(1000);
        imageHeap = new MyHeap();
    }

    //Load the file
    public void loadData() {
        String fileName ="src/main/resources/people_small.txt";
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine())!=null){
                String[] fields = line.split(";");
                if (fields.length >= 13){
                    PeopleRecord record = new PeopleRecord(fields[0], fields[1], fields[2], fields[3],
                            fields[4], fields[5], fields[6], fields[7],
                            fields[8], fields[9], fields[10], fields[11], fields[12]);
                    bst.insert(record);
                    System.out.println(record);
                }

            }
        }catch (IOException e){
            System.out.println("Error loading data: "+e.getMessage());
        }
    }


    public void BSTVisualizerImageRec() {
        MyBST.BSTVisualizer visualizer = bst.new BSTVisualizer(bst); // 创建 BSTVisualizer 实例
        visualizer.setVisible(true); // 设置窗口可见
    }

    public void HeapVisualizerImageRec() {
        MyHeap.HeapVisualizer visualizer = imageHeap.new HeapVisualizer(imageHeap); // 创建 BSTVisualizer 实例
        visualizer.setVisible(true); // 设置窗口可见
    }





    // Search Name
    public ArrayList<PeopleRecord> search(String firstName, String lastName) {
        ArrayList<PeopleRecord> result = bst.search(firstName, lastName);
        return result;
    }

    //Sort
    public ArrayList<PeopleRecord> sort(){
        ArrayList<PeopleRecord> records = bst.getAllRecords();

        for (PeopleRecord record : records) {
            heap.insert(record);
            imageHeap.insert(record);
        }


        ArrayList<PeopleRecord> results = new ArrayList<>();
        while (!heap.isEmpty()){
            results.add(heap.pop());
        }
        return results;
    }



    // Get the most frequent words in relevant fields
    public Map<String, Integer> getMostFrequentWords(int count, int len) throws ShortLengthException {
        if (len < 3) {
            throw new ShortLengthException("Word length must be at least 3.");
        }

        List<PeopleRecord> records = bst.getAllRecords();
        for (PeopleRecord record : records) {
            String[] fields = {record.getGivenName(), record.getFamilyName(), record.getCompanyName(),
                    record.getAddress(), record.getCity(), record.getCounty(), record.getState()};
            for (String field : fields) {
                String[] words = field.split("\\W+");
                for (String word : words) {
                    if (word.length() >= len) {
                        hashmap.put(word.toLowerCase(), hashmap.getOrDefault(word.toLowerCase(), 0) + 1);
                    }
                }
            }
        }

        return hashmap.getAllEntries().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Sort by frequency descending
                .limit(count)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (a, b) -> b, LinkedHashMap::new));
    }

    class ShortLengthException extends Exception {
        public ShortLengthException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        DatabaseProcessing process = new DatabaseProcessing();
        process.loadData();




        ArrayList<PeopleRecord> result = process.search("Clorinda","Heimann");
        for(PeopleRecord i : result){
            System.out.println(i);
        }

        System.out.println("Sorted Records: ");

        System.out.println("Sorted Records: "+ process.sort());

        // 最频繁单词测试
        try {
            Map<String, Integer> frequentWords = process.getMostFrequentWords(5, 4);
            System.out.println("Most Frequent Words: " + frequentWords);
        } catch (ShortLengthException e) {
            System.err.println(e.getMessage());
        }

        //绘图
        process.BSTVisualizerImageRec();
        process.HeapVisualizerImageRec();





    }
}
