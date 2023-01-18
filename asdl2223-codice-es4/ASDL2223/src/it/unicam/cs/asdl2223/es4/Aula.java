package it.unicam.cs.asdl2223.es4;

/**
 * Un oggetto della classe aula rappresenta una certa aula con le sue facilities
 * e le sue prenotazioni.
 * 
 * @author Template: Luca Tesei, Implementation: Collective
 *
 */
public class Aula implements Comparable<Aula> {

    /**
     * numero iniziale delle posizioni dell'array facilities. Se viene richiesto
     * di inserire una facility e l'array è pieno questo viene raddoppiato. La
     * costante è protected solo per consentirne l'accesso ai test JUnit
     */
    protected static final int INIT_NUM_FACILITIES = 5;

    /**
     * Numero iniziale delle posizioni dell'array prenotazioni. Se viene
     * richiesto di inserire una prenotazione e l'array è pieno questo viene
     * raddoppiato. La costante è protected solo per consentirne l'accesso ai
     * test JUnit.
     */
    protected static final int INIT_NUM_PRENOTAZIONI = 100;

    // Identificativo unico di un'aula
    private final String nome;

    // Location dell'aula
    private final String location;

    /**
     * Insieme delle facilities di quest'aula. L'array viene creato all'inizio
     * della dimensione specificata nella costante INIT_NUM_FACILITIES. Il
     * metodo addFacility(Facility) raddoppia l'array qualora non ci sia più
     * spazio per inserire la facility.
     */
    private Facility[] facilities;

    // numero corrente di facilities inserite
    private int numFacilities;

    /**
     * Insieme delle prenotazioni per quest'aula. L'array viene creato
     * all'inizio della dimensione specificata nella costante
     * INIT_NUM_PRENOTAZIONI. Il metodo addPrenotazione(TimeSlot, String,
     * String) raddoppia l'array qualora non ci sia più spazio per inserire la
     * prenotazione.
     */
    private Prenotazione[] prenotazioni;

    // numero corrente di prenotazioni inserite
    private int numPrenotazioni;

    /**
     * Costruisce una certa aula con nome e location. Il set delle facilities è
     * vuoto. L'aula non ha inizialmente nessuna prenotazione.
     * 
     * @param nome
     *                     il nome dell'aula
     * @param location
     *                     la location dell'aula
     * 
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla
     */
    public Aula(String nome, String location) {
        if (nome == null || location == null) {
            throw new NullPointerException("Uno dei valori passati è null.");
        }
        this.nome = nome;
        this.location = location;
        facilities = new Facility[INIT_NUM_FACILITIES];
        prenotazioni = new Prenotazione[INIT_NUM_PRENOTAZIONI];
        numFacilities = 0;
        numPrenotazioni = 0;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
        return getNome().hashCode();
    }

