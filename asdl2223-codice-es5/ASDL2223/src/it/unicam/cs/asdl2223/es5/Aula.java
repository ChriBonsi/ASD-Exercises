package it.unicam.cs.asdl2223.es5;

import java.util.*;


/**
 * Un oggetto della classe aula rappresenta una certa aula con le sue facilities
 * e le sue prenotazioni.
 *
 * @author Template: Luca Tesei, Implementazione: Collettiva
 */
public class Aula implements Comparable<Aula> {
    // Identificativo unico di un'aula
    private final String nome;

    // Location dell'aula
    private final String location;

    // Insieme delle facilities di quest'aula
    private final Set<Facility> facilities;

    // Insieme delle prenotazioni per quest'aula, segue l'ordinamento naturale
    // delle prenotazioni
    private final SortedSet<Prenotazione> prenotazioni;

    /**
     * Costruisce una certa aula con nome e location. Il set delle facilities è
     * vuoto. L'aula non ha inizialmente nessuna prenotazione.
     *
     * @param nome     il nome dell'aula
     * @param location la location dell'aula
     * @throws NullPointerException se una qualsiasi delle informazioni
     *                              richieste è nulla
     */
    public Aula(String nome, String location) {
        if (nome == null || location == null) {
            throw new NullPointerException("Uno dei parametri passati è nullo.");
        }
        this.nome = nome;
        this.location = location;
        this.facilities = new HashSet<Facility>();
        this.prenotazioni = new TreeSet<Prenotazione>();
    }

    /**
     * Costruisce una certa aula con nome, location e insieme delle facilities.
     * L'aula non ha inizialmente nessuna prenotazione.
     *
     * @param nome       il nome dell'aula
     * @param location   la location dell'aula
     * @param facilities l'insieme delle facilities dell'aula
     * @throws NullPointerException se una qualsiasi delle informazioni
     *                              richieste è nulla
     */
    public Aula(String nome, String location, Set<Facility> facilities) {
        if (nome == null || location == null || facilities == null) {
            throw new NullPointerException("Uno dei parametri passati è nullo.");
        }
        this.nome = nome;
        this.location = location;
        this.facilities = facilities;
        this.prenotazioni = new TreeSet<Prenotazione>();
    }

    /**
     * Aggiunge una faciltity a questa aula.
     *
     * @param f la facility da aggiungere
     * @return true se la facility non era già presente e quindi è stata
     * aggiunta, false altrimenti
     * @throws NullPointerException se la facility passata è nulla
     */
    public boolean addFacility(Facility f) {
        if (f == null) {
            throw new NullPointerException("La facility passata è nulla.");
        }
        if (this.facilities.contains(f)) return false;
        else return facilities.add(f);
    }

    /**
     * Determina se l'aula è libera in un certo time slot.
     *
     * @param ts il time slot da controllare
     * @return true se l'aula risulta libera per tutto il periodo del time slot
     * specificato
     * @throws NullPointerException se il time slot passato è nullo
     */
    public boolean isFree(TimeSlot ts) {
        /*
         * NOTA: sfruttare l'ordinamento tra le prenotazioni per rispondere in
         * maniera efficiente: poiché le prenotazioni sono in ordine crescente
         * di time slot se arrivo a una prenotazione che segue il time slot
         * specificato posso concludere che l'aula è libera nel time slot
         * desiderato e posso interrompere la ricerca
         */
        if (ts == null) {
            throw new NullPointerException("Il TimeSlot passato è nullo.");
        }
        if (prenotazioni.isEmpty()) return true;

        Iterator<Prenotazione> booking = prenotazioni.iterator();
        while (booking.hasNext()) {
            TimeSlot current = (booking.next()).getTimeSlot();
            if (current.compareTo(ts) == 0 || current.overlapsWith(ts))
                return false;
            else if (current.compareTo(ts) < 0 && !current.overlapsWith(ts))
                return true;
        }
        return true;
    }

