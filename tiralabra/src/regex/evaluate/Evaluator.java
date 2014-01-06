
package regex.evaluate;

import regex.*;
import regex.regexparts.*;
import regex.parser.*;
import regex.validator.Validator;

public class Evaluator {
    
    private String regexString;
    private Parser parser;
    private REsubexp regexTree;
    private Validator validator;
    
    
    public Evaluator(String regexString, Parser parser, Validator validator) {
        this.regexString = regexString;
        this.parser = parser;
        this.regexTree = this.parser.parseString(this.regexString);
        this.validator = validator;
    }
    
    public Evaluator(String regexString, Parser parser) {
        this(regexString, parser, new Validator(regexString));
    }
    
    public Evaluator(Parser parser) {
        this(null, parser, new Validator());
    }
    
    public Evaluator() {
        this(new Parser());
    }
    
    /**
     * Returns true if the parameter string matches
     * the regex given to the object
     * @param s string to be evaluated
     * @return true if string matches, false otherwise
     */
    public boolean evaluateString(String s) {
        
        
        REsubexp r = regexTree;
        
        boolean reduceTree = false;
        
        if(s == null) {
            return false;
        }
        
        if(s.length() > r.height()) {
            reduceTree = true;
        }
        
        if(!s.isEmpty()) {
            for(int i = 0; i < s.length(); i++) {
                r = r.derivative(s.charAt(i));
                
                if(reduceTree) {
                    r = r.reduce();
                }
            }
        }
        
        return r.matchesEmpty();
    }
    
    /**
     * Set a new regex for the object
     * @param regexString The regex to be set
     */
    public void loadRegex(String regexString) {
        this.regexString = regexString;
        this.regexTree = parser.parseString(regexString);
        
        if(validator != null) {
            validator.setRegex(regexString);
            
            if(!validator.validate())
                throw new IllegalArgumentException("Regex syntax error");
        }
    }
    
}
