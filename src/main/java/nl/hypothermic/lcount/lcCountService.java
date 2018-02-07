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
	
	lcCountService(String content) {
		this.content = content;
	}
	
	private String content;
	
    @Override
    protected Task<HashMap<String, Integer>> createTask() {
        return new Task<HashMap<String, Integer>>() {
            @Override
            protected HashMap<String, Integer> call() throws Exception {
            	HashMap<String, Integer> x = new HashMap<String, Integer>();
            	// letters
            	x.put("ltrs", content.replaceAll("\\W", "").length());
            	// nums
            	x.put("nums", content.replaceAll("\\D", "").length());
            	// woorden via StringTokenizer lib
            	x.put("word", new StringTokenizer(content).countTokens());
            	// chars
            	x.put("char", content.length());
            	return x;
            }
        };
    }
}