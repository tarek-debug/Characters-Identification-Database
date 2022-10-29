//import java.util.SortedMap;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;



public class TreeMultiMap <K, V extends List<V>> extends TreeMap<K, V> {
    private K key;
    private V value;
    protected List<V> list;


    public TreeMultiMap(K key, V value) {
        list= new ArrayList<V>();
        this.key = key;
        this.value = value;

    }

    public void add(K key,V value){
        if (containsKey(key)){
            list.add(value);
        } else {
            put(key,value);
        }
    }

    /**
     * public int size(){}
     * public V replace(){}
     * public void values(){}
     *
     */
}
