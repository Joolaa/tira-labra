
package regex.regexparts;

import regex.*;


public class REunion implements REsubexpBinary{
    
    private REsubexp left;
    private REsubexp right;
    
    public REunion(REsubexp left, REsubexp right) {
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
        return getLeft().matchesEmpty() || getRight().matchesEmpty();
    }
    
    @Override
    public REsubexp derivative(char c) {
        return new REunion(getLeft().derivative(c), getRight().derivative(c));
    }
    
    @Override
    public boolean matchesSome() {
        return getLeft().matchesSome() || getRight().matchesSome();
    }
    
    @Override
    public REsubexp reduce() {
        
        REsubexp reducedLeft = getLeft().reduce();
        
        if(!reducedLeft.matchesSome())
            return getRight().reduce();
        
        REsubexp reducedRight = getRight().reduce();
        
        if(!reducedRight.matchesSome())
            return reducedLeft;
        
        return new REunion(reducedLeft, reducedRight);
    }
    
    @Override
    public int height() {
        
        return Math.max(getLeft().height(), getRight().height()) + 1;
    }
    
}
