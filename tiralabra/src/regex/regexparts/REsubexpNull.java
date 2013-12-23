package regex.regexparts;


public class REsubexpNull implements REsubexpAtom{
    
    public REsubexpNull(){}
    
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
    
}
