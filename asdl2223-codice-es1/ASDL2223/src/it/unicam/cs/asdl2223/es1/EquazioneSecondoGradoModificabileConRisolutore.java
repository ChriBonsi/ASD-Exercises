/**
 * 
 */
package it.unicam.cs.asdl2223.es1;

/**
 * Un oggetto di questa classe permette di rappresentare una equazione di
 * secondo grado e di trovarne le soluzioni reali. I valori dei parametri
 * dell'equazione possono cambiare nel tempo. All'inizio e ogni volta che viene
 * cambiato un parametro la soluzione dell'equazione non esiste e deve essere
 * calcolata con il metodo <code>solve()</code>. È possibile sapere se al
 * momento la soluzione dell'equazione esiste con il metodo
 * <code>isSolved()</code>. Qualora la soluzione corrente non esista e si tenti
 * di ottenerla verrà lanciata una eccezione.
 * 
 * @author Template: Luca Tesei, Implementation: Collettiva da Esercitazione a
 *         Casa
 *
 */
public class EquazioneSecondoGradoModificabileConRisolutore {
    /*
     * Costante piccola per il confronto di due numeri double
     */
    private static final double EPSILON = 1.0E-15;

    private double a;

    private double b;

    private double c;

    private boolean solved;

    private SoluzioneEquazioneSecondoGrado lastSolution;

    /**
     * Costruisce una equazione di secondo grado modificabile. All'inizio
     * l'equazione non è risolta.
     * 
     * 
     * @param a
     *              coefficiente del termine x^2, deve essere diverso da zero.
     * @param b
     *              coefficiente del termine x
     * @param c
     *              termine noto
     * @throws IllegalArgumentException
     *                                      se il parametro <code>a</code> è
     *                                      zero
     * 
     */
    public EquazioneSecondoGradoModificabileConRisolutore(double a, double b, double c) {
        if (a != 0){
            this.a = a;
            this.b = b;
            this.c = c;
        }
        else{
            throw new IllegalArgumentException("The a parameter is equal to 0.");
        }
    }

    /**
     * @return il valore corrente del parametro a
     */
    public double getA() {
        return a;
    }

    /**
     * Cambia il parametro a di questa equazione. Dopo questa operazione
     * l'equazione andrà risolta di nuovo.
     * 
     * @param a
     *              il nuovo valore del parametro a
     * @throws IllegalArgumentException
     *                                      se il nuovo valore è zero
     */
    public void setA(double a) {
        if (a != 0){
            this.a = a;
        }
        else{
            throw new IllegalArgumentException("Il parametro a è 0");
        }
        this.solved = false;
    }

    /**
     * @return il valore corrente del parametro b
     */
    public double getB() {
        return b;
    }

    /**
     * Cambia il parametro b di questa equazione. Dopo questa operazione
     * l'equazione andrà risolta di nuovo.
     * 
     * @param b
     *              il nuovo valore del parametro b
     */
    public void setB(double b) {
        this.b = b;
        this.solved = false;
    }

    /**
     * @return il valore corrente del parametro c
     */
    public double getC() {
        return c;
    }

    /**
     * Cambia il parametro c di questa equazione. Dopo questa operazione
     * l'equazione andrà risolta di nuovo.
     * 
     * @param c
     *              il nuovo valore del parametro c
     */
    public void setC(double c) {
        this.c = c;
        this.solved = false;
    }

    /**
     * Determina se l'equazione, nel suo stato corrente, è già stata risolta.
     * 
     * @return true se l'equazione è risolta, false altrimenti
     */
    public boolean isSolved() {
        return solved;
    }

    /**
     * Risolve l'equazione risultante dai parametri a, b e c correnti. Se
     * l'equazione era già stata risolta con i parametri correnti non viene
     * risolta di nuovo.
     */
    public void solve() {
        if (!this.isSolved()){

            EquazioneSecondoGrado auto = new EquazioneSecondoGrado(this.getA(), this.getB(), this.getC());
            double delta = Math.pow(this.b,2)-4*this.a*this.c;

            if (delta < 0) {
                lastSolution = new SoluzioneEquazioneSecondoGrado(auto);
                this.solved = true;
            }

            else if (delta == 0) {
                double sol =(-1*this.b)/(2*this.a);

                lastSolution = new SoluzioneEquazioneSecondoGrado(auto, sol);
                this.solved = true;
            }

            else if (delta > 0) {
                double s1 =(-1*this.b + Math.sqrt(delta))/(2*this.a);
                double s2 =(-1*this.b - Math.sqrt(delta))/(2*this.a);

                lastSolution = new SoluzioneEquazioneSecondoGrado(auto, s1,s2);
                this.solved = true;
            }
        }
        else System.out.println("Equation already solved.");
    }

    /**
     * Restituisce la soluzione dell'equazione risultante dai parametri
     * correnti. L'equazione con i parametri correnti deve essere stata
     * precedentemente risolta.
     * 
     * @return la soluzione
     * @throws IllegalStateException
     *                                   se l'equazione risulta non risolta,
     *                                   all'inizio o dopo il cambiamento di
     *                                   almeno uno dei parametri
     */
    public SoluzioneEquazioneSecondoGrado getSolution() {
        if(!this.isSolved()) {
            throw new IllegalArgumentException("The equation has not been solved yet.");
        }
        else {
            //System.out.println(lastSolution.toString());
            return lastSolution;
        }
    }

}
