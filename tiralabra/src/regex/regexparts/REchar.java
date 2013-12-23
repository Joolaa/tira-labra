
package regex.regexparts;

import regex.*;

public class REchar implements REsubexpAtom {
    
    private char matchingChar;
    
    public REchar(char matchingChar) {
        this.matchingChar = matchingChar;
    }
    
    @Override
    public REsubexp getLeft() {
        return null;
    }
    
    @Override
    public boolean matches(char c) {
        if(c == matchingChar)
            return true;
        return false;
    }
    
    @Override
    public boolean matchesEmpty() {
        return false;
    }
    
}
