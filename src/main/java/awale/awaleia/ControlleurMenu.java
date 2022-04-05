package awale.awaleia;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class ControlleurMenu extends Application implements Observateur {

    private final Partie partie;

    public ControlleurMenu(Partie partie) {
        this.partie = partie;
        partie.ajouterObservateur(this);
    }

    public void reglerProfondeur() {
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Réglage profondeur de recherche");
        dialog.setHeaderText("Donnez la profondeur voulue (Entre 1 et 10 de préférence)");
        dialog.setContentText("Profondeur :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> this.partie.setProfondeur(Integer.parseInt(s)));
    }

    public void quitterApplication() {
        Platform.exit();
    }

    public void lireLesRegles() {
        getHostServices().showDocument("https://fr.wikipedia.org/wiki/Awal%C3%A9#Les_r%C3%A8gles");
    }

    @Override
    public void start(Stage stage) {
        // Inutile, juste nécessaire pour ouvrir un lien.
    }

    @Override
    public void reagir() {
    }
}
