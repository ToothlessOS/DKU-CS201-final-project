package xyz.toothlessos.util;
import java.util.ArrayList;
import java.util.List;

public class MyHashmap {
    private static class Entry {
        String key;
        int value;

        Entry(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    public MyHashmap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Entry[] newTable = new Entry[newCapacity];

        for (Entry entry : table) {
            if (entry != null) {
                int hash = Math.abs(entry.key.hashCode()) % newCapacity;
                int i = 0;
                while (newTable[(hash + i * i) % newCapacity] != null) {
                    i++;
                }
                newTable[(hash + i * i) % newCapacity] = entry;
            }
        }

        this.capacity = newCapacity;
        this.table = newTable;
    }

    public void put(String key, int value) {
        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int hash = hash(key);
        int i = 0;

        while (table[(hash + i * i) % capacity] != null) {
            Entry entry = table[(hash + i * i) % capacity];
            if (entry.key.equals(key)) {
                entry.value = value; // Update existing key
                return;
            }
            i++;
        }

        table[(hash + i * i) % capacity] = new Entry(key, value);
        size++;
    }

    public Integer get(String key) {
        int hash = hash(key);
        int i = 0;

        while (table[(hash + i * i) % capacity] != null) {
            Entry entry = table[(hash + i * i) % capacity];
            if (entry.key.equals(key)) {
                return entry.value;
            }
            i++;
        }
        return null; // Key not found
    }

    public Integer getOrDefault(String key, int defaultValue) {
        Integer value = get(key);
        return value != null ? value : defaultValue;
    }

    public void delete(String key) {
        int hash = hash(key);
        int i = 0;

        while (table[(hash + i * i) % capacity] != null) {
            Entry entry = table[(hash + i * i) % capacity];
            if (entry.key.equals(key)) {
                table[(hash + i * i) % capacity] = null;
                size--;
                rehash(); // Rehash the entire table to handle deleted slots
                return;
            }
            i++;
        }
    }

    private void rehash() {
        Entry[] oldTable = table;
        table = new Entry[capacity];
        size = 0;

        for (Entry entry : oldTable) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }

    public int size() {
        return size;
    }

    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();
        for (Entry entry : table) {
            if (entry != null) {
                entries.add(entry);
            }
        }
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry entry : table) {
            if (entry != null) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 2); // Remove trailing ", "
        sb.append("}");
        return sb.toString();
    }
}
