package br.com.arcom.powerbatch;

/*

import br.com.arcom.powerbatch.conf.Module;
import br.com.arcom.powerbatch.services.AgenteTimerService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Timer;
import java.util.TimerTask;

public final class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector( new Module() );

        AgenteTimerService service = injector.getInstance(AgenteTimerService.class);

        Timer timer = new Timer();
        timer.schedule((TimerTask) service, 0 , 5000 );
    }

}
*/

import br.com.arcom.powerbatch.modules.AgenteModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Pane rootLayout;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Agente - Java");
        this.primaryStage.setResizable(false);

        initRootLayout();
    }

    public void initRootLayout() {

        try {
            // DI - Guice:
            Injector injector = Guice.createInjector( new AgenteModule() );

            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();

            loader.setControllerFactory(injector::getInstance);

            loader.setLocation(Main.class.getResource("/fxml/AgenteView.fxml"));
            rootLayout = (Pane) loader.load();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        launch( args );
    }
}
