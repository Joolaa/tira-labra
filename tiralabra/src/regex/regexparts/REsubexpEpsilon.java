package regex.regexparts;

public class REsubexpEpsilon implements REsubexpAtom{
    
    public REsubexpEpsilon(){}
    
    @Override
    public REsubexp getLeft() {
        return null;
    }
    
    @Override
    public boolean matches(char c) {
        return false;
    }
    
    @Override
    public boolean matchesEmpty() {
        return true;
    }
    
}
