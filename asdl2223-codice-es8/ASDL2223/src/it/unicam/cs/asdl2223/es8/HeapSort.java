/**
 * 
 */
package it.unicam.cs.asdl2223.es8;

import java.util.List;

/**
 * Classe che implementa un algoritmo di ordinamento basato su heap.
 *
 * @author Template: Luca Tesei, Implementation: collettiva
 */
public class HeapSort<E extends Comparable<E>> implements SortingAlgorithm<E> {

    private int operations;
    private int heapSize;


    @Override
    public SortingAlgorithmResult<E> sort(List<E> l) {
        if (l == null) throw new NullPointerException("Tentativo di ordinare una lista null");
        if (l.size() <= 1) return new SortingAlgorithmResult<E>(l, 0);
        this.heapSize = l.size();
        this.operations = 0;
        for (int i = (l.size() / 2) - 1; i >= 0; i--) {
            heapify(l, i);
        }
        for (int i = l.size() - 1; i > 0; i--) {
            E app = l.get(i);
            l.set(i, l.get(0));
            l.set(0, app);
            this.heapSize--;
            heapify(l, 0);
        }
        return new SortingAlgorithmResult<>(l, operations);
    }

    private void heapify(List<E> l, int i) {
        if (!hasLeft(i)) return;
        int max = i;
        if (l.get(max).compareTo(l.get(left(i))) < 0) max = left(i);
        this.operations++;
        if (hasRight(i)) {
            this.operations++;
            if (l.get(max).compareTo(l.get(right(i))) < 0) {
                max = right(i);
            }
        }
        if (max != i) {
            E app = l.get(i);
            l.set(i, l.get(max));
            l.set(max, app);
            heapify(l, max);
        }
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private boolean hasLeft(int i) {
        return left(i) < this.heapSize;
    }

    private boolean hasRight(int i) {
        return right(i) < this.heapSize;
    }

    @Override
    public String getName() {
        return "HeapSort";
    }
}
