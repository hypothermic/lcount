package nl.hypothermic.lcount;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class lcInterfaceController implements Initializable {
	
	/****************************\
	* LetterCount by Hypothermic *
	* lcInterfaceController.java *
	* www.github.com/hypothermic *
	*-------= 15/02/2018 =-------*
	\****************************/
    
    @FXML private TextArea sourcearea;
    @FXML private MenuButton langbtn;
    @FXML private Button exitbtn;
    @FXML private ToggleButton autobtn;
    @FXML private ProgressBar ldbar;
    @FXML private TextField outletters;
    @FXML private TextField outnums;
    @FXML private TextField outwords;
    @FXML private TextField outchars;
    @FXML private Button calcbtn;
    @FXML private MenuItem langEN;
    @FXML private MenuItem langNL;
    
    private int lang = 0;
    private boolean auto = false;
	
	/* Taal mnu | exit btn
	 * Laad bar
	 * Letters
	 * Nummers
	 * Woorden
	 * Karakters
	 * Bereken btn
	 */
    
    /* Lang:
     * 0 = EN
     * 1 = NL
     */
    
    @FXML private void calc(ActionEvent event) {
    	final lcCountService srv = new lcCountService(sourcearea.getText());
        srv.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent workerStateEvent) {
        		ldbar.setProgress(0);
            	if (lang == 0) {
                	HashMap<String, Integer> h = srv.getValue();
                	outletters.setText("Letters: " + h.get("ltrs"));
                	outnums.setText("Numbers: " + h.get("nums"));
                	outwords.setText("Words: " + h.get("word"));
                	outchars.setText("Characters: " + h.get("char"));
            	} else {
                	HashMap<String, Integer> h = srv.getValue();
                	outletters.setText("Letters: " + h.get("ltrs"));
                	outnums.setText("Nummers: " + h.get("nums"));
                	outwords.setText("Woorden: " + h.get("word"));
                	outchars.setText("Karakters: " + h.get("char"));
            	}
            }
        });
        srv.setOnFailed(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent workerStateEvent) {
            	System.out.println("[ERROR] Exception: " + srv.getException().getStackTrace().toString());
            }
        });
        srv.restart();
        ldbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
    }
    
    @FXML private void exit(ActionEvent event) throws Exception {
    	Platform.exit();
    }
    
    @FXML private void setEN(ActionEvent event) {
    	lang = 0;
    	chUIlang(new String[] {"Language","Exit","Calculate","Letter Counter"});
    }
    
    @FXML private void setNL(ActionEvent event) {
    	lang = 1;
    	chUIlang(new String[] {"Taal","Afsluiten","Bereken","Letter Teller"});
    }
    
    @FXML private void chUIlang(String[] x) {
    	/* input string toewijzingen:
    	 * 0 = langbtn
    	 * 1 = exitbtn
    	 * 2 = calcbtn
    	 * 3 = stage titel
    	 */
    	langbtn.setText(x[0]);
    	exitbtn.setText(x[1]);
    	calcbtn.setText(x[2]);
    	((Stage) sourcearea.getScene().getWindow()).setTitle(x[3]);
    	calc(null);
    }
    
    @FXML private void autoswitch(ActionEvent event) {
    	auto ^= true;
    	if (auto) { 
    		autobtn.setTextFill(Color.RED);
    	} else { 
    		autobtn.setTextFill(Color.BLACK); 
    	}
    }
    
    @FXML private void refresh() {
    	if (auto) { 
    		calc(null);
    	}
    }
    
    @FXML private void popup() {
    	// todo
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}