
package ListaEnlazada;

/**
 *
 * @author Luis Miguel Báez
 */
public class ArrayLista<T> {
    
    public int DEFAULT_CAPACITY = 100;
    public int CAPACITY;
    private Object[] elementData;
    private int size;

    public ArrayLista(int capacity) {
        this.elementData = new Object[capacity];
        this.size = 0;
        this.CAPACITY = capacity;
    }
    
    public ArrayLista(){
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.CAPACITY = DEFAULT_CAPACITY;
    }

    public int size(){
        return this.size;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
    
    public boolean add(T data){
        aumentarCapacidad(size + 1); 
        elementData[size] = data;
        size++;
        return true;
    }
    
    public boolean add(int index, T data){
        if (index > size || index < 0){
            System.err.println("El indice "+index+" no esta en el rango [0,"+size+"]");
            return false;
        }
        
        aumentarCapacidad(size + 1); 
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = data;
        size++;
        return true;
    }
    
    public T get(int index){
        return (T) elementData[index];
    }
    
    public T set(int index, T data){
        if (index > size || index < 0){
            System.err.println("El indice "+index+" no esta en el rango [0,"+size+"]");
            return null;
        }
        T oldValue = (T) elementData[index];
        elementData[index] = data;
        return oldValue;
    }
    
    public int indexOf(Object obj){
        for (int i = 0; i < size - 1; i++) {
            if (elementData[i].equals(obj)){
                return i;
            }
        }
        return -1;
    }
    
    public boolean remove(Object obj){
        if (obj == null){
            for (int i = 0; i < size - 1; i++) {
                if (elementData[i] == null){
                    remove(i);
                    return true;
                }
            }
        } else{
            for (int i = 0; i < size - 1; i++) {
                if (elementData[i].equals(obj)){
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    public T remove(int index){
        if (index > size || index < 0){
            System.err.println("El indice "+index+" no esta en el rango [0,"+size+"]");
            return null;
        }
        T retorno = (T) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0){
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return retorno;
    }
    
    public int contains(Object obj){
        int count = 0;
        for (int i = 0; i < size - 1; i++) {
            if (elementData[i].equals(obj)){
                count++;
            }
        }
        return count;
    }
    
    private void aumentarCapacidad(int capacity){
        if (capacity == CAPACITY){
            Object[] tmp = new Object[elementData.length * 2];
            System.arraycopy(elementData, 0, tmp, 0, elementData.length);
            elementData = tmp;
            CAPACITY = elementData.length;
        }
    }
}
