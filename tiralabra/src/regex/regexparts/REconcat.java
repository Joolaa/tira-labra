
package regex.regexparts;

import regex.*;


public class REconcat implements REsubexpBinary {
    
    private REsubexp left;
    private REsubexp right;
    
    public REconcat(REsubexp left, REsubexp right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public REsubexp getLeft() {
        return left;
    }
    
    @Override
    public REsubexp getRight() {
        return right;
    }
    
    @Override
    public boolean matchesEmpty() {
        return getLeft().matchesEmpty() && getRight().matchesEmpty();
    }
    
}