    /**
     * Determina se questa aula soddisfa tutte le facilities richieste
     * rappresentate da un certo insieme dato.
     *
     * @param requestedFacilities l'insieme di facilities richieste da
     *                            soddisfare
     * @return true se e solo se tutte le facilities di
     * {@code requestedFacilities} sono soddisfatte da questa aula.
     * @throws NullPointerException se il set di facility richieste è nullo
     */
    public boolean satisfiesFacilities(Set<Facility> requestedFacilities) {
        if (requestedFacilities == null) {
            throw new NullPointerException("Il Set di facilities è nullo.");
        }
        if (requestedFacilities.isEmpty()) return true;
        Iterator req = requestedFacilities.iterator();
        boolean check;

        do {
            if (facilities.contains(req.next())) {
                check = true;
            } else check = false;
        } while (req.hasNext() && check);

        return check;
    }

    /**
     * Prenota l'aula controllando eventuali sovrapposizioni.
     *
     * @param ts
     * @param docente
     * @param motivo
     * @throws IllegalArgumentException se la prenotazione comporta una
     *                                  sovrapposizione con un'altra
     *                                  prenotazione nella stessa aula.
     * @throws NullPointerException     se una qualsiasi delle informazioni
     *                                  richieste è nulla.
     */
    public void addPrenotazione(TimeSlot ts, String docente, String motivo) {
        if (ts == null || docente == null || motivo == null) {
            throw new NullPointerException("Uno dei parametri è nullo.");
        }
        Iterator<Prenotazione> pren = prenotazioni.iterator();
        while (pren.hasNext()) {
            Prenotazione check = pren.next();
            if (ts.overlapsWith(check.getTimeSlot())) {
                throw new IllegalArgumentException("È già presente una prenotazione per questo orario");
            }
        }

        this.prenotazioni.add(new Prenotazione(this, ts, docente, motivo));
    }

    /**
     * Cancella una prenotazione di questa aula.
     *
     * @param p la prenotazione da cancellare
     * @return true se la prenotazione è stata cancellata, false se non era
     * presente.
     * @throws NullPointerException se la prenotazione passata è null
     */
    public boolean removePrenotazione(Prenotazione p) {
        if (p == null) {
            throw new NullPointerException("La prenotazione è nulla.");
        }
        return prenotazioni.remove(p);
    }

    /**
     * Rimuove tutte le prenotazioni di questa aula che iniziano prima (o
     * esattamente in) di un punto nel tempo specificato.
     *
     * @param timePoint un certo punto nel tempo
     * @return true se almeno una prenotazione è stata cancellata, false
     * altrimenti.
     * @throws NullPointerException se il punto nel tempo passato è nullo.
     */
    public boolean removePrenotazioniBefore(GregorianCalendar timePoint) {
        /*
         * NOTA: sfruttare l'ordinamento tra le prenotazioni per rispondere in
         * maniera efficiente: poiché le prenotazioni sono in ordine crescente
         * di time slot se ho raggiunto una prenotazione con tempo di inizio
         * maggiore del tempo indicato posso smettere la procedura
         */
        SortedSet pren = new TreeSet<Prenotazione>();
        Iterator<Prenotazione> booking = prenotazioni.iterator();
        boolean discriminator = false;
        while (booking.hasNext()) {
            Prenotazione temp = booking.next();
            GregorianCalendar current = temp.getTimeSlot().getStart();
            if (current.after(timePoint)) {
                pren.add(temp);
                discriminator = true;
            }
        }
        if (discriminator) {
            prenotazioni.clear();
            prenotazioni.addAll(pren);
        }
        return discriminator;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
        return this.nome.hashCode();
    }

    /* Due aule sono uguali se e solo se hanno lo stesso nome */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Aula)) {
            return false;
        }
        return this.nome.equals(((Aula) obj).nome);
    }

    /* L'ordinamento naturale si basa sul nome dell'aula */
    @Override
    public int compareTo(Aula o) {
        return this.getNome().compareTo(o.getNome());
    }

    /**
     * @return the facilities
     */
    public Set<Facility> getFacilities() {
        return facilities;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the prenotazioni
     */
    public SortedSet<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
}
