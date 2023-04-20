package com.demo.custom_haspmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyCustomHashMap<K, V> {

    private int bucketCapacity = 16;
    private float loadFactor = 0.75f;
    private Entry<K, V>[] table;

    public MyCustomHashMap() {
        table = new Entry[bucketCapacity];
    }

    public MyCustomHashMap(int capacity) {
        this.bucketCapacity = capacity;
        table = new Entry[capacity];
    }

    private void increaseCapacity() {
        bucketCapacity = bucketCapacity * 2;
    }

    public void put(Object key, Object value) {
        if (table.length <= bucketCapacity * loadFactor) {
            increaseCapacity();
        }
        int index = getHashCode(key);
        Entry newEntry = new Entry(key, value);
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> oldValue = table[index];
            if (oldValue.getKey() instanceof List) {
                List<K> allKeys = (List<K>) oldValue.getKey();
                List<V> allValues = (List<V>) oldValue.getValue();
                int position = -1;
                for (int i = 0; i < allKeys.size(); i++) {
                    if (allKeys.get(i) == newEntry.getKey()) {
                        position = i;
                        break;
                    }
                }
                if (position > -1) {
                    allValues.remove(position);
                    allValues.add(position, (V) newEntry.getValue());
                } else {
                    allKeys.add((K) newEntry.getKey());
                    allValues.add((V) newEntry.getValue());
                }
                table[index] = new Entry(allKeys, allValues);
            } else {
                if (((Entry) oldValue).getKey() == newEntry.getKey()) {
                    table[index] = newEntry;
                } else {

                    List<K> allKeys = new ArrayList<>();
                    allKeys.add((K) ((Entry<?, ?>) oldValue).getKey());
                    allKeys.add((K) ((Entry<?, ?>) newEntry).getKey());

                    List<V> allValues = new ArrayList<>();
                    allValues.add((V) ((Entry<?, ?>) oldValue).getValue());
                    allValues.add((V) ((Entry<?, ?>) newEntry).getValue());
                    table[index] = new Entry(allKeys, allValues);
                }


            }


        }


        System.out.println("Hash Map data" + table.toString());
    }


    private int getHashCode(Object key) {
        return key.hashCode() & (bucketCapacity - 1);
    }
}
