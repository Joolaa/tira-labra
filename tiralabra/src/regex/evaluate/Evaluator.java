
package regex.evaluate;

import regex.*;
import regex.regexparts.*;
import regex.parser.*;

public class Evaluator {
    
    private String regexString;
    private Parser parser;
    private REsubexp regexTree;
    
    public Evaluator(String regexString, Parser parser) {
        this.regexString = regexString;
        this.parser = parser;
        this.regexTree = this.parser.parseString(this.regexString);
    }
    
    public Evaluator(Parser parser) {
        this.parser = parser;
        this.regexString = null;
        this.regexTree = null;
    }
    
    public boolean evaluateString(String s) {
        
        REsubexp r = regexTree;
        
        if(!s.isEmpty()) {
            for(int i = 0; i < s.length(); i++) {
                r = r.derivative(s.charAt(i));
            }
        }
        
        return r.matchesEmpty();
    }
    
    public void loadRegex(String regexString) {
        this.regexString = regexString;
        this.regexTree = parser.parseString(regexString);
    }
    
}
