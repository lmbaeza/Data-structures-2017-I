
package SegmentTree;

/**
 *
 * @author Luis Miguel Báez
 */

import java.util.Arrays;

class SegmentTree {

    private Node[] heap;
    private double[] array;
    private int size;

    public SegmentTree(double[] array) {
        this.array = Arrays.copyOf(array, array.length);
        size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));
        heap = new Node[size];
        build(1, 0, array.length);
    }


    public int size() {
        return array.length;
    }

    private void build(int v, int from, int size) {
        heap[v] = new Node();
        heap[v].from = from;
        heap[v].to = from + size - 1;

        if (size == 1) {
            heap[v].sum = array[from];
            heap[v].min = array[from];
            heap[v].max = array[from];
        } else {
            build(2 * v, from, size / 2);
            build(2 * v + 1, from + size / 2, size - size / 2);

            heap[v].sum = heap[2 * v].sum + heap[2 * v + 1].sum;
            heap[v].min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
            heap[v].max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
        }
    }

    public double sum(int from, int to) {
        return sum(1, from, to);
    }

    private double sum(int v, int from, int to) {
        Node n = heap[v];

        if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
            return (to - from + 1) * n.pendingVal;
        }

        if (contains(from, to, n.from, n.to)) {
            return heap[v].sum;
        }

        if (intersects(from, to, n.from, n.to)) {
            propagate(v);
            double leftSum = sum(2 * v, from, to);
            double rightSum = sum(2 * v + 1, from, to);

            return leftSum + rightSum;
        }

        return 0;
    }


    public double min(int from, int to) {
        return min(1, from, to);
    }

    private double min(int v, int from, int to) { 
        Node n = heap[v];

        if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
            return n.pendingVal;
        }

        if (contains(from, to, n.from, n.to)) {
            return heap[v].min;
        }

        if (intersects(from, to, n.from, n.to)) {
            propagate(v);
            double leftMin = min(2 * v, from, to);
            double rightMin = min(2 * v + 1, from, to);

            return Math.min(leftMin, rightMin);
        }

        return Integer.MAX_VALUE;
    }
    
    public double max(int from, int to) {
        return max(1, from, to);
    }
    
    private double max(int v, int from, int to) { 
        Node n = heap[v];

        if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
            return n.pendingVal;
        }

        if (contains(from, to, n.from, n.to)) {
            return heap[v].max;
        }

        if (intersects(from, to, n.from, n.to)) {
            propagate(v);
            double leftMin = max(2 * v, from, to);
            double rightMin = max(2 * v + 1, from, to);

            return Math.max(leftMin, rightMin);
        }

        return Integer.MIN_VALUE;
    }
    
    public void update(int from, double value){
        update(from, from, value); 
    }

    public void update(int from, int to, double value) {
        update(1, from, to, value);
    }

    private void update(int v, int from, int to, double value) {

        Node n = heap[v];

      
        if (contains(from, to, n.from, n.to)) {
            change(n, value);
        }

        if (n.size() == 1) return;

        if (intersects(from, to, n.from, n.to)) {

            propagate(v);

            update(2 * v, from, to, value);
            update(2 * v + 1, from, to, value);

            n.sum = heap[2 * v].sum + heap[2 * v + 1].sum;
            n.min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
            n.max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
        }
    }

    private void propagate(int v) {
        Node n = heap[v];

        if (n.pendingVal != null) {
            change(heap[2 * v], n.pendingVal);
            change(heap[2 * v + 1], n.pendingVal);
            n.pendingVal = null; //unset the pending propagation value
        }
    }

    private void change(Node n, double value) {
        n.pendingVal = value;
        n.sum = n.size() * value;
        n.min = value;
        n.max = value;
        array[n.from] = value;

    }

    private boolean contains(int from1, int to1, int from2, int to2) {
        return from2 >= from1 && to2 <= to1;
    }

    private boolean intersects(int from1, int to1, int from2, int to2) {
        return from1 <= from2 && to1 >= from2 || from1 >= from2 && from1 <= to2;
    }

    private static class Node {
        double sum;
        double min;
        double max;
        Double pendingVal = null;
        int from;
        int to;

        int size() {
            return to - from + 1;
        }
    }
    
    public static void main(String[] args) {
        double[] array = {4.5, 4.2, 4.7, 3.1, 3.3, 4, 3, 5, 4.2, 3, 2.9, 4.2};
        SegmentTree segment = new SegmentTree(array);
        double max = segment.max(0, 11);
        double min = segment.min(0, 11);
        double sum = segment.sum(0, 11);
        System.out.println("Max : " + max + " Min : " + min + " Sum : " + redondear(sum, 2)); 
        segment.update(1, 100); // actualiza en la posicion 1 el valor 100
        max = segment.max(0, 11);
        min = segment.min(0, 11);
        sum = segment.sum(0, 11);
        System.out.println("Max : " + max + " Min : " + min + " Sum : " + redondear(sum, 2)); 
    }
    public static double redondear(double number, int digitos){
        int cifras= (int) Math.pow(10,digitos);
        return Math.rint(number * cifras) / cifras;
    }
}