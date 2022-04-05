package awale.awaleia;

/**
 * La classe Joueur.
 */
public class Joueur {

    private int grenier;
    private boolean gagnant;

    /**
     * Instancie un nouveau Joueur.
     */
    public Joueur() {
        this.grenier = 0;
        this.gagnant = false;
    }

    /**
     * Instancie un nouveau Joueur par copie d'un autre joueur.
     *
     * @param joueur le joueur à copier
     */
    public Joueur(Joueur joueur) {
        this.grenier = joueur.getGrenier();
        this.gagnant = joueur.isGagnant();
    }

    /**
     * Fonction pour ajouter des graines au grenier.
     *
     * @param graines les graines
     */
    public void ajouterGrenier(int graines) {
        this.grenier += graines;
    }

    /**
     * Getter du nombre de graines du grenier.
     *
     * @return le nombre de graines dans le grenier
     */
    public int getGrenier() {
        return grenier;
    }

    /**
     * Fonction qui définit que le joueur a gagné.
     */
    public void aGagne() {
        this.gagnant = true;
    }

    /**
     * Getter sur la victoire du joueur.
     *
     * @return le booln indiquant la victoire du joueur.
     */
    public boolean isGagnant() {
        return gagnant;
    }
}
