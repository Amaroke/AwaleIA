package awale.awaleia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Awale extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Partie partie = new Partie();
        FXMLLoader fxmlLoader = new FXMLLoader(Awale.class.getResource("partie.fxml"));

        ControlleurPlateau controlleurPlateau = new ControlleurPlateau(partie);
        ControlleurMenu controlleurMenu = new ControlleurMenu(partie);

        fxmlLoader.setControllerFactory(ic -> {
            if (ic.equals(ControlleurPlateau.class)) return controlleurPlateau;
            else if (ic.equals(ControlleurMenu.class)) return controlleurMenu;
            return null;
        });

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Awale");
        stage.setScene(scene);
        stage.show();
        partie.prevenirObservateurs();
    }

    public static void main(String[] args) {
        launch();
    }
}