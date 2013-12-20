
package regex;


public class REstar implements REsubexp{
    
    private REsubexp left;
    
    public REstar(REsubexp left) {
        this.left = left;
    }
    
    @Override
    public REsubexp getLeft() {
        return left;
    }
    
}
