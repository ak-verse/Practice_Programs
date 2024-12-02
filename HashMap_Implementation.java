class CustomHashMap<K,V> {
    int size;
    int capacity;
    float loadFactor;
    
    class CustomEntry<K, V>{  //use generic in class declaration
        final K key;            // make key final
        V value;
        final int hash;         // make hash final
        CustomEntry next;
        
        //constructor
        CustomEntry(K key, V value, int hash, CustomEntry next){
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
        
        public V getValue(){
            return this.value;
        }
        public K getKey(){
            return this.key;
        }
    }
    
    transient CustomEntry<K,V>[] table;       //make table transient

    @SuppressWarnings("unchecked")
    CustomHashMap(){
        loadFactor = .75f;
        size = 0;
        capacity = 16;
        this.table = null;  // assign EMPTY TABLE to map
    }
    
    
    
    public V get(Object key){
        if(key == null)
            return getForNullKey();
        else
            return getEntry(key);
    }
    
    private V getForNullKey(){
        for(CustomEntry<K,V> e = table[0] ; e != null ; e = e.next){
            if(e.key == null)
                return e.value;
        }
        return null;
    }
    
    private V getEntry(Object key){
        int index = key.hashCode() % capacity ;
        for(CustomEntry<K,V> e = table[index] ; e != null ; e = e.next){
            if(key.equals(e.key))
                return e.value;
        }
        return null;
    }
    
    public V put(K key, V value){
        if(table == null){
            table = new CustomEntry[capacity];
        }
        
        if(key == null){
            return putForNullKey(value);
        }
        
        int hash = key.hashCode() ;
        int i = hash % capacity ;
        
        // Collision case , update existing key and return old value
        for(CustomEntry<K,V> e = table[i] ; e != null ; e = e.next){
            if(key.equals(e.key) && e.hash == key.hashCode()){
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        
        //key not found i.e. no collision , add new entry and return null
        addEntry(key, value, hash, i);
        return null;
    }
    
    private V putForNullKey(V value){
        
        // Collision case , update existing key and return old value
        for(CustomEntry<K,V> e = table[0] ; e != null ; e = e.next){
            if(e.key == null){
                V oldValue = e.value ;
                e.value = value;
                return oldValue;
            }
        }
        
        //key not found i.e. no collision ;  add new entry and return NULL
        addEntry(null, value, 0, 0);
        return null;
    }
    
    private void addEntry(K key, V value, int hash, int index){
        CustomEntry<K,V> entry = new CustomEntry<K,V>(key, value, hash, table[index]);
        table[index] = entry ;
        size++;
    }
}
