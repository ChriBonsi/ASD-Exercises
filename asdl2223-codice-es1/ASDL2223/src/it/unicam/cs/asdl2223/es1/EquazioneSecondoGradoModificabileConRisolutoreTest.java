/**
 * 
 */
package it.unicam.cs.asdl2223.es1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Template: Luca Tesei, Implementation: Collettiva da Esercitazione a
 *         Casa
 *
 */
class EquazioneSecondoGradoModificabileConRisolutoreTest {
    /*
     * Costante piccola per il confronto di due numeri double
     */
    static final double EPSILON = 1.0E-15;

    @Test
    final void testEquazioneSecondoGradoModificabileConRisolutore() {
        // controllo che il valore 0 su a lanci l'eccezione
        assertThrows(IllegalArgumentException.class,
                () -> new EquazioneSecondoGradoModificabileConRisolutore(0, 1, 1));
        // devo controllare che comunque nel caso normale il costruttore
        // funziona
        EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, 1);
        // Controllo che all'inizio l'equazione non sia risolta
        assertFalse(eq.isSolved());
    }

    @Test
    final void testGetA() {
        double x = 10;
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                x, 1, 1);
        // controllo che il valore restituito sia quello che ho messo
        // all'interno
        // dell'oggetto
        assertTrue(x == e1.getA());
        // in generale si dovrebbe usare assertTrue(Math.abs(x -
        // e1.getA())<EPSILON) ma in
        // questo caso il valore che testiamo non ha subito manipolazioni quindi
        // la sua rappresentazione sarà la stessa di quella inserita nel
        // costruttore senza errori di approssimazione

    }

    @Test
    final void testSetA() {
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, 1);
        e1.setA(10);

        assertTrue(10 == e1.getA());
    }

    @Test
    final void testGetB() {
        double x = 10;
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, x, 1);

        assertTrue(x == e1.getB());
    }

    @Test
    final void testSetB() {
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, 1);
        e1.setB(10);

        assertTrue(10 == e1.getB());
    }

    @Test
    final void testGetC() {
        double x = 10;
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, x);

        assertTrue(x == e1.getC());
    }

    @Test
    final void testSetC() {
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, 1);
        e1.setC(10);

        assertTrue(10 == e1.getC());
    }

    @Test
    final void testIsSolved() {
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, 1);
        assertFalse(e1.isSolved());
        e1.solve();
        assertTrue(e1.isSolved());
    }

    @Test
    final void testSolve() {
        EquazioneSecondoGradoModificabileConRisolutore e3 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, 3);
        // controllo semplicemente che la chiamata a solve() non generi errori
        e3.solve();
        // i test con i valori delle soluzioni vanno fatti nel test del metodo
        // getSolution()
    }

    @Test
    final void testGetSolution() {
        EquazioneSecondoGradoModificabileConRisolutore e1 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 1, 1);
        EquazioneSecondoGradoModificabileConRisolutore e2 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, 2, 1);
        EquazioneSecondoGradoModificabileConRisolutore e3 = new EquazioneSecondoGradoModificabileConRisolutore(
                1, -5, 6);
        assertThrows(IllegalArgumentException.class, () -> e1.getSolution() );
        assertThrows(IllegalArgumentException.class, () -> e2.getSolution() );
        assertThrows(IllegalArgumentException.class, () -> e3.getSolution() );
        e1.solve();
        e2.solve();
        e3.solve();
        assertTrue(e1.getSolution().isEmptySolution());
        assertTrue(e2.getSolution().isOneSolution() && e2.getSolution().getS1() - (-1.0) < EPSILON);
        assertTrue(e3.getSolution().getS1() - 3.0 < EPSILON && e3.getSolution().getS2() - 2.0 < EPSILON);

    }

}
