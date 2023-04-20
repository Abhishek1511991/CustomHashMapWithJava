package com.demo.custom_haspmap;

class Entry<K, V> {

   private K Key;
   private V value;

   public Entry(Object key, Object value) {
      setKey((K) key);
      setValue((V) value);
   }

   public K getKey() {
      return Key;
   }

   public void setKey(K key) {
      Key = key;
   }

   public V getValue() {
      return value;
   }

   public void setValue(V value) {
      this.value = value;
   }
}
