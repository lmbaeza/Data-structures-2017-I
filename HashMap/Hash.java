package HashMap;

/**
 *
 * @author Luis Miguel Báez
 */
 
class Hash<K,V> {
    
    private MapEntry <K,V>[] array;
    private int size;
 
    public Hash (){
        array = new MapEntry[100];
    }
    
    public Hash (int capacity) {
        array = new MapEntry[capacity];
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
       return (size == 0);
    }
    
    public V put(K key, V value) {
        int index = hashFuntion(key);
        
        for( ; ; ) {
            if (array[index] == null) {
                MapEntry newEntry = new MapEntry<>(key, value);
                array[index] = newEntry;
                ++size;
                checkForExpansion();
                return null;
            }else if (array[index].getKey().equals(key)) {
                V oldValue = array[index].getValue();
                array[index] = new MapEntry<>(key, value);
                return oldValue;
            }else {
                index = incIndex(index);
            }
        }
    }
 
    public V get(K key) {
        int index = hashFuntion(key);
        for( ; ; ) {
            
            if (array[index] == null){
                return null;
            } else if(array[index].getKey().equals(key)) {
                return array[index].getValue();
            } else {
                index = incIndex(index);
            }
        }
    }
 
    private int incIndex(int index) {
        ++index;
        if(index >= array.length){
            index = 0;
        }
        return index;
    }
 
    private int hashFuntion(K key) { 
        String cadena = key.toString();
        int codeAscii = 0;
        int number = 1;
        
        for (int i = 0; i < cadena.length(); i++) {
            if(i % 7 == 0)number += i;
            codeAscii += cadena.charAt(i) * number;
        }
        
        int retorno = (int)(codeAscii % array.length);
        return retorno;
    }
 
    private void checkForExpansion() {
        if((size * 10) / array.length > 2) {
            MapEntry<K,V>[] oldArray = array;
            array = new MapEntry[array.length*2];
            size = 0;
            for(int i = 0; i < oldArray.length; ++i) {
                if(oldArray[i] != null) {
                   put(oldArray[i].getKey(), oldArray[i].getValue()); 
                }
            }
        }
    }
 
    static class MapEntry<K,V> {
        
        private final K key;
        private final V value;
 
        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
 
        public K getKey() {
            return key;
        }
 
        public V getValue() {
            return value;
        }   
    }
}