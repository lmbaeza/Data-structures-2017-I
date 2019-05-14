package Queue;

/*
 *
 * @author Luis Miguel Báez
 */
public class Queue<T> {

    public Node<T> first;
    public Node<T> end;
    public int size;

    public Queue() {
      this.end = null;
      this.size = 0;
    }
    
    public boolean isEmpty(){
        return first == null;
    }
    
    public int size() {
        return size;
    }
    
    public void enqueue(T data ){
        Node<T> new_node = new Node<>(data);
        if (isEmpty()) {
            first = new_node;
            end = new_node;
        } else {
            end.next = new_node;
            end = new_node;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()){
            return null;
        }

        T retorno = first.data; 
        first = first.next;
        size--;
        return retorno;
    } 

    public T poll() {
        if (isEmpty()){
          return null;
        }else{
          return first.data;
        }
    }
    
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) { 
            this.data = data;
            this.next = null;
        }
    }
}