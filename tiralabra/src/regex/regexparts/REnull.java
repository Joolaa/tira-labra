package regex.regexparts;


public class REnull implements REsubexpAtom{
    
    public REnull(){}
    
    @Override
    public boolean matches(char c) {
        return false;
    }
    
    @Override
    public REsubexp getLeft() {
        return null;
    }
    
    @Override
    public boolean matchesEmpty() {
        return false;
    }
    
    @Override
    public REsubexp derivative(char c) {
        return new REnull();
    }
    
    @Override
    public boolean matchesSome() {
        return false;
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
