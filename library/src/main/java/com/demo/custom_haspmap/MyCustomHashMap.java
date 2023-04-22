package com.demo.custom_haspmap;

import java.util.ArrayList;
import java.util.List;

public class MyCustomHashMap<K, V> {

    private int bucketCapacity = 16;
    private final float loadFactor = 0.75f;
    private Entry<K, V>[] table;

    public MyCustomHashMap() {
        table = new Entry[bucketCapacity];
    }

    public MyCustomHashMap(int capacity) {
        this.bucketCapacity = capacity;
        table = new Entry[capacity];
    }

    private Boolean increaseCapacity() {
        int currentCapacity = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                ++currentCapacity;
            }
        }

        if (currentCapacity <= bucketCapacity * loadFactor) {
            bucketCapacity = bucketCapacity * 2;
            Entry<K, V>[] old = table;
            table = new Entry[bucketCapacity];
            for (int i = 0; i < old.length; i++) {
                table[i] = old[i];
            }
            return true;
        } else {
            return false;
        }
    }

    public void put(Object key, Object value) {
        boolean isBucketIncreaseRequired = increaseCapacity();
        int index = getHashCode(key);
        Entry newEntry = new Entry(key, value);
        //case when no value at index
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> oldValue = table[index];
            // Case when list of data present at same index
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
                // when new key match with existing key in list
                if (position > -1) {
                    allValues.remove(position);
                    allValues.add(position, (V) newEntry.getValue());
                } else {
                    // when no key match with existing key in list
                    allKeys.add((K) newEntry.getKey());
                    allValues.add((V) newEntry.getValue());
                }
                table[index] = new Entry(allKeys, allValues);
            } else {
                //If single object present with same key
                if (((Entry) oldValue).getKey() == newEntry.getKey()) {
                    table[index] = newEntry;
                } else {
                    //If single object present with diffirent key
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

    public V get(K Key) {
        V value = null;
        int index = getHashCode(Key);
        Entry<K, V> oldValue = table[index];
        // Case when list of data present at same index
        if (oldValue.getKey() instanceof List) {
            List<K> allKeys = (List<K>) oldValue.getKey();
            List<V> allValues = (List<V>) oldValue.getValue();
            int position = -1;
            for (int j = 0; j < allKeys.size(); j++) {
                if (allKeys.get(j) == Key) {
                    position = j;
                    break;
                }
            }
            if (position >= 0) {
                value = allValues.get(position);
            }
        } else {
            //If single object present with same key
            if (((Entry) oldValue).getKey() == Key) {
                value = oldValue.getValue();
            }
        }

        return value;
    }

    public void remove(K Key) {
        int index = getHashCode(Key);
        Entry<K, V> oldValue = table[index];
        // Case when list of data present at same index
        if (oldValue.getKey() instanceof List) {
            List<K> allKeys = (List<K>) oldValue.getKey();
            List<V> allValues = (List<V>) oldValue.getValue();
            int position = -1;
            for (int j = 0; j < allKeys.size(); j++) {
                if (allKeys.get(j) == Key) {
                    position = j;
                    break;
                }
            }
            if (position >= 0) {
                allValues.remove(position);
                allKeys.remove(position);
                table[index] = new Entry(allKeys, allValues);
            }
        } else {
            //If single object present with same key
            if (((Entry) oldValue).getKey() == Key) {
                table[index] = null;
            }
        }
    }


    private int getHashCode(Object key) {
        return key.hashCode() & (bucketCapacity - 1);
    }
}
