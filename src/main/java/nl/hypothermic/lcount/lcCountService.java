package nl.hypothermic.lcount;

import java.util.HashMap;
import java.util.StringTokenizer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class lcCountService extends Service<HashMap<String, Integer>> {
	
	/****************************\
	* LetterCount by Hypothermic *
	*    lcCountService.java     *
	* www.github.com/hypothermic *
	*-------= 15/02/2018 =-------*
	\****************************/
	
	lcCountService(String content, String customregex, Boolean casesens) {
		this.content = content;
		this.customregex = customregex;
		this.casesens = casesens;
	}
	
	private String content;
	private String customregex;
	private boolean casesens;
	
    @Override
    protected Task<HashMap<String, Integer>> createTask() {
        return new Task<HashMap<String, Integer>>() {
            @Override
            protected HashMap<String, Integer> call() throws Exception {
            	// initialiseer hashmap
            	HashMap<String, Integer> x = new HashMap<String, Integer>();
            	// letters getal -> hashmap
        		x.put("ltrs", content.replaceAll("\\W", "").length());
        		// nums getal "
        		x.put("nums", content.replaceAll("\\D", "").length());
        		// woorden getal via StringTokenizer lib "
        		x.put("word", new StringTokenizer(content).countTokens());
        		// chars getal "
        		x.put("char", content.length());
        		// als "case sensitive" checkbox aangevinkt is
            	if (!casesens) {
            		// tel aangepaste regex
            		try { x.put("cstm", ((content.length() - content.replace(customregex, "").length()) / customregex.length())); } catch (Exception e) { /* customregex is leeg */ }
            	} else {
            		System.out.println("Debug");
            		// hetzelfde als hierboven maar dan case insensitive
            		try { x.put("cstm", ((content.length() - content.toLowerCase().replace(customregex.toLowerCase(), "").length()) / customregex.length())); } catch (Exception e) { /* customregex is leeg */ }
            	}
            	// service geeft hashmap terug aan vragende functie (calc())
            	return x;
            }
        };
    }
}