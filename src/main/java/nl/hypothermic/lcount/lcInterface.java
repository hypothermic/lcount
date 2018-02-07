package nl.hypothermic.lcount;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
		launch(args);
	}

	@Override
	public void start(Stage xstage) throws IOException {
		xstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              System.out.println("Applicatie is afgesloten. ^C");
	          }
	      }); 
        Parent root = FXMLLoader.load(getClass().getResource("lcInterfaceFX.fxml"));
        Scene scene = new Scene(root);
        xstage.setTitle("Letter Counter");
        xstage.setScene(scene);
        xstage.show();
	}
}
