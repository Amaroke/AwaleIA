//TODO DOC A FAIRE

package awale.awaleia;

import java.util.ArrayList;

/**
 * La classe awaleia.Partie.
 */
public class Partie {

    private ArrayList<Observateur> observateurs = new ArrayList<>();
    private final Plateau plateau;
    private final IA ia;
    private final Joueur joueur;
    private boolean termine;
    private boolean tourDeLIA;
    private int profondeur;

    /**
     * Instantiates une nouvelle Partie.
     */
    public Partie() {
        this.plateau = new Plateau();
        this.ia = new IA();
        this.joueur = new Joueur();
        this.tourDeLIA = ((Math.random() * (2)) > 1);
        this.termine = false;
        this.profondeur = 5;
    }

    /**
     * Instancie une nouvelle Partie par copie d'une autre.
     */
    public Partie(Partie partie) {
        this.plateau = new Plateau(partie.getPlateau());
        this.ia = new IA(partie.getIa());
        this.joueur = new Joueur(partie.getJoueur());
        this.tourDeLIA = partie.isTourDeLIA();
        this.termine = partie.isTermine();
        this.observateurs = partie.getObservateurs();
        this.profondeur = partie.getProfondeur();
    }

    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    /**
     * Prevenir observateurs.
     */
    public void prevenirObservateurs() {
        for (Observateur o : this.observateurs) {
            o.reagir();
        }
    }

    /**
     * La fonction jouant le coup d'un joueur.
     *
     * @param coup le coup
     */
    public void jouer(int coup) {
            // La variable "graines" contient le nombre de graines à distribuer.
            int graines = this.rafler(coup);
            int caseSuivante = coup;
            while (graines != 0) {
                // La variable "caseSuivante" prends la variable de la case suivante en suivant le sens antihoraire.
                caseSuivante = (caseSuivante == 0) ? 6 : (caseSuivante == 11) ? 5 : (caseSuivante > 0 && caseSuivante < 6) ? caseSuivante - 1 : caseSuivante + 1;
                // Sauter, si on a plus de 12 grcoupaines on ne pose pas dans la case où l'on a pris.
                if (caseSuivante != coup) {
                    // Une graine est déposée dans cette case.
                    this.plateau.deposerGraineCase(caseSuivante);
                    // Si on est au dernier dépôt de graines.
                    if (graines == 1) {
                        // Si la dernière case où l'on dépose possède une ou deux graines.
                        if (this.plateau.getNbGraines(caseSuivante) == 2 || this.plateau.getNbGraines(caseSuivante) == 3) {
                            // Si le coup est joué par l'IA et que la dernière case est chez le joueur.
                            if (isTourDeLIA()) {
                                this.capturer(caseSuivante, this.ia);
                            } else {
                                this.capturer(caseSuivante, this.joueur);
                            }
                        }
                    }
                    graines--;
                }
            }
        // Si le joueur possède au moins 25 graines dans son grenier alors la parte est terminé et il est le gagnant.
        if (this.joueur.getGrenier() >= 25) {
            this.termine = true;
            joueur.aGagne();
        } else if (this.ia.getGrenier() >= 25) {
            this.termine = true;
            ia.aGagne();
        }
        // C'est le tour de l'autre joueur.
        this.changementDeTour();
        if (this.plateau.partieJoueurVide() && !this.isTourDeLIA()) {
            this.ia.ajouterGrenier(getPlateau().prendreToutesLesGraines());
            this.termine = true;
        }
        if (this.plateau.partieAdversaireVide() && this.isTourDeLIA()) {
            this.joueur.ajouterGrenier(getPlateau().prendreToutesLesGraines());
            this.termine = true;
        }
    }

