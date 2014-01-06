package regex.regexparts;

public class REwildCard implements REsubexpAtom {
    
    public REwildCard(){}
    
    @Override
    public REsubexp getLeft() {
        return null;
    }
    
    @Override
    public boolean matches(char c) {
        return true;
    }
    
    @Override
    public boolean matchesEmpty() {
        return false;
    }
    
    @Override
    public REsubexp derivative(char c) {
        return new REepsilon();
    }
    
    @Override
    public boolean matchesSome() {
        return true;
    }
    
    @Override
    public REsubexp reduce() {
        return this;
    }
    
    @Override
    public int height() {
        return 1;
    }
    
}
