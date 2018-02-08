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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    
	// initialiseer elementen
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
    @FXML private TextField incustom;
    @FXML private TextField outcustom;
    @FXML private CheckBox cbcasesens;
    
    private int lang = 0;
    private boolean auto = false;
    private String customregex;
    private boolean casesensitive;
	
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
    	// bereken knop

    	// initialiseer service en geef de geschreven tekst, custom regex en case sensitive status aan constructor door
    	final lcCountService srv = new lcCountService(sourcearea.getText(), customregex, casesensitive);
    	// als service slaagt
        srv.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent workerStateEvent) {
        		ldbar.setProgress(0);
        		// verkrijg hashmap van service
        		HashMap<String, Integer> h = srv.getValue();
            	if (lang == 0) { // als taal engels is
                	// zet gegevens in output fields
                	outletters.setText("Letters: " + h.get("ltrs"));
                	outnums.setText("Numbers: " + h.get("nums"));
                	outwords.setText("Words: " + h.get("word"));
                	outchars.setText("Characters: " + h.get("char"));
            	} else { // hetzelfde als boven maar als taal nl is
                	outletters.setText("Letters: " + h.get("ltrs"));
                	outnums.setText("Nummers: " + h.get("nums"));
                	outwords.setText("Woorden: " + h.get("word"));
                	outchars.setText("Karakters: " + h.get("char"));
            	}
            	// maakt niet uit of het EN of NL is.
            	if (h.get("cstm") != null) { outcustom.setText("" + h.get("cstm")); } else { outcustom.setText("0"); }
            }
        });
        // als er in de service een exception (of andere throwable) optreedt
        srv.setOnFailed(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent workerStateEvent) {
            	System.out.println("[ERROR] Exception: " + srv.getException().getStackTrace().toString());
            }
        });
        // start lcCountService
        srv.restart();
        ldbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
    }
    
    @FXML private void exit(ActionEvent event) throws Exception {
    	// exit knop
    	Platform.exit();
    }
    
    @FXML private void setEN(ActionEvent event) {
    	// dropdown keuze "English"
    	lang = 0;
    	chUIlang(new String[] {"Language","Exit","Calculate","Letter Counter","Custom rule","Case sensitive"});
    }
    
    @FXML private void setNL(ActionEvent event) {
    	// dropdown keuze "Nederlands"
    	lang = 1;
    	chUIlang(new String[] {"Taal","Afsluiten","Bereken","Letter Teller","Aangepaste regel","Hoofdlettergevoelig"});
    }
    
    @FXML private void chUIlang(String[] x) {
    	/* input string toewijzingen:
    	 * 0 = langbtn
    	 * 1 = exitbtn
    	 * 2 = calcbtn
    	 * 3 = stage titel
    	 * 4 = incustom
    	 * 5 = case sens cb
    	 */
    	langbtn.setText(x[0]);
    	exitbtn.setText(x[1]);
    	calcbtn.setText(x[2]);
    	((Stage) sourcearea.getScene().getWindow()).setTitle(x[3]);
    	incustom.setPromptText(x[4]);
    	cbcasesens.setText(x[5]);
    	calc(null);
    }
    
    @FXML private void autoswitch(ActionEvent event) {
    	// auto knop
    	auto ^= true;
    	if (auto) { 
    		autobtn.setTextFill(Color.RED);
    	} else { 
    		autobtn.setTextFill(Color.BLACK); 
    	}
    }
    
    @FXML private void refresh() {
    	// als auto knop aanstaat
    	if (auto) { 
    		// bereken
    		calc(null);
    	}
    }
    
    @FXML private void setRegex() { // wanneer er een letter bij wordt getypt in het "aangepast" veld
    	// als text in "aangepast" veld niet null is
    	if (incustom.getText() != null) {
    		// set customregex
    		customregex = incustom.getText();
    	}
    	if (auto) {
    		calc(null);
    	}
    }
    
    @FXML private void chcasesens() { // wanneer "case sens" checkbox state is veranderd
    	// verander boolean
    	casesensitive ^= true;
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		// lege functie die JavaFX nodig heeft
	}
}