    /* Due aule sono uguali se e solo se hanno lo stesso nome */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Aula)) return false;
        return this.getNome().equals(((Aula) obj).getNome());
    }

    /* L'ordinamento naturale si basa sul nome dell'aula */
    @Override
    public int compareTo(Aula o) {
        return this.getNome().compareTo(o.getNome());
    }

    /**
     * @return the facilities
     */
    public Facility[] getFacilities() {
        return this.facilities;
    }

    /**
     * @return il numero corrente di facilities
     */
    public int getNumeroFacilities() {
        return this.numFacilities;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * @return the prenotazioni
     */
    public Prenotazione[] getPrenotazioni() {
        return this.prenotazioni;
    }

    /**
     * @return il numero corrente di prenotazioni
     */
    public int getNumeroPrenotazioni() {
        return this.numPrenotazioni;
    }

    /**
     * Aggiunge una faciltity a questa aula. Controlla se la facility è già
     * presente, nel qual caso non la inserisce.
     * 
     * @param f
     *              la facility da aggiungere
     * @return true se la facility non era già presente e quindi è stata
     *         aggiunta, false altrimenti
     * @throws NullPointerException
     *                                  se la facility passata è nulla
     */
    public boolean addFacility(Facility f) {
        /*
         * Nota: attenzione! Per controllare se una facility è già presente
         * bisogna usare il metodo equals della classe Facility.
         * 
         * Nota: attenzione bis! Si noti che per le sottoclassi di Facility non
         * è richiesto di ridefinire ulteriormente il metodo equals...
         */
        if (f == null) {
            throw new NullPointerException("La facility passata è nulla.");
        }
        if (!(facilities[facilities.length - 1] == null)) {
            newFacilityArray();
        }
        int i = 0;
        while (i < facilities.length && !(facilities[i] == null)) {
            if (facilities[i].equals(f)) {
                return false;
            }
            i++;
        }
        facilities[i] = f;
        numFacilities++;
        return true;
    }

    /**
     * Determina se l'aula è libera in un certo time slot.
     * 
     * 
     * @param ts
     *               il time slot da controllare
     * 
     * @return true se l'aula risulta libera per tutto il periodo del time slot
     *         specificato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean isFree(TimeSlot ts) {
        if (ts == null) {
            throw new NullPointerException("Il TimeSlot passato è nullo.");
        }
        if (prenotazioni[0] == null) return true;
        for (Prenotazione p : this.prenotazioni) {
            if (!(p == null) && p.getTimeSlot().overlapsWith(ts)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determina se questa aula soddisfa tutte le facilities richieste
     * rappresentate da un certo insieme dato.
     * 
     * @param requestedFacilities
     *                                l'insieme di facilities richieste da
     *                                soddisfare, sono da considerare solo le
     *                                posizioni diverse da null
     * @return true se e solo se tutte le facilities di
     *         {@code requestedFacilities} sono soddisfatte da questa aula.
     * @throws NullPointerException
     *                                  se il set di facility richieste è nullo
     */
    public boolean satisfiesFacilities(Facility[] requestedFacilities) {
        int cont = 0;
        if (requestedFacilities[0] == null) return true;
        for (Facility f : this.facilities) {
            if (!(f == null)) {

                for (Facility f1 : requestedFacilities) {
                    if (!(f1 == null) && f.equals(f1)) cont++;
                }
            }
        }
        return cont >= requestedFacilities.length;
    }

    /**
     * Prenota l'aula controllando eventuali sovrapposizioni.
     * 
     * @param ts
     * @param docente
     * @param motivo
     * @throws IllegalArgumentException
     *                                      se la prenotazione comporta una
     *                                      sovrapposizione con un'altra
     *                                      prenotazione nella stessa aula.
     * @throws NullPointerException
     *                                      se una qualsiasi delle informazioni
     *                                      richieste è nulla.
     */
    public void addPrenotazione(TimeSlot ts, String docente, String motivo) {
        if (ts == null || docente == null || motivo == null) {
            throw new NullPointerException("La facility passata è nulla.");
        }
        if (!(prenotazioni[prenotazioni.length - 1] == null)) {
            newPrenotazioneArray();
        }
        if (!this.isFree(ts)) {
            throw new IllegalArgumentException("L'aula è già prenotata per quell'orario.");
        }
        boolean flag = false;
        for (int i = 0; i < prenotazioni.length; i++) {
            if (prenotazioni[i] == null && !flag) {
                flag = true;
                prenotazioni[i] = new Prenotazione(this, ts, docente, motivo);
                numPrenotazioni++;
            }
        }
    }

    void newFacilityArray() {
        Facility[] temp = new Facility[facilities.length*2];
        System.arraycopy(facilities, 0, temp, 0, facilities.length);
        facilities = temp;
    }

    void newPrenotazioneArray() {
        Prenotazione[] temp = new Prenotazione[prenotazioni.length*2];
        System.arraycopy(prenotazioni, 0, temp, 0, prenotazioni.length);
        prenotazioni = temp;
    }
}
