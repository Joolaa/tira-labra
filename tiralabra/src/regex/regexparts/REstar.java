
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
    
}
