package it.unicam.cs.asdl2223.es4;

import java.util.Objects;

/**
 * Una prenotazione riguarda una certa aula per un certo time slot.
 * 
 * @author Template: Luca Tesei, Implementation: Collective
 *
 */
public class Prenotazione implements Comparable<Prenotazione> {

    private final Aula aula;

    private final TimeSlot timeSlot;

    private String docente;

    private String motivo;

    /**
     * Costruisce una prenotazione.
     * 
     * @param aula
     *                     l'aula a cui la prenotazione si riferisce
     * @param timeSlot
     *                     il time slot della prenotazione
     * @param docente
     *                     il nome del docente che ha prenotato l'aula
     * @param motivo
     *                     il motivo della prenotazione
     * @throws NullPointerException
     *                                  se uno qualsiasi degli oggetti passati è
     *                                  null
     */
    public Prenotazione(Aula aula, TimeSlot timeSlot, String docente,
                        String motivo) {
        if (aula == null || timeSlot == null || docente == null || motivo == null) {
            throw new NullPointerException("Uno degli oggetti passati è nullo.");
        }
        this.aula = aula;
        this.timeSlot = timeSlot;
        this.docente = docente;
        this.motivo = motivo;
    }

    /**
     * @return the aula
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * @return the timeSlot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * @return the docente
     */
    public String getDocente() {
        return docente;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param docente the docente to set
     */
    public void setDocente(String docente) {
        this.docente = docente;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aula == null) ? 0 : aula.hashCode());
        result = prime * result
                + ((timeSlot == null) ? 0 : timeSlot.hashCode());
        return result;
    }

    /*
     * L'uguaglianza è data solo da stessa aula e stesso time slot. Non sono
     * ammesse prenotazioni diverse con stessa aula e stesso time slot.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Prenotazione)) return false;
        return (this.getAula().equals(((Prenotazione) obj).getAula()) && (this.getTimeSlot().equals
                (((Prenotazione) obj).getTimeSlot())));
    }

    /*
     * Una prenotazione precede un altra in base all'ordine dei time slot. Se
     * due prenotazioni hanno lo stesso time slot allora una precede l'altra in
     * base all'ordine tra le aule.
     */
    @Override
    public int compareTo(Prenotazione o) {
        int compare = this.getTimeSlot().compareTo(o.getTimeSlot());
        if (compare == 0) {
            compare = this.getAula().compareTo(o.getAula());
        }
        return compare;
    }

    @Override
    public String toString() {
        return "Prenotazione [aula = " + aula + ", time slot =" + timeSlot
                + ", docente=" + docente + ", motivo=" + motivo + "]";
    }

}
