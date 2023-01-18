package it.unicam.cs.asdl2223.es9;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Classe che implementa uno heap binario che può contenere elementi non nulli
 * possibilmente ripetuti.
 * 
 * @author Template: Luca Tesei, Implementation: collettiva
 *
 * @param <E>
 *                il tipo degli elementi dello heap, che devono avere un
 *                ordinamento naturale.
 */
public class MaxHeap<E extends Comparable<E>> {

    /**
     * L'array che serve come base per lo heap
     */
    private ArrayList<E> heap;

    /**
     * Costruisce uno heap vuoto.
     */
    public MaxHeap() {
        this.heap = new ArrayList<E>();
    }

    /**
     * Restituisce il numero di elementi nello heap.
     * 
     * @return il numero di elementi nello heap
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Determina se lo heap è vuoto.
     * 
     * @return true se lo heap è vuoto.
     */
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    /**
     * Costruisce uno heap a partire da una lista di elementi.
     * 
     * @param list
     *                 lista di elementi
     * @throws NullPointerException
     *                                  se la lista è nulla
     */
    public MaxHeap(List<E> list) {
        if (list == null) {
            throw new NullPointerException("La lista passata è nulla");
        }
        this.heap = new ArrayList<E>(list);
        int seeSize = heap.size();

        for (int i = (this.heap.size())/2; i >= 0 ; i--) {
            this.heapify(i);
        }
    }

    /**
     * Inserisce un elemento nello heap
     * 
     * @param el
     *               l'elemento da inserire
     * @throws NullPointerException
     *                                  se l'elemento è null
     * 
     */
    public void insert(E el) {
        if (el == null) {
            throw new NullPointerException("L'elemento passato è null");
        }
        this.heap.add(el);
        int i = this.size()-1;
        while (i > 0 && this.heap.get(parentIndex(i)).compareTo(this.heap.get(i)) < 0){
            E temp = this.heap.get(i);
            this.heap.set(i, this.heap.get(parentIndex(i)));
            this.heap.set(parentIndex(i), temp);

            i = parentIndex(i);
        }
    }

    /**
     * Funzione di comodo per calcolare l'indice del figlio sinistro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int leftIndex(int i) {
        if (i == 0) return 1;
        else return 2*i+1;
    }

    /**
     * Funzione di comodo per calcolare l'indice del figlio destro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int rightIndex(int i) {
        if (i == 0) return 2;
        else return 2*i+2;
    }

    /**
     * Funzione di comodo per calcolare l'indice del genitore del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int parentIndex(int i) {
        if (i == 1 || i == 2) return 0;
        else return (i-1)/2;
    }

    /**
     * Ritorna l'elemento massimo senza toglierlo.
     * 
     * @return l'elemento massimo dello heap oppure null se lo heap è vuoto
     */
    public E getMax() {
        if (this.isEmpty()) return null;
        else return heap.get(0);
    }

    /**
     * Estrae l'elemento massimo dallo heap. Dopo la chiamata tale elemento non
     * è più presente nello heap.
     * 
     * @return l'elemento massimo di questo heap oppure null se lo heap è vuoto
     */
    public E extractMax() {
        if (this.isEmpty()) return null;

        E max = this.getMax();

        this.heap.set(0, this.heap.get(this.size()-1));
        this.heap.set(this.size()-1, max);
        this.heap.remove(this.size()-1);

        this.heapify(0);

        return max;
    }

    /**
     * Ricostituisce uno heap a partire dal nodo in posizione i assumendo che i
     * suoi sottoalberi sinistro e destro (se esistono) siano heap.
     */
    private void heapify(int i) {
        int l = leftIndex(i);
        int r = rightIndex(i);
        int largest = l;

        if (l < this.size() && this.heap.get(l).compareTo(this.heap.get(i)) > 0) {
            largest = l;
        } else largest = i;

        if (r < this.size() && this.heap.get(r).compareTo(this.heap.get(largest)) > 0) {
            largest = r;
        }

        if (largest != i) {
            E temp = this.heap.get(i);
            this.heap.set(i, this.heap.get(largest));
            this.heap.set(largest, temp);

            this.heapify(largest);
        }
    }
    
    /**
     * Only for JUnit testing purposes.
     * 
     * @return the arraylist representing this max heap
     */
    protected ArrayList<E> getHeap() {
        return this.heap;
    }
}
