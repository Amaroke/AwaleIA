package awale.awaleia;

/**
 * La classe Plateau.
 */
public class Plateau {

    private final int[] plateau;

    /**
     * Instancie un nouveau Plateau.
     */
    public Plateau() {
        this.plateau = new int[12];
        init();
    }

    /**
     * Instancie un nouveau Plateau par copie d'un autre plateau.
     *
     * @param plateau le plateau à copier
     */
    public Plateau(Plateau plateau) {
        this.plateau = new int[12];
        for (int i = 0; i < 12; i++) {
            this.plateau[i] = plateau.getNbGraines(i);
        }
    }

    /**
     * Initialise un plateau, à l'état de début de partie.
     */
    public void init() {
        for (int i = 0; i < 12; i++) {
            plateau[i] = 4;
        }
    }

    /**
     * Prendre les graines d'une case.
     *
     * @param emplacement l'emplacement d'où il faut prendre les graines
     * @return le nombre de graines prises
     */
    public int prendreGrainesCase(int emplacement) {
        int res = this.plateau[emplacement];
        this.plateau[emplacement] = 0;
        return res;
    }


    /**
     * Fonction qui prend toutes les graines du plateau.
     *
     * @return le nombre de graines prises.
     */
    public int prendreToutesLesGraines() {
        int res = 0;
        for (int i = 0; i < 12; i++) {
            res += prendreGrainesCase(i);
        }
        return res;
    }

    /**
     * Fonction qui dépose une graine dans une case.
     *
     * @param emplacement l'emplacement du dépôt
     */
    public void deposerGraineCase(int emplacement) {
        this.plateau[emplacement]++;
    }

    /**
     * Test si le côté du joueur sur le plateau est vide.
     *
     * @return Si le plateau est vide
     */
    public boolean partieJoueurVide() {
        for (int i = 6; i < 12; i++) {
            if (plateau[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test si le côté de l'IA sur le plateau est vide.
     *
     * @return Si le plateau est vide
     */
    public boolean partieAdversaireVide() {
        for (int i = 0; i < 6; i++) {
            if (plateau[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Getter nombre graines.
     *
     * @param emplacement l'emplacement d'où l'on veut le nombre de graines
     * @return Le nombre graines
     */
    public int getNbGraines(int emplacement) {
        return this.plateau[emplacement];
    }

}