    /**
     * La fonction qui effectue la capture.
     *
     * @param caseACapturer la case à capturer
     * @param joueur        le joueur
     */
    public void capturer(int caseACapturer, Joueur joueur) {
        // On crée une copie de la partie pour observer ce qui se passe si l'on capture.
        Partie partieATester = new Partie(this);
        partieATester.capturerTest(caseACapturer, partieATester.getJoueur());
        // Si la capture n'affame pas le joueur.
        if (!(!partieATester.isTourDeLIA() && partieATester.getPlateau().partieAdversaireVide()) && !(partieATester.isTourDeLIA() && partieATester.getPlateau().partieJoueurVide())) {
            // Le joueur prend les graines de la case et les mets dans son grenier.
            joueur.ajouterGrenier(this.plateau.prendreGrainesCase(caseACapturer));
        }

    }

    public void capturerTest(int caseACapturer, Joueur joueur) {
        joueur.ajouterGrenier(this.plateau.prendreGrainesCase(caseACapturer));
    }

    /**
     * La fonction qui effectue une rafle et renvoi le nombre de cases récupérées.
     *
     * @param caseARafler la case à rafler
     * @return le nombre de cases récupérées
     */
    public int rafler(int caseARafler) {
        int nbGrainesRaflee = this.plateau.prendreGrainesCase(caseARafler);
        // Je pars d'une case et je rafle toutes les graines des cases précédentes et du même côté du plateau.
        if (this.tourDeLIA) {
            for (int i = caseARafler + 1; i < 6; i++) {
                if (this.plateau.getNbGraines(i) == 2 || this.plateau.getNbGraines(i) == 3) {
                    nbGrainesRaflee += this.plateau.prendreGrainesCase(i);
                } else {
                    break;
                }
            }
        } else {
            for (int i = caseARafler - 1; i >= 6; i--) {
                if (this.plateau.getNbGraines(i) == 2 || this.plateau.getNbGraines(i) == 3) {
                    nbGrainesRaflee += this.plateau.prendreGrainesCase(i);
                } else {
                    break;
                }
            }
        }
        return nbGrainesRaflee;
    }

    /**
     * La fonction qui renvoi la liste des coups possibles du tout actuel.
     *
     * @return les coups possibles
     */
    public ArrayList<Integer> testCoupsPossibles() {
        ArrayList<Integer> coupsPossibles = new ArrayList<>();
        Partie partieATester = new Partie(this);
        if (this.tourDeLIA) {
            for (int i = 0; i < 6; i++) {
                if (partieATester.getPlateau().getNbGraines(i) != 0) {
                    partieATester.jouer(i);
                    if (!partieATester.plateau.partieAdversaireVide()) {
                        coupsPossibles.add(i);
                    }
                    partieATester = new Partie(this);
                }
            }
        } else {
            for (int i = 6; i < 12; i++) {
                if (partieATester.getPlateau().getNbGraines(i) != 0) {
                    partieATester.jouer(i);
                    if (!partieATester.plateau.partieAdversaireVide()) {
                        coupsPossibles.add(i);
                    }
                    partieATester = new Partie(this);
                }
            }
        }
        return coupsPossibles;
    }

    /**
     * Effectue un changement de tour.
     */
    public void changementDeTour() {
        this.tourDeLIA = !this.tourDeLIA;
    }

    public int eval0() {
        return this.ia.getGrenier() - this.joueur.getGrenier();
    }

    /**
     * Renvoi si la partie est terminé.
     *
     * @return booléen, indiquant la fin de la partie
     */
    public boolean isTermine() {
        return termine;
    }

    /**
     * Renvoi si c'est le tour du joueur.
     *
     * @return booléen, indiquant si c'est le tour du joueur
     */
    public boolean isTourDeLIA() {
        return tourDeLIA;
    }

    /**
     * Renvoi le plateau de jeu.
     *
     * @return le plateau
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Getter sur l'ia.
     *
     * @return l'ia
     */
    public IA getIa() {
        return ia;
    }

    /**
     * Getter sur le joueur.
     *
     * @return le joueur
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Gets observateurs.
     *
     * @return the observateurs
     */
    public ArrayList<Observateur> getObservateurs() {
        return observateurs;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }

    /**
     * Sets tour de lia.
     *
     * @param tourDeLIA the tour de lia
     */
    public void setTourDeLIA(boolean tourDeLIA) {
        this.tourDeLIA = tourDeLIA;
    }
}
