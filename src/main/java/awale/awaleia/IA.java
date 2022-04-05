package awale.awaleia;

import java.util.ArrayList;

/**
 * La classe Ia.
 */
public class IA extends Joueur {

    /**
     * Instancie une nouvelle Ia.
     */
    public IA() {
        super();
    }

    /**
     * Instancie une nouvelle Ia par copie d'une autre.
     *
     * @param ia l'ia à copier
     */
    public IA(IA ia) {
        super(ia);
    }

    /**
     * Fonction minimax avec elagage Alpha Beta.
     *
     * @param etat l'état de la partie
     * @param c    le facteur de branchement
     * @return l'état le plus favorable
     */
    public int minimaxAvecElagageAlphaBeta(Partie etat, int c) {
        ArrayList<Partie> successeurs = new ArrayList<>();
        ArrayList<Integer> successeursCoups = new ArrayList<>();
        int coupChoisi = -1;
        float score, scoreMax;
        ArrayList<Integer> coupsPossibles = etat.testCoupsPossibles();
        for (Integer i : coupsPossibles) {
            Partie copiePartie = new Partie(etat);
            copiePartie.jouer(i);
            successeurs.add(copiePartie);
            successeursCoups.add(i);
        }
        scoreMax = Integer.MIN_VALUE;
        for (int i = 0; i < successeurs.size(); i++) {
            score = evaluationAlphaBeta(c, successeurs.get(i), Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("L'évaluation du coup " + successeursCoups.get(i) + " est de " + score);
            if (score >= scoreMax) {
                coupChoisi = successeursCoups.get(i);
                scoreMax = score;
            }
        }
        System.out.println();
        return coupChoisi;
    }

    /**
     * Fonction d'évaluation alpha beta.
     *
     * @param c    la profondeur
     * @param etat l'état testé
     * @return le résultat de l'évaluation
     */
    public float evaluationAlphaBeta(int c, Partie etat, float alpha, float beta) {
        ArrayList<Partie> successeurs = new ArrayList<>();
        float scoreMax, scoreMin;
        if (etat.isTermine()) {
            return (etat.getJoueur().isGagnant() ? Integer.MIN_VALUE : (etat.getIa().isGagnant()) ? Integer.MAX_VALUE : 0);
        }
        if (c == 0) {
            return etat.eval0();
        }
        ArrayList<Integer> coupsPossibles = etat.testCoupsPossibles();
        for (Integer i : coupsPossibles) {
            Partie copiePartie = new Partie(etat);
            copiePartie.jouer(i);
            successeurs.add(new Partie(copiePartie));
        }
        if (etat.isTourDeLIA()) {
            scoreMax = Integer.MIN_VALUE;
            for (Partie successeur : successeurs) {
                scoreMax = Math.max(scoreMax, evaluationAlphaBeta(c - 1, successeur, alpha, beta));
                if (scoreMax >= beta) {
                    return scoreMax;
                }
                alpha = Math.max(alpha, scoreMax);
            }
            return scoreMax;
        } else {
            scoreMin = Integer.MAX_VALUE;
            for (Partie successeur : successeurs) {
                scoreMin = Math.min(scoreMin, evaluationAlphaBeta(c - 1, successeur, alpha, beta));
                if (scoreMin <= alpha) {
                    return scoreMin;
                }
                beta = Math.min(beta, scoreMin);
            }
            return scoreMin;
        }
    }
}
