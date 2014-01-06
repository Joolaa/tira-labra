
package regex.regexparts;

import regex.*;


public class REstar implements REsubexp{
    
    private REsubexp left;
    
    public REstar(REsubexp left) {
        this.left = left;
    }
    
    @Override
    public REsubexp getLeft() {
        return left;
    }
    
    @Override
    public boolean matchesEmpty() {
        return true;
    }
    
    @Override
    public REsubexp derivative(char c) {
        return new REconcat(getLeft().derivative(c), this);
    }
    
    @Override
    public boolean matchesSome() {
        return true;
    }
    
    @Override
    public REsubexp reduce() {
        
        REsubexp reducedLeft = getLeft().reduce();
        
        if(!reducedLeft.matchesSome())
            return new REepsilon();
        
        return new REstar(reducedLeft);
    }
    
    @Override
    public int height() {
        return getLeft().height() + 1;
    }
    
}
