package awale.awaleia;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControlleurPlateau implements Observateur {

    private final Partie partie;

    @FXML
    private Button trou0;
    @FXML
    private Button trou1;
    @FXML
    private Button trou2;
    @FXML
    private Button trou3;
    @FXML
    private Button trou4;
    @FXML
    private Button trou5;
    @FXML
    private Button trou6;
    @FXML
    private Button trou7;
    @FXML
    private Button trou8;
    @FXML
    private Button trou9;
    @FXML
    private Button trou10;
    @FXML
    private Button trou11;
    @FXML
    private Button grenierJoueur;
    @FXML
    private Button grenierIA;
    @FXML
    private Label tourIA;
    @FXML
    private Label tourJoueur;


    public ControlleurPlateau(Partie partie) {
        this.partie = partie;
        this.partie.ajouterObservateur(this);
    }

    public void jouer6() {
        this.partie.jouer(6);
        this.partie.prevenirObservateurs();
    }

    public void jouer7() {
        this.partie.jouer(7);
        this.partie.prevenirObservateurs();
    }

    public void jouer8() {
        this.partie.jouer(8);
        this.partie.prevenirObservateurs();
    }

    public void jouer9() {
        this.partie.jouer(9);
        this.partie.prevenirObservateurs();
    }

    public void jouer10() {
        this.partie.jouer(10);
        this.partie.prevenirObservateurs();
    }

    public void jouer11() {
        this.partie.jouer(11);
        this.partie.prevenirObservateurs();
    }

    @Override
    public void reagir() {
        tourIA.setDisable(false);
        tourJoueur.setDisable(true);
        trou0.setDisable(true);
        trou1.setDisable(true);
        trou2.setDisable(true);
        trou3.setDisable(true);
        trou4.setDisable(true);
        trou5.setDisable(true);
        grenierJoueur.setDisable(true);
        grenierIA.setDisable(true);
        trou6.setDisable(true);
        trou7.setDisable(true);
        trou8.setDisable(true);
        trou9.setDisable(true);
        trou10.setDisable(true);
        trou11.setDisable(true);
        trou6.setStyle("-fx-background-color : transparent");
        trou7.setStyle("-fx-background-color : transparent");
        trou8.setStyle("-fx-background-color : transparent");
        trou9.setStyle("-fx-background-color : transparent");
        trou10.setStyle("-fx-background-color : transparent");
        trou11.setStyle("-fx-background-color : transparent");
        if (!partie.isTermine()) {
            if (!partie.isTourDeLIA()) {
                tourIA.setDisable(true);
                tourJoueur.setDisable(false);
                for (Integer i : partie.testCoupsPossibles()) {
                    if (i == 6) {
                        trou6.setStyle("-fx-background-color : #C6FE87");
                        trou6.setDisable(false);
                    } else if (i == 7) {
                        trou7.setStyle("-fx-background-color : #C6FE87");
                        trou7.setDisable(false);
                    } else if (i == 8) {
                        trou8.setStyle("-fx-background-color : #C6FE87");
                        trou8.setDisable(false);
                    } else if (i == 9) {
                        trou9.setStyle("-fx-background-color : #C6FE87");
                        trou9.setDisable(false);
                    } else if (i == 10) {
                        trou10.setStyle("-fx-background-color : #C6FE87");
                        trou10.setDisable(false);
                    } else if (i == 11) {
                        trou11.setStyle("-fx-background-color : #C6FE87");
                        trou11.setDisable(false);
                    }
                }
            } else {
                Thread taskThread = new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        partie.jouer(partie.getIa().minimaxAvecElagageAlphaBeta(new Partie(partie), this.partie.getProfondeur()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(partie::prevenirObservateurs);
                });
                taskThread.start();
            }
        } else {
            System.out.println("FIN DU GAME");
        }
        trou0.setText("" + this.partie.getPlateau().getNbGraines(0));
        trou1.setText("" + this.partie.getPlateau().getNbGraines(1));
        trou2.setText("" + this.partie.getPlateau().getNbGraines(2));
        trou3.setText("" + this.partie.getPlateau().getNbGraines(3));
        trou4.setText("" + this.partie.getPlateau().getNbGraines(4));
        trou5.setText("" + this.partie.getPlateau().getNbGraines(5));
        trou6.setText("" + this.partie.getPlateau().getNbGraines(6));
        trou7.setText("" + this.partie.getPlateau().getNbGraines(7));
        trou8.setText("" + this.partie.getPlateau().getNbGraines(8));
        trou9.setText("" + this.partie.getPlateau().getNbGraines(9));
        trou10.setText("" + this.partie.getPlateau().getNbGraines(10));
        trou11.setText("" + this.partie.getPlateau().getNbGraines(11));
        grenierJoueur.setText("" + this.partie.getJoueur().getGrenier());
        grenierIA.setText("" + this.partie.getIa().getGrenier());
    }

}
