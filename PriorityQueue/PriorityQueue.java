package PriorityQueue;

/**
 *
 * @author Luis Miguel Báez
 */

import java.util.*;
 
 
public class PriorityQueue {
 
    static final class HeapMax<Entero extends Comparable> { 
        List<Entero> datos; 
 
        public HeapMax() {
            datos = new ArrayList<>();
        }
 
        public HeapMax(Entero[] datos) {
 
            this.datos = new ArrayList<>(datos.length);
            
            this.datos.addAll(Arrays.asList(datos));
            
            for (int i = father(datos.length - 1); i >= 0; i--){
                percolateDown(i);
            }
        }
 
        public void percolateDown(int p) {
            int left = left(p);
            int right = right(p);  
            int min = p; 
            
            if (left < datos.size() && datos.get(left).compareTo(datos.get(min)) > 0){
                min = left;
            }
            if (right < datos.size() && datos.get(right).compareTo(datos.get(min)) > 0){
                min = right;
            }
            
            if (p != min) {
                Collections.swap(datos, min, p);
                percolateDown(min);
            }
        }
 
        public void percolateUp(int u) {
            int p = father(u);
            if (p >= 0 && datos.get(u).compareTo(datos.get(p)) > 0) {
                Collections.swap(datos, u, p);
                percolateUp(p);
            }
        }
 
        public Entero Query() {
            if (datos.isEmpty()){
              return null;
            }
            return datos.get(0);
        }
 
        public void insert(Entero v) {
            datos.add(v);
            percolateUp(datos.size()-1);
        }
 
        public Entero remove() {
            if (datos.isEmpty()){
              return null;
            }
            Entero v = datos.get(0);
            datos.set(0, datos.get(datos.size()-1));
            datos.remove(datos.size()-1);
            percolateDown(0);
            return v;
        }
        
        private int father(int position) {  
            return (position - 1) / 2;
        }
 
        private int left(int position) {
            return (2 * position) + 1; 
        }
 
        private int right(int position) { 
            return (2 * position) + 2;
        }
    }
    static class Entero<T> implements Comparable<Entero> {
        public int prioridad;
        public T data;
 
        public Entero(int prioridad, T data) {
            this.prioridad = prioridad;
            this.data = data;
        }
        
        @Override
        public int compareTo(Entero obj) {
            return this.prioridad - obj.prioridad;
        }
    }
    
    public static void main(String[] arg){
        Entero[] entero = new Entero[10];
        
        for (int i = 0; i < entero.length; i++) {
            entero[i] = new Entero((int)(Math.random() * 100), (int)(Math.random() * 100));
        }
        HeapMax heap = new HeapMax(entero);
        Entero retorno;
        for (int i = 0; i < entero.length; i++) {
            retorno = (Entero) heap.remove();
            System.out.println("Prioridad : "+retorno.prioridad + " dato : " +retorno.data);
        }
    }
}