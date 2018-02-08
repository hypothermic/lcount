package nl.hypothermic.lcount;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class lcInterface extends Application {
	
	/****************************\
	* LetterCount by Hypothermic *
	*      lcInterface.java      *
	* www.github.com/hypothermic *
	*-------= 15/02/2018 =-------*
	\****************************/
	
	public static void main(String[] args) {
		// start runtime, launch applicatie
		launch(args);
	}

	@Override
	public void start(Stage xstage) throws IOException {
		// voor als applicatie van cli gelanceerd wordt
		xstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              System.out.println("Applicatie is afgesloten. ^C");
	          }
	      }); 
		// laad onze elementen uit de fxml config op een scene
        Parent root = FXMLLoader.load(getClass().getResource("lcInterfaceFX.fxml"));
        Scene scene = new Scene(root);
        // configureer de stage die we van JavaFX hebben gekregen en zet onze scene er op
        xstage.setTitle("Letter Counter");
        xstage.setMinHeight(305);
        xstage.setMinWidth(400);
        xstage.getIcons().add(new Image("https://m.hypothermic.nl/resources/lcount/logo.png")); // taskbar icoon ophalen van website. JavaFX is geweldig.
        xstage.setScene(scene);
        xstage.show();
	}
}
