/**
 * 
 */
package it.unicam.cs.asdl2223.es9;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'algoritmo di Merge Sort integrata nel framework di
 * valutazione numerica. Non è richiesta l'implementazione in loco.
 * 
 * @author Template: Luca Tesei, Implementazione: collettiva
 *
 */
public class MergeSort<E extends Comparable<E>> implements SortingAlgorithm<E> {

    int compare = 0;

    public SortingAlgorithmResult<E> sort(List<E> l) {
        compare = 0;
        mergeSort(l, 0, l.size() - 1);
        return new SortingAlgorithmResult<>(l, compare);
    }

    void mergeSort(List<E> l, int head, int tail) {
        if (head < tail) {

            int middle = (head + tail) / 2;

            mergeSort(l, head, middle);
            mergeSort(l, middle + 1, tail);

            merge(l, head, middle, tail);
        }
    }


    void merge(List<E> l, int head, int middle, int tail) {
        int first1 = head, first2 = middle + 1;

        List<E> temp = new ArrayList<>();


        for (int i = head; i <= tail; i++) {
            compare++;
            if (first1 > middle) {
                temp.add(l.get(first2));
                first2++;
            } else if (first2 > tail) {
                temp.add(l.get(first1));
                first1++;
            } else if (l.get(first1).compareTo(l.get(first2)) < 0) {
                temp.add(l.get(first1));
                first1++;
            } else {
                temp.add(l.get(first2));
                first2++;
            }
        }

        for (int j = 0; j < temp.size(); j++) {
            l.set(head, temp.get(j));
            head++;
        }
    }

    public String getName() {
        return "MergeSort";
    }
}
