package Stack;
/*
 *
 * @author Luis Miguel Báez
 */
public class Stack<T> {
    
    private Node<T> head;

    public Stack() {
        this.head = null;
    }
    
    public boolean isEmpty(){
        return head == null;
    }
    
    public void push(T data){
        Node<T> node = new Node<>(data);
        
        if(isEmpty()){
            head = node;
            head.next = null;
        } else{
            node.next = head;
            head = node;
        }
    }
    
    public T pop(){
        if (isEmpty()){
            return null;
        } else{
            T retorno = head.dato;
            head = head.next;
            return retorno;
        }
    }
    
    public T peek(){
        return head.dato;
    }
    
    private static class Node<T> {
        public Node<T> next;
        public T dato;

        public Node(Node<T> next, T dato) {
            this.next = next;
            this.dato = dato;
        }

        public Node(T data) {
            this(null, data);
        }
    }
}