package it.unicam.cs.asdl2223.es3;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Un time slot è un intervallo di tempo continuo che può essere associato ad
 * una prenotazione. Gli oggetti della classe sono immutabili. Non sono ammessi
 * time slot che iniziano e finiscono nello stesso istante.
 *
 * @author Luca Tesei
 *
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /**
     * Rappresenta la soglia di tolleranza da considerare nella sovrapposizione
     * di due Time Slot. Se si sovrappongono per un numero di minuti minore o
     * uguale a questa soglia allora NON vengono considerati sovrapposti.
     */
    public static final int MINUTES_OF_TOLERANCE_FOR_OVERLAPPING = 5;


    //decido di usare il costruttore
    // GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute)

    private final GregorianCalendar start;
    private final GregorianCalendar stop;

    /**
     * Crea un time slot tra due istanti di inizio e fine
     *
     * @param start
     *                  inizio del time slot
     * @param stop
     *                  fine del time slot
     * @throws NullPointerException
     *                                      se uno dei due istanti, start o
     *                                      stop, è null
     * @throws IllegalArgumentException
     *                                      se start è uguale o successivo a
     *                                      stop
     */
    public TimeSlot(GregorianCalendar start, GregorianCalendar stop) {
        if (start == null || stop == null) {
            throw new NullPointerException("Uno dei due tempi assegnati è nullo");
        } else if (start.compareTo(stop) >= 0 /*start == stop || start.after(stop)*/) {
            throw new IllegalArgumentException("Il tempo di start è uguale o successivo al tempo di stop");
        } else {
            this.start = start;
            this.stop = stop;
        }
    }

    /**
     * @return the start
     */
    public GregorianCalendar getStart() {
        return start;
    }

    /**
     * @return the stop
     */
    public GregorianCalendar getStop() {
        return stop;
    }

    /**
     * Un time slot è uguale a un altro se rappresenta esattamente lo stesso
     * intervallo di tempo, cioè se inizia nello stesso istante e termina nello
     * stesso istante.
     */
    @Override
    public boolean equals(Object obj) {
        /*
         * Ho usato gli equals nei controlli degli if perché .getStart mi dà un oggetto GregorianCalendar che ha già un
         * equals corretto.
         */

        if (this == obj) return true;
        if (!(obj instanceof TimeSlot)) return false;
        return this.getStart().equals(((TimeSlot) obj).getStart()) && this.getStop().equals(((TimeSlot) obj).getStop());
    }

    /**
     * Il codice hash associato a un timeslot viene calcolato a partire dei due
     * istanti di inizio e fine, in accordo con i campi usati per il metodo
     * equals.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getStop());
    }

    /**
     * Un time slot precede un altro se inizia prima. Se due time slot iniziano
     * nello stesso momento quello che finisce prima precede l'altro. Se hanno
     * stesso inizio e stessa fine sono uguali, in compatibilità con equals.
     */
    @Override
    public int compareTo(TimeSlot o) {
        if (this.getStart().equals(o.getStart())) {
            if (this.getStop().equals(o.getStop())) {
                return 0;
            }
            return this.getStop().compareTo(o.getStop());
        } else return this.getStart().compareTo(o.getStart());
    }

    /**
     * Determina il numero di minuti di sovrapposizione tra questo timeslot e
     * quello passato.
     *
     * @param o
     *              il time slot da confrontare con questo
     * @return il numero di minuti di sovrapposizione tra questo time slot e
     *         quello passato, oppure -1 se non c'è sovrapposizione. Se questo
     *         time slot finisce esattamente al millisecondo dove inizia il time
     *         slot <code>o</code> non c'è sovrapposizione, così come se questo
     *         time slot inizia esattamente al millisecondo in cui finisce il
     *         time slot <code>o</code>. In questi ultimi due casi il risultato
     *         deve essere -1 e non 0. Nel caso in cui la sovrapposizione non è
     *         di un numero esatto di minuti, cioè ci sono secondi e
     *         millisecondi che avanzano, il numero dei minuti di
     *         sovrapposizione da restituire deve essere arrotondato per difetto
     * @throws NullPointerException
     *                                      se il time slot passato è nullo
     * @throws IllegalArgumentException
     *                                      se i minuti di sovrapposizione
     *                                      superano Integer.MAX_VALUE
     */
    public int getMinutesOfOverlappingWith(TimeSlot o) {
        long millisDifference = 0;
        boolean intersecate = false;
        System.out.println(millisDifference);
        //1
        if (this.getStart().before(o.getStart()) && o.getStart().before(this.getStop())) {
            intersecate = true;

            if (o.getStop().before(this.getStop())) {
                millisDifference = o.getStop().getTimeInMillis() - o.getStart().getTimeInMillis();
            } else {
                millisDifference = this.getStop().getTimeInMillis() - o.getStart().getTimeInMillis();
            }

        } else if /*2*/ (o.getStart().before(this.getStart()) && this.getStart().before(o.getStop())) {
            intersecate = true;

            if (this.getStop().before(o.getStop())) {
                millisDifference = this.getStop().getTimeInMillis() - this.getStart().getTimeInMillis();
            } else {
                millisDifference = o.getStop().getTimeInMillis() - this.getStart().getTimeInMillis();
            }
        }
        int overlapping = (int) ((millisDifference / 1000) / 60);

        if (o == null) {
            throw new NullPointerException("Lo slot passato è nullo.");
        } else if (overlapping > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("I minuti di sovrapposizione superano il massimo valore.");
        } else if (intersecate) {
            return overlapping;
        } else return -1;
    }

    /**
     * Determina se questo time slot si sovrappone a un altro time slot dato,
     * considerando la soglia di tolleranza.
     *
     * @param o
     *              il time slot che viene passato per il controllo di
     *              sovrapposizione
     * @return true se questo time slot si sovrappone per più (strettamente) di
     *         MINUTES_OF_TOLERANCE_FOR_OVERLAPPING minuti a quello passato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean overlapsWith(TimeSlot o) {
        if (o == null) {
            throw new NullPointerException("Lo slot è nullo.");
        }
        return this.getMinutesOfOverlappingWith(o) > MINUTES_OF_TOLERANCE_FOR_OVERLAPPING;
    }

    /**
     * Ridefinisce il modo in cui viene reso un TimeSlot con una String.
     *
     * Esempio 1, stringa da restituire: "[4/11/2019 11.0 - 4/11/2019 13.0]"
     *
     * Esempio 2, stringa da restituire: "[10/11/2019 11.15 - 10/11/2019 23.45]"
     *
     * I secondi e i millisecondi eventuali non vengono scritti.
     */
    @Override
    public String toString() {
        int startMonth = this.getStart().get(Calendar.MONTH);
        int stopMonth = this.getStop().get(Calendar.MONTH);
        return "[" + this.getStart().get(Calendar.DATE) + "/" + (startMonth + 1) + "/" + this.getStart().get(Calendar.YEAR) + " " +
                this.getStart().get(Calendar.HOUR_OF_DAY) + "." + this.getStart().get(Calendar.MINUTE) + " - " +
                this.getStop().get(Calendar.DATE) + "/" + (stopMonth + 1) + "/" + this.getStop().get(Calendar.YEAR) + " " +
                this.getStop().get(Calendar.HOUR_OF_DAY) + "." + this.getStop().get(Calendar.MINUTE) + "]";
    }

}
