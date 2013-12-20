
package regex;

public class REchar implements REsubexpAtom {
    
    private char matchingChar;
    
    public REchar(char matchingChar) {
        this.matchingChar = matchingChar;
    }
    
    @Override
    public REsubexp getLeft() {
        return null;
    }
    
    public boolean matches(char c) {
        if(c == matchingChar)
            return true;
        return false;
    }
    
}
