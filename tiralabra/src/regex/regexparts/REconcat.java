
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
    
    @Override
    public REsubexp derivative(char c) {
        if(getLeft().matchesEmpty())
            return new REunion(new REconcat(getLeft().derivative(c),
                    getRight()), getRight().derivative(c));
        return new REconcat(getLeft().derivative(c), getRight());
    }
    
    @Override
    public boolean matchesSome() {
        
        return getLeft().matchesSome() && getRight().matchesSome();
    }
    
    @Override
    public REsubexp reduce() {
        
        REsubexp reducedLeft = getLeft().reduce();
        REsubexp reducedRight = getRight().reduce();
        
        if(!reducedLeft.matchesSome() || !reducedRight.matchesSome())
            return new REnull();
        
        return new REconcat(reducedLeft, reducedRight);
    }
    
    @Override
    public int height() {
        return Math.max(getLeft().height(), getRight().height()) + 1;
    }
    
}